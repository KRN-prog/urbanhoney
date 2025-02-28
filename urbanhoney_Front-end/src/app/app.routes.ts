import { Routes } from '@angular/router';
import { LandingPageComponent } from './pages/landing-page/landing-page.component';
import { ArticlePageComponent } from './pages/article-page/article-page.component';
import { ArticlesPageComponent } from './pages/articles-page/articles-page.component';
import { CartPageComponent } from './pages/cart-page/cart-page.component';
import { ConnexionPageComponent } from './pages/connexion-page/connexion-page.component';
import { InscriptionPageComponent } from './pages/inscription-page/inscription-page.component';
import { ProfilPageComponent } from './pages/profil-page/profil-page.component';

export const routes: Routes = [{
    path: '',
    component: LandingPageComponent
},
{
    path: 'article/:articleId',
    component: ArticlePageComponent
},
{
    path: 'articles/:articleType',
    component: ArticlesPageComponent
},
{
    path: 'cart',
    component: CartPageComponent
},
{
    path: 'connexion',
    component: ConnexionPageComponent
},
{
    path: 'inscription',
    component: InscriptionPageComponent
},
{
    path: 'profil',
    component: ProfilPageComponent
}];
