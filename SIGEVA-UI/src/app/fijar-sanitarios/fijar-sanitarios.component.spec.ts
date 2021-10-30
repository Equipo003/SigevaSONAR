import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FijarSanitariosComponent } from './fijar-sanitarios.component';

describe('FijarSanitariosComponent', () => {
  let component: FijarSanitariosComponent;
  let fixture: ComponentFixture<FijarSanitariosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FijarSanitariosComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FijarSanitariosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
