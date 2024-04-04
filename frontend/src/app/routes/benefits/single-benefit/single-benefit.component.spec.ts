import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SingleBenefitComponent } from './single-benefit.component';

describe('SingleBenefitComponent', () => {
  let component: SingleBenefitComponent;
  let fixture: ComponentFixture<SingleBenefitComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SingleBenefitComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SingleBenefitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
