import { ComponentFixture, TestBed } from "@angular/core/testing"

import { LayoutSingleComponent } from "./layout-single.component"

describe("LayoutSingleComponent", () => {
  let component: LayoutSingleComponent
  let fixture: ComponentFixture<LayoutSingleComponent>

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LayoutSingleComponent],
    }).compileComponents()

    fixture = TestBed.createComponent(LayoutSingleComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it("should create", () => {
    expect(component).toBeTruthy()
  })
})
