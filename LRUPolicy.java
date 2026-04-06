import java.util.HashMap;
import java.util.Map;

public class LRUPolicy<K> implements EvictionPolicy<K> {
    private final Map<K, DLLNode<K>> lookup;
    private final DLLNode<K> sentinel;
    private final DLLNode<K> end;

    public LRUPolicy() {
        this.lookup = new HashMap<>();
        this.sentinel = new DLLNode<>(null);
        this.end = new DLLNode<>(null);
        this.sentinel.right = this.end;
        this.end.left = this.sentinel;
    }

    private static class DLLNode<K> {
        K key;
        DLLNode<K> left;
        DLLNode<K> right;

        DLLNode(K key) {
            this.key = key;
        }
    }

    private void moveToFront(DLLNode<K> node) {
        node.right = sentinel.right;
        node.left = sentinel;
        sentinel.right.left = node;
        sentinel.right = node;
    }

    private void detach(DLLNode<K> node) {
        node.left.right = node.right;
        node.right.left = node.left;
    }

    @Override
    public void keyAccessed(K key) {
        if (lookup.containsKey(key)) {
            DLLNode<K> existing = lookup.get(key);
            detach(existing);
            moveToFront(existing);
        }
    }

    @Override
    public void keyAdded(K key) {
        DLLNode<K> fresh = new DLLNode<>(key);
        lookup.put(key, fresh);
        moveToFront(fresh);
    }

    @Override
    public K evictKey() {
        if (lookup.isEmpty())
            return null;
        DLLNode<K> oldest = end.left;
        K evictedKey = oldest.key;
        detach(oldest);
        lookup.remove(evictedKey);
        return evictedKey;
    }
}
