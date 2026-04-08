package seedu.blockbook.model.gamer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.blockbook.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ServerTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Server(null));
    }

    @Test
    public void constructor_invalidServer_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Server("server_name"));
    }

    @Test
    public void isValidServer() {
        assertThrows(NullPointerException.class, () -> Server.isValidServer(null));

        assertFalse(Server.isValidServer("server name"));
        assertFalse(Server.isValidServer("server_name"));
        assertFalse(Server.isValidServer("server/name"));
        assertFalse(Server.isValidServer("server@name"));
        assertFalse(Server.isValidServer("A".repeat(51)));

        assertTrue(Server.isValidServer("localhost"));
        assertTrue(Server.isValidServer("127.0.0.1"));
        assertTrue(Server.isValidServer("127.0.0.1:8080"));
        assertTrue(Server.isValidServer("na-east-1"));
        assertTrue(Server.isValidServer("mc.example.net"));
    }

    @Test
    public void equals() {
        Server server = new Server("127.0.0.1:8080");

        assertTrue(server.equals(new Server("127.0.0.1:8080")));
        assertTrue(server.equals(server));

        assertFalse(server.equals(null));
        assertFalse(server.equals(5.0f));
        assertFalse(server.equals(new Server("10.0.0.1:8080")));
    }

    @Test
    public void hashCode_equalObjects_sameHashCode() {
        Server s1 = new Server("127.0.0.1:8080");
        Server s2 = new Server("127.0.0.1:8080");

        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());
    }
}
