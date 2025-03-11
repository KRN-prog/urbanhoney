import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from "../../components/header/header.component";
import { User } from '../../core/models/User';
import { Router } from '@angular/router';
import { LocalStorageService } from '../../core/services/localStorage.service';
import { HttpHeaders } from '@angular/common/http';
import { AuthService } from '../../core/services/auth.service';
import { NgFor, NgIf } from '@angular/common';
import { ArticlesService } from '../../core/services/articles.service';
import { Article } from '../../core/models/Article';
import { UserService } from '../../core/services/user.service';

@Component({
  selector: 'app-admin-page',
  imports: [HeaderComponent, NgIf, NgFor],
  templateUrl: './admin-page.component.html',
  styleUrl: './admin-page.component.scss'
})
export class AdminPageComponent implements OnInit {
  pageLoading: boolean = true;
  userInfos!: User;
  articles: Array<Article> = [];
  users: Array<User> = [];

  constructor(private router: Router, public localStorageService: LocalStorageService, private authService: AuthService, private articlesService: ArticlesService, private userService: UserService) {}

  ngOnInit(): void {
    this.authUser();
  }

  authUser() {
    const userToken = this.localStorageService.getUser();

    if (userToken === null) {
      this.router.navigate(['/']);
    }

    const headers: HttpHeaders = new HttpHeaders({
      Authorization: `Bearer ${this.localStorageService.getUser()}`,
    });

    this.authService.authUser(headers).subscribe(
      (response) => {
        if (response.is_admin === null || response.is_admin == false) {
          this.router.navigate(['/']);
        }
        this.userInfos = response;
        this.pageLoading = false;
        this.getAllArticles();
        this.getAllUsers();
      },
      (error) => {
        this.localStorageService.removeUser();
        this.router.navigate(['/']);
      },
    );
  }

  getAllArticles(): any {
    this.articlesService.getAllArticles().subscribe(
      (response: any) => {
        this.articles = response.success;
      }
    );
  }

  getAllUsers(): any {
    const headers: HttpHeaders = new HttpHeaders({
      Authorization: `Bearer ${this.localStorageService.getUser()}`,
    });

    this.userService.getAllUsers(headers).subscribe(
      (response: any) => {
        console.log(response.success);
        
        this.users = response.success;
      }
    );
  }
}
