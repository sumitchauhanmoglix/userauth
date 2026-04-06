import { Component } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router'; 

@Component({
  selector: 'signin',
  standalone: true,
  imports: [CommonModule, HttpClientModule, FormsModule],
  templateUrl: './signin.html',
  styleUrls: ['./signin.css']
})
export class Signin {
  username: string = '';
  password: string = '';

    constructor(private http: HttpClient, private router: Router) {}

  signUp() {
    const payload = {
      username: this.username,
      password: this.password
    };

    this.http.post<any>('/api/v1/user/sign-up', payload, { observe: 'response' })
      .subscribe({
        next: (response) => {
          console.log('Sign Up Success:', response);
          const token = response.headers.get('X-Refresh-Token');
          if (token) {
            localStorage.setItem('X-Refresh-Token', token);
          }
          this.router.navigate(['/home']);

        },
        error: (error) => {
          console.error('Sign Up Error:', error);
          alert('Sign Up Failed');
        }
      });
  }

  login(){
    this.router.navigate(['']);

  }
}