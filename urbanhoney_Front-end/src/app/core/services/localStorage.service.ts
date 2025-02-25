import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class LocalStorageService {
  private readonly CARD_KEY = 'card';

  constructor() {
    this.initializeLocalStorage();
  }

  private initializeLocalStorage(): void {
    if (!localStorage.getItem(this.CARD_KEY)) {
      localStorage.setItem(this.CARD_KEY, JSON.stringify([]));
      console.log('Tableau "card" initialisé dans le localStorage.');
    }
  }

  getCard(): any[] {
    const item = localStorage.getItem(this.CARD_KEY);
    return item ? JSON.parse(item) : [];
  }

  setCard(card: any[]): void {
    localStorage.setItem(this.CARD_KEY, JSON.stringify(card));
  }

  addToCard(item: any): void {
    const card = this.getCard();
    card.push(item);
    this.setCard(card);
  }

  removeFromCard(item: any): void {
    const card = this.getCard();
    const updatedCard = card.filter((i: any) => i !== item);
    this.setCard(updatedCard);
  }

  clearCard(): void {
    this.setCard([]);
  }
}