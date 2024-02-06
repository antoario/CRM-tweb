import { FormField } from "../types/forms"

export class FormControl<T> {
  value: T | undefined
  key: string
  label: string
  required: boolean
  order: number
  type: string
  options: { key: string; value: string }[]

  constructor(
    options: {
      value?: T
      key?: string
      label?: string
      required?: boolean
      order?: number
      controlType?: string
      type?: string
      options?: { key: string; value: string }[]
    } = {}
  ) {
    this.value = options.value
    this.key = options.key || ""
    this.label = options.label || ""
    this.required = !!options.required
    this.order = options.order === undefined ? 1 : options.order
    this.type = options.type || ""
    this.options = options.options || []
  }
}

export class TextQuestion extends FormControl<string> {
  override type = "text"
}

export class EmailQuestion extends FormControl<string> {
  override type = "email"
}

export const addEmployee: FormControl<any>[] = [
  new TextQuestion({
    key: "firstName",
    label: "First Name",
    value: "Mario",
    required: true,
    order: 1,
  }),
  new TextQuestion({
    key: "surname",
    label: "Surname",
    value: "Rossi",
    required: true,
    order: 1,
  }),
  new EmailQuestion({
    key: "email",
    label: "Email",
    value: "",
    required: true,
    order: 2,
  }),
]
