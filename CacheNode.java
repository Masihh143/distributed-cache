import java.util.HashMap;
import java.util.Map;

public class CacheNode<K, V> {
    private final String nodeId;
    private final int capacity;
    private final Map<K, V> dataStore;
    private final EvictionPolicy<K> evictionPolicy;

    public CacheNode(String nodeId, int capacity) {
        this.nodeId = nodeId;
        this.capacity = capacity;
        this.dataStore = new HashMap<>();
        this.evictionPolicy = new LRUPolicy<>();
    }

    public V get(K key) {
        if (dataStore.containsKey(key)) {
            System.out.println("[Node " + nodeId + "] hit: " + key);
            evictionPolicy.keyAccessed(key);
            return dataStore.get(key);
        }
        System.out.println("[Node " + nodeId + "] miss: " + key);
        return null;
    }

    public void put(K key, V value) {
        if (dataStore.containsKey(key)) {
            dataStore.put(key, value);
            evictionPolicy.keyAccessed(key);
            return;
        }
        if (dataStore.size() >= capacity) {
            K removed = evictionPolicy.evictKey();
            if (removed != null) {
                System.out.println("[Node " + nodeId + "] evicted: " + removed);
                dataStore.remove(removed);
            }
        }
        dataStore.put(key, value);
        evictionPolicy.keyAdded(key);
        System.out.println("[Node " + nodeId + "] stored: " + key);
    }

    public int size() { return dataStore.size(); }
    public String getId() { return nodeId; }
    public int getCap() { return capacity; }
    public String[] getKeys() { return dataStore.keySet().stream().map(Object::toString).toArray(String[]::new); }
}
