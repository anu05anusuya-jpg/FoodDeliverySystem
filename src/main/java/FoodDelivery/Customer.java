
package FoodDelivery;

public class Customer extends Person {
    private String customerId;
    private String email;

    public Customer(String name, String phone,
                    String customerId, String email) {
        super(name, phone);
        this.customerId = customerId;
        this.email = email;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getEmail() {
        return email;
    }
}