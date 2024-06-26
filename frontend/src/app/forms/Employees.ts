import { CustomForm, DateQuestion, EmailQuestion, JustInfo, SelectForm, TextForm } from "../types/data"

export const addEmployee: CustomForm<any>[] = [
  new JustInfo({
    order: 0,
    key: "personal_info",
    width: "100%",
    label: "Personal Information",
    subtext: "Add your personal information",
  }),
  new TextForm({
    order: -1,
    key: "url_image",
    label: "Image Profile",
  }),
  new TextForm({
    order: 1,
    key: "first_name",
    label: "First Name",
    required: true,
  }),
  new TextForm({
    order: 2,
    key: "last_name",
    label: "Lastname",
    required: true,
  }),
  new EmailQuestion({
    order: 3,
    key: "email",
    label: "Email",
    required: false,
  }),
  new DateQuestion({
    order: 3,
    key: "date_of_birth",
    label: "Date of Birth",
    required: true,
  }),
  new TextForm({ key: "address", label: "Address", order: 4 }),
  new JustInfo({
    order: 4,
    key: "dep_info",
    width: "100%",
    label: "Assign to department",
    subtext: "Add your personal information. You can assign later",
  }),
  new JustInfo({
    order: 6,
    key: "position_info",
    width: "100%",
    label: "Assign position",
    subtext: "What is the role inside the department. You can assign later",
  }),
]
