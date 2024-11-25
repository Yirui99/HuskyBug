package application;

public class DataLoaderContext {
    private DataLoader dataLoader;

    // 设置策略
    public void setDataLoader(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    public DataLoader getDataLoader() {
        return dataLoader;
    }
}
