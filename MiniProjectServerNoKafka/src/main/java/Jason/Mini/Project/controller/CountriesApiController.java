package Jason.Mini.Project.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Jason.Mini.Project.model.CountryInfo;
import Jason.Mini.Project.services.CountriesService;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/countries", produces = MediaType.APPLICATION_JSON_VALUE)
public class CountriesApiController {

    @Autowired
    private CountriesService countriesService;

    // Works with Angular
    @GetMapping(path = "/{username}/{countryName}")
    public ResponseEntity<String> getCountryInfoByName(@PathVariable String username,
            @PathVariable String countryName) throws IOException, InterruptedException {
        CountryInfo countryInfo = countriesService.search(username, countryName);

        if (countryInfo.getCapital() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error");
        }
        return ResponseEntity.ok(countryInfo.toJson().toString());
    }

    // Works with Angular
    @GetMapping(path = "/{username}")
    public ResponseEntity<String> getUserSearchHistory(@PathVariable String username) {
        List<CountryInfo> listCountryInfo = countriesService.searchHistory(username);

        if (listCountryInfo.size() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error");
        }
        return ResponseEntity.ok(CountryInfo.toJsonArray(listCountryInfo));
    }
}
