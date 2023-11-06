import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'concatclasses'
})
export class ConcatclassesPipe implements PipeTransform {

  transform(classNames: any[]) {
    return classNames.filter((v) => Boolean(v)).join(' ');
  }

}
