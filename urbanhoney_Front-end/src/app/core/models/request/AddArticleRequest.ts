import { SubCategorie } from "../SubCategorie";

export interface AddArticleRequest {
    title: string,
    description: string,
    subCategorie: SubCategorie,
    brand: string,
    price: Array<number | string>,
    color: Array<string>,
    size: Array<string>,
    pictures: Array<string>,
    composition: string,
    entretient: string
}