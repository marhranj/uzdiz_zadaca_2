package marhranj_zadaca_2.decorator;

public class DecoratorImpl implements Decorator {

    @Override
    public String decorate() {
        return "|";
    }

    @Override
    public String decorateZaglavlje() {
        return decorate();
    }

}
