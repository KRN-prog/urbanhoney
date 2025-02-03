import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable({
    providedIn: 'root',
})
export class UserService {

    private getUser = 'http://localhost:8080/urbanhoney/user/';

    constructor(private http: HttpClient) {}

    getUserById(userId: number): Observable<any> {
        return this.http.get<{"success": string}>(this.getUser + userId);
    }
}