package marhranj_zadaca_2.visitor;

import marhranj_zadaca_2.entiteti.Emisija;

public interface Visitor {

    void visit(Emisija emisija);

    long getUkupnoTrajanjeReklami();

}
