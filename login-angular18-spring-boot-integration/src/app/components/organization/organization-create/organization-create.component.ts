import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { OrganizationService, Organization } from '../../../services/organization.service';

@Component({
  selector: 'app-organization-create',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ],
  templateUrl: './organization-create.component.html',
  styleUrls: ['./organization-create.component.css']
})
export class OrganizationCreateComponent {
  orgForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private orgService: OrganizationService,
    private dialogRef: MatDialogRef<OrganizationCreateComponent>
  ) {
    this.orgForm = this.fb.group({
      organizationCode: ['', Validators.required],
      organizationName: ['', Validators.required],
      organizationContact: ['', Validators.required],
      organizationAddress: this.fb.group({
        dno: ['', Validators.required],
        addressLine: ['', Validators.required],
        villageOrCity: ['', Validators.required],
        mandal: ['', Validators.required],
        district: ['', Validators.required],
        state: ['', Validators.required],
        country: ['', Validators.required]
      })
    });
  }

  save() {
    if (this.orgForm.valid) {
      const org: Organization = this.orgForm.value;
      this.orgService.createOrganization(org).subscribe({
        next: (created) => {
          this.dialogRef.close(created); // return created org to parent
        },
        error: (err) => {
          console.error('Failed to create organization', err);
        }
      });
    }
  }

  close() {
    this.dialogRef.close();
  }
}
