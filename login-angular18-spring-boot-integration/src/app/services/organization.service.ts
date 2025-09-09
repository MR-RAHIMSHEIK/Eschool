import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const BASE_URL = 'http://localhost:8080/api';

export interface Address {
  dno: string;
  addressLine: string;
  villageOrCity: string;
  mandal: string;
  district: string;
  state: string;
  country: string;
}

export interface Branch {
  id?: number;
  branchCode: string;
  branchName: string;
  branchContact: string;
  branchAddress: Address;
}

export interface Organization {
  id?: number;
  organizationCode: string;
  organizationName: string;
  organizationContact: string;
  organizationAddress: Address;
  branches?: Branch[];
}

@Injectable({
  providedIn: 'root'
})
export class OrganizationService {
  constructor(private http: HttpClient) {}

  // 1. Create Organization
  createOrganization(request: Organization): Observable<Organization> {
    return this.http.post<Organization>(`${BASE_URL}/organizations`, request);
  }

  // 2. Add Branch to Organization
  addBranch(orgId: number, request: Branch): Observable<Branch> {
    return this.http.post<Branch>(`${BASE_URL}/organizations/${orgId}/branches`, request);
  }

  // 3. Get All Organizations
  getOrganizations(): Observable<Organization[]> {
    return this.http.get<Organization[]>(`${BASE_URL}/organizations`);
  }

  // 4. Get All Branches of Specific Organization
  getBranches(orgId: number): Observable<Branch[]> {
    return this.http.get<Branch[]>(`${BASE_URL}/organizations/${orgId}/branches`);
  }

updateBranch(orgId: number, branchId: number, request: Branch): Observable<Branch> {
  return this.http.put<Branch>(`${BASE_URL}/organizations/${orgId}/branches/${branchId}`, request);
}

deleteBranch(orgId: number, branchId: number): Observable<void> {
  return this.http.delete<void>(`${BASE_URL}/organizations/${orgId}/branches/${branchId}`);
}

}

