import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // declaring my variables to store user registration details
        String firstName;
        String lastName;
        String username;
        String password;
        String cellPhoneNumber;

        // display the registration prompt
        System.out.println("Registration");
        //collects user details step by step
        System.out.print("Enter first name: ");
        firstName = scanner.nextLine();


        System.out.print("Enter last name: ");
        lastName = scanner.nextLine();

        System.out.print("Enter Username: ");
        username = scanner.nextLine();

        System.out.print("Enter password: ");
        password = scanner.nextLine();

        System.out.print("Enter cellphone number: ");
        cellPhoneNumber = scanner.nextLine();

        // I created a login object using the collected details
        Login user = new Login(username, password, cellPhoneNumber, firstName, lastName);

        // here i call the registerUser method and store the result
        String registrationResult = user.registerUser();

        //display registration result to the user
        System.out.println("\n--- REGISTRATION STATUS ---");// line breaker, assited by AI
        System.out.println(registrationResult);

        // check if registration was successful before proceeding to login
        if (user.checkUserName() &&
                user.checkPasswordComplexity() &&
                user.checkCellPhoneNumber()) {

            System.out.println("Login user");

            // Declare local variables to hold user login attempts
            System.out.print("Enter Login username: ");
            String inputUsername = scanner.nextLine();

            System.out.print("Enter Login password: ");
            String inputPassword = scanner.nextLine();

            //Authenticate the users credentials
            boolean isAuthenication = user.loginUser(inputUsername, inputPassword);
            //displaying the login status based on authentication result
            String loginOutput = user.returnLoginStatus(isAuthenication);
            System.out.println("\n--- LOGIN STATUS ---");
            System.out.println(loginOutput);
        } else {
           // if the registration failed, display this message
            System.out.println("\n [!] Registration failed, please try again.");
        }
    }
}