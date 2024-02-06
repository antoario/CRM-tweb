export interface UserData {
  email: string
  name: string
  surname: string
}

export interface UserSession {
  user: UserData
  token: string
}
