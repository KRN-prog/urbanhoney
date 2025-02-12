import { Component, Input } from '@angular/core';
import { Category } from '../../core/models/Category';

@Component({
  selector: 'app-categorie-scheme',
  imports: [],
  templateUrl: './categorie-scheme.component.html',
  styleUrl: './categorie-scheme.component.scss'
})
export class CategorieSchemeComponent {
  @Input() category!: Category;
}
