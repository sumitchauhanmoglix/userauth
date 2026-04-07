import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { Router } from '@angular/router'; 

@Component({
  selector: 'app-item',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './item.html',
  styleUrl: './item.css',
})
export class Item {

  @Input() url!: string;
  @Input() name!: string;
  @Input() description!: string;
  @Input() amount!: number;


  constructor(private router: Router) {
  }

  openPdp() {
    this.router.navigate(['/login']);
  }
}