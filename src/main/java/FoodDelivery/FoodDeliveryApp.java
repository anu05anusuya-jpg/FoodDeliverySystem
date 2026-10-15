
package FoodDelivery;

import java.util.Scanner;

public class FoodDeliveryApp {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            FoodDeliveryService service = new FoodDeliveryService();
            OrderHistory orderHistory = new OrderHistory();

            System.out.println("\n==================================");
            System.out.println("    FOOD DELIVERY MANAGEMENT");
            System.out.println("==================================");

            String name = readRequired(scanner, "Enter your name: ");
            String phone = readPhone(scanner);
            String customerId = readRequired(scanner, "Enter customer ID: ");
            String email = readEmail(scanner);

            Customer customer = new Customer(name, phone, customerId, email);

            DeliveryAddress address = readAddress(scanner);

            Order order = new Order(customer);
            order.setDeliveryAddress(address);

            boolean running = true;

            while (running) {
                displayMainMenu();

                int choice = readIntInRange(
                        scanner, "Enter choice: ", 1, 9);

                try {
                    switch (choice) {

                        case 1:
                            service.displayMenu();
                            break;

                        case 2:
                            service.displayMenu();

                            String foodId = readRequired(
                                    scanner, "Enter Food ID: ");

                            int quantity = readPositiveInt(
                                    scanner, "Enter quantity: ");

                            if (service.addFoodToOrder(
                                    order, foodId, quantity)) {
                                System.out.println(
                                        "Food added to cart successfully!");
                                showTotals(order);
                            }
                            break;

                        case 3:
                            if (order.hasItems()) {
                                order.displayOrder();
                            } else {
                                System.out.println("Your cart is empty.");
                            }
                            break;

                        case 4:
                            if (!order.hasItems()) {
                                System.out.println(
                                        "Error: Your cart is empty.");
                                break;
                            }

                            order.displayOrder();

                            String updateId = readRequired(
                                    scanner, "Enter Food ID to update: ");

                            int newQuantity = readPositiveInt(
                                    scanner, "Enter new total quantity: ");

                            if (order.updateQuantity(
                                    updateId, newQuantity)) {
                                System.out.println(
                                        "Quantity updated successfully!");
                                showTotals(order);
                            } else {
                                System.out.println(
                                        "Error: Food ID not found in cart.");
                            }
                            break;

                        case 5:
                            if (!order.hasItems()) {
                                System.out.println(
                                        "Error: Your cart is empty.");
                                break;
                            }

                            String reduceId = readRequired(
                                    scanner, "Enter Food ID to reduce: ");

                            int reduceBy = readPositiveInt(
                                    scanner, "How many quantity to reduce? ");

                            if (order.reduceQuantity(reduceId, reduceBy)) {
                                System.out.println(
                                        "Quantity reduced successfully!");
                                showTotals(order);
                            } else {
                                System.out.println(
                                        "Error: Food not found, or reduction "
                                                + "exceeds current quantity.");
                            }
                            break;

                        case 6:
                            if (!order.hasItems()) {
                                System.out.println(
                                        "Error: Your cart is empty.");
                                break;
                            }

                            order.displayOrder();

                            String removeId = readRequired(
                                    scanner, "Enter Food ID to remove: ");

                            if (order.removeItem(removeId)) {
                                System.out.println(
                                        "Food removed completely.");
                                showTotals(order);
                            } else {
                                System.out.println(
                                        "Error: Food ID not found in cart.");
                            }
                            break;

                        case 7:
                            if (!order.hasItems()) {
                                System.out.println(
                                        "Error: Add food before checkout.");
                                break;
                            }

                            // Display the cart and delivery address once.
                            order.displayOrder();

                            String confirm = readRequired(
                                    scanner, "Confirm order? (yes/no): ");

                            if (confirm.equalsIgnoreCase("yes")
                                    || confirm.equalsIgnoreCase("y")) {

                                boolean paid = processPayment(scanner, order);

                                if (paid) {
                                    order.setOrderStatus("Order Placed");

                                    displayReceipt(order);

                                    orderHistory.addOrder(order);

                                    // Start a new cart for the next order.
                                    order = new Order(customer);
                                    order.setDeliveryAddress(address);

                                } else {
                                    System.out.println(
                                            "Payment not completed. Try again.");
                                }

                            } else if (confirm.equalsIgnoreCase("no")
                                    || confirm.equalsIgnoreCase("n")) {
                                System.out.println("Checkout cancelled.");
                            } else {
                                System.out.println(
                                        "Invalid input. Enter yes or no.");
                            }
                            break;

                        case 8:
                            System.out.println(
                                    "Orders placed this session: "
                                            + orderHistory.getOrderCount());
                            System.out.println("Thank you for visiting!");
                            running = false;
                            break;

                        case 9:
                            orderHistory.displayHistory();
                            break;

                        default:
                            System.out.println("Invalid menu choice.");
                    }

                } catch (IllegalArgumentException e) {
                    System.out.println("Input Error: " + e.getMessage());

                } catch (Exception e) {
                    System.out.println(
                            "Unexpected error: Please try again.");
                }
            }

        } catch (Exception e) {
            System.out.println("Application error: " + e.getMessage());
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n========== MAIN MENU ==========");
        System.out.println("1. View Food Menu");
        System.out.println("2. Add Food to Cart");
        System.out.println("3. View Cart");
        System.out.println("4. Update Food Quantity");
        System.out.println("5. Reduce Food Quantity");
        System.out.println("6. Remove Food Completely");
        System.out.println("7. Checkout");
        System.out.println("8. Exit");
        System.out.println("9. View Order History");
        System.out.println("===============================");
    }

    private static String readRequired(
            Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String value = scanner.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("Error: This field cannot be empty.");
        }
    }

    private static String readPhone(Scanner scanner) {
        while (true) {
            String phone = readRequired(
                    scanner, "Enter 10-digit phone number: ");

            if (phone.matches("[6-9]\\d{9}")) {
                return phone;
            }

            System.out.println(
                    "Error: Enter a valid 10-digit Indian phone number.");
        }
    }

    private static String readEmail(Scanner scanner) {
        while (true) {
            String email = readRequired(scanner, "Enter email: ");

            if (email.matches(
                    "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                return email;
            }

            System.out.println("Error: Enter a valid email address.");
        }
    }

    private static DeliveryAddress readAddress(Scanner scanner) {
        System.out.println("\n========== DELIVERY ADDRESS ==========");

        String houseNumber = readRequired(
                scanner, "Enter house number: ");
        String street = readRequired(
                scanner, "Enter street: ");
        String city = readRequired(
                scanner, "Enter city: ");

        String pincode;

        while (true) {
            pincode = readRequired(
                    scanner, "Enter 6-digit pincode: ");

            if (pincode.matches("\\d{6}")) {
                break;
            }

            System.out.println(
                    "Error: Pincode must contain exactly 6 digits.");
        }

        return new DeliveryAddress(
                houseNumber, street, city, pincode);
    }

    private static int readPositiveInt(
            Scanner scanner, String message) {
        return readIntInRange(
                scanner, message, 1, Integer.MAX_VALUE);
    }

    private static int readIntInRange(
            Scanner scanner,
            String message,
            int min,
            int max) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            try {
                int value = Integer.parseInt(input);

                if (value >= min && value <= max) {
                    return value;
                }

                System.out.println(
                        "Error: Enter a number from "
                                + min + " to " + max + ".");

            } catch (NumberFormatException e) {
                System.out.println(
                        "Error: Invalid number. Enter digits only.");
            }
        }
    }

    private static void showTotals(Order order) {
        System.out.printf("Food Total: Rs. %.2f%n",
                order.getTotalAmount());

        System.out.printf("Delivery Charge: Rs. %.2f%n",
                order.getDeliveryCharge());

        System.out.printf("Grand Total: Rs. %.2f%n",
                order.getGrandTotal());
    }

    private static boolean processPayment(
            Scanner scanner, Order order) {
        System.out.println("\n========== PAYMENT ==========");

        System.out.printf("Amount to pay: Rs. %.2f%n",
                order.getGrandTotal());

        System.out.println("1. UPI");
        System.out.println("2. Card");
        System.out.println("3. Cash on Delivery");

        int choice = readIntInRange(
                scanner, "Select payment method (1-3): ", 1, 3);

        Payable payment;

        switch (choice) {
            case 1:
                payment = new UPIPayment();
                break;
            case 2:
                payment = new CardPayment();
                break;
            case 3:
                payment = new CashPayment();
                break;
            default:
                throw new IllegalArgumentException(
                        "Invalid payment method.");
        }

        payment.pay(order.getGrandTotal());

        // This is a payment simulation, not a real gateway.
        return true;
    }

    private static void displayReceipt(Order order) {
        System.out.println("\n========================================");
        System.out.println("          FINAL BILL RECEIPT");
        System.out.println("========================================");

        System.out.println("Order ID : " + order.getOrderId());
        System.out.println("Customer : " + order.getCustomer().getName());

        // Address is shown during checkout, not repeated in the receipt.
        System.out.println("----------------------------------------");

        System.out.printf("Food Total      : Rs. %.2f%n",
                order.getTotalAmount());

        System.out.printf("Delivery Charge : Rs. %.2f%n",
                order.getDeliveryCharge());

        System.out.printf("GRAND TOTAL     : Rs. %.2f%n",
                order.getGrandTotal());

        System.out.println("----------------------------------------");
        System.out.println("Order Status    : " + order.getOrderStatus());
        System.out.println("Thank you for ordering!");
        System.out.println("========================================");
    }
}