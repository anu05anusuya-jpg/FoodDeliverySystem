
package FoodDelivery;

public class CashPayment implements Payable {
    @Override
    public void pay(double amount) {
        System.out.println("Cash on Delivery selected.");
        System.out.printf("Amount to collect: Rs. %.2f%n", amount);
        System.out.println("Payment will be collected upon delivery.");
    }
}