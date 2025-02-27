import { Component, OnInit } from '@angular/core';
import { LocalStorageService } from '../../core/services/localStorage.service';
import { NgFor, NgIf } from '@angular/common';
import { HeaderComponent } from "../../components/header/header.component";
import { ArticleCart } from '../../core/models/ArticleCart';

@Component({
  selector: 'app-cart-page',
  imports: [NgFor, NgIf, HeaderComponent],
  templateUrl: './cart-page.component.html',
  styleUrl: './cart-page.component.scss'
})
export class CartPageComponent implements OnInit {
  cartContent: Array<ArticleCart> = [];
  uniqueCartContent: Array<ArticleCart> = [];
  totalPrice!: number;
  emptyCart: boolean = false;
  emptyCartMsg!: string;

  constructor(public localStorageService: LocalStorageService) {}

  ngOnInit(): void {
    console.log(this.localStorageService.getCart());

    this.localStorageService.cart$.subscribe((cart) => {
      this.cartContent = cart;
      this.uniqueCartContent = this.showCartContent(this.localStorageService.getCart());

      if (this.cartContent.length > 0) {
        this.emptyCart = false;
        this.totalPrice = 0;
        for (let i = 0; i < this.cartContent.length; i++) {
          this.totalPrice += parseFloat(this.cartContent[i].price[0]);
          this.totalPrice = Math.round(this.totalPrice * 100) / 100;
        } 
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

  deleteFromCart(itemId: number): any {
    this.localStorageService.removeFromCard(itemId);
  }

  removeOneFromCart(itemId: number): any {
    this.localStorageService.removeOneFromCard(itemId);
  }

}
