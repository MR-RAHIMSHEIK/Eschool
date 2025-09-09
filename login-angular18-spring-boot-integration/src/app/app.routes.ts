import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { AuthGuard } from './guards/auth.guard';
import { RegisterComponent } from './components/register/register.component';
import { LayoutComponent } from './components/layout/layout.component';
import { OrganizationListComponent } from './components/organization/organization-list/organization-list.component';
import { HrDashboardComponent } from './components/hr/hr-dashboard/hr-dashboard.component';

export const routes: Routes = [
    { path: '', redirectTo: 'login', pathMatch: 'full' },
    { path: 'login', component: LoginComponent },
    // {path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard]},
    { path: 'register', component: RegisterComponent },
    {
        path: '', component: LayoutComponent,
        children: [
            { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] },
            { path: 'organizations', component: OrganizationListComponent, canActivate: [AuthGuard] },
            { path: 'hr', component: HrDashboardComponent, canActivate: [AuthGuard] }
        ]
    }
];
