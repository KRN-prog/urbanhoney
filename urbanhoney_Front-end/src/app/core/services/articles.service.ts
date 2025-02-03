import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Article } from "../models/Article";

@Injectable({
    providedIn: 'root',
})
export class ArticlesService {

    private article = 'http://localhost:8080/urbanhoney/article/';
    private articles = 'http://localhost:8080/urbanhoney/articles/';

    constructor(private http: HttpClient) {}

    postArticle(articleData: Article, jwtToken: string): Observable<any> {
        const requestBody = {
            ...articleData,
            authorization: jwtToken,
        };

        return this.http.post<{"success": string}>(this.article, requestBody);
    }

    getArticleById(idArticle: number): Observable<any> {
        return this.http.get<{"success": Article}>(this.article + idArticle);
    }

    getAllArticles(): Observable<any> {
        return this.http.get<{"success": Array<Article>}>(this.articles);
    }

    getArticlesByType(articleType: string): Observable<any> {
        return this.http.get<{"success": Array<Article>}>(this.articles + articleType);
    }

    /*deleteArticleById(idArticle: number): Observable<any> {
        return this.http.delete<{"success": Array<Article>}>(this.articles + idArticle);
    }*/
}