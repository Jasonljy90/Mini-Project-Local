export interface CountryInfo {
  id: string; // in sql only
  countryName: string; // in sql only
  currencyName: string;
  currencySymbol: string;
  area: number;
  capital: string;
  region: string;
  subregion: string;
  languages: string;
  maps: string;
  population: number;
  flags: string;
  date: Date; // in sql only
  username: string; // in sql only
}
