import { CustomForm, TextForm } from "../../types/data"

export const formBenefits: CustomForm<any>[] = [
  new TextForm({
    key: "description",
    label: "Image Profile",
    required: true,
  }),
  new TextForm({
    key: "value",
    label: "First Name",
    required: true,
  }),
]
