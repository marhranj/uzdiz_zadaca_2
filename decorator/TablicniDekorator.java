package marhranj_zadaca_2.decorator;

public abstract class TablicniDekorator implements Decorator {

    private Decorator decorator;

    @Override
    public String decorate() {
        return decorator.decorate();
    }

    public void postaviDekorator(Decorator decorator) {
        this.decorator = decorator;
    }

}
