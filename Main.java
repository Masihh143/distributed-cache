public class Main {
    public static void main(String[] args) {
        Database db = new Database();
        DistributedCache<String, String> cache = new DistributedCache<>(3, db, 2);

        System.out.println("--- Store ---");
        cache.put("u1", "A");
        cache.put("u2", "B");
        cache.put("u3", "C");
        cache.put("u4", "D");
        cache.printStatus();

        System.out.println("--- Read ---");
        cache.get("u1");

        System.out.println("--- Evict ---");
        cache.put("u7", "G"); 
        cache.put("u10", "H");
        cache.get("u1");
        cache.put("u13", "I");
        cache.printStatus();
    }
}
