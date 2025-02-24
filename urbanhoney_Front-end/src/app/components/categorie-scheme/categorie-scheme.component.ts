import { Component, Input } from '@angular/core';
import { Category } from '../../core/models/Category';
import { Router } from '@angular/router';

@Component({
  selector: 'app-categorie-scheme',
  imports: [],
  templateUrl: './categorie-scheme.component.html',
  styleUrl: './categorie-scheme.component.scss'
})
export class CategorieSchemeComponent {
  @Input() category!: Category;

  constructor(private router: Router) {}

  goToArtilesByType(articleType: string): void {
    this.router.navigate(['/articles/'+articleType]);
  }
}
