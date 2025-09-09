import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Employee, EmployeeService } from '../../../services/employee.service';
import { FormsModule } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { EmployeeFormComponent } from '../employee-form/employee-form.component';

@Component({
  selector: 'app-hr-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './hr-dashboard.component.html',
  styleUrls: ['./hr-dashboard.component.css']
})
export class HrDashboardComponent {
  employees: Employee[] = [];
  loading = true;
  errorMessage = '';

  constructor(
    private employeeService: EmployeeService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.loadEmployees();
  }

  loadEmployees(): void {
    this.employeeService.getAllEmployees().subscribe({
      next: (data) => {
        this.employees = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Failed to load employees', err);
        this.errorMessage = 'Failed to load employee data';
        this.loading = false;
      }
    });
  }

  openAddEmployeeDialog() {
    const dialogRef = this.dialog.open(EmployeeFormComponent, {
      width: '600px',
      data: {
        employee: {
          employeeCode: '',
          employeeName: '',
          qualification: '',
          category: '',
          dob: '',
          age: 0,
          primaryContact: '',
          secondaryContact: '',
          address: '',
          gender: '',
          photograph: '',
          isAppUser: false
        } as Employee
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.addEmployee(result);
      }
    });
  }

  addEmployee(newEmployee: Employee): void {
    this.employeeService.createEmployee(newEmployee).subscribe({
      next: () => this.loadEmployees(),
      error: (err) => console.error('Error creating employee', err)
    });
  }

  updateEmployee(emp: Employee): void {
    if (!emp.id) return;
    this.employeeService.updateEmployee(emp.id, emp).subscribe({
      next: () => this.loadEmployees(),
      error: (err) => console.error('Error updating employee', err)
    });
  }

  deleteEmployee(id: number): void {
    this.employeeService.deleteEmployee(id).subscribe({
      next: () => this.loadEmployees(),
      error: (err) => console.error('Error deleting employee', err)
    });
  }
}
