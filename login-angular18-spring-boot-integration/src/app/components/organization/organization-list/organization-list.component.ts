import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog } from '@angular/material/dialog';
import { OrganizationService, Organization } from '../../../services/organization.service';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { OrganizationCreateComponent } from '../organization-create/organization-create.component';
import { BranchDialogComponent } from '../branch-dialog/branch-dialog.component';

@Component({
  selector: 'app-organization-list',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatButtonModule, MatIconModule],
  templateUrl: './organization-list.component.html',
  styleUrls: ['./organization-list.component.css']
})
export class OrganizationListComponent implements OnInit {

  organizations: Organization[] = [];
  loading = true;
  errorMessage = '';
  displayedColumns: string[] = ['code', 'name', 'contact', 'actions'];

  constructor(
    private orgService: OrganizationService,
    private location: Location,
    private router: Router,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.loadOrganizations();
  }

  loadOrganizations() {
    this.loading = true;
    this.orgService.getOrganizations().subscribe({
      next: (data) => {
        this.organizations = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Failed to load organizations', err);
        this.errorMessage = 'Failed to load organizations';
        this.loading = false;
      }
    });
  }

  openCreateDialog() {
    const dialogRef = this.dialog.open(OrganizationCreateComponent, {
      width: '700px'
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.loadOrganizations(); // refresh list after new org created
      }
    });
  }

  viewBranches(org: Organization) {
  this.dialog.open(BranchDialogComponent, {
    width: '1200px',
    height: '600px',
    maxWidth: '95vw', 
    data: { orgId: org.id, orgName: org.organizationName }
  });
}

  goBack() {
    this.location.back();
  }
}
