package marhranj_zadaca_2.entiteti;

import marhranj_zadaca_2.composite.Component;
import marhranj_zadaca_2.composite.Composite;

import java.util.List;

import static marhranj_zadaca_2.Konstante.*;

public class TvKuca extends Composite<Component> {

    private static TvKuca instanca;

    static {
        instanca = new TvKuca();
    }

    private TvKuca() {}

    public static TvKuca dajInstancu() {
        return instanca;
    }

    public List<Uloga> dohvatiInicijalneUloge() {
        return dohvatiSvuDjecu()
                .get(INDEX_INICIJALNIH_PODATAKA)
                .dohvatiDijetePremaIndexu(INDEX_INICIJALNIH_ULOGA)
                .dohvatiSvuDjecu();
    }

    public List<Osoba> dohvatiInicijalneOsobe() {
        return dohvatiSvuDjecu()
                .get(INDEX_INICIJALNIH_PODATAKA)
                .dohvatiDijetePremaIndexu(INDEX_INICIJALNIH_OSOBA)
                .dohvatiSvuDjecu();
    }

    public List<VrstaEmisije> dohvatiInicijalneVrsteEmisija() {
        return dohvatiSvuDjecu()
                .get(INDEX_INICIJALNIH_PODATAKA)
                .dohvatiDijetePremaIndexu(INDEX_INICIJALNIH_VRSTA_EMISIJA)
                .dohvatiSvuDjecu();
    }

    public List<Emisija> dohvatiInicijalneEmisije() {
        return dohvatiSvuDjecu()
                .get(INDEX_INICIJALNIH_PODATAKA)
                .dohvatiDijetePremaIndexu(INDEX_INICIJALNIH_EMISIJA)
                .dohvatiSvuDjecu();
    }

}
