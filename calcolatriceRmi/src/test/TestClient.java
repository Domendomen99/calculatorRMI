import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class TestClient {

    @Test
    public void testIsFloat() {
        assertTrue(Client.isFloat("3.14"));
        assertFalse(Client.isFloat("abc"));
    }

    @Test
    public void testLeggiSceltaMenu() {
        String input = "2\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        int result = Client.leggiSceltaMenu();
        assertEquals(2, result);
    }

}
