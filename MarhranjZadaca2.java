package marhranj_zadaca_2;

import marhranj_zadaca_2.composite.Component;
import marhranj_zadaca_2.composite.Composite;
import marhranj_zadaca_2.entiteti.*;
import marhranj_zadaca_2.helperi.Izbornik;
import marhranj_zadaca_2.helperi.UcitacKlasa;
import marhranj_zadaca_2.helperi.UpravljacArgumentimaKmdLin;
import marhranj_zadaca_2.helperi.UpravljacDatotekama;

import java.io.IOException;
import java.util.Arrays;

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
        postaviPlanPrograma(new UcitacKlasa(upravljacDatotekama));
        new Izbornik();
    }

    private static void postaviPlanPrograma(UcitacKlasa ucitacKlasa) {
        Composite<Component> inicijalniPodaci = new Composite<>();
        Composite<Program> planPrograma = new Composite<>();

        TvKuca.dajInstancu().dodajDijete(inicijalniPodaci);
        TvKuca.dajInstancu().dodajDijete(planPrograma);

        sloziInicijalnePodatke(ucitacKlasa, inicijalniPodaci);
        sloziPlanPrograma(ucitacKlasa, planPrograma);
    }

    private static void sloziInicijalnePodatke(UcitacKlasa ucitacKlasa, Composite<Component> inicijalniPodaci) {
        Composite<Uloga> sveUloge = new Composite<>();
        sveUloge.dodajSvuDjecu(ucitacKlasa.ucitajUloge());
        inicijalniPodaci.dodajDijete(sveUloge);

        Composite<Osoba> sveOsobe = new Composite<>();
        sveOsobe.dodajSvuDjecu(ucitacKlasa.ucitajOsobe());
        inicijalniPodaci.dodajDijete(sveOsobe);

        Composite<VrstaEmisije> sveVrsteEmisija = new Composite<>();
        sveVrsteEmisija.dodajSvuDjecu(ucitacKlasa.ucitajVrsteEmisija());
        inicijalniPodaci.dodajDijete(sveVrsteEmisija);

        Composite<Emisija> sveEmisije = new Composite<>();
        sveEmisije.dodajSvuDjecu(ucitacKlasa.ucitajEmisije());
        inicijalniPodaci.dodajDijete(sveEmisije);
    }

    private static void sloziPlanPrograma(UcitacKlasa ucitacKlasa, Composite<Program> planPrograma) {
        planPrograma.dodajSvuDjecu(ucitacKlasa.ucitajPrograme());
        planPrograma.dohvatiSvuDjecu()
            .forEach(program -> {
                Raspored raspored = dohvatiPopunjenRaspored(program);
                ((Composite) program).dodajDijete(raspored);
            });
    }

    private static Raspored dohvatiPopunjenRaspored(Program program) {
        Dan ponedjeljak = new Dan(program.getPocetak(), program.getKraj(), "Ponedjeljak");
        Dan utorak = new Dan(program.getPocetak(), program.getKraj(), "Utorak");
        Dan srijeda = new Dan(program.getPocetak(), program.getKraj(), "Srijeda");
        Dan cetvrtak = new Dan(program.getPocetak(), program.getKraj(), "Cetvrtak");
        Dan petak = new Dan(program.getPocetak(), program.getKraj(), "Petak");
        Dan subota = new Dan(program.getPocetak(), program.getKraj(), "Subota");
        Dan nedjelja = new Dan(program.getPocetak(), program.getKraj(), "Nedjelja");

        Raspored raspored = new Raspored();
        ((Composite) raspored).dodajSvuDjecu(Arrays.asList(ponedjeljak, utorak, srijeda, cetvrtak, petak, subota, nedjelja));
        try {
            raspored.popuniRaspored(new UpravljacDatotekama().procitajDatoteku(program.getNazivDatotekeRasporeda()));
        } catch (IOException e) {
            System.err.println(String.format("Ne postoji datoteka s nazivom %s", program.getNazivDatotekeRasporeda()));
        }
        return raspored;
    }

}
