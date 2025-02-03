import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { AddOrderRequest } from "../models/request/AddOrderRequest";
import { Observable } from "rxjs";

@Injectable({
    providedIn: 'root',
})
export class OrderService {

    private order = 'http://localhost:8080/urbanhoney/order/';
    private newOrder = 'http://localhost:8080/urbanhoney/order/new';
    private orders = 'http://localhost:8080/urbanhoney/orders/';

    constructor(private http: HttpClient) {}

    postNewOrder(newOrderData: AddOrderRequest, jwtToken: string): Observable<any> {
        const requestBody = {
            ...newOrderData,
            authorization: jwtToken,
        };

        return this.http.post<{"success": string}>(this.newOrder, requestBody);
    }

    getAllOrdersOfUser(): Observable<any> {
        return this.http.get<{"success": string}>(this.orders);
    }

    deleteOrderById(orderId: number): Observable<any> {
        return this.http.delete<{"success": string}>(this.order + orderId);
    }
}