import { SubCategorie } from "./SubCategorie";

export interface ArticleCart {
    articleId: number,
    title: string,
    description: string,
    sub_category_linked_id: SubCategorie,
    brand: string,
    price: string,
    colors: Array<string>,
    size: Array<string>,
    pictures: Array<string>,
    composition: string,
    entretient: string,
    count: number
}