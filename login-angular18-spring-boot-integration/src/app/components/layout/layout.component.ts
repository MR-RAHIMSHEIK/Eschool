import { Component, OnInit } from '@angular/core';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.css']   // ✅ fixed to styleUrls (plural)
})
export class LayoutComponent implements OnInit {
  username: string = 'User'; // default

  constructor(private router: Router) {}

  ngOnInit(): void {
    // ✅ Example 1: If username saved directly in localStorage
    const storedUser = localStorage.getItem('username');
    if (storedUser) {
      this.username = storedUser;
    }

    // ✅ Example 2: Extract username from JWT token if available
    const token = localStorage.getItem('token');
    if (token) {
      try {
        const payload = JSON.parse(atob(token.split('.')[1]));
        if (payload.sub) {
          this.username = payload.sub; // or payload.name depending on backend
        }
      } catch (e) {
        console.error('Invalid token', e);
      }
    }
  }

  logout() {
    localStorage.removeItem('auth-key');
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    sessionStorage.clear();
    this.router.navigate(['/login']);
  }
}
