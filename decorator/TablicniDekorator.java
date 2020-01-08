package marhranj_zadaca_2.decorator;

public abstract class TablicniDekorator implements Decorator {

    private Decorator decorator;

    @Override
    public String decorate() {
        return decorator.decorate();
    }

    @Override
    public String decorateZaglavlje() {
        return decorator.decorateZaglavlje();
    }

    public void postaviDekorator(Decorator decorator) {
        this.decorator = decorator;
    }

    public String centrirajString (int duzinaPolja, String string) {
        int padSize = duzinaPolja - string.length();
        int padStart = string.length() + padSize / 2;

        string = String.format("%" + padStart + "s", string);
        string = String.format("%-" + duzinaPolja  + "s", string);

        return string;
    }

}
