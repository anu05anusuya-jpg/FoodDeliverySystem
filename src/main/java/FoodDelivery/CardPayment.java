
package FoodDelivery;

public class CardPayment implements Payable {
    @Override
    public void pay(double amount) {
        System.out.printf("Card payment simulated: Rs. %.2f%n", amount);
        System.out.println("Payment successful (simulation).");
    }
}