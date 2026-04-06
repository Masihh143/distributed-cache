public class Main {
    public static void main(String[] args) {
        Database storage = new Database();
        DistributedCache<String, String> distCache = new DistributedCache<>(3, storage, 2);

        System.out.println("--- Store ---");
        distCache.put("u1", "A");
        distCache.put("u2", "B");
        distCache.put("u3", "C");
        distCache.put("u4", "D");
        distCache.printStatus();

        System.out.println("--- Read ---");
        distCache.get("u1");

        System.out.println("--- Evict ---");
        distCache.put("u7", "G");
        distCache.put("u10", "H");
        distCache.get("u1");
        distCache.put("u13", "I");
        distCache.printStatus();
    }
}
