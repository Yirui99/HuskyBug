package application;

public class CellphonesControllerFactory implements ProductControllerFactory {
    @Override
    public BaseProductController createController() {
        return new Cellphones();
    }
}
