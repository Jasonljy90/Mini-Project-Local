package Jason.Mini.Project.services;

import Jason.Mini.Project.repositories.OrdersMongoRepository;
import Jason.Mini.Project.model.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import org.bson.Document;

@Service
public class OrdersMongoService {

    @Autowired
    private OrdersMongoRepository ordersMongoRepository;

    public Document insertOrder(List<Orders> shoppingOrderList, float totalPrice, String username) {
        return ordersMongoRepository.insertOrder(shoppingOrderList, totalPrice, username);
    }

    // Gets review JsonObject by username
    public List<Document> getOrderHistoryByUsername(String username) {
        return ordersMongoRepository.ordersByUsername(username);
    }

    // Gets review JsonObject by username
    // public JsonArray getOrderHistoryByUsername(String username) {
    // JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

    // List<ShoppingOrdersMongo> listAllOrders =
    // ordersMongoRepository.ordersByUsername(username);

    // // Check if review exists
    // if (listAllOrders.size() != 0) {
    // for (int i = 0; i < listAllOrders.size(); i++) {
    // JsonObject jsonObject = Utils.toJsonObj(listAllOrders.get(i));
    // jsonArrayBuilder.add(jsonObject);
    // }
    // JsonArray jsonArray = jsonArrayBuilder.build();
    // return jsonArray;
    // }

    // return Json.createArrayBuilder().build();

    // }
}
