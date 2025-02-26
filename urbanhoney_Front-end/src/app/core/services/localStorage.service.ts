import { Injectable } from '@angular/core';
import { ArticleCart } from '../models/ArticleCart';

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
    const itemParse = item ? JSON.parse(item) : [];
    const uniqueArticles: ArticleCart[] = [];
    const countMap: any = {};

    itemParse.forEach((item: ArticleCart) => {
      // Vérifier si l'objet existe déjà dans uniqueArticles
      const index = uniqueArticles.findIndex(obj => JSON.stringify(obj) === JSON.stringify(item));

      if (index !== -1) {
        // Si l'objet existe déjà, incrémenter son compteur
        countMap[index] += 1;
      } else {
        // Ajouter l'objet au tableau unique et initialiser son compteur
        uniqueArticles.push(item);
        countMap[uniqueArticles.length - 1] = 1;
      }
    });

    // Ajouter le compteur aux objets uniques
    return uniqueArticles.map((item, index) => ({ ...item, count: countMap[index] }));
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