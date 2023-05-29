package Jason.Mini.Project.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

import Jason.Mini.Project.Utils;
import Jason.Mini.Project.model.ChargeRequest;
import Jason.Mini.Project.model.Checkout;
import Jason.Mini.Project.model.Orders;
import Jason.Mini.Project.model.ChargeRequest.Currency;
import Jason.Mini.Project.services.OrdersMongoService;
import Jason.Mini.Project.services.StripePaymentService;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

import java.math.BigDecimal;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin(origins = "*")
public class ChargeController {

    @Autowired
    private StripePaymentService paymentsService;

    @Autowired
    private OrdersMongoService ordersMongoService;

    /*
     * String username, float totalPrice,
     * List<ShoppingCart> shoppingCartList, String stripeToken,
     */
    @ResponseBody
    @PostMapping(path = "/charge", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public JsonObject charge(@RequestBody Checkout checkout) throws Exception {

        ChargeRequest chargeRequest = new ChargeRequest();
        List<Orders> orders = Utils.toList(checkout.getOrders());
        BigDecimal total = new BigDecimal(checkout.getTotalPrice());
        int totalPrice = total.intValue();
        chargeRequest.setAmount(totalPrice);
        chargeRequest.setDescription("Jason Travel Store");
        chargeRequest.setCurrency(Currency.EUR);
        chargeRequest.setStripeEmail("jasonljy90@gmail.com");
        chargeRequest.setStripeToken(checkout.getStripeToken());

        Charge charge = paymentsService.charge(chargeRequest);
        System.out.println("status: " + charge.getStatus());
        if (!charge.getStatus().equals("succeeded")) {
            throw new Exception("Payment Failed");
        }
        String usernameInput = checkout.getUsername();
        String username = usernameInput.substring(1, usernameInput.length() - 1);

        // Save to Mongo
        Document insertDoc = ordersMongoService.insertOrder(orders,
                totalPrice,
                username);
        if (insertDoc.get("totalPrice") == null) {
            throw new InvalidDataAccessApiUsageException("Error saving to mongo");
        }

        List<Orders> ordersDb = insertDoc.getList("orders", Orders.class);

        if (ordersDb.size() > 0) {
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
            jsonObjectBuilder.add("success", true);
            JsonObject result = jsonObjectBuilder.build();
            return result;
        }
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("success", false);
        JsonObject result = jsonObjectBuilder.build();
        return result;
    }

    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        return "result";
    }
}