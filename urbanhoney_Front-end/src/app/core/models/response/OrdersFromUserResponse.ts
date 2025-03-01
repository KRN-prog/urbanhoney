import { Article } from "../Article";

export interface OrdersFromUserResponse {
    articlesEntities: Array<Article>,
    orderId: number,
    total: string
}