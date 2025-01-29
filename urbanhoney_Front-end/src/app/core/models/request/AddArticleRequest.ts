import { SubCategorie } from "../subCategorie";

export interface AddArticleRequest {
    title: string,
    description: string,
    subCategorie: SubCategorie,
    brand: string,
    price: string,
    color: Array<string>,
    size: Array<string>,
    pictures: Array<string>,
    composition: string,
    entretient: string
}