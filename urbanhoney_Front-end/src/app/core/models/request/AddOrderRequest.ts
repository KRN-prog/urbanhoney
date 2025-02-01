import { Article } from "../Article";
import { User } from "../User";

export interface AddOrderRequest {
    articleList: Array<Article>,
    total: Array<string>,
    userId: User
}