public class ModuloStrategy implements DistributionStrategy {
    @Override
    public int getTargetNode(String key, int nodeCount) {
        if (nodeCount <= 0)
            return -1;
        return Math.abs(key.hashCode()) % nodeCount;
    }
}
