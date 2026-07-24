// app.component.ts — The root component of the application.
// Uses the 'app-root' selector and is bootstrapped in main.ts.
// Contains the app-header and router-outlet to display routed views.

import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './components/header/header.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, HeaderComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'student-course-portal';
}
