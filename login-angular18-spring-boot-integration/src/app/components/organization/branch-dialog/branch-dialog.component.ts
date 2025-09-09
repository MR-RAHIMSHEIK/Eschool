import { Component, Inject, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialog } from '@angular/material/dialog';
import { OrganizationService, Branch } from '../../../services/organization.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { MatPaginator, PageEvent, MatPaginatorModule } from "@angular/material/paginator";
import { CommonModule } from '@angular/common';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-branch-dialog',
  standalone: true,
  templateUrl: './branch-dialog.component.html',
  styleUrls: ['./branch-dialog.component.css'],
  imports: [
    CommonModule,
    FormsModule,          // <-- needed for [(ngModel)]
    MatTableModule,
    MatPaginatorModule,
    MatButtonModule
  ]
})
export class BranchDialogComponent implements OnInit, AfterViewInit {
  orgId!: number;
  orgName!: string;

  // Table & pagination
  dataSource = new MatTableDataSource<Branch>([]);
  displayedColumns: string[] = ['branchCode', 'branchName', 'branchContact', 'address', 'actions'];
  pageSize = 5;
  pageIndex = 0;

  loading = true;

  // inline edit state
  editingRowId: number | null = null;       // existing row id OR -1 for a new row
  draft: Branch | null = null;              // working copy for edits/add

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: { orgId: number; orgName: string },
    private dialogRef: MatDialogRef<BranchDialogComponent>,
    private organizationService: OrganizationService,
    private snackBar: MatSnackBar,
    private dialog: MatDialog
  ) {
    this.orgId = data.orgId;
    this.orgName = data.orgName;
  }

  ngOnInit(): void {
    this.loadBranches();
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
  }

  private refreshTable(rows: Branch[]) {
    this.dataSource.data = rows;
    // keep paginator wired
    if (this.paginator) this.dataSource.paginator = this.paginator;
  }

  loadBranches(): void {
    this.loading = true;
    this.organizationService.getBranches(this.orgId).subscribe({
      next: (res) => {
        this.refreshTable(res || []);
        this.loading = false;
      },
      error: () => {
        this.snackBar.open('Failed to load branches', 'Close', { duration: 3000 });
        this.loading = false;
      }
    });
  }

  onPageChange(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
  }

  // ---- ADD ----
  startAdd(): void {
    if (this.editingRowId !== null) return; // avoid two editors at once
    const temp: Branch = {
      id: -1, // temp row id
      branchCode: '',
      branchName: '',
      branchContact: '',
      branchAddress: {
        dno: '',
        addressLine: '',
        villageOrCity: '',
        mandal: '',
        district: '',
        state: '',
        country: ''
      }
    };
    this.draft = { ...temp };
    this.editingRowId = -1;
    // prepend temp row
    this.refreshTable([temp, ...this.dataSource.data]);
  }

  saveAdd(): void {
    if (!this.draft) return;
    // create payload without temp id
    const { id, ...payload } = this.draft as any;
    this.organizationService.addBranch(this.orgId, payload as Branch).subscribe({
      next: (created) => {
        // replace temp row with actual created row
        const rows = this.dataSource.data.filter(r => r.id !== -1);
        this.refreshTable([created, ...rows]);
        this.snackBar.open('Branch added successfully', 'Close', { duration: 3000 });
        this.editingRowId = null;
        this.draft = null;
      },
      error: () => this.snackBar.open('Failed to add branch', 'Close', { duration: 3000 })
    });
  }

  cancelAdd(): void {
    // remove temp row
    const rows = this.dataSource.data.filter(r => r.id !== -1);
    this.refreshTable(rows);
    this.editingRowId = null;
    this.draft = null;
  }

  // ---- EDIT ----
  startEdit(row: Branch): void {
    if (this.editingRowId !== null) return;
    this.editingRowId = row.id ?? null;
    // deep copy minimal fields we edit (code/name/contact); keep address passthrough
    this.draft = JSON.parse(JSON.stringify(row));
  }

  saveEdit(): void {
    if (!this.draft || this.draft.id == null) return;
    this.organizationService.updateBranch(this.orgId, this.draft.id, this.draft).subscribe({
      next: (updated) => {
        const rows = this.dataSource.data.map(r => (r.id === updated.id ? updated : r));
        this.refreshTable(rows);
        this.snackBar.open('Branch updated', 'Close', { duration: 3000 });
        this.editingRowId = null;
        this.draft = null;
      },
      error: () => this.snackBar.open('Failed to update branch', 'Close', { duration: 3000 })
    });
  }

  cancelEdit(): void {
    this.editingRowId = null;
    this.draft = null;
  }

  // ---- DELETE ----
  deleteBranch(branchId?: number): void {
    if (!branchId) return;
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: { title: 'Delete Branch', message: 'Are you sure you want to delete this branch?' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.organizationService.deleteBranch(this.orgId, branchId).subscribe({
          next: () => {
            this.refreshTable(this.dataSource.data.filter(b => b.id !== branchId));
            this.snackBar.open('Branch deleted successfully', 'Close', { duration: 3000 });
          },
          error: () => this.snackBar.open('Failed to delete branch', 'Close', { duration: 3000 })
        });
      }
    });
  }

  close(): void {
    this.dialogRef.close();
  }
  
}
