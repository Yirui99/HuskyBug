package application;

public class BeautyControllerFactory implements ProductControllerFactory {
    @Override
    public BaseProductController createController() {
        return new Beauty();
    }
}
