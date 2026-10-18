import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // fire up the scanner to read what the user types
        Scanner scanner = new Scanner(System.in);
        Login user = registerAndLogin(scanner);

        // stop the program here if login fails
        if (user == null) {
            return;
        }

        System.out.println("Welcome to QuickChat.");

        // ask how many loops the user wants to do for messages
        int messageLimit = readNonNegativeInt(
                scanner, "How many messages would you like to enter? ");

        int enteredMessages = 0;
        boolean running = true;
        boolean totalDisplayed = false;

        Message messageSession =
                new Message("0000000000", 0, "", "");

        // main menu loop
        while (running) {
            System.out.println("\n1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");

            String option = scanner.nextLine().trim();

            switch (option) {
                case "1":
                    // check if we hit the limit the user typed earlier
                    if (enteredMessages >= messageLimit) {
                        System.out.println(
                                "You have reached the number of messages you selected.");
                        break;
                    }

                    // call method to prompt for recipient and text
                    Message newMessage = enterMessage(scanner, enteredMessages);

                    System.out.println("Message ID generated: "
                            + newMessage.getMessageId());
                    System.out.println("\n" + newMessage.toDisplayString());
                    System.out.println("Message Number: "
                            + newMessage.getMessageNumber());

                    // prompt user what to do with the message
                    System.out.println("\n1) Send Message");
                    System.out.println("2) Disregard Message");
                    System.out.println("3) Store Message to send later");
                    System.out.print("Choose an option: ");

                    String action = scanner.nextLine().trim();
                    String actionResult = newMessage.SentMessage(action);
                    System.out.println(actionResult);

                    // handle disregard choice and zero deletion check
                    if (action.equals("2")) {
                        System.out.print("Enter 0 to delete this message: ");

                        while (!scanner.nextLine().trim().equals("0")) {
                            System.out.print(
                                    "Please enter 0 to delete this message: ");
                        }
                    } else if (action.equals("1")
                            || action.equals("3")) {
                        if (!newMessage.storeMessage()) {
                            System.out.println("Could not write messages.json.");
                        }
                    }

                    enteredMessages++;

                    // show total if loop limit is reached
                    if (enteredMessages == messageLimit && !totalDisplayed) {
                        System.out.println("Total messages sent: "
                                + messageSession.returnTotalMessagess());
                        totalDisplayed = true;
                    }
                    break;

                case "2":
                    // feature coming later
                    System.out.println("Coming Soon.");
                    break;

                case "3":
                    // break out of the while loop to exit
                    running = false;
                    break;

                default:
                    System.out.println("Please choose 1, 2, or 3.");
            }
        }

        if (!totalDisplayed) {
            System.out.println("Total messages sent: "
                    + messageSession.returnTotalMessagess());
        }

        scanner.close();
    }

    // handles user registration and login flow
    private static Login registerAndLogin(Scanner scanner) {
        System.out.println("Registration");

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter cellphone number (+27 followed by 9 digits): ");
        String cellPhoneNumber = scanner.nextLine();

        Login user = new Login(username, password, cellPhoneNumber,
                firstName, lastName);

        System.out.println("\n--- REGISTRATION STATUS ---");
        System.out.println(user.registerUser());

        if (!user.isRegistrationValid()) {
            System.out.println(
                    "Registration failed. Please restart and try again.");
            return null;
        }

        System.out.println("\nLogin user");
        System.out.print("Enter login username: ");
        String inputUsername = scanner.nextLine();

        System.out.print("Enter login password: ");
        String inputPassword = scanner.nextLine();

        boolean authenticated =
                user.loginUser(inputUsername, inputPassword);

        System.out.println("\n--- LOGIN STATUS ---");
        System.out.println(user.returnLoginStatus(authenticated));

        return authenticated ? user : null;
    }

    // helper function to capture and validate message details
    private static Message enterMessage(Scanner scanner, int messageNumber) {
        String recipient;

        // keep asking until phone number is valid
        while (true) {
            System.out.print(
                    "Enter recipient cellphone number (+27 followed by 9 digits): ");
            recipient = scanner.nextLine().trim();

            Message check =
                    new Message("0000000000", messageNumber, recipient, "");
            System.out.println(check.checkRecipientCell());

            if (check.hasValidRecipientCell()) {
                break;
            }
        }

        // keep asking until message text is valid under 250 chars
        while (true) {
            System.out.print(
                    "Enter your message (maximum 250 characters): ");
            String text = scanner.nextLine();

            Message check =
                    new Message("0000000000", messageNumber, recipient, text);

            if (check.isMessageLengthValid() && !text.trim().isEmpty()) {
                System.out.println("Message sent");
                return new Message(messageNumber, recipient, text);
            }

            if (!check.isMessageLengthValid()) {
                System.out.println(
                        "Please enter a message of less than 250 characters.");
                System.out.println(check.checkMessageLength());
            } else {
                System.out.println("Please enter a message.");
            }
        }
    }

    // makes sure the user enters a proper number instead of crashing on letters
    private static int readNonNegativeInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();

            try {
                int count = Integer.parseInt(value);
                if (count >= 0) {
                    return count;
                }
            } catch (NumberFormatException ignored) {
                // ignore and loop again if they type letters
            }

            System.out.println(
                    "Please enter a whole number that is zero or greater.");
        }
    }
}