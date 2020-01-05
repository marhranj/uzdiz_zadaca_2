package marhranj_zadaca_2.entiteti;

import marhranj_zadaca_2.composite.Component;
import marhranj_zadaca_2.composite.Composite;
import marhranj_zadaca_2.helperi.VremenaUtils;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class Dan extends Composite<Dan> {

    private LocalTime pocetakEmitiranja;
    private LocalTime krajEmitiranja;
    private String naziv;

    public Dan(LocalTime pocetakEmitiranja, LocalTime krajEmitiranja, String naziv) {
        this.pocetakEmitiranja = pocetakEmitiranja;
        this.krajEmitiranja = krajEmitiranja;
        this.naziv = naziv;
    }

    public String getNaziv() {
        return naziv;
    }

    public boolean dodajEmisiju(LocalTime pocetak, Emisija emisija) {
        boolean uspjesnoDodano = false;
        LocalTime kraj = pocetak.plusMinutes(emisija.getTrajanje());
        if (!zauzetTermin(pocetak, kraj) && unutarVremenaEmitiranjaPrograma(pocetak, kraj)) {
            ((Component) this).dodajDijete(new Termin(pocetak, pocetak.plusMinutes(emisija.getTrajanje()), emisija));
            Collections.sort((List<Termin>) (List) dohvatiSvuDjecu());
            uspjesnoDodano = true;
        } else {
            System.err.println("Nije moguce dodati emisiju: " + emisija.getNazivEmisije() + ", u " + pocetak + " na dan " + naziv);
        }
        return uspjesnoDodano;
    }

    public boolean dodajEmisiju(Emisija emisija) {
        boolean uspjesnoDodano = false;
        LocalTime pocetak = pronadjiSlobodnoVrijeme(emisija);
        LocalTime kraj = pocetak.plusMinutes(emisija.getTrajanje());
        if (unutarVremenaEmitiranjaPrograma(pocetak, kraj)) {
            ((Component) this).dodajDijete(new Termin(pocetak, pocetak.plusMinutes(emisija.getTrajanje()), emisija));
            Collections.sort((List<Termin>) (List) dohvatiSvuDjecu());
            uspjesnoDodano = true;
        } else {
            System.err.println("Nije moguce dodati emisiju: " + emisija.getNazivEmisije() + ", u " + pocetak + " na dan " + naziv);
        }
        return uspjesnoDodano;
    }

    public String dohvatiTermineZaDan() {
        StringBuilder ispis = new StringBuilder(naziv + ":" + System.lineSeparator());
        for (Termin termin : (List<Termin>) (List) dohvatiSvuDjecu()) {
            List<Osoba> osobeEmisije = termin.getEmisija().getOsobe();
            String osobeOutput = osobeEmisije.isEmpty()
                    ? ""
                    : Arrays.toString(osobeEmisije.toArray());
            ispis.append(String.format("%-40s %5s %10s %-40s %n", termin.getEmisija().getNazivEmisije(),
                    termin.getPocetak().toString(), termin.getKraj().toString(), osobeOutput));
        }
        return ispis.toString();
    }

    private LocalTime pronadjiSlobodnoVrijeme(Emisija emisija) {
        List<Termin> termini = (List<Termin>) (List) dohvatiSvuDjecu();
        LocalTime pocetakEmisije = termini.size() > 1 ?  termini.get(0).getKraj() : this.pocetakEmitiranja;
        LocalTime krajEmisije = pocetakEmisije.plusMinutes(emisija.getTrajanje());
        int i = 0;
        while (zauzetTermin(pocetakEmisije, krajEmisije) && unutarVremenaEmitiranjaPrograma(pocetakEmisije, krajEmisije)) {
            pocetakEmisije = termini.get(i++).getKraj();
            krajEmisije = pocetakEmisije.plusMinutes(emisija.getTrajanje());
        }
        return pocetakEmisije;
    }

    private boolean zauzetTermin(LocalTime pocetak, LocalTime kraj) {
        Predicate<Termin> unutarVremena = termin ->
                VremenaUtils.vremenskiPeriodUnutarDrugogVremenskogPerioda(pocetak, kraj, termin.getPocetak(), termin.getKraj()) ||
                VremenaUtils.vremenskiPeriodUnutarDrugogVremenskogPerioda(termin.getPocetak(), termin.getKraj(), pocetak, kraj);
        List<Termin> termini = (List<Termin>) (List) dohvatiSvuDjecu();
        return termini.stream()
                .anyMatch(unutarVremena);
    }

    private boolean unutarVremenaEmitiranjaPrograma(LocalTime pocetak, LocalTime kraj) {
        return VremenaUtils.poslijeIliUIstoVrijeme(pocetak, this.pocetakEmitiranja)
                && VremenaUtils.prijeIliUIstoVrijeme(kraj, this.krajEmitiranja);
    }

}
