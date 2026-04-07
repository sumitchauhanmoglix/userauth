import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpErrorResponse } from '@angular/common/http';
import { Observable, tap, catchError } from 'rxjs';
import { Router } from '@angular/router';
import { throwError } from 'rxjs';

@Injectable()
export class LoggerInterceptor implements HttpInterceptor {
  constructor(private router: Router) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    console.log('Request URL:', req.url);
    console.log('Request Method:', req.method);
    console.log('Request Body:', req.body);

    return next.handle(req).pipe(
      tap({
        next: (event) => console.log('Response:', event),
        error: (err) => console.error('Error Response:', err)
      }),
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {
          console.log('401 Unauthorized - Redirecting to logout');
          localStorage.removeItem('X-Refresh-Token');
          localStorage.removeItem('X-Access-Token');
          localStorage.removeItem('X-User-Id');
          this.router.navigate(['']);
        }
        return throwError(() => error);
      })
    );
  }
}