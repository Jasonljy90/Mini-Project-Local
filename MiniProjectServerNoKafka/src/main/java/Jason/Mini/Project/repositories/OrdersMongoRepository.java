package Jason.Mini.Project.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import Jason.Mini.Project.Utils;
import Jason.Mini.Project.model.Orders;
import java.util.List;

import org.bson.Document;

@Repository
public class OrdersMongoRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    public Document insertOrder(List<Orders> shoppingOrderList, float totalPrice, String username) {
        Document doc = Utils.toDocument(shoppingOrderList, totalPrice, username);
        return mongoTemplate.insert(doc, "orders");
    }

    public List<Document> ordersByUsername(String username) {
        Query query = Query.query(Criteria.where("username").is(username));

        List<Document> result = mongoTemplate.find(query, Document.class, "orders");

        return result;
    }

    // public List<ShoppingOrdersMongo> ordersByUsername(String username) {
    // Query query = Query.query(Criteria.where("username").is(username));

    // List<Document> result = mongoTemplate.find(query, Document.class, "orders");

    // List<ShoppingOrdersMongo> listAllOrders = new ArrayList<>();

    // for (Document x : result) {
    // ShoppingOrdersMongo shoppingOrdersMongo = new ShoppingOrdersMongo();
    // shoppingOrdersMongo.setUsername(x.getString("username"));
    // shoppingOrdersMongo.setOrderDate(x.getString("orderDate"));
    // shoppingOrdersMongo.setTotalPrice(x.getDouble("totalPrice"));

    // List<Document> ordersDoc = x.getList("orders", Document.class);
    // List<ShoppingCart> orderList = new ArrayList<>();

    // for (Document order : ordersDoc) {
    // ShoppingCart shoppingCart = new ShoppingCart();
    // shoppingCart.setProductId(order.getInteger("productId"));
    // shoppingCart.setPrice(order.getDouble("price"));
    // shoppingCart.setQuantity(order.getInteger("quantity"));
    // orderList.add(shoppingCart);
    // }

    // shoppingOrdersMongo.setOrders(orderList);

    // listAllOrders.add(shoppingOrdersMongo);
    // }
    // return listAllOrders;
    // }
    // public List<ShoppingOrdersMongo> ordersByUsername(String username) {

    // List<ShoppingOrdersMongo> listAllOrders =
    // mongoTemplate.findAll(ShoppingOrdersMongo.class, "orders");

    // List<ShoppingOrdersMongo> listWithUsername = listAllOrders
    // .stream()
    // .filter(p -> p.getUsername().equals(username))
    // .collect(Collectors.toList());
    // return listWithUsername;
    // }

}
