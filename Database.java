import java.util.HashMap;
import java.util.Map;

public class Database {
    private final Map<String, String> records = new HashMap<>();

    public String fetch(String key) {
        System.out.println("[DB] reading: " + key);
        try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
        }
        return records.get(key);
    }

    public void persist(String key, String value) {
        System.out.println("[DB] writing: " + key + "=" + value);
        records.put(key, value);
    }
}
