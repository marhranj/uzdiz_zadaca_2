package marhranj_zadaca_2.entiteti;

import marhranj_zadaca_2.composite.Component;
import marhranj_zadaca_2.composite.Composite;
import marhranj_zadaca_2.decorator.Decorator;
import marhranj_zadaca_2.helperi.VremenaUtils;
import marhranj_zadaca_2.iterator.ContainerEmisija;
import marhranj_zadaca_2.iterator.Iterator;
import marhranj_zadaca_2.visitor.EmisijaVisitor;
import marhranj_zadaca_2.visitor.Visitor;

import java.time.LocalTime;
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

    @Override
    public String decorate() {
        String ispis = String.format(" %-16s | %10.2f |", naziv, (double) izracunajUkupanPrihodOdReklami());
        return super.decorate() + ispis;
    }

    public Dan dohvatiDekorator(Decorator decorator) {
        super.postaviDekorator(decorator);
        return this;
    }

    public String getNaziv() {
        return naziv;
    }

    public boolean dodajEmisiju(LocalTime pocetak, Emisija emisija) {
        boolean uspjesnoDodano = false;
        LocalTime kraj = pocetak.plusMinutes(emisija.getTrajanje());
        if (!zauzetTermin(pocetak, kraj) && unutarVremenaEmitiranjaPrograma(pocetak, kraj)) {
            emisija.setPocetakIKraj(pocetak);
            ((Component) this).dodajDijete(emisija);
            Collections.sort((List<Emisija>) (List) dohvatiSvuDjecu());
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
            emisija.setPocetakIKraj(pocetak);
            ((Component) this).dodajDijete(emisija);
            Collections.sort((List<Emisija>) (List) dohvatiSvuDjecu());
            uspjesnoDodano = true;
        } else {
            System.err.println("Nije moguce dodati emisiju: " + emisija.getNazivEmisije() + ", u " + pocetak + " na dan " + naziv);
        }
        return uspjesnoDodano;
    }

    private long izracunajUkupanPrihodOdReklami() {
        Visitor emisijaVisitor = new EmisijaVisitor();
        Iterator<Emisija> iteratorEmisija = new ContainerEmisija(this).dohvatiIterator();
        while (iteratorEmisija.hasNext()) {
            Emisija emisija = iteratorEmisija.next();
            emisijaVisitor.visit(emisija);
        }
        return emisijaVisitor.getUkupnoTrajanjeReklami();
    }

    private LocalTime pronadjiSlobodnoVrijeme(Emisija emisija) {
        List<Emisija> emisije = (List<Emisija>) (List) dohvatiSvuDjecu();
        LocalTime pocetakEmisije = emisije.size() > 1 ?  emisije.get(0).getKraj() : this.pocetakEmitiranja;
        LocalTime krajEmisije = pocetakEmisije.plusMinutes(emisija.getTrajanje());
        int i = 0;
        while (zauzetTermin(pocetakEmisije, krajEmisije) && unutarVremenaEmitiranjaPrograma(pocetakEmisije, krajEmisije)) {
            pocetakEmisije = emisije.get(i++).getKraj();
            krajEmisije = pocetakEmisije.plusMinutes(emisija.getTrajanje());
        }
        return pocetakEmisije;
    }

    private boolean zauzetTermin(LocalTime pocetak, LocalTime kraj) {
        Predicate<Emisija> unutarVremena = emisija ->
                VremenaUtils.vremenskiPeriodUnutarDrugogVremenskogPerioda(pocetak, kraj, emisija.getPocetak(), emisija.getKraj()) ||
                VremenaUtils.vremenskiPeriodUnutarDrugogVremenskogPerioda(emisija.getPocetak(), emisija.getKraj(), pocetak, kraj);
        List<Emisija> emisije = (List<Emisija>) (List) dohvatiSvuDjecu();
        return emisije.stream()
                .anyMatch(unutarVremena);
    }

    private boolean unutarVremenaEmitiranjaPrograma(LocalTime pocetak, LocalTime kraj) {
        return VremenaUtils.vremenskiPeriodUnutarDrugogVremenskogPerioda(pocetak, kraj, this.pocetakEmitiranja, this.krajEmitiranja);
    }

}
