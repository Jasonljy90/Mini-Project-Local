import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { lastValueFrom } from 'rxjs';
import { CountryInfo } from '../models/countryinfo';

@Injectable({
  providedIn: 'root',
})
export class CountryService {
  private API_URI: string = '/api/countries';
  message: any;

  constructor(private httpClient: HttpClient) {}

  getCountryInfoByName(username: string, countryName: string): Promise<any> {
    const headers = new HttpHeaders().set(
      'Content-Type',
      'application/json; charset=utf-8'
    );

    return lastValueFrom(
      this.httpClient.get<CountryInfo[]>(
        `${this.API_URI}/${username}/${countryName}`,
        { headers: headers }
      )
    );
  }

  getSearchHistoryByUsername(username: string): any {
    const headers = new HttpHeaders().set(
      'Content-Type',
      'application/json; charset=utf-8'
    );

    this.message = lastValueFrom(
      this.httpClient.get<CountryInfo[]>(`${this.API_URI}/${username}`, {
        headers: headers,
      })
    );
    console.log(this.message);
    return this.message;
  }
}
