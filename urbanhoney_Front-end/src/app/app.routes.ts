import { Routes } from '@angular/router';
import { LandingPageComponent } from './pages/landing-page/landing-page.component';
import { ArticlePageComponent } from './pages/article-page/article-page.component';

export const routes: Routes = [{
    path: '',
    component: LandingPageComponent
},
{
    path: 'article/:articleId',
    component: ArticlePageComponent
}];
