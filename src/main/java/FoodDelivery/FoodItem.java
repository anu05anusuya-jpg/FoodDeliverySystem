
package FoodDelivery;

public class FoodItem {
    private String foodId;
    private String foodName;
    private double price;

    public FoodItem(String foodId, String foodName, double price) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.price = price;
    }

    public String getFoodId() {
        return foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public double getPrice() {
        return price;
    }
}