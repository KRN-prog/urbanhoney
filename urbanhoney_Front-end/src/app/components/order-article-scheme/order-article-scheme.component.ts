import { Component, Input } from '@angular/core';
import { OrdersFromUserResponse } from '../../core/models/response/OrdersFromUserResponse';
import { NgFor } from '@angular/common';

@Component({
  selector: 'app-order-article-scheme',
  imports: [NgFor],
  templateUrl: './order-article-scheme.component.html',
  styleUrl: './order-article-scheme.component.scss'
})
export class OrderArticleSchemeComponent {
  @Input() ordersResponse!: Array<OrdersFromUserResponse>;
}
