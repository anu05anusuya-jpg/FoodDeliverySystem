
package FoodDelivery;

public class DeliveryAddress {
    private String houseNumber;
    private String street;
    private String city;
    private String pincode;

    public DeliveryAddress(String houseNumber, String street,
                           String city, String pincode) {
        this.houseNumber = houseNumber;
        this.street = street;
        this.city = city;
        this.pincode = pincode;
    }

    public void displayAddress() {
        System.out.println("House Number : " + houseNumber);
        System.out.println("Street       : " + street);
        System.out.println("City         : " + city);
        System.out.println("Pincode      : " + pincode);
    }
}
