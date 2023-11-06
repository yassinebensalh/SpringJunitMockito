import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'dataFilter',
})
export class DataFilterPipe implements PipeTransform {
    transform(
        data: any[],
        nameFilter: string,
        numberFilter: string,
        dateFilter: string
    ): any[] {
        if (!data || (!nameFilter && !numberFilter && !dateFilter)) {
            return data;
        }

        nameFilter = nameFilter.toLowerCase();
        numberFilter = numberFilter.toLowerCase();

        const formated = dateFilter ? new Date(dateFilter) : new Date();

        return data.filter((item) => {
            if (
                formated.toISOString().slice(0, 10) ===
                new Date().toISOString().slice(0, 10)
            ) {
                return (
                    item.full_name.toLowerCase().includes(nameFilter) &&
                    item.phone_number.toLowerCase().includes(numberFilter)
                );
            }
            return (
                new Date(item.payment_date)
                    .toISOString()
                    .slice(0, 10)
                    .includes(formated.toISOString().slice(0, 10)) &&
                item.full_name.toLowerCase().includes(nameFilter) &&
                item.phone_number.toLowerCase().includes(numberFilter)
            );
        });
    }
}
