import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { AddCategorieRequest } from "../models/request/AddCategorieRequest";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { AddSubCategorieRequest } from "../models/request/AddSubCategorieRequest";
import { Category } from "../models/Category";
import { SubCategorie } from "../models/SubCategorie";

@Injectable({
    providedIn: 'root',
})
export class CategorieService {

    private newCategories = 'http://localhost:8080/urbanhoney/categorie/new';
    private getCategories = 'http://localhost:8080/urbanhoney/categorie/get/';
    private getAllCategorie = 'http://localhost:8080/urbanhoney/categorie/get';
    private subCategories = 'http://localhost:8080/urbanhoney/categorie/sub_categorie/';
    private getAllsubCategoriesByCategorieName = 'http://localhost:8080/urbanhoney/categorie/get/sub_categorie/categorie/';
    private newSubCategories = 'http://localhost:8080/urbanhoney/categorie/sub_categorie/new';
    private deleteSubCategories = 'http://localhost:8080/urbanhoney/categorie/sub_categorie/delete/';
    
    constructor(private http: HttpClient) {}

    newCategorie(newCategorieData: AddCategorieRequest, jwtToken: string): Observable<any> {
        const requestBody = {
            ...newCategorieData,
            authorization: jwtToken,
        };

        return this.http.post<{"success": string}>(this.newCategories, requestBody);
    }

    newSubCategorie(newSubCategorieData: AddSubCategorieRequest, jwtToken: string): Observable<any> {
        const requestBody = {
            ...newSubCategorieData,
            authorization: jwtToken,
        };

        return this.http.post<{"success": string}>(this.newSubCategories, requestBody);
    }

    getAllCategories(): Observable<any> {
        return this.http.get<{"success": Array<Category>}>(this.getAllCategorie);
    }

    getCategoriesByType(typeCategorie: string): Observable<any> {
        return this.http.get<{"success": Category}>(this.getCategories + typeCategorie);
    }

    getAllSubCategories(): Observable<any> {
        return this.http.get<{"success": Array<SubCategorie>}>(this.subCategories);
    }

    getAllSubCategoriesByCategoryName(categoryName: string | null): Observable<any> {
        return this.http.get<{"success": Array<SubCategorie>}>(this.getAllsubCategoriesByCategorieName + categoryName);
    }

    getSubCategoriesByName(subCategorieName: string): Observable<any> {
        return this.http.get<{"success": SubCategorie}>(this.subCategories + subCategorieName);
    }

    deleteSubCategoriesByName(subCategorieName: string): Observable<any> {
        return this.http.delete<{"success": string}>(this.deleteSubCategories + subCategorieName);
    }
}