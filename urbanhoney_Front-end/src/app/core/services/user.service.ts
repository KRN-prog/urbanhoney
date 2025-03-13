import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable({
    providedIn: 'root',
})
export class UserService {

    private getUser = 'http://localhost:8080/urbanhoney/user/';
    private getUsers = 'http://localhost:8080/urbanhoney/user';
    private revokeAdmin = 'http://localhost:8080/urbanhoney/user/revoke/';
    private setAdmin = 'http://localhost:8080/urbanhoney/user/set/';

    constructor(private http: HttpClient) {}

    getUserById(userId: number): Observable<any> {
        return this.http.get<{"success": string}>(this.getUser + userId);
    }

    getAllUsers(authHeaders: HttpHeaders): Observable<any> {
        return this.http.get<{"success": any}>(this.getUsers, { headers: authHeaders });
    }

    revokeAdminStatus(userId: number, authHeaders: HttpHeaders): Observable<any> {
        return this.http.put<{"success": any}>(this.revokeAdmin + userId, {}, { headers: authHeaders });
    }

    setAdminStatus(userId: number, authHeaders: HttpHeaders): Observable<any> {
        return this.http.put<{"success": any}>(this.setAdmin + userId, {}, { headers: authHeaders });
    }
}