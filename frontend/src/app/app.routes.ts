import { Routes } from '@angular/router';
import { UtentiComponent } from './routes/utenti/utenti.component';
import { canActivateTeam } from './guard';
import { EveryoneComponent } from './routes/everyone/everyone.component';
import { LoggedHomeComponent } from './routes/logged-home/logged-home.component';

export const routesLogged: Routes = [
  {
    title: 'Utenti',
    path: 'utenti',
    component: UtentiComponent,
    data: { icon: 'supervisor_account' },
  },
  {
    title: 'Impostazdioni',
    path: 'impostazioni',
    component: UtentiComponent,
    data: { icon: 'settings' },
  },
];
export const routes: Routes = [
  { path: '', component: EveryoneComponent },
  {
    title: 'logged',
    path: 'logged',
    component: LoggedHomeComponent,
    canActivate: [canActivateTeam],
    children: routesLogged,
  },
];
