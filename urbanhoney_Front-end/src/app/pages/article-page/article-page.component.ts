import { Component, OnInit } from '@angular/core';
import { ArticlesService } from '../../core/services/articles.service';
import { Article } from '../../core/models/Article';
import { ActivatedRoute } from '@angular/router';
import { NgFor, NgIf } from '@angular/common';
import { HeaderComponent } from "../../components/header/header.component";
import { FormsModule } from '@angular/forms';
import { LocalStorageService } from '../../core/services/localStorage.service';

@Component({
  selector: 'app-article-page',
  standalone: true,
  imports: [NgFor, NgIf, HeaderComponent, FormsModule],
  templateUrl: './article-page.component.html',
  styleUrl: './article-page.component.scss'
})
export class ArticlePageComponent implements OnInit {
  article!: Article;
  mainImage!: string;
  errorAddToCard: boolean = false;
  errorMsgAddToCard: string = "Impossible d'ajouter cette article dans votre panier, veuillez ajouter une taille est une couleur !";

  cartData: any = {
    color: '',
    size: '',
  };

  constructor(private route: ActivatedRoute, private articlesService: ArticlesService, private localStorageService: LocalStorageService) {}

  ngOnInit(): void {
    this.getArticle();
  }

  getCurrentImage(image: any) {
    this.mainImage = image;
  }

  getArticle():any {
    this.articlesService.getArticleById(this.route.snapshot.paramMap.get('articleId')).subscribe({
      next: (response) => {
        this.article = response.success;
        this.mainImage = response.success.pictures[0];
        console.log(response.success);
        
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
      console.log(this.localStorageService.getCard());
      
    }else {
      this.errorAddToCard = true;
    }
  }
}
