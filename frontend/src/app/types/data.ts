export interface Department {
  department_id: string
  name: string
  description: string
  manager_id: string
}

export interface Employee {
  employee_id: string
  first_name: string
  last_name: string
  date_of_birth: Date
  email: string
  department_id: string
}

export interface Position {
  position_id: string
  title: string
  description: string
  level: string
  department_id: string
}

export interface Projects {
  project_id: string
  name: string
  description: string
  start_date: Date
  end_date: Date | null
  department_id: string
}
