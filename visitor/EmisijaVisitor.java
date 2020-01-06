package marhranj_zadaca_2.visitor;

import marhranj_zadaca_2.entiteti.Emisija;
import marhranj_zadaca_2.entiteti.VrstaEmisije;

public class EmisijaVisitor implements Visitor {

    private long ukupnoTrajanjeReklami;

    @Override
    public void visit(Emisija emisija) {
        VrstaEmisije vrstaEmisije = emisija.getVrstaEmisije();
        if (vrstaEmisije != null && vrstaEmisije.imaReklame()) {
            ukupnoTrajanjeReklami += vrstaEmisije.getMaksTrajanjeReklami();
        }
    }

    @Override
    public long getUkupnoTrajanjeReklami() {
        return ukupnoTrajanjeReklami;
    }

}
