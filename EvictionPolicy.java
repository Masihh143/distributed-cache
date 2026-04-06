public interface EvictionPolicy<K> {
    void keyAccessed(K key);

    void keyAdded(K key);

    K evictKey();
}
