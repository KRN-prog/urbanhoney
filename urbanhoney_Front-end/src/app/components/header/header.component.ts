import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalStorageService } from '../../core/services/localStorage.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {

  constructor(private router: Router, public localStorageService: LocalStorageService) {}

  homeRouting(): void {
    this.router.navigate(['/']);
  }

  cardRouting(): void {
    this.router.navigate(['/cart']);
  }
}
