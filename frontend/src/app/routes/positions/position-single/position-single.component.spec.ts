import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PositionSingleComponent } from './position-single.component';

describe('PositionSingleComponent', () => {
  let component: PositionSingleComponent;
  let fixture: ComponentFixture<PositionSingleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PositionSingleComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PositionSingleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
