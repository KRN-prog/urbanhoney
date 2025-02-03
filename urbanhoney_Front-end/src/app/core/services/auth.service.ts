import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { AuthRequest } from "../models/request/AuthRequest";
import { User } from "../models/User";

@Injectable({
    providedIn: 'root',
})
export class AuthService {

    private login = 'http://localhost:8080/urbanhoney/auth/login';
    private register = 'http://localhost:8080/urbanhoney/auth/register';
    private authMe = 'http://localhost:8080/urbanhoney/auth/me';

    constructor(private http: HttpClient) {}

    headers: HttpHeaders = new HttpHeaders({
        'Content-Type': 'application/json',
    });

    loginUser(loginData: AuthRequest): Observable<any> {
        return this.http.post<{"success": any}>(this.login, loginData);
    }

    registerUser(registerData: User): Observable<any> {
        return this.http.post<{"success": User}>(this.register, registerData);
    }

    authUser(authHeaders: HttpHeaders): Observable<any> {
        return this.http.get<any>(this.authMe, { headers: authHeaders });
    }
}