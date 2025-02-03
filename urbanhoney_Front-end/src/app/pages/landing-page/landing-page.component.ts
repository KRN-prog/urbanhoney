import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ArticlesService } from '../../core/services/articles.service';

@Component({
  selector: 'app-landing-page',
  imports: [],
  templateUrl: './landing-page.component.html',
  styleUrl: './landing-page.component.scss'
})
export class LandingPageComponent {

  constructor() {}

  ngOnInit(): void {
  }
}
