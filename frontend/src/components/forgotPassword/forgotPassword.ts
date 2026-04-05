import { Component } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router'; 

@Component({
  selector: 'forgotPassword',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './forgotPassword.html',
  styleUrls: ['./forgotPassword.css'],
})
export class ForgotPassword {

    constructor(private http: HttpClient, private router: Router) {}

    forgotPassword() {
    
  }

}
