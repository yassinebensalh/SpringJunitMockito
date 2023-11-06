export interface GYM_MEMBER {
    id: number;
    full_name: string;
    phone_number: string;
    payment_date: string;
    payment_amount: number;
    payment_method: string;
    payment_status: string;
    payment_comment: string;
    created_at: string;
    updated_at: string;
}
export interface GYM_MEMBERS {
    all: GYM_MEMBER[];
    paid: GYM_MEMBER[];
    unpaid: GYM_MEMBER[];
}
export enum TAB_NAME {
    ALL = 'all',
    PAID = 'paid',
    UNPAID = 'unpaid',
}
export interface TABS_DATA {
    name: TAB_NAME;
    icon: string;
}
