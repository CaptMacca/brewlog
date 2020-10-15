import { Component, Input, OnInit } from '@angular/core';
import { Brew } from '@app/brew/model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-recent5brews',
  templateUrl: './recent5brews.component.html',
  styleUrls: ['./recent5brews.component.css']
})
export class Recent5brewsComponent implements OnInit {

  @Input() brews: Brew[];

  constructor(private readonly router: Router) {}

  ngOnInit() {}

  viewBrew(brew: Brew) {
    if (brew) {
      this.router.navigate(['/main/brews/' + brew.id]);
    }
  }
}
