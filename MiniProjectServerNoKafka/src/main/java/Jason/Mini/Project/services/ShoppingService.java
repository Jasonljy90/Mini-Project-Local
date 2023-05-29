package Jason.Mini.Project.services;

import java.io.StringReader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonBuilderFactory;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class ShoppingService {

        private final String SHOPPING_PRODUCTS_URL = "https://fakestoreapi.com/products";

        public JsonArray search() {
                RestTemplate template = new RestTemplate();
                ResponseEntity<String> resp = template.getForEntity(SHOPPING_PRODUCTS_URL, String.class);
                String payload = resp.getBody();

                JsonReader reader = Json.createReader(new StringReader(payload));
                JsonArray result = reader.readArray();

                JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

                for (int i = 0; i < result.size(); i++) {
                        JsonBuilderFactory factory = Json.createBuilderFactory(null);
                        JsonObject jsonObject = factory.createObjectBuilder()
                                        .add("item", result.get(i).asJsonObject().get("id"))
                                        .add("title", result.get(i).asJsonObject().getString("title"))
                                        .add("unitPrice", result.get(i).asJsonObject().get("price"))
                                        .add("category", result.get(i).asJsonObject().getString("category"))
                                        .add("description", result.get(i).asJsonObject().getString("description"))
                                        .add("image", result.get(i).asJsonObject().getString("image"))
                                        .build();
                        arrayBuilder.add(jsonObject);
                }
                return arrayBuilder.build();
        }
}
