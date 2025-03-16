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
  orderResponse: boolean = false;
  orderMessage!: string;

  constructor(private router: Router, public localStorageService: LocalStorageService, private orderService: OrderService, private authService: AuthService) {}

  ngOnInit(): void {
    this.localStorageService.cart$.subscribe((cart) => {
      this.totalPrice = [];
      
      this.cartContent = cart;
      
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

  deleteCart(): void {
    this.localStorageService.clearCart();
  }

  orderVerification(): boolean {
    window.scrollTo({ top: 0, behavior: 'smooth' });
    document.body.style.overflow = 'hidden';
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
        this.putOrder();
      }
    );
  }

  putOrder(): void {
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
    
    if (this.user !== undefined) {
      const newOrderData: AddOrderRequest = {
        article_list: articlesIds,
        size: articlesColor,
        color: articlesSize,
        total: this.totalPrice,
        userId: this.user
      };

      this.orderService.postNewOrder(newOrderData, headers).subscribe({
        next: (response) => {
          this.deleteCart();
          this.orderModal = false;
          this.orderResponse = true;
          this.orderMessage = `${response.success} please go to your profil to see it`
        },
        error: (error) => {
          this.orderResponse = true;
          this.orderMessage = `${error}+ please try again`
          console.error('Erreur lors de la création de la commande :', error);
        },
      }); 
    }
  }

  registerRouting(): void {
    this.router.navigate(['/register']);
  }

  loginRouting(): void {
    this.router.navigate(['/login']);
  }

  closeGetLoggedModal() {
    document.body.style.overflow = 'auto';
    this.loginOrRegisterModal = false;
  }

  closeOrderModal() {
    document.body.style.overflow = 'auto';
    this.orderModal = false;
  }

}
