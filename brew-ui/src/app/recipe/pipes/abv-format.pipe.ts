import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'abvFormat'
})
export class AbvFormatPipe implements PipeTransform {

  transform(value: any, ...args: any[]): any {
    if (value) {
      return value.split(' ').filter(a=> a !== '%').join(' ');
    } else {
      return null;
    }
  }

}
