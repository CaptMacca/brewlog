import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-ibu-gauge',
  templateUrl: './ibu-gauge.component.html',
  styleUrls: ['./ibu-gauge.component.css']
})
export class IbuGaugeComponent implements OnInit {

  @Input() ibuValue: number;
  @Input() ibuMax = 12.0;
  @Input() thickness = 12;

  gaugeType = 'semi';
  gaugeLabel = 'Bitterness';
  gaugeAppendText = 'IBU';

  constructor() { }

  ngOnInit() {
  }

}
