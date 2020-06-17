import { Component, Input, OnInit } from '@angular/core';
import { AbstractControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-brew-notes',
  templateUrl: './brew-notes.component.html',
  styleUrls: ['./brew-notes.component.css']
})
export class BrewNotesComponent implements OnInit {

  @Input()
  parentForm: FormGroup;

  constructor() { }

  ngOnInit() {
  }

  public get notes(): AbstractControl {
    return this.parentForm.get('notes');
  }

  public get tastingNotes(): AbstractControl {
    return this.parentForm.get('tastingNotes');
  }

}
