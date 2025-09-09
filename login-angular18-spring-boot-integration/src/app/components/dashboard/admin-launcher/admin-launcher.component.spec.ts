import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminLauncherComponent } from './admin-launcher.component';

describe('AdminLauncherComponent', () => {
  let component: AdminLauncherComponent;
  let fixture: ComponentFixture<AdminLauncherComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminLauncherComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminLauncherComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
