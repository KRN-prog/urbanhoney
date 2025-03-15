import { SubCategorie } from "../SubCategorie";

export interface AddArticleRequest {
    title: string,
    description: string,
    sub_category: SubCategorie,
    brand: string,
    price: Array<number | string>,
    color: Array<string>,
    size: Array<string>,
    pictures: Array<string>,
    composition: string,
    entretien: string
}