package FoodDelivery;

public class UPIPayment implements Payable {
    @Override
    public void pay(double amount) {
        System.out.printf("UPI payment simulated: Rs. %.2f%n", amount);
        System.out.println("Payment successful (simulation).");
    }
}