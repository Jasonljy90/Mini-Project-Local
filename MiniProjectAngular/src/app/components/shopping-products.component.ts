import { Component, OnDestroy, OnInit, Output } from '@angular/core';
import { Inventory, Selection } from '../models/shoppingproduct';
import { Subject, Subscription } from 'rxjs';
import { ShoppingService } from '../services/shopping.service';
import { ActivatedRoute } from '@angular/router';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';

@Component({
  selector: 'app-shopping-products',
  templateUrl: './shopping-products.component.html',
  styleUrls: ['./shopping-products.component.css'],
})
export class ShoppingProductsComponent implements OnInit, OnDestroy {
  routeSub$!: Subscription;
  inventories: Inventory[] = [];
  form!: FormGroup;

  displayedColumns: string[] = [
    'image',
    'item',
    'Title',
    'Price',
    'Category',
    'Description',
  ];

  @Output()
  onNewItem = new Subject<Selection>();

  constructor(
    private activatedRoute: ActivatedRoute,
    private shoppingSvc: ShoppingService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    console.log(this.activatedRoute);

    this.routeSub$ = this.activatedRoute.params.subscribe(() => {});
    this.getProductList();
    this.form = this.createForm();
  }

  getProductList() {
    this.shoppingSvc
      .getProducts()
      .then((response) => {
        this.inventories = response;
      })
      .catch((error) => {
        console.log(error);
      });
  }

  ngOnDestroy() {
    this.routeSub$.unsubscribe;
  }

  selectItem(item: number) {
    console.info(`item: ${item}`);
    const itemCtrl = this.form.get('item') as FormControl;
    const unitPriceCtrl = this.form.get('unitPrice') as FormControl;
    const inv = this.findItem(item);
    if (!!inv) {
      itemCtrl.setValue(inv.item);
      unitPriceCtrl.setValue(inv.unitPrice);
    }
  }

  addToCart() {
    const selection = this.form.value as Selection;
    this.onNewItem.next(selection);
    this.form.reset();
  }

  private createForm(): FormGroup {
    return this.fb.group({
      item: this.fb.control<number>(0, [Validators.required]),
      unitPrice: this.fb.control<number>(0, [
        Validators.required,
        Validators.min(0.05),
      ]),
      quantity: this.fb.control<number>(0, [
        Validators.required,
        Validators.min(1),
      ]),
    });
  }

  private findItem(item: number): Inventory | undefined {
    return this.inventories.find((i) => i.item == item);
  }
}
