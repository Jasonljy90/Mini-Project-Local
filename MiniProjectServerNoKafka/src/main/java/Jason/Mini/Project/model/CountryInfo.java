package Jason.Mini.Project.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class CountryInfo {
    private String countryName;
    private String currencyName;
    private String currencySymbol;
    private String capital;
    private String region;
    private String subregion;
    private String languages;
    private Integer area;
    private String maps;
    private Integer population;
    private String flags;
    private Date date;
    private String username;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public CountryInfo() {
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getMaps() {
        return maps;
    }

    public void setMaps(String maps) {
        this.maps = maps;
    }

    public String getFlags() {
        return flags;
    }

    public void setFlags(String flags) {
        this.flags = flags;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public static CountryInfo create(String username, JsonObject doc) {
        final CountryInfo countryInfo = new CountryInfo();
        String countryName = doc.getJsonObject("name").getString("common");
        String code = toCurrencyCode(countryName);
        countryInfo.setCountryName(countryName);
        countryInfo.setCurrencyName(doc.getJsonObject("currencies").getJsonObject(code).getString("name"));
        countryInfo.setCurrencySymbol(doc.getJsonObject("currencies").getJsonObject(code).getString("symbol"));
        countryInfo.setArea(doc.getInt("area"));
        countryInfo.setCapital(doc.getJsonArray("capital").getString(0));
        countryInfo.setRegion(doc.getString("region"));
        countryInfo.setSubregion(doc.getString("subregion"));
        countryInfo.setLanguages(doc.getJsonObject("languages").getString(toCountryCode(countryName)));//
        countryInfo.setMaps(doc.getJsonObject("maps").getString("googleMaps"));
        countryInfo.setPopulation(doc.getInt("population"));
        countryInfo.setFlags(toCountryFlag(countryName));
        countryInfo.setUsername(username);
        countryInfo.setDate(Date.valueOf(LocalDate.now()));

        return countryInfo;
    }

    public JsonObject toJson() {
        JsonObjectBuilder json = Json.createObjectBuilder()
                .add("currencyName", currencyName)
                .add("currencySymbol", currencySymbol)
                .add("capital", capital)
                .add("region", region)
                .add("subregion", subregion)
                .add("languages", languages)
                .add("area", area)
                .add("maps", maps)
                .add("population", population)
                .add("flags", flags);
        return json.build();
    }

    public static String toJsonArray(List<CountryInfo> listCountryInfo) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(listCountryInfo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "error";
    }

    public static String toCurrencyCode(String countryName) {

        HashMap<String, String> currencyCode = new HashMap<String, String>();
        currencyCode.put("japan", "JPY");
        currencyCode.put("australia", "AUD");
        currencyCode.put("singapore", "SGD");
        currencyCode.put("canada", "CAD");
        currencyCode.put("south korea", "KRW");
        currencyCode.put("malaysia", "MYR");

        return currencyCode.get(countryName.toLowerCase());
    }

    public static String toCountryFlag(String countryName) {

        HashMap<String, String> countryFlag = new HashMap<String, String>();
        countryFlag.put("japan", "https://flagcdn.com/w320/jp.png");
        countryFlag.put("australia", "https://flagpedia.net/data/flags/w580/au.webp");
        countryFlag.put("singapore", "https://flagcdn.com/w320/sg.png");
        countryFlag.put("canada", "https://flagcdn.com/w320/ca.png");
        countryFlag.put("south korea", "https://flagpedia.net/data/flags/w580/kr.webp");
        countryFlag.put("malaysia", "https://flagpedia.net/data/flags/w580/my.webp");

        return countryFlag.get(countryName.toLowerCase());
    }

    public static String toCountryCode(String countryName) {

        HashMap<String, String> countryCode = new HashMap<String, String>();
        countryCode.put("japan", "jpn");
        countryCode.put("australia", "eng");
        countryCode.put("singapore", "eng");
        countryCode.put("canada", "eng");
        countryCode.put("south korea", "kor");
        countryCode.put("malaysia", "msa");

        return countryCode.get(countryName.toLowerCase());
    }
}
