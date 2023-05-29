import { Component } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { SenderService } from '../services/sender.service';

@Component({
  selector: 'app-search-history',
  templateUrl: './search-history.component.html',
  styleUrls: ['./search-history.component.css'],
})
export class SearchHistoryComponent {
  searchHistoryForm!: FormGroup;
  originalString!: string;
  modifiedString!: string;

  constructor(private router: Router, private service: SenderService) {}

  ngOnInit(): void {
    this.originalString = this.service.username;
    this.modifiedString = this.originalString.replace(/"/g, '');
    this.router.navigate(['/searchhistory', this.modifiedString]);
  }
}
