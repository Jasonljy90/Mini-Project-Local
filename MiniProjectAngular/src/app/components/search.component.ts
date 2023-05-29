import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
})
export class SearchComponent implements OnInit {
  searchForm!: FormGroup;
  countryName?: string;

  countries: String[] = [
    'japan',
    'australia',
    'singapore',
    'canada',
    'south korea',
    'malaysia',
  ];

  constructor(private fb: FormBuilder, private router: Router) {}

  ngOnInit(): void {
    this.searchForm = this.createForm();
  }

  search() {
    const countryName = this.searchForm?.value['countryName'];
    console.log('Search Component: ' + countryName);
    this.router.navigate(['/countryinfo', countryName]);
  }

  createForm(): FormGroup {
    return this.fb.group({
      countryName: this.fb.control('', [Validators.required]),
    });
  }
}
