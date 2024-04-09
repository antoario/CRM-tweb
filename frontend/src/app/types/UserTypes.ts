export interface UserData {
  email: string
  first_name: string
  last_name: string
  role: number
  id_department?: string
  benefits: []
}

export interface UserSession {
  user: UserData
  token: string
}
