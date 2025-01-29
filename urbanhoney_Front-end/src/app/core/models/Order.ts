import { Article } from "./Article"

export interface Order {
    order_id: number,
    article_id: Array<Article>
    total: string,
    user_id: object
}