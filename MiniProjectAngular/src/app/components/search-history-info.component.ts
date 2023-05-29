import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription, Observable, switchMap } from 'rxjs';
import { CountryInfo } from '../models/countryinfo';
import { CountryService } from '../services/country.service';

@Component({
  selector: 'app-search-history-info',
  templateUrl: './search-history-info.component.html',
  styleUrls: ['./search-history-info.component.css'],
})
export class SearchHistoryInfoComponent implements OnInit, OnDestroy {
  countryName = '';
  currencyName = '';
  currencySymbol = '';
  area = 0;
  capital = '';
  region = '';
  subregion = '';
  languages = '';
  maps = '';
  population = 0;
  flags = '';
  param$!: Subscription;
  searchHistoryInfo!: Observable<CountryInfo[]>;
  username!: string;
  countryInfos: any[] = [];

  constructor(
    private activatedRoute: ActivatedRoute,
    private countryService: CountryService
  ) {}

  ngOnInit(): void {
    this.searchHistoryInfo = this.activatedRoute.paramMap.pipe(
      switchMap(async (params) => {
        const username = params.get('username') ?? '';
        const countryInfo =
          this.countryService.getSearchHistoryByUsername(username);
        return countryInfo;
      })
    );
    this.searchHistoryInfo.subscribe((countryInfosArray) => {
      console.log('inside search history array sub' + countryInfosArray);
      this.countryInfos = countryInfosArray;
    });
  }

  ngOnDestroy(): void {
    console.log('destroy sub');
    this.param$.unsubscribe();
  }
}
