
package FoodDelivery;

import java.util.ArrayList;
import java.util.List;

public class FoodDeliveryService {
    private final List<FoodItem> menu = new ArrayList<>();

    public FoodDeliveryService() {
        menu.add(new FoodItem("FD101", "Burger", 150.00));
        menu.add(new FoodItem("FD102", "Pizza", 250.00));
        menu.add(new FoodItem("FD103", "Sandwich", 100.00));
    }

    public void displayMenu() {
        System.out.println("\n==============================================");
        System.out.println("                  FOOD MENU");
        System.out.println("==============================================");
        System.out.printf("%-10s %-15s %-12s %s%n",
                "Food ID", "Food Name", "Price", "Availability");
        System.out.println("----------------------------------------------");

        for (FoodItem item : menu) {
            System.out.printf("%-10s %-15s Rs. %-8.2f Available%n",
                    item.getFoodId(), item.getFoodName(), item.getPrice());
        }

        System.out.println("==============================================");
    }

    public FoodItem findFoodById(String foodId) {
        for (FoodItem item : menu) {
            if (item.getFoodId().equalsIgnoreCase(foodId.trim())) {
                return item;
            }
        }
        return null;
    }

    public boolean addFoodToOrder(Order order, String foodId, int quantity) {
        FoodItem food = findFoodById(foodId);

        if (food == null) {
            System.out.println("Error: Invalid Food ID.");
            return false;
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero.");
        }

        order.addItem(food, quantity);
        return true;
    }
}