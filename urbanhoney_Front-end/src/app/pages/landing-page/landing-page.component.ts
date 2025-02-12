import { Component, OnInit } from '@angular/core';
import { CategorieService } from '../../core/services/categorie.service';
import { Category } from '../../core/models/Category';
import { CategorieSchemeComponent } from "../../components/categorie-scheme/categorie-scheme.component";
import { NgFor } from '@angular/common';

@Component({
  selector: 'app-landing-page',
  imports: [CategorieSchemeComponent, NgFor],
  templateUrl: './landing-page.component.html',
  styleUrl: './landing-page.component.scss'
})
export class LandingPageComponent implements OnInit {
  categories: Array<Category> = [];

  constructor(private categorieService: CategorieService) {}

  ngOnInit(): void {
    this.getCategories();
  }

  getCategories(): any {
    this.categorieService.getAllCategories().subscribe(
      (response: any) => {
        console.log(response);
        this.categories = response.success;
      }
    );
  }
}
