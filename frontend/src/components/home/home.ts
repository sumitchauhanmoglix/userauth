import { Component } from '@angular/core';
import { Logout } from "../logout/logout";
import { Item } from "../item/item";

@Component({
  selector: 'app-home',
  imports: [Logout, Item],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {}
