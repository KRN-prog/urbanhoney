import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { AddOrderRequest } from "../models/request/AddOrderRequest";
import { Observable } from "rxjs";
import { Order } from "../models/Order";

@Injectable({
    providedIn: 'root',
})
export class OrderService {

    private order = 'http://localhost:8080/urbanhoney/order/';
    private newOrder = 'http://localhost:8080/urbanhoney/order/new';
    private orders = 'http://localhost:8080/urbanhoney/orders';

    constructor(private http: HttpClient) {}

    postNewOrder(newOrderData: AddOrderRequest, authHeaders: HttpHeaders): Observable<{ success: string }> {
        return this.http.post<{"success": string}>(this.newOrder, newOrderData, { headers: authHeaders });
    }

    getAllOrdersOfUser(authHeaders: HttpHeaders): Observable<any> {
        return this.http.get<Array<Order>>(this.orders, { headers: authHeaders });
    }

    deleteOrderById(orderId: number): Observable<{ success: string }> {
        return this.http.delete<{"success": string}>(this.order + orderId);
    }
}