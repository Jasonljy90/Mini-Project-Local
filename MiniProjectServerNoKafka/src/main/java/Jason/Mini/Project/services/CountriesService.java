package Jason.Mini.Project.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.JsonReader;
import jakarta.json.Json;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import jakarta.json.JsonObject;

import Jason.Mini.Project.model.CountryInfo;
import Jason.Mini.Project.repositories.CountryRepository;

@Service
public class CountriesService {

    @Autowired
    private CountryRepository countryRepository;

    private final String URL_COUNTRY = "https://rest-country-api.p.rapidapi.com/";

    public CountryInfo search(String username, String countryName) throws IOException, InterruptedException {
        String countryUrl = URL_COUNTRY + countryName;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(countryUrl))
                .header("X-RapidAPI-Key", "f25cfa8427msh99b5f021065da47p1eb6e0jsn8b4785f9e11e")
                .header("X-RapidAPI-Host", "rest-country-api.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        String payload = response.body();

        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject data = reader.readObject();

        CountryInfo countryInfo = CountryInfo.create(username, data);
        countryRepository.saveSearch(countryInfo);
        return countryInfo;
    }

    public List<CountryInfo> searchHistory(String username) {
        final SqlRowSet searchHistoryRows = countryRepository.findOrderDetailsByOrderId(username);
        List<CountryInfo> listCountryInfo = new ArrayList<>();

        while (searchHistoryRows.next()) {
            CountryInfo countryInfo = new CountryInfo();
            countryInfo.setCountryName(searchHistoryRows.getString("country_name"));
            countryInfo.setCurrencyName(searchHistoryRows.getString("currency_name"));
            countryInfo.setCurrencySymbol(searchHistoryRows.getString("currency_symbol"));
            countryInfo.setArea(searchHistoryRows.getInt("area"));
            countryInfo.setCapital(searchHistoryRows.getString("capital"));
            countryInfo.setRegion(searchHistoryRows.getString("region"));
            countryInfo.setSubregion(searchHistoryRows.getString("subregion"));
            countryInfo.setLanguages(searchHistoryRows.getString("languages"));
            countryInfo.setMaps(searchHistoryRows.getString("maps"));
            countryInfo.setPopulation(searchHistoryRows.getInt("population"));
            countryInfo.setFlags(searchHistoryRows.getString("flags"));
            countryInfo.setDate(searchHistoryRows.getDate("date"));
            countryInfo.setUsername(searchHistoryRows.getString("username"));
            listCountryInfo.add(countryInfo);
        }
        return listCountryInfo;
    }
}
