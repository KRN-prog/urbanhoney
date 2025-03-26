import { Article } from "../Article";
import { User } from "../User";

export interface OrdersFromUserResponse {
    orderArticles: Array<Article>,
    orderId: number,
    total: string,
    userId: User
}