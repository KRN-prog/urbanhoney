import { SubCategorie } from "./SubCategorie";

export interface Article {
    articleId: number,
    title: string,
    description: string,
    subCategoryLinkedId: SubCategorie,
    brand: string,
    price: string,
    color: Array<string>,
    size: Array<string>,
    pictures: Array<string>,
    composition: string,
    entretient: string
}