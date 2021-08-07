import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'ibuFormat'
})
export class IbuFormatPipe implements PipeTransform {

  transform(value: any, ...args: any[]): any {
    if (value) {
      return value.split(' ')
        .filter(a => a !== 'IBUS')
        .filter(a => a !== 'IBU')
        .join('');
    } else {
      return null;
    }
  }
}
