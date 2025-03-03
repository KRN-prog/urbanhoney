import { User } from "../User";

export interface AddOrderRequest {
    article_list: Array<number>,
    size: Array<string>,
    color: Array<string>,
    total: Array<string>,
    userId: User | null
}