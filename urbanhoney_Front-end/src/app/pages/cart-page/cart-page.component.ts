import { Component, OnInit } from '@angular/core';
import { LocalStorageService } from '../../core/services/localStorage.service';
import { NgFor, NgIf } from '@angular/common';
import { HeaderComponent } from "../../components/header/header.component";
import { ArticleCart } from '../../core/models/ArticleCart';
import { Router } from '@angular/router';
import { OrderService } from '../../core/services/order.service';
import { AddOrderRequest } from '../../core/models/request/AddOrderRequest';
import { HttpHeaders } from '@angular/common/http';
import { AuthService } from '../../core/services/auth.service';
import { User } from '../../core/models/User';

@Component({
  selector: 'app-cart-page',
  imports: [NgFor, NgIf, HeaderComponent],
  templateUrl: './cart-page.component.html',
  styleUrl: './cart-page.component.scss'
})
export class CartPageComponent implements OnInit {
  user!: User | undefined;
  cartContent: Array<ArticleCart> = [];
  uniqueCartContent: Array<ArticleCart> = [];
  totalPriceCalc!: number;
  totalPrice: Array<string> = [];
  emptyCart: boolean = false;
  emptyCartMsg!: string;
  loginOrRegisterModal!: boolean;
  orderModal!: boolean;

  constructor(private router: Router, public localStorageService: LocalStorageService, private orderService: OrderService, private authService: AuthService) {}

  ngOnInit(): void {
    this.localStorageService.cart$.subscribe((cart) => {
      this.cartContent = cart;
      console.log(cart);
      
      this.uniqueCartContent = this.showCartContent(this.localStorageService.getCart());

      if (this.cartContent.length > 0) {
        this.emptyCart = false;
        this.totalPriceCalc = 0;
        for (let i = 0; i < this.cartContent.length; i++) {
          this.totalPriceCalc += parseFloat(this.cartContent[i].price[0]);
          this.totalPriceCalc = Math.round(this.totalPriceCalc * 100) / 100;
        }
        this.totalPrice.push(this.cartContent[0].price[1], this.totalPriceCalc.toString());
      }else {
        this.emptyCart = true;
        this.emptyCartMsg = "Your cart is empty !";
      }
    });
  }

  showCartContent(cart: any): any {
    const uniqueArticles: ArticleCart[] = [];
    const countMap: any = {};

    cart.forEach((item: ArticleCart) => {
      const index = uniqueArticles.findIndex(obj => JSON.stringify(obj.articleId) === JSON.stringify(item.articleId)
      && JSON.stringify(obj.colors) === JSON.stringify(item.colors)
      && JSON.stringify(obj.size) === JSON.stringify(item.size));

      if (index !== -1) {
        countMap[index] += 1;
      } else {
        uniqueArticles.push(item);
        countMap[uniqueArticles.length - 1] = 1;
      }
    });

    return uniqueArticles.map((item, index) => ({ ...item, count: countMap[index] }));
  }

  deleteFromCart(itemId: number): void {
    this.localStorageService.removeFromCard(itemId);
  }

  removeOneFromCart(itemId: number): void {
    this.localStorageService.removeOneFromCard(itemId);
  }

  orderVerification(): boolean {
    if (!this.localStorageService.getUser()) {
      this.loginOrRegisterModal = true;
      return false;
    }

    this.orderModal = true
    return true;
  }

  order(): void {
    const headers: HttpHeaders = new HttpHeaders({
      Authorization: `Bearer ${this.localStorageService.getUser()}`,
    });

    this.authService.authUser(headers).subscribe(
      (response) => {
        this.user = response
        console.log(response);
        this.putOrder();
      }
    );
  }

  putOrder(): void {
    console.log(this.totalPrice);
    const headers: HttpHeaders = new HttpHeaders({
      Authorization: `Bearer ${this.localStorageService.getUser()}`,
    });

    let articlesIds = [];
    let articlesColor = [];
    let articlesSize = [];

    for (let i = 0; i < this.cartContent.length; i++) {
      articlesIds.push(this.cartContent[i].articleId);
      articlesColor.push(this.cartContent[i].colors[0]);
      articlesSize.push(this.cartContent[i].size[0]);
    }

    console.log(articlesIds);
    console.log(articlesColor);
    console.log(articlesSize);
    
    
    if (this.user !== undefined) {
      console.log("shii");
      
      const newOrderData: AddOrderRequest = {
        article_list: articlesIds,
        size: ["#ffffff", "#000000"],
        color: ["#ffffff", "#000000"],
        total: this.totalPrice,
        userId: this.user
      };

      this.orderService.postNewOrder(newOrderData, headers).subscribe({
        next: (response) => {
          console.log('Commande créée avec succès :', response);
        },
        error: (error) => {
          console.error('Erreur lors de la création de la commande :', error);
        },
      }); 
    }else{
      console.log("eee");
    }
  }

  registerRouting(): void {
    this.router.navigate(['/register']);
  }

  loginRouting(): void {
    this.router.navigate(['/login']);
  }

}
