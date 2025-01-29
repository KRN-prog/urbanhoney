import { Component, OnInit, Renderer2 } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {
  title = 'Urbanhoney';

  constructor(private renderer: Renderer2) {}

  ngOnInit() {
    this.renderer.setStyle(document.body, 'margin', '0');
    this.renderer.setStyle(document.body, 'padding', '0');
    this.renderer.setStyle(document.body, 'font-family', 'sans-serif');
  }
}
