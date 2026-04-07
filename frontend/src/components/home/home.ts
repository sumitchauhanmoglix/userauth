import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Logout } from "../logout/logout";
import { Item } from "../item/item";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, Logout, Item],
  templateUrl: './home.html',
  styleUrls: ['./home.css'],
})
export class Home implements OnInit {
  products: { url: string; name: string; description: string; amount: number }[] = [];

  constructor(private http: HttpClient, private cd: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.getProducts();
  }

  getProducts() {
    const headers = new HttpHeaders({
      'X-User-Id': localStorage.getItem('X-User-Id') || '',
      'X-Access-Token': localStorage.getItem('X-Access-Token') || ''
    });

    this.http.get<any[]>('http://localhost:8080/api/v1/product', { headers })
      .subscribe({
        next: (data) => {
          console.log('API Response:', data);
          this.products = data.map(p => ({
            url: p.url || '',
            name: p.name || '',
            description: p.description || '',
            amount: p.amount || 0
          }));
          this.cd.markForCheck();
        },
        error: (err) => {
          console.error('API Error:', err);
        }
      });
  }
}