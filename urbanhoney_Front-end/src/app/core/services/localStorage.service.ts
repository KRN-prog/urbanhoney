import { Injectable } from '@angular/core';
import { ArticleCart } from '../models/ArticleCart';
import { BehaviorSubject } from 'rxjs';
import { User } from '../models/User';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root',
})
export class LocalStorageService {
  private readonly CART_KEY = 'card';
  private readonly USER_KEY = 'user';
  private cartSubject = new BehaviorSubject<any[]>(this.getCart());

  cart$ = this.cartSubject.asObservable();

  constructor() {
    this.initializeLocalStorage();
  }

  private initializeLocalStorage(): void {
    if (!localStorage.getItem(this.CART_KEY)) {
      localStorage.setItem(this.CART_KEY, JSON.stringify([]));
    }
  }

  getCart(): ArticleCart[] {
    const item = localStorage.getItem(this.CART_KEY);
    return item ? JSON.parse(item) : [];
  }

  setCart(card: ArticleCart[]): void {
    localStorage.setItem(this.CART_KEY, JSON.stringify(card));
    this.cartSubject.next(card);
  }

  addToCart(item: ArticleCart): void {
    const card = this.getCart();
    card.push(item);
    this.setCart(card);
  }

  removeFromCart(itemId: number): void {
    const card = this.getCart();
    const updatedCard = card.filter((i: any) => i.articleId !== itemId);
    this.setCart(updatedCard);
  }

  removeOneFromCart(itemId: number): void {
    const card = this.getCart();
    const index = card.findIndex((i: any) => JSON.stringify(i.articleId) === JSON.stringify(itemId));
    if (index !== -1) {
      card.splice(index, 1);
      this.setCart(card);
    }
  }

  clearCart(): void {
    this.setCart([]);
  }


  getUser(): string {
    const item = localStorage.getItem(this.USER_KEY);
    return item ? JSON.parse(item) : null;
  }

  setUser(token: string): void {
    if (!localStorage.getItem(this.USER_KEY)) {
      localStorage.setItem(this.USER_KEY, JSON.stringify(token));
    }
  }

  removeUser(): void {
    localStorage.removeItem(this.USER_KEY);
  }

  decodeToken(token: string) {
    try {
      const decodedToken = jwtDecode(token);
    } catch (error) {
      console.error('Erreur lors du décodage du token :', error);
    }
  }
}