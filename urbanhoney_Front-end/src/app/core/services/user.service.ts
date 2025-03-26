import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { User } from "../models/User";

@Injectable({
    providedIn: 'root',
})
export class UserService {

    private getUser = 'http://localhost:8080/urbanhoney/user/';
    private getUsers = 'http://localhost:8080/urbanhoney/user';
    private revokeAdmin = 'http://localhost:8080/urbanhoney/user/revoke/';
    private setAdmin = 'http://localhost:8080/urbanhoney/user/set/';

    constructor(private http: HttpClient) {}

    getUserById(userId: number): Observable<{success: string}> {
        return this.http.get<{"success": string}>(this.getUser + userId);
    }

    getAllUsers(authHeaders: HttpHeaders): Observable<{success: User[]}> {
        return this.http.get<{"success": Array<User>}>(this.getUsers, { headers: authHeaders });
    }

    revokeAdminStatus(userId: number, authHeaders: HttpHeaders): Observable<{success: string}> {
        return this.http.put<{"success": string}>(this.revokeAdmin + userId, {}, { headers: authHeaders });
    }

    setAdminStatus(userId: number, authHeaders: HttpHeaders): Observable<{success: string}> {
        return this.http.put<{"success": string}>(this.setAdmin + userId, {}, { headers: authHeaders });
    }
}