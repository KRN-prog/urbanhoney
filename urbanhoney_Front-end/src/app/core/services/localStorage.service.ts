import { Injectable } from '@angular/core';
import { ArticleCart } from '../models/ArticleCart';
import { BehaviorSubject } from 'rxjs';
import { User } from '../models/User';

@Injectable({
  providedIn: 'root',
})
export class LocalStorageService {
  private readonly CARD_KEY = 'card';
  private readonly USER_KEY = 'user';
  private cartSubject = new BehaviorSubject<any[]>(this.getCart());

  cart$ = this.cartSubject.asObservable();

  constructor() {
    this.initializeLocalStorage();
  }

  private initializeLocalStorage(): void {
    if (!localStorage.getItem(this.CARD_KEY)) {
      localStorage.setItem(this.CARD_KEY, JSON.stringify([]));
      console.log('Tableau "card" initialisé dans le localStorage.');
    }
  }

  getCart(): any[] {
    const item = localStorage.getItem(this.CARD_KEY);
    return item ? JSON.parse(item) : [];
  }

  setCard(card: any[]): void {
    localStorage.setItem(this.CARD_KEY, JSON.stringify(card));
    this.cartSubject.next(card);
  }

  addToCard(item: any): void {
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


  getUser(): User | null {
    const item = localStorage.getItem(this.USER_KEY);
    return item ? JSON.parse(item) : null;
  }
}