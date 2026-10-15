package FoodDelivery;

import java.util.ArrayList;
import java.util.List;

public class OrderHistory {

    private final List<Order> completedOrders = new ArrayList<>();

    public void addOrder(Order order) {
        if (order != null && order.hasItems()) {
            completedOrders.add(order);
            System.out.println("Order saved to history!");
        }
    }

    public int getOrderCount() {
        return completedOrders.size();
    }

    public void displayHistory() {

        System.out.println("\n========================================");
        System.out.println("              ORDER HISTORY");
        System.out.println("========================================");

        if (completedOrders.isEmpty()) {
            System.out.println("No orders placed yet.");
            System.out.println("========================================");
            return;
        }

        for (Order order : completedOrders) {

            System.out.println("\nOrder ID     : " + order.getOrderId());
            System.out.println("Customer     : "
                    + order.getCustomer().getName());
            System.out.println("Order Status : "
                    + order.getOrderStatus());

            System.out.println("----------------------------------------");

            System.out.printf("Food Total      : Rs. %.2f%n",
                    order.getTotalAmount());

            System.out.printf("Delivery Charge : Rs. %.2f%n",
                    order.getDeliveryCharge());

            System.out.printf("GRAND TOTAL     : Rs. %.2f%n",
                    order.getGrandTotal());

            System.out.println("----------------------------------------");
        }

        System.out.println("Total orders placed: "
                + completedOrders.size());
        System.out.println("========================================");
    }
}

