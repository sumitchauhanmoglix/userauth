import { Component } from '@angular/core';
import { Logout } from "../logout/logout";

@Component({
  selector: 'app-home',
  imports: [Logout],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {}
