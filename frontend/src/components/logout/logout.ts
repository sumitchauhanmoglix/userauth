import { Component } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router'; 

@Component({
  selector: 'logout',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './logout.html',
  styleUrls: ['./logout.css'],
})
export class Logout {

  token : string = "";

    constructor(private http: HttpClient, private router: Router) {}

    logout() {
    const payload = {
      token: localStorage.getItem('X-Refresh-Token'),
    };

    this.http.post('/api/v1/user/logout', payload)
      .subscribe({
        next: (response) => {
          console.log('Logout Success:', response);
          localStorage.removeItem('X-Refresh-Token');
          this.router.navigate(['']);

        },
        error: (error) => {
          console.error('Logout Error:', error);
          this.router.navigate(['']);
        }
      });
  }

}
