export enum costTypes {
    'cost' = 'cost',
    'service' = 'service',
    'faq' = 'faq',
    'blog' = 'blog',
}
export enum CostType {
    'enregistrement' = 'enregistrement',
    'timbrage' = 'timbrage',
}

export enum PieceType {
    'effet' = 'effet',
    'jugement' = 'jugement',
    'contrat de prêt' = 'contrat de prêt',
    'contrat de caution' = 'contrat de caution',
}
export interface filterCriteria {
    name: string;
    number: number;
    date: Date;
}
