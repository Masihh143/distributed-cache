public class ModuloStrategy implements DistributionStrategy {
    @Override
    public int getTargetNode(String key, int totalNodes) {
        if (totalNodes <= 0) return -1;
        return Math.abs(key.hashCode()) % totalNodes;
    }
}
