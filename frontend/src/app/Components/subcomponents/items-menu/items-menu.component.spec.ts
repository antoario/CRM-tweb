import { ComponentFixture, TestBed } from "@angular/core/testing"

import { ItemsMenuComponent } from "./items-menu.component"

describe("ItemsMenuComponent", () => {
  let component: ItemsMenuComponent
  let fixture: ComponentFixture<ItemsMenuComponent>

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ItemsMenuComponent],
    }).compileComponents()

    fixture = TestBed.createComponent(ItemsMenuComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it("should create", () => {
    expect(component).toBeTruthy()
  })
})
