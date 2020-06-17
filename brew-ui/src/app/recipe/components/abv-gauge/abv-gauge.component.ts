import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-abv-gauge',
  templateUrl: './abv-gauge.component.html',
  styleUrls: ['./abv-gauge.component.css']
})
export class AbvGaugeComponent implements OnInit {

  @Input() abvValue: number;
  @Input() abvMax = 12.0;
  @Input() thickness = 12;

  gaugeType = 'semi';
  gaugeLabel = 'Alcohol %';
  gaugeAppendText = '%';

  constructor() { }

  ngOnInit() {
  }

}
