import { Component, inject } from '@angular/core';
import { IntegrationService } from '../../services/integration.service';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LoginRequest } from '../../models/login-request';
import { Router, RouterLink } from '@angular/router';
import { LocalStorageService } from '../../services/local-storage.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})

export class LoginComponent {

  constructor(private integration: IntegrationService ,private storage : LocalStorageService) {}

  userForm : FormGroup =  new FormGroup({
    username: new FormControl('', Validators.required),
    password: new FormControl('', [Validators.required, Validators.minLength(4)])
  });

  router = inject(Router);
  request: LoginRequest = new LoginRequest;

  login() {

    this.storage.remove('auth-key');
    this.storage.remove('username');
    
    const formValue =  this.userForm.value;

    if(formValue.username == '' || formValue.password == '') {
      alert('Wrong Credentilas');
      return;
    }

    this.request.username = formValue.username;
    this.request.password = formValue.password;

    this.integration.doLogin(this.request).subscribe({
      next:(res) => {
        //console.log("Received Response:"+res.token);

        this.storage.set('auth-key', res.token);
        this.storage.set('username', this.request.username);

        this.integration.dashboard().subscribe({
          next: (dashboardres) => {
           // console.log("Dashboard res:"+dashboardres.response);

            this.router.navigateByUrl('dashboard');
          }, error : (err) => {
           // console.log("Dashboard error received :" + err); 
            this.storage.remove('auth-key');
            this.storage.remove('username');
          }
        });
      }, error: (err) => {
        console.log("Error Received Response:"+err);
        this.storage.remove('auth-key');
        this.storage.remove('username');
      }
    });
  }

   // 👁️ Toggle Password Visibility
  togglePassword() {
    const passwordInput = document.getElementById('password') as HTMLInputElement;
    const eyeIcon = document.getElementById('eyeIcon');

    if (passwordInput && eyeIcon) {
      if (passwordInput.type === 'password') {
        passwordInput.type = 'text';
        eyeIcon.innerHTML = `
          <path d="M12 7c2.76 0 5 2.24 5 5 0 .65-.13 1.26-.36 
          1.83l2.92 2.92c1.51-1.26 2.7-2.89 
          3.43-4.75-1.73-4.39-6-7.5-11-7.5-1.4 
          0-2.74.25-3.98.7l2.16 2.16C10.74 7.13 
          11.35 7 12 7zM2 4.27l2.28 2.28.46.46C3.08 
          8.3 1.78 10.02 1 12c1.73 4.39 6 7.5 11 
          7.5 1.55 0 3.03-.3 4.38-.84l.42.42L19.73 
          22 21 20.73 3.27 3 2 4.27zM7.53 9.8l1.55 
          1.55c-.05.21-.08.43-.08.65 0 1.66 1.34 
          3 3 3 .22 0 .44-.03.65-.08l1.55 
          1.55c-.67.33-1.41.53-2.2.53-2.76 
          0-5-2.24-5-5 0-.79.2-1.53.53-2.2zm4.31-.78l3.15 
          3.15.02-.16c0-1.66-1.34-3-3-3l-.17.01z"/>`;
      } else {
        passwordInput.type = 'password';
        eyeIcon.innerHTML = `
          <path d="M12 4.5C7 4.5 2.73 7.61 1 
          12c1.73 4.39 6 7.5 11 7.5s9.27-3.11 
          11-7.5c-1.73-4.39-6-7.5-11-7.5zM12 
          17c-2.76 0-5-2.24-5-5s2.24-5 
          5-5 5 2.24 5 5-2.24 5-5 
          5zm0-8c-1.66 0-3 1.34-3 
          3s1.34 3 3 3 3-1.34 3-3-1.34-3-3-3z"/>`;
      }
    }
  }
}
