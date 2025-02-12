import { Category } from "../Category";

export interface AddSubCategorieRequest {
    subCategorieName: string,
    categorieLinkId: Category,
    gender: string
}