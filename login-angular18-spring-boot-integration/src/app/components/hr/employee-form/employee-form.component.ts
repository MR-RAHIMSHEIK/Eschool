import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Employee,EmployeeService } from '../../../services/employee.service';

@Component({
  selector: 'app-employee-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './employee-form.component.html',
  styleUrls: ['./employee-form.component.css']
})
export class EmployeeFormComponent {
  employee: Employee;

  constructor(
    private dialogRef: MatDialogRef<EmployeeFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { employee: Employee },
     private employeeService: EmployeeService
  ) {
    this.employee = { ...data.employee };
  }

  save() {
    this.dialogRef.close(this.employee);
  }

  cancel() {
    this.dialogRef.close();
  }

  onFileSelected(event: Event): void {
  const file = (event.target as HTMLInputElement).files?.[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = () => {
      this.employee.photograph = reader.result as string; // Base64 preview
    };
    reader.readAsDataURL(file);

    // upload to server in parallel
    this.employeeService.uploadPhoto(file).subscribe({
      next: (path) => this.employee.photograph = path,
      error: (err) => console.error('Upload failed', err)
    });
  }
}


}
