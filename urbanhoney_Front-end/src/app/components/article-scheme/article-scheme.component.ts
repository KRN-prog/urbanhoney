import { Component, Input } from '@angular/core';
import { Article } from '../../core/models/Article';
import { SafeUrlPipe } from '../../pipes/safe-url.pipe';
import { NgFor } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-article-scheme',
  imports: [SafeUrlPipe, NgFor],
  templateUrl: './article-scheme.component.html',
  styleUrl: './article-scheme.component.scss'
})
export class ArticleSchemeComponent {
  @Input() article!: Article;

  constructor(private router: Router) {}

  goToArticle(articleId: number): void {
    this.router.navigate(['/article/'+articleId]);
  }
}
