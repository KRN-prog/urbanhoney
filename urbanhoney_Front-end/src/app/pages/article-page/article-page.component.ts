import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ArticlesService } from '../../core/services/articles.service';
import { Article } from '../../core/models/Article';
import { ActivatedRoute } from '@angular/router';
import { NgFor, NgIf } from '@angular/common';
import { HeaderComponent } from "../../components/header/header.component";
import { FooterComponent } from "../../components/footer/footer.component";
import { FormsModule } from '@angular/forms';
import { LocalStorageService } from '../../core/services/localStorage.service';

@Component({
  selector: 'app-article-page',
  standalone: true,
  imports: [NgFor, NgIf, HeaderComponent, FormsModule, FooterComponent],
  templateUrl: './article-page.component.html',
  styleUrl: './article-page.component.scss'
})
export class ArticlePageComponent implements OnInit {
  article!: Article;
  mainImage!: string;
  successAddToCard: boolean = false;
  errorAddToCard: boolean = false;
  AddToCardMsg: string = "Article added to your cart.";
  errorMsgAddToCard: string = "Impossible to add this item to your cart. Please select a size and a color !";
  isColorSelected!: string;
  isSizeSelected!: string;

  cartData: any = {
    color: '',
    size: '',
  };

  constructor(private route: ActivatedRoute, private articlesService: ArticlesService, private localStorageService: LocalStorageService) {}

  ngOnInit(): void {
    this.getArticle();
  }

  toggleSelectColor(color: string) {
    this.isColorSelected = color;
  }

  toggleSelectSize(size: string) {
    this.isSizeSelected = size;
    
  }

  getCurrentImage(image: any) {
    this.mainImage = image;
  }

  getArticle():any {
    this.articlesService.getArticleById(this.route.snapshot.paramMap.get('articleId')).subscribe({
      next: (response) => {
        this.article = response.success;
        this.mainImage = response.success.pictures[0];
      },
      error: (err) => {
        console.error('Erreur lors du chargement du produit :', err);
      }
    });
  }

  addToCart(): void {
    if (this.cartData.color != "" && this.cartData.size != "") {
      const article: any = {...this.article}
      article.colors = [this.cartData.color];
      article.size = [this.cartData.size]; 
      this.errorAddToCard = false;
      this.localStorageService.addToCard(article);
      this.successAddToCard = true;
    }else {
      this.errorAddToCard = true;
    }
  }
}
