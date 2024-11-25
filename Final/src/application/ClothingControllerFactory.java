package application;

public class ClothingControllerFactory implements ProductControllerFactory {
    @Override
    public BaseProductController createController() {
        return new Clothing();
    }
}
