package application;

public class ComputersControllerFactory implements ProductControllerFactory {
    @Override
    public BaseProductController createController() {
        return new Computers();
    }
}
