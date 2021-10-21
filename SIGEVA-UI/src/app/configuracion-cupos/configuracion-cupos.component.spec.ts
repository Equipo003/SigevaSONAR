import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfiguracionCuposComponent } from './configuracion-cupos.component';

describe('ConfiguracionCuposComponent', () => {
  let component: ConfiguracionCuposComponent;
  let fixture: ComponentFixture<ConfiguracionCuposComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConfiguracionCuposComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfiguracionCuposComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
