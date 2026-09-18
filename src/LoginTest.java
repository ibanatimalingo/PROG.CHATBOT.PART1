import org.junit.Test;
import static org.junit.Assert.*;

public class LoginTest {

    @Test
    public void testCheckUserNameCorrectFormat() {
        // Test Data: "kyl_1" (contains '_' and <= 5 chars)
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Name");
        assertTrue(login.checkUserName());
    }

    @Test
    public void testCheckUserNameIncorrectFormat() {
        // This only tests invalid characters, not the length/format boundary.
        // TODO: add a case like "kylename" (long, no underscore) to actually
        // test the boundary of the OR-condition (<=5 chars OR contains '_').
        Login login = new Login("kyle!!!!!!", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Name");
        assertFalse(login.checkUserName());
    }

    @Test
    public void testPasswordSuccess() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Name");
        assertTrue(login.checkPasswordComplexity());
    }

    @Test
    public void testPasswordFailure() {
        Login login = new Login("kyl_1", "password", "+27838968976", "Kyle", "Name");
        assertFalse(login.checkPasswordComplexity());
    }

    @Test
    public void testLoginUserSuccess() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Name");
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginUserFailure() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Name");
        assertFalse(login.loginUser("kyl_1", "wrongpassword"));
    }

    // TODO: missing test for checkCellPhoneNumber()

    // TODO: missing null-safety test (e.g. loginUser() called with a null
    // username or password, to confirm it doesn't throw NullPointerException)
}
