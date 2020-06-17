import { Component, Input, OnInit } from '@angular/core';
import { Brew } from '@app/model';

@Component({
  selector: 'app-recent5brews',
  templateUrl: './recent5brews.component.html',
  styleUrls: ['./recent5brews.component.css']
})
export class Recent5brewsComponent implements OnInit {

  @Input() brews: Brew[];

  constructor() {}

  ngOnInit() {}
}
