import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from "../../components/header/header.component";
import { LocalStorageService } from '../../core/services/localStorage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-inscription-page',
  imports: [HeaderComponent],
  templateUrl: './inscription-page.component.html',
  styleUrl: './inscription-page.component.scss'
})
export class InscriptionPageComponent implements OnInit {

  constructor(private router: Router, public localStorageService: LocalStorageService) {}
  
  ngOnInit(): void {
    if (this.localStorageService.getUser() != null) {
      this.homeRouting();
    }
  }

  homeRouting(): void {
    this.router.navigate(['/']);
  }
}
