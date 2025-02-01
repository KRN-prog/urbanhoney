import { SubCategorie } from "./SubCategorie";

export interface Article {
    article_id: number,
    title: string,
    description: string,
    sub_categorie_id: SubCategorie,
    brand: string,
    price: string,
    colors: Array<string>,
    size: Array<string>,
    pictures: Array<string>,
    composition: string,
    entretient: string
}