import { Component, OnDestroy, OnInit } from '@angular/core';
import { Observable, Subscription, switchMap } from 'rxjs';
import { CountryInfo } from '../models/countryinfo';
import { CountryService } from '../services/country.service';
import { ActivatedRoute } from '@angular/router';
import { SenderService } from '../services/sender.service';

@Component({
  selector: 'app-country-info',
  templateUrl: './countryinfo.component.html',
  styleUrls: ['./countryinfo.component.css'],
})
export class CountryInfoComponent implements OnInit, OnDestroy {
  param$!: Subscription;
  countryinfo!: Observable<CountryInfo>;
  username!: string;
  countryInfomation!: any;
  originalString!: string;
  modifiedString!: string;

  constructor(
    private activatedRoute: ActivatedRoute,
    private countryService: CountryService,
    private service: SenderService
  ) {}

  ngOnInit(): void {
    this.countryinfo = this.activatedRoute.paramMap.pipe(
      switchMap(async (params) => {
        const countryName = params.get('countryName') ?? '';
        this.originalString = this.service.username;
        this.modifiedString = this.originalString.replace(/"/g, '');
        const countryInfo = this.countryService.getCountryInfoByName(
          this.modifiedString,
          countryName
        );
        return countryInfo;
      })
    );
    this.countryinfo.subscribe((countryInfo) => {
      console.log('inside countryinfo sub' + countryInfo);
      this.countryInfomation = countryInfo;
    });
  }

  ngOnDestroy(): void {
    console.log('destroy sub');
    this.param$.unsubscribe();
  }
}
