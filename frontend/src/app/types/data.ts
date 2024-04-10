export interface Department {
  id: string
  name: string
  description: string
  manager_id: string
}

export interface Employee {
  id: string
  first_name: string
  last_name: string
  date_of_birth: Date
  email: string
  department_id: number
  benefits: string[]
  image_url?: string
  position_id: string
  role: number
  password: string
}

export interface Position {
  id: string
  title: string
  description: string
  level: string
  department_id: string
}

export interface Benefits {
  id: string
  description: string
  value: string
}

export interface Contracts {
  id: string
  employee_id: string
  contract_type: string
  start_date: string
  end_date: string | null
  salary: number
}

export interface Project {
  id: string
  name: string
  description: string
  start_date: Date
  end_date: Date | null
  department_id: string
}

export class CustomForm<T> {
  value: T | undefined
  key: string
  label: string
  required: boolean
  order: number
  type: string
  options: { key: string; value: string }[]
  subForm: Map<string, CustomForm<T>>
  width: string
  subtext: string
  blocked: boolean

  constructor(
    options: {
      value?: T
      key?: string
      label?: string
      required?: boolean
      order?: number
      controlType?: string
      type?: string
      options?: optionSelect[]
      subForm?: Map<string, CustomForm<T>>
      width?: string
      subtext?: string
      blocked?: boolean
    } = {}
  ) {
    this.value = options.value
    this.key = options.key || ""
    this.label = options.label || ""
    this.required = !!options.required
    this.order = options.order === undefined ? 1 : options.order
    this.type = options.type || ""
    this.options = options.options || []
    this.subForm = options.subForm || new Map<string, CustomForm<T>>()
    this.width = options.width || "49%"
    this.subtext = options.subtext || ""
    this.blocked = options.blocked || false
  }
}

export type optionSelect = { key: string; value: string }

export class TextForm extends CustomForm<string> {
  override type = "text"
}

export class SubForm extends CustomForm<any> {
  override type = "group"
}

export class EmailQuestion extends CustomForm<string> {
  override type = "email"
}

export class JustInfo extends CustomForm<string> {
  override type = "info"
}

export class DateQuestion extends CustomForm<string> {
  override type = "date"
}

export class SelectForm extends CustomForm<string> {
  override type = "select"

  setOptions(options: OptionSelect[]) {
    this.options = options
  }
}

export type OptionSelect = { key: string; value: string }
