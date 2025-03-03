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
      console.log('Tableau "card" initialisé dans le localStorage.');
    }
  }

  getCart(): ArticleCart[] {
    const item = localStorage.getItem(this.CART_KEY);
    return item ? JSON.parse(item) : [];
  }

  setCard(card: ArticleCart[]): void {
    localStorage.setItem(this.CART_KEY, JSON.stringify(card));
    this.cartSubject.next(card);
  }

  addToCard(item: ArticleCart): void {
    const card = this.getCart();
    card.push(item);
    this.setCard(card);
  }

  removeFromCard(itemId: number): void {
    const card = this.getCart();
    const updatedCard = card.filter((i: any) => i.articleId !== itemId);
    this.setCard(updatedCard);
  }

  removeOneFromCard(itemId: number): void {
    const card = this.getCart();
    const index = card.findIndex((i: any) => JSON.stringify(i.articleId) === JSON.stringify(itemId));
    if (index !== -1) {
      card.splice(index, 1);
      this.setCard(card);
    }
  }

  clearCard(): void {
    this.setCard([]);
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
    // Exemple de token JWT (remplacez par votre token réel)

    try {
      const decodedToken = jwtDecode(token); // Décoder le token
      console.log('Token décodé :', decodedToken);
    } catch (error) {
      console.error('Erreur lors du décodage du token :', error);
    }
  }
}