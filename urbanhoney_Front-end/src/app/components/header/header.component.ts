import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalStorageService } from '../../core/services/localStorage.service';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [NgIf],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {

  constructor(private router: Router, public localStorageService: LocalStorageService) {}

  homeRouting(): void {
    this.router.navigate(['/']);
  }

  loginRouting(): void {
    this.router.navigate(['/login']);
  }

  registerRouting(): void {
    this.router.navigate(['/register']);
  }

  profilRouting(): void {
    this.router.navigate(['/profil']);
  }

  cardRouting(): void {
    this.router.navigate(['/cart']);
  }
}
