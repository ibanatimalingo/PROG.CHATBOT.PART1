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
        // Tests a username that is too long and lacks an underscore (violates the AND-condition).
        Login login = new Login("kylename", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Name");
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
    public void testCheckCellPhoneNumberValid() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Name");
        assertTrue(login.checkCellPhoneNumber());
    }

    @Test
    public void testCheckCellPhoneNumberInvalid() {
        // Invalid format (missing +27 or wrong length)
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "0838968976", "Kyle", "Name");
        assertFalse(login.checkCellPhoneNumber());
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

    @Test
    public void testLoginUserNullSafety() {
        // Confirms that passing null values to loginUser does not throw a NullPointerException
        Login login = new Login(null, null, "+27838968976", "Kyle", "Name");
        assertFalse(login.loginUser(null, null));
        assertFalse(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }
}