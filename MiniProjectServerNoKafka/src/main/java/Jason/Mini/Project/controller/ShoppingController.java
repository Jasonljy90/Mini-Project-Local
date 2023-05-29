package Jason.Mini.Project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import Jason.Mini.Project.services.OrdersMongoService;
import Jason.Mini.Project.services.ShoppingService;
import jakarta.json.JsonArray;
import java.util.List;

import org.bson.Document;

@Controller
@RequestMapping(path = "/shopping")
@CrossOrigin(origins = "*")
public class ShoppingController {

    @Autowired
    private ShoppingService shoppingService;

    @Autowired
    private OrdersMongoService ordersMongoService;

    // Works with Angular
    @GetMapping(path = "/search")
    public ResponseEntity<String> searchProducts() {
        JsonArray shoppingInfos = shoppingService.search();

        if (shoppingInfos.size() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error");
        }

        return ResponseEntity.ok(shoppingInfos.toString());
    }

    // Able to get orders to mongodb by username
    @ResponseBody
    @GetMapping(path = "/order/history/{username}", produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public List<Document> getOrderHistoryByUsername(@PathVariable("username") String username) {

        return ordersMongoService.getOrderHistoryByUsername(username);
    }

}
