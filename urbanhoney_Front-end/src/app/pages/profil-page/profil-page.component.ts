import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalStorageService } from '../../core/services/localStorage.service';
import { AuthService } from '../../core/services/auth.service';
import { HttpHeaders } from '@angular/common/http';
import { HeaderComponent } from "../../components/header/header.component";
import { User } from '../../core/models/User';
import { NgIf } from '@angular/common';
import { OrderService } from '../../core/services/order.service';
import { OrderArticleSchemeComponent } from "../../components/order-article-scheme/order-article-scheme.component";
import { OrdersFromUserResponse } from '../../core/models/response/OrdersFromUserResponse';
import { FooterComponent } from '../../components/footer/footer.component';

@Component({
  selector: 'app-profil-page',
  imports: [HeaderComponent, NgIf, OrderArticleSchemeComponent, FooterComponent],
  templateUrl: './profil-page.component.html',
  styleUrl: './profil-page.component.scss'
})
export class ProfilPageComponent implements OnInit {
  pageLoading: boolean = true;
  userInfos!: User;
  ordersLoading!: boolean;
  ordersLoadingError!: boolean;
  ordersList!: Array<OrdersFromUserResponse>
  isDisabled = false;

  constructor(private router: Router, public localStorageService: LocalStorageService, private authService: AuthService, private orderService: OrderService) {}

  ngOnInit(): void {
    this.authUser();
  }

  authUser(): void {
    const userToken = this.localStorageService.getUser();
    
    if (userToken === null) {
      this.router.navigate(['/']);
    }

    const headers: HttpHeaders = new HttpHeaders({
      Authorization: `Bearer ${this.localStorageService.getUser()}`,
    });

    this.authService.authUser(headers).subscribe(
      (response) => {
        this.pageLoading = false;
        this.userInfos = response;
      },
      (error) => {
        this.localStorageService.removeUser();
        this.router.navigate(['/']);
      },
    );
    
  }


  ordersFromUser(): void {
    this.ordersLoading = true;
    const headers: HttpHeaders = new HttpHeaders({
      Authorization: `Bearer ${this.localStorageService.getUser()}`,
    });

    this.orderService.getAllOrdersOfUser(headers).subscribe(
      (response) => {
        this.isDisabled = true;
        this.ordersLoading = false;
        this.ordersLoadingError = false;
        this.ordersList = response;
      },
      (error) => {
        this.ordersLoadingError = true;
      }
    )
  }
}
