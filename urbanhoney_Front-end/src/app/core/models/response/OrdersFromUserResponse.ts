import { Article } from "../Article";
import { User } from "../User";

export interface OrdersFromUserResponse {
    orderArticles: Array<any>,
    orderId: number,
    total: string,
    userId: User
}