import { Categorie } from "./Categorie";

export interface SubCategorie {
    sub_categorie_id: number,
    sub_categorie_name: string,
    categorie_linked_id: Categorie,
    gender: string
}