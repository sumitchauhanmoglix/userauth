import { Routes } from '@angular/router';
import { Login } from '../components/login/login';
import { Logout } from '../components/logout/logout';

export const routes: Routes = [
  { path: '', loadComponent: () => import('../components/login/login').then(m => m.Login) },
  { path: 'logout', loadComponent: () => import('../components/logout/logout').then(m => m.Logout) },
];