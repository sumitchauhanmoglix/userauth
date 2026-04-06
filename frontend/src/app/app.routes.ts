import { Routes } from '@angular/router';
import { Login } from '../components/login/login';
import { Logout } from '../components/logout/logout';
import { Signin } from '../components/signin/signin';
import { ForgotPassword } from '../components/forgotPassword/forgotPassword';


export const routes: Routes = [
  { path: '', loadComponent: () => import('../components/login/login').then(m => m.Login) },
  { path: 'login', loadComponent: () => import('../components/login/login').then(m => m.Login) },
  { path: 'logout', loadComponent: () => import('../components/logout/logout').then(m => m.Logout) },
  { path: 'sign-in', loadComponent: () => import('../components/signin/signin').then(m => m.Signin) },
  { path: 'forgot-password', loadComponent: () => import('../components/forgotPassword/forgotPassword').then(m =>m.ForgotPassword)},
  { path: 'home', loadComponent: () => import('../components/home/home').then(m =>m.Home)},
  { path: 'test-item', loadComponent: () => import('../components/item/item').then(m =>m.Item)}
];