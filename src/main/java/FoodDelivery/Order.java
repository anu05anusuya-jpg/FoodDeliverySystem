
package FoodDelivery;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private static int nextOrderNumber = 1001;
    private static final double DELIVERY_CHARGE = 40.0;

    private final String orderId;
    private final Customer customer;
    private final List<OrderItem> cart;
    private DeliveryAddress deliveryAddress;
    private String orderStatus;

    public Order(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer is required.");
        }

        this.orderId = "ORD" + nextOrderNumber++;
        this.customer = customer;
        this.cart = new ArrayList<>();
        this.orderStatus = "Cart";
    }

    public String getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<OrderItem> getCart() {
        return cart;
    }

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(DeliveryAddress address) {
        this.deliveryAddress = address;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String status) {
        this.orderStatus = status;
    }

    public boolean hasItems() {
        return !cart.isEmpty();
    }

    public boolean isEmpty() {
        return cart.isEmpty();
    }

    public double getTotalAmount() {
        double total = 0;
        for (OrderItem item : cart) {
            total += item.getSubtotal();
        }
        return total;
    }

    public double getDeliveryCharge() {
        return cart.isEmpty() ? 0 : DELIVERY_CHARGE;
    }

    public double getGrandTotal() {
        return getTotalAmount() + getDeliveryCharge();
    }

    public void addItem(FoodItem food, int quantity) {
        if (food == null || quantity <= 0) {
            throw new IllegalArgumentException("Invalid food or quantity.");
        }

        for (OrderItem item : cart) {
            if (item.getFoodItem().getFoodId()
                    .equalsIgnoreCase(food.getFoodId())) {
                item.addQuantity(quantity);
                return;
            }
        }

        cart.add(new OrderItem(food, quantity));
    }

    public boolean updateQuantity(String foodId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero.");
        }

        for (OrderItem item : cart) {
            if (item.getFoodItem().getFoodId()
                    .equalsIgnoreCase(foodId)) {
                item.setQuantity(quantity);
                return true;
            }
        }
        return false;
    }

    public boolean reduceQuantity(String foodId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero.");
        }

        for (int i = 0; i < cart.size(); i++) {
            OrderItem item = cart.get(i);

            if (item.getFoodItem().getFoodId()
                    .equalsIgnoreCase(foodId)) {
                if (quantity > item.getQuantity()) {
                    return false;
                }

                int remaining = item.getQuantity() - quantity;

                if (remaining == 0) {
                    cart.remove(i);
                } else {
                    item.setQuantity(remaining);
                }
                return true;
            }
        }
        return false;
    }

    public boolean removeItem(String foodId) {
        return cart.removeIf(item ->
                item.getFoodItem().getFoodId()
                        .equalsIgnoreCase(foodId));
    }

    public void displayOrder() {
        System.out.println("\n==============================================");
        System.out.println("                  YOUR CART");
        System.out.println("==============================================");
        System.out.println("Order ID : " + orderId);
        System.out.println("Customer : " + customer.getName());
        System.out.println("----------------------------------------------");

        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        System.out.printf("%-10s %-15s %-10s %-12s%n",
                "Food ID", "Food Name", "Quantity", "Subtotal");
        System.out.println("----------------------------------------------");

        for (OrderItem item : cart) {
            System.out.printf("%-10s %-15s %-10d Rs. %.2f%n",
                    item.getFoodItem().getFoodId(),
                    item.getFoodItem().getFoodName(),
                    item.getQuantity(),
                    item.getSubtotal());
        }

        System.out.println("----------------------------------------------");
        System.out.printf("Food Total      : Rs. %.2f%n", getTotalAmount());
        System.out.printf("Delivery Charge : Rs. %.2f%n", getDeliveryCharge());
        System.out.printf("GRAND TOTAL     : Rs. %.2f%n", getGrandTotal());
        System.out.println("==============================================");

        if (deliveryAddress != null) {
            System.out.println("\nDelivery Address:");
            deliveryAddress.displayAddress();
        }
    }
}