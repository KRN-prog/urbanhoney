import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from "../../components/header/header.component";
import { Router } from '@angular/router';
import { LocalStorageService } from '../../core/services/localStorage.service';
import { AuthService } from '../../core/services/auth.service';
import { AuthRequest } from '../../core/models/request/AuthRequest';
import { NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-connexion-page',
  imports: [HeaderComponent, NgIf, FormsModule],
  templateUrl: './connexion-page.component.html',
  styleUrl: './connexion-page.component.scss'
})
export class ConnexionPageComponent implements OnInit {
  loginData: AuthRequest = {
    emailOrUsername: '',
    password: ''
  }
  error: boolean = false;
  errorsMsg!: string;

  constructor(private titleService: Title, private router: Router, public localStorageService: LocalStorageService, private authService: AuthService) {}

  ngOnInit(): void {
    this.titleService.setTitle('Urbanhoney | Log in');
    if (this.localStorageService.getUser() != null) {
      this.homeRouting();
    }
  }

  homeRouting(): void {
    this.router.navigate(['/']);
  }

  emailOrUsernameVerification(): boolean {
    if (this.loginData.emailOrUsername.length <= 0) {
      this.error = true;
      this.errorsMsg = "Please enter a correct email or username !";
      return true;
    }
    return false;
  }

  passwordVerification(): boolean {
    if (this.loginData.password.length <= 0) {
      this.error = true;
      this.errorsMsg = "Please enter a correct password !";
      return true;
    }
    return false;
  }

  loginUser(): void {
    if (!this.emailOrUsernameVerification() && !this.passwordVerification()) {
      this.authService.loginUser(this.loginData).subscribe(
        (response) => {
          this.localStorageService.setUser(response.success.token);
          this.router.navigate(['/']);
        },
        (error) => {
          this.error = true;
          this.errorsMsg = "Your email, username or password is incorrect !";
        }
      ); 
    }else {
      this.error = true;
      this.errorsMsg = "Please fill all field correctly please !";
    }
  }

}
