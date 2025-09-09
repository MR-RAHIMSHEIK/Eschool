import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { IntegrationService, ModuleCard } from '../../services/integration.service';
import { MatDialog } from '@angular/material/dialog';
import { AdminLauncherComponent } from './admin-launcher/admin-launcher.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {
  modules: ModuleCard[] = [];
  loading = true;
  errorMessage = '';

  constructor(
    private router: Router,
    private integrationService: IntegrationService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.integrationService.getModules().subscribe({
      next: (data) => {
        this.modules = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Failed to load modules:', err);
        this.errorMessage = 'Failed to load dashboard modules.';
        this.loading = false;
      }
    });
  }

  navigate(route: string) {
    if (!route) return;
    this.router.navigate([route]);
  }

  /**
   * Handles click on modules.
   * If it's Administration → open AdminLauncher popup.
   * Else → navigate normally.
   */
  handleModuleClick(mod: ModuleCard) {
    const name = (mod?.name || '').toLowerCase();
    if (name.includes('admin')) {
      this.openAdminPopup();
    } else {
      this.navigate(mod.route);
    }
  }

  /**
   * Opens Angular Material dialog with AdminLauncherComponent.
   */
  openAdminPopup() {
    this.dialog.open(AdminLauncherComponent, {
      width: '820px',
      maxWidth: '95vw',
      panelClass: 'admin-dialog'
    });
  }
}