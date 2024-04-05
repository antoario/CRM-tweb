export interface UserData {
  email: string
  name: string
  surname: string
  role: number
  idDepartment?: string
  benefits: []
}

export interface UserSession {
  user: UserData
  token: string
}
