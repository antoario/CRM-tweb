export interface User {
  id: string
  name: string
  surname: string
  token: string
  email: string
  role: string
  phone_number: string
}

export interface Columns {
  key: string
  label: string
}

export enum ROLE {
  superAdmin,
  manager,
  employee,
}
