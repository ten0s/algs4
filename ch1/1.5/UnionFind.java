public interface UnionFind {
    public int cost();
    public void clearCost();

    public int count();
    public boolean connected(int p, int q);
    public int find(int p);
    public void union(int p, int q);
}
