import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { MatDialogRef } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatMenuModule } from '@angular/material/menu';

@Component({
  selector: 'app-admin-launcher',
  standalone: true,
  imports: [MatIconModule, MatButtonModule,MatMenuModule],
  templateUrl: './admin-launcher.component.html',
  styleUrls: ['./admin-launcher.component.css']
})
export class AdminLauncherComponent {

  constructor(
    private router: Router,
    private dialogRef: MatDialogRef<AdminLauncherComponent>
  ) {}

  /**
   * Navigate to the selected option and close dialog.
   */
  handleAdminOption(option: string) {
    switch (option) {
      case 'organization':
        this.router.navigate(['/organizations']);
        break;
      case 'geo-master':
        this.router.navigate(['/admin/masters/geographical']);
        break;
      case 'subject-master':
        this.router.navigate(['/admin/masters/subjects']);
        break;
      case 'class-master':
        this.router.navigate(['/admin/masters/classes']);
        break;
      case 'user-role':
        this.router.navigate(['/admin/user-roles']);
        break;
      case 'academic-years':
        this.router.navigate(['/admin/academic-years']);
        break;
      case 'route-master':
        this.router.navigate(['/admin/route-master']);
        break;
      case 'forms':
        this.router.navigate(['/admin/forms']);
        break;
    }
    this.dialogRef.close();
  }


  close() {
    this.dialogRef.close();
  }
}