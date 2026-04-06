public interface DistributionStrategy {
    int getTargetNode(String key, int nodeCount);
}
