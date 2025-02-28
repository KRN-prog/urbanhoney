import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalStorageService } from '../../core/services/localStorage.service';
import { AuthService } from '../../core/services/auth.service';
import { HttpHeaders } from '@angular/common/http';
import { HeaderComponent } from "../../components/header/header.component";
import { User } from '../../core/models/User';
import { NgIf } from '@angular/common';
import { OrderService } from '../../core/services/order.service';

@Component({
  selector: 'app-profil-page',
  imports: [HeaderComponent, NgIf],
  templateUrl: './profil-page.component.html',
  styleUrl: './profil-page.component.scss'
})
export class ProfilPageComponent implements OnInit {
  loading: boolean = true;
  isDisabled = false;
  userInfos!: User;

  constructor(private router: Router, public localStorageService: LocalStorageService, private authService: AuthService, private orderService: OrderService) {}

  ngOnInit(): void {
    this.authUser();
  }

  authUser(): void {
    console.log(this.localStorageService.getUser());
    
    const userToken = this.localStorageService.getUser();
    
    if (userToken === null) {
      this.router.navigate(['/']);
    }

    const headers: HttpHeaders = new HttpHeaders({
      Authorization: `Bearer ${this.localStorageService.getUser()}`,
    });

    this.authService.authUser(headers).subscribe(
      (response) => {
        this.loading = false;
        this.userInfos = response;
        console.log(response);
      },
      (error) => {
        this.localStorageService.removeUser();
        this.router.navigate(['/']);
      },
    );
    
  }


  ordersFromUser(): void {
    const headers: HttpHeaders = new HttpHeaders({
      Authorization: `Bearer ${this.localStorageService.getUser()}`,
    });

    this.orderService.getAllOrdersOfUser(headers).subscribe(
      (response) => {
        this.isDisabled = true;
        console.log(response);
      },
      (error) => {
        console.log(error);
        
      }
    )
  }
}
