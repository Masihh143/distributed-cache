import java.util.ArrayList;
import java.util.List;

public class DistributedCache<K, V> {
    private final int totalNodes;
    private final Database database;
    private final DistributionStrategy strategy;
    private final List<CacheNode<K, V>> cacheNodes;

    public DistributedCache(int totalNodes, Database database, int capacity) {
        this(totalNodes, database, new ModuloStrategy(), capacity);
    }

    public DistributedCache(int totalNodes, Database database, DistributionStrategy strategy, int capacity) {
        this.totalNodes = totalNodes;
        this.database = database;
        this.strategy = strategy;
        this.cacheNodes = new ArrayList<>();
        for (int idx = 0; idx < totalNodes; idx++) {
            cacheNodes.add(new CacheNode<>(String.valueOf(idx), capacity));
        }
    }

    public V get(K key) {
        int targetIdx = strategy.getTargetNode(key.toString(), totalNodes);
        CacheNode<K, V> targetNode = cacheNodes.get(targetIdx);
        V result = targetNode.get(key);
        if (result != null) {
            System.out.println("[DistCache] Hit " + key + " on Node " + targetIdx);
            return result;
        }
        System.out.println("[DistCache] Miss " + key + ". fetching from db...");
        String fetchedVal = database.fetch(key.toString());
        if (fetchedVal != null) {
            targetNode.put(key, (V) fetchedVal);
        }
        return (V) fetchedVal;
    }

    public void put(K key, V value) {
        int targetIdx = strategy.getTargetNode(key.toString(), totalNodes);
        CacheNode<K, V> targetNode = cacheNodes.get(targetIdx);
        database.persist(key.toString(), value.toString());
        targetNode.put(key, value);
        System.out.println("[DistCache] Saved " + key + " to Node " + targetIdx);
    }

    public void printStatus() {
        System.out.println("\n--- Cache Status ---");
        for (CacheNode<K, V> entry : cacheNodes) {
            System.out.println("Node " + entry.getId() + " (" + entry.size() + "/" + entry.getCap() + "): ["
                    + String.join(", ", entry.getKeys()) + "]");
        }
        System.out.println("--------------------\n");
    }
}
