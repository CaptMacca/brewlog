import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-brew-summary',
  templateUrl: './summary.component.html',
  styleUrls: ['./summary.component.css']
})
export class SummaryComponent implements OnInit {

  @Output() finaliseBrew = new EventEmitter<string>();

  constructor() { }

  ngOnInit() {
  }

  save() {
    this.finaliseBrew.emit('yes');
  }

  cancel() {
    this.finaliseBrew.emit('no')
  }

}
