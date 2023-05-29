package Jason.Mini.Project.model;

import java.util.List;

public class ShoppingOrdersMongo {
    private String username;
    private double totalPrice;
    private String orderDate;
    private List<ShoppingCart> orders;

    public ShoppingOrdersMongo() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public List<ShoppingCart> getOrders() {
        return orders;
    }

    public void setOrders(List<ShoppingCart> orders) {
        this.orders = orders;
    }

}
