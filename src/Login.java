// here we import scanner class for our user input
import java.util.Scanner;
import java.util.regex.Pattern;
// the login class handles user registration and authentication details
public class Login {
    // declaring the private variables
    private String username;
    private String password;
    private String cellPhoneNumber;
    private String firstName;
    private String lastName;

    //initialized a new login object with user detail
    public Login(String username, String password, String cellPhoneNumber, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    //default constuctor- allows creation an empty login object
    public Login() {
    }

    //getter and setter method for encupsulation - provide controlled access to private fields
    //return the username
    public String getUsername() {
        return username;
    }

    //updates the username
    public void setUsername(String username) {
        this.username = username;
    }

    //returns my password
    public String getPassword() {
        return password;
    }

    //updates passwords
    public void setPassword(String password) {
        this.password = password;
    }

    //returns the cellphone number
    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    //updates the cellphone number
    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
    }

    // returns the firstname
    public String getFirstName() {
        return firstName;
    }

    //updates the first name
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // returns the lastname
    public String getLastName() {
        return lastName;
    }

    //updates the lastname
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    //
    public boolean checkUserName() {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    // validates the password complexity e.g atleast 8 characters,one capital letter
    public boolean checkPasswordComplexity() {
        // validates cellphone number format(example: +27 or 0 followed by 9 digits)
        String passwordRegex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!._-]).{8,}$";// AI assisted me here, its for the complex symbols the user might use
        // Check that password is not null first, then check if it matches the password rules (regex)
// This stops the code from crashing if password was never set
        return password != null && Pattern.matches(passwordRegex, password);
    }


    public boolean checkCellPhoneNumber() {

        String cellPhoneRegex = "^\\+27[0-9]{9}$";//Ai assisted me here
        return cellPhoneNumber != null && Pattern.matches(cellPhoneRegex, cellPhoneNumber);// null means no value or nothing is assigned, prevents my program from chasing when i try to use a variable that hasnt been given a value
    }

    //registers the user and returns a message based on the validation results
    public String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber()) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }
        return "Username successfully captured.\nPassword successfully captured.\nCell phone number successfully added."; // '\n' is basically a line breaker
    }

    // Checks if the entered username and password match the stored username and password
    // Returns true only if both match exactly
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return this.username.equals(enteredUsername) && this.password.equals(enteredPassword);
    }


    public String returnLoginStatus(boolean isLoggedIn) {
        if (isLoggedIn) {
            return "Welcome " + firstName + " " + lastName + " It is great to see you.";
        } else {
            return "Username or password incorrect, please try again."; // Compares the stored username and password with the entered values, returning 'true' only if BOTH match exactly.
        }
    }
}