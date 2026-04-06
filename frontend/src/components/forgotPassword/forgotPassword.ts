import { Component } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router'; 

@Component({
  selector: 'forgotPassword',
  standalone: true,
  imports: [CommonModule, HttpClientModule, FormsModule],
  templateUrl: './forgotPassword.html',
  styleUrls: ['./forgotPassword.css']
})
export class ForgotPassword {
  username: string = '';
  password: string = '';
  oldPassword : string = '';

    constructor(private http: HttpClient, private router: Router) {}

    login(){
          this.router.navigate(['/login']);
    }

    resetPassword(){
      const payload = {
      username: this.username,
      oldPassword : this.oldPassword,
      password: this.password
    };

    this.http.post<any>('/api/v1/user/reset-password', payload, { observe: 'response' })
      .subscribe({
        next: (response) => {
          console.log('Sign Up Success:', response);
          const token = response.headers.get('X-Refresh-Token');
          if (token) {
            localStorage.setItem('X-Refresh-Token', token);
          }
          this.router.navigate(['/login']);

        },
        error: (error) => {
          console.error('Reset Password Failed', error);
          alert('Reset Password failed.');
        }
      });
    }

  
}