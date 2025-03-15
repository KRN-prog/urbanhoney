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
import { CategorieService } from '../../core/services/categorie.service';
import { Category } from '../../core/models/Category';
import { FormArray, FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { SubCategorie } from '../../core/models/SubCategorie';
import { AddArticleRequest } from '../../core/models/request/AddArticleRequest';

@Component({
  selector: 'app-admin-page',
  imports: [HeaderComponent, NgIf, NgFor, ReactiveFormsModule],
  templateUrl: './admin-page.component.html',
  styleUrl: './admin-page.component.scss'
})
export class AdminPageComponent implements OnInit {
  pageLoading: boolean = true;
  userInfos!: User;
  articles: Array<Article> = [];
  users: Array<User> = [];
  categories: Array<Category> = [];
  //categorySelected: string = "";
  subCategorySelected!: SubCategorie;
  newArticleForm: any;
  availableSizes = ['XS', 'S', 'M', 'L', 'XL'];
  subCategoryResponse: Array<SubCategorie> = [];

  constructor(private fb: FormBuilder, private router: Router, public localStorageService: LocalStorageService, private authService: AuthService, private articlesService: ArticlesService, private userService: UserService, private categorieService: CategorieService) {}

  ngOnInit(): void {
    this.authUser();
    this.getAllCategories();
    this.initializeArticleForm();
    this.newArticleForm.get('category.sub').valueChanges.subscribe((newSubCategory: string) => {
      this.categorieService.getSubCategoriesByName(newSubCategory).subscribe(
        (response) => {
          console.log(response);
          this.subCategorySelected = response.success
        },
        (error) => {
          console.log(error);
        },
      );;
    });
  }

  get sizes(): FormArray {
    return this.newArticleForm.get('specifications.size') as FormArray;
  }

  getSelectedSizes(): string[] {
    const selectedSizes: string[] = [];
    const sizeArray = this.newArticleForm.get('specifications.size') as FormArray;
  
    sizeArray.controls.forEach((control, index) => {
      if (control.value) { // Si la valeur est true (case cochée)
        selectedSizes.push(this.availableSizes[index]); // Ajoutez la taille correspondante
      }
    });
  
    return selectedSizes;
  }


  addSubCategorie(subCategory: any): void {
    this.subCategorySelected = subCategory;
  }

  initializeArticleForm(): void {
    console.log('availableSizes:', this.availableSizes);

    const sizeControls = this.availableSizes.map(() => new FormControl(false));
    console.log('sizeControls:', sizeControls);

    const sizesArray = this.fb.array(sizeControls);
    console.log('sizesArray:', sizesArray);

    this.newArticleForm = this.fb.group({
      title: ['', [Validators.required, Validators.minLength(5)]],  // Min 5 caractères
      description: ['', Validators.required],
      category: this.fb.group({
        main: ['', Validators.required],  
        sub: ['', Validators.required]
      }),
      brand: ['', Validators.required],
      price: ['', [Validators.required, Validators.min(1)]],
      images: ['', Validators.required],
      specifications: this.fb.group({
        color: ['', Validators.required],
        size: sizesArray,
        composition: ['', Validators.required],
        entretien: ['', Validators.required]
      })
    });
  }

  transformFormToRequest(): AddArticleRequest {

    console.log("============"+this.subCategorySelected.sub_category_id+"===============");
    
    const formValue = this.newArticleForm.value;

    const selectedSizes = this.getSelectedSizes();
    const colors = formValue.specifications.color.split(',').map((c: string) => c.trim());
    const pictures = formValue.images.split(',').map((p: string) => p.trim());

    const request: AddArticleRequest = {
      title: formValue.title,
      description: formValue.description,
      sub_category: this.subCategorySelected,
      brand: formValue.brand,
      price: [parseFloat(formValue.price), "€"],
      color: colors,
      size: selectedSizes,
      pictures: pictures,
      composition: formValue.specifications.composition,
      entretien: formValue.specifications.entretien
    };

    console.log(request);
    

    return request;
  }

  authUser(): any {
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

  revokeAdminStatus(userId: number): any {
    const headers: HttpHeaders = new HttpHeaders({
      Authorization: `Bearer ${this.localStorageService.getUser()}`,
    });

    this.userService.revokeAdminStatus(userId, headers).subscribe(
      (response: any) => {
        console.log(response);
        this.users = this.users.map(user =>
          user.id === userId ? { ...user, is_admin: false } : user
        );

        console.log(this.users);
        
      },
      (error) => {
        console.log(error);
      }
    );
  }

  setAdminStatus(userId: number): any {
    const headers: HttpHeaders = new HttpHeaders({
      Authorization: `Bearer ${this.localStorageService.getUser()}`,
    });

    this.userService.setAdminStatus(userId, headers).subscribe(
      (response: any) => {
        this.users = this.users.map(user =>
          user.id === userId ? { ...user, is_admin: true } : user
        );

        console.log(this.users);
        
      },
      (error) => {
        console.log(error);
      }
    );
  }

  getAllCategories(): any {
    this.categorieService.getAllCategories().subscribe(
      (response: any) => {
        this.categories = response.success;
      },
      (error) => {
        console.log(error);
      }
    )
  }

  verifySexe(sexe: string): string  {
    const getSubCategory: string = this.newArticleForm.get('category')?.get('sub').value;
    const subTheme: SubCategorie | undefined = this.subCategoryResponse.find((subCategory) => subCategory.sub_category_name === getSubCategory);

    if (subTheme == undefined) {
      return '';
    }

    const getGender: string = subTheme.gender == sexe ? sexe : '';
    
    return getGender;
  }

  onCategoryChange(event: Event): any {
    const selectElement = event.target as HTMLSelectElement;
    const selectedCategory = selectElement.value;

    console.log(selectedCategory);
    
    this.categorieService.getAllSubCategoriesByCategoryName(selectedCategory).subscribe(
      (response: any) => {
        console.log(response.success);
        
        this.subCategoryResponse = response.success;
      },
      (error) => {
        console.log(error);
      }
    )
  }

  addArticle(): any {
    console.log("=====================================");
    console.log(this.localStorageService.getUser());

    const headers: HttpHeaders = new HttpHeaders({
      Authorization: `Bearer ${this.localStorageService.getUser()}`,
    });
    
    if (this.newArticleForm.invalid) {
      console.error('Le formulaire est invalide');
      return;
    }

    const request: AddArticleRequest = this.transformFormToRequest();
    console.log('Données transformées :', request);

    this.articlesService.postArticle(request, headers).subscribe(
      (response: any) => {
        console.log(response);
      },
      (error) => {
        console.log(error);
      }
    );
  }
}