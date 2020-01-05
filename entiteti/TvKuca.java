package marhranj_zadaca_2.entiteti;

import marhranj_zadaca_2.composite.Component;
import marhranj_zadaca_2.composite.Composite;

import java.util.ArrayList;
import java.util.List;

public class TvKuca {

    public static final int INDEX_INICIJALNIH_PODATAKA = 0;
    public static final int INDEX_PLANA_PROGRAMA = 1;

    private static final int INDEX_INICIJALNIH_ULOGA = 0;
    private static final int INDEX_INICIJALNIH_OSOBA = 1;
    private static final int INDEX_INICIJALNIH_VRSTA_EMISIJA = 2;
    private static final int INDEX_INICIJALNIH_EMISIJA = 3;

    private static TvKuca instanca;

    private List<Component> stabloKomponenti = new ArrayList<>();

    static {
        instanca = new TvKuca();
    }

    private TvKuca() {}

    public static TvKuca dajInstancu() {
        return instanca;
    }

    public List<Component> getStabloKomponenti() {
        return stabloKomponenti;
    }

    public void dodajListStablu(Component list) {
        this.stabloKomponenti.add(list);
    }

    public List<Uloga> dohvatiInicijalneUloge() {
        return ((Composite) getStabloKomponenti()
                .get(INDEX_INICIJALNIH_PODATAKA)
                .dohvatiPremaIndexu(INDEX_INICIJALNIH_ULOGA))
                .dohvatiListove();
    }

    public List<Osoba> dohvatiInicijalneOsobe() {
        return ((Composite) getStabloKomponenti()
                .get(INDEX_INICIJALNIH_PODATAKA)
                .dohvatiPremaIndexu(INDEX_INICIJALNIH_OSOBA))
                .dohvatiListove();
    }

    public List<VrstaEmisije> dohvatiInicijalneVrsteEmisija() {
        return ((Composite) getStabloKomponenti()
                .get(INDEX_INICIJALNIH_PODATAKA)
                .dohvatiPremaIndexu(INDEX_INICIJALNIH_VRSTA_EMISIJA))
                .dohvatiListove();
    }

    public List<Emisija> dohvatiInicijalneEmisije() {
        return ((Composite) getStabloKomponenti()
                .get(INDEX_INICIJALNIH_PODATAKA)
                .dohvatiPremaIndexu(INDEX_INICIJALNIH_EMISIJA))
                .dohvatiListove();
    }

}
