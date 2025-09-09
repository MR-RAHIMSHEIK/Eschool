import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Employee {
  id?: number;
  employeeCode: string;
  employeeName: string;
  qualification?: string;
  category?: string;
  dob?: string;
  age?: number;
  primaryContact?: string;
  secondaryContact?: string;
  address?: string;
  gender?: string;
  photograph?: string;
  isAppUser: boolean;
  username?: string; // required if isAppUser = true
  password?: string; // required if isAppUser = true
  branchId?: number;
}

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private baseUrl = 'http://localhost:8080/api/hr/employees';

  constructor(private http: HttpClient) {}

  getAllEmployees(): Observable<Employee[]> {
    return this.http.get<Employee[]>(this.baseUrl);
  }

  getEmployeeById(id: number): Observable<Employee> {
    return this.http.get<Employee>(`${this.baseUrl}/${id}`);
  }

  createEmployee(employee: Employee): Observable<Employee> {
    return this.http.post<Employee>(this.baseUrl, employee);
  }

  updateEmployee(id: number, employee: Employee): Observable<Employee> {
    return this.http.put<Employee>(`${this.baseUrl}/${id}`, employee);
  }

  deleteEmployee(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
  
  uploadPhoto(file: File): Observable<string> {
  const formData = new FormData();
  formData.append('file', file);
  return this.http.post(this.baseUrl + '/upload-photo', formData, {
    responseType: 'text'
  });
  }

}
