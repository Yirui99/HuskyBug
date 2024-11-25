package application;

public class OthersControllerFactory implements ProductControllerFactory {
    @Override
    public BaseProductController createController() {
        return new Others();
    }
}
