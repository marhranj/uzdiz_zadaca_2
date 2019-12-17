package marhranj_zadaca_2;

import marhranj_zadaca_2.entiteti.TvKuca;
import marhranj_zadaca_2.helperi.Izbornik;
import marhranj_zadaca_2.helperi.UcitacKlasa;
import marhranj_zadaca_2.helperi.UpravljacArgumentimaKmdLin;
import marhranj_zadaca_2.helperi.UpravljacDatotekama;

public class MarhranjZadaca2 {

    public static void main(String... args) {
        UpravljacArgumentimaKmdLin upravljacArgumentimaKmdLin = new UpravljacArgumentimaKmdLin(args);
        if (!upravljacArgumentimaKmdLin.popuniNaizveDatoteka()) {
            System.err.println("Niste popunili sve odgovarajuce parametre");
            System.exit(0);
        }
        UpravljacDatotekama upravljacDatotekama = new UpravljacDatotekama(upravljacArgumentimaKmdLin);
        if (!upravljacDatotekama.ucitajDatoteke()) {
            System.err.println("Neispravni nazivi datoteka");
            System.exit(0);
        }
        UcitacKlasa ucitacKlasa = new UcitacKlasa(upravljacDatotekama);
        postaviZnacajkeTvKuce(ucitacKlasa);
        new Izbornik();
    }

    private static void postaviZnacajkeTvKuce(UcitacKlasa ucitacKlasa) {
        TvKuca tvKuca = TvKuca.dajInstancu();
        tvKuca.setUloge(ucitacKlasa.ucitajUloge());
        tvKuca.setOsobe(ucitacKlasa.ucitajOsobe());
        tvKuca.setEmisije(ucitacKlasa.ucitajEmisije());
        tvKuca.setProgrami(ucitacKlasa.ucitajPrograme());
    }

}
