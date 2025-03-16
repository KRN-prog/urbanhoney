import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Article } from "../models/Article";
import { AddArticleRequest } from "../models/request/AddArticleRequest";
import { GetArticlesByGender } from "../models/request/GetArticlesByGender";

@Injectable({
    providedIn: 'root',
})
export class ArticlesService {

    private article = 'http://localhost:8080/urbanhoney/article/';
    private postNewArticle = 'http://localhost:8080/urbanhoney/article';
    private articles = 'http://localhost:8080/urbanhoney/articles';
    private articlesSlashed = 'http://localhost:8080/urbanhoney/articles/';
    private articlesBySubCategory = 'http://localhost:8080/urbanhoney/articles/subcategory/';
    private articlesByGender = 'http://localhost:8080/urbanhoney/articles/gender';

    constructor(private http: HttpClient) {}

    postArticle(articleData: AddArticleRequest, authHeaders: HttpHeaders): Observable<any> {
        return this.http.post<{"success": string}>(this.postNewArticle, articleData, { headers: authHeaders });
    }

    getArticleById(idArticle: string | null): Observable<any> {
        return this.http.get<{"success": Article}>(this.article + idArticle);
    }

    getArticleByGender(gender: string): Observable<any> {
        const params = new HttpParams().set('gender', gender);
        return this.http.get<{"success": Array<Article>}>(this.articlesByGender, { params });
    }

    getAllArticles(): Observable<any> {
        return this.http.get<{"success": Array<Article>}>(this.articles);
    }

    getArticlesByType(articleType: string | null): Observable<any> {
        return this.http.get<{"success": Array<Article>}>(this.articlesSlashed + articleType);
    }

    getArticlesBySubCategoryName(categoryName: string | null): Observable<any> {
        return this.http.get<{"success": Array<Article>}>(this.articlesBySubCategory + categoryName);
    }

    /*deleteArticleById(idArticle: number): Observable<any> {
        return this.http.delete<{"success": Array<Article>}>(this.articles + idArticle);
    }*/
}