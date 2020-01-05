package marhranj_zadaca_2;

import marhranj_zadaca_2.composite.Component;
import marhranj_zadaca_2.composite.Composite;
import marhranj_zadaca_2.entiteti.*;
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
        postaviStabloRasporeda(ucitacKlasa);
        new Izbornik();
    }

    private static void postaviStabloRasporeda(UcitacKlasa ucitacKlasa) {
        Composite<Component> inicijalniPodaci = new Composite<>();
        Composite<Component> planPrograma = new Composite<>();

        TvKuca.dajInstancu().dodajListStablu(inicijalniPodaci);
        TvKuca.dajInstancu().dodajListStablu(planPrograma);

        modificirajInicijalnePodatke(ucitacKlasa, inicijalniPodaci);

    }

    private static void modificirajInicijalnePodatke(UcitacKlasa ucitacKlasa, Composite<Component> inicijalniPodaci) {
        Composite<Uloga> sveUloge = new Composite<>();
        sveUloge.dodajSve(ucitacKlasa.ucitajUloge());
        inicijalniPodaci.dodaj(sveUloge);

        Composite<Osoba> sveOsobe = new Composite<>();
        sveOsobe.dodajSve(ucitacKlasa.ucitajOsobe());
        inicijalniPodaci.dodaj(sveOsobe);

        Composite<VrstaEmisije> sveVrsteEmisija = new Composite<>();
        sveVrsteEmisija.dodajSve(ucitacKlasa.ucitajVrsteEmisija());
        inicijalniPodaci.dodaj(sveVrsteEmisija);

        Composite<Emisija> sveEmisije = new Composite<>();
        sveEmisije.dodajSve(ucitacKlasa.ucitajEmisije());
        inicijalniPodaci.dodaj(sveEmisije);
    }

}
