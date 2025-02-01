import { Categorie } from "../Categorie";

export interface AddSubCategorieRequest {
    subCategorieName: string,
    categorieLinkId: Categorie,
    gender: string
}