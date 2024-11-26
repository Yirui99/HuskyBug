ackage application;

public class GroceriesControllerFactory implements ProductControllerFactory {
    @Override
    public BaseProductController createController() {
        return new Groceries();
    }
}
