package FoodDelivery;

public class OrderItem {
    private FoodItem foodItem;
    private int quantity;

    public OrderItem(FoodItem foodItem, int quantity) {
        if (foodItem == null || quantity <= 0) {
            throw new IllegalArgumentException("Invalid food or quantity.");
        }
        this.foodItem = foodItem;
        this.quantity = quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }
        this.quantity = quantity;
    }

    public FoodItem getFoodItem() {
        return foodItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void addQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }
        this.quantity += quantity;
    }

    public double getSubtotal() {
        return foodItem.getPrice() * quantity;
    }

    public void displayItem() {
        System.out.printf("%-15s x %-3d Rs. %.2f%n",
                foodItem.getFoodName(), quantity, getSubtotal());
    }
}