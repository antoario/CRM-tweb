import { ROLE } from "./types"

export const MENUITEMS: { text: string; link: string; icon: string; canActive: number }[] = [
  { text: "Home", link: "/", icon: "home", canActive: ROLE.employee },
  { text: "Employee", link: "/employees", icon: "group", canActive: ROLE.manager },
  { text: "Departments", link: "/departments", icon: "diversity_2", canActive: ROLE.manager },
  { text: "Projects", link: "/projects", icon: "home_repair_service", canActive: ROLE.manager },
  { text: "Positions", link: "/positions", icon: "radar", canActive: ROLE.manager },
  // { text: "Contracts", link: "/contracts", icon: "description" },
  { text: "Benefits", link: "/benefits", icon: "diversity_1", canActive: ROLE.manager },
]
