import {Routes} from '@angular/router';
import {UtentiComponent} from "./routes/utenti/utenti.component";

export const routes: Routes = [
  {
    title: "Home", path: "", component: UtentiComponent, data: {icon: "home"}
  },
  {
    title: "Utenti", path: "utenti", component: UtentiComponent, data: {icon: "supervisor_account"}
  },
  {title: "Impostazdioni", path: "impostazioni", component: UtentiComponent, data: {icon: "settings"}},
];
