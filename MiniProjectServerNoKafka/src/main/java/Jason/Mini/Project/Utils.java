package Jason.Mini.Project;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Jason.Mini.Project.model.Orders;

public class Utils {

    // Convert ShoppingOrders obj to Document
    public static Document toDocument(List<Orders> shoppingOrderList, float totalPrice, String username) {
        Document document = new Document();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date orderDate = new Date();
        document.put("username", username);
        document.put("totalPrice", totalPrice / 100);
        document.put("orderDate", formatter.format(orderDate));
        document.put("orders", shoppingOrderList);

        return document;
    }

    public static List<Orders> toList(String shoppingCartList)
            throws JsonMappingException, JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        List<Orders> checkoutList = objectMapper.readValue(shoppingCartList, new TypeReference<List<Orders>>() {
        });
        return checkoutList;
    }

}
