import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from "../../components/header/header.component";
import { LocalStorageService } from '../../core/services/localStorage.service';
import { Router } from '@angular/router';
import { RegisterRequest } from '../../core/models/request/RegisterRequest';
import { FormsModule } from '@angular/forms';
import { NgFor, NgIf } from '@angular/common';
import { AuthService } from '../../core/services/auth.service';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-inscription-page',
  imports: [HeaderComponent, FormsModule, NgIf],
  templateUrl: './inscription-page.component.html',
  styleUrl: './inscription-page.component.scss'
})
export class InscriptionPageComponent implements OnInit {
  registerData: RegisterRequest = {
    email: '',
    username: '',
    password: '',
    profile_picture: 'https://i.ibb.co/jZH3P5P7/A-black-image.jpg',
    isAdmin: false
  };
  error: boolean = false;
  errorsMsg!: string;
  specialCharactersRegex: RegExp = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]+/;

  constructor(private titleService: Title, private router: Router, public localStorageService: LocalStorageService, private authService: AuthService) {}
  
  ngOnInit(): void {
    this.titleService.setTitle('Urbanhoney | Sign up');
    if (this.localStorageService.getUser() != null) {
      this.homeRouting();
    }
  }

  homeRouting(): void {
    this.router.navigate(['/']);
  }

  emailVerification(): boolean {
    if (!this.registerData.email.includes("@")) {
      this.error = true;
      this.errorsMsg = "Please enter a valid email !";
      return true;
    }else {
      this.error = false;
      this.errorsMsg = "";
    }
    return false;
  }

  usernameVerification(): boolean {
    if (this.registerData.username.length < 8) {
      this.error = true;
      this.errorsMsg = "Your username must be at least 8 char long !";
      return true;
    }else {
      this.error = false;
      this.errorsMsg = "";
    }
    
    if (this.registerData.username.search(this.specialCharactersRegex) != -1) {
      this.error = true;
      this.errorsMsg = "Your username must not contain special char !";
      return true;
    }else {
      this.error = false;
      this.errorsMsg = "";
    }
    return false;
  }

  passwordVerification(): boolean {
    if (this.registerData.password.length < 8) {
      this.error = true;
      this.errorsMsg = "Your username must be at least 8 char long !";
      return true;
    }else {
      this.error = false;
      this.errorsMsg = "";
    }
    return false;
  }

  registerUser(): void {
    if (!this.emailVerification() && !this.usernameVerification() && !this.passwordVerification()) {
      this.authService.registerUser(this.registerData).subscribe(
        (response: any) => {
          switch (response.Success) {
            case 'User register successfully':
              this.router.navigate(['/login']);
              break;
  
            default:
              this.error = true;
              this.errorsMsg = "An error as occured please try later !";
              break;
          }
        },
        (error) => {
          this.error = true;
          this.errorsMsg = error.error.error;
        }
      );
    }else {
      this.error = true;
      this.errorsMsg = "Please fill all field correctly please !";
    }
  }
}
