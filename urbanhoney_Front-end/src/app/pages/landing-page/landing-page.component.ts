import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { CategorieService } from '../../core/services/categorie.service';
import { Category } from '../../core/models/Category';
import { CategorieSchemeComponent } from "../../components/categorie-scheme/categorie-scheme.component";
import { NgFor } from '@angular/common';
import { ArticlesService } from '../../core/services/articles.service';
import { Article } from '../../core/models/Article';
import { ArticleSchemeComponent } from "../../components/article-scheme/article-scheme.component";
import { HeaderComponent } from "../../components/header/header.component";
import { FooterComponent } from '../../components/footer/footer.component';
import { GetArticlesByGender } from '../../core/models/request/GetArticlesByGender';

@Component({
  selector: 'app-landing-page',
  imports: [CategorieSchemeComponent, NgFor, ArticleSchemeComponent, HeaderComponent, FooterComponent],
  templateUrl: './landing-page.component.html',
  styleUrl: './landing-page.component.scss'
})
export class LandingPageComponent implements OnInit {
  @ViewChild('videoPlayer') videoPlayer!: ElementRef<HTMLVideoElement>;
  categories: Array<Category> = [];
  maleArticles: Array<Article> = [];
  femaleArticles: Array<Article> = [];
  articles: Array<Article> = [];
  gender!: GetArticlesByGender;

  constructor(private categorieService: CategorieService, private articlesService: ArticlesService) {}

  ngOnInit(): void {
    this.getCategories();
    this.getAllArticles();
    this.loadArticlesByGender("m", this.maleArticles);
    this.loadArticlesByGender("f", this.femaleArticles);
  }

  ngAfterViewInit() {
    this.videoPlayer.nativeElement.muted = true;

    this.videoPlayer.nativeElement.play().catch((error) => {
      console.error('La lecture automatique a échoué :', error);
    });
  }
  
  getCategories(): void {
    this.categorieService.getAllCategories().subscribe(
      (response: any) => {
        this.categories = response.success;
      }
    );
  }

  getAllArticles(): void {
    this.articlesService.getAllArticles().subscribe(
      (response: any) => {
        this.articles = response.success;
      }
    );
  }

  loadArticlesByGender(gender: string, genderToSave: Array<Article>): void {
    this.articlesService.getArticleByGender(gender).subscribe(
      (response: any) => {
        for (let i = 0; i < response.success.length; i++) {
          genderToSave.push(response.success[i]);
          console.log(genderToSave);
          
        }
      },
      (error: any) => {
        console.log(error);
      }
    );
  }
}
