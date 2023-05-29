export interface Inventory {
  item: number;
  title: string;
  unitPrice: number;
  category: string;
  description: string;
  image: string;
}

export interface Selection extends Inventory {
  quantity: number;
}
