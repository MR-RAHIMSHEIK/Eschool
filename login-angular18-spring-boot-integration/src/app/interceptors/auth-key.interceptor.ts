import { HttpEvent, HttpHandler, HttpInterceptor, HttpInterceptorFn, HttpRequest ,HttpErrorResponse} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable,throwError } from 'rxjs';
import { LocalStorageService } from '../services/local-storage.service';
import { Router } from '@angular/router';
import { catchError } from 'rxjs/operators';

@Injectable()
export class authKeyInterceptor implements HttpInterceptor {

  constructor(private storageService: LocalStorageService,
    private router: Router
  ) {}
  
  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const token = this.storageService.get('auth-key');

   // console.log("Authentication key:"+token);

    if (token) {
      req = req.clone({
        url:  req.url,
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }
     return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401 || error.status === 403) {
          // 🔑 Token is expired or invalid
          this.storageService.remove('auth-key');   // Clear token
          this.router.navigate(['/login']);        // Redirect to login
        }
        return throwError(() => error);
      })
    );
  }
}
