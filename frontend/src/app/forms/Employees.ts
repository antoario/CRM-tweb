import { CustomForm, EmailQuestion, SubForm, TextForm } from "../types/data"

export const addEmployee: CustomForm<any>[] = [
  new TextForm({
    key: "img_url",
    label: "Image Profile",
    value: "",
  }),
  new TextForm({
    key: "first_name",
    label: "First Name",
    required: true,
  }),
  new TextForm({
    key: "last_name",
    label: "Lastname",
    required: true,
  }),
  new EmailQuestion({
    key: "email",
    label: "Email",
    value: "",
    required: false,
  }),

  new SubForm({
    key: "address_info",
    subForm: [new TextForm({ key: "address", label: "Address", required: true })],
  }),
]
