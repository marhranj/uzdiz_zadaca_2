package marhranj_zadaca_2.entiteti;

import marhranj_zadaca_2.composite.Composite;
import marhranj_zadaca_2.decorator.Decorator;
import marhranj_zadaca_2.helperi.DohvacanjeEntiteta;
import marhranj_zadaca_2.observer.Observer;
import marhranj_zadaca_2.prototype.Prototype;
import marhranj_zadaca_2.visitor.Visitable;
import marhranj_zadaca_2.visitor.Visitor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Emisija extends Composite<Emisija> implements Prototype, Visitable, Comparable<Emisija>, Observer {

    private int id;
    private String nazivEmisije;
    private long trajanje;
    private VrstaEmisije vrstaEmisije;
    private List<Osoba> osobe = new ArrayList<>();
    private LocalTime pocetak;
    private LocalTime kraj;

    public Emisija() {
    }

    public Emisija(Emisija emisija) {
        if (emisija != null) {
            this.id = emisija.id;
            this.nazivEmisije = emisija.nazivEmisije;
            this.trajanje = emisija.trajanje;
            this.vrstaEmisije = emisija.vrstaEmisije;
            this.osobe = new ArrayList<>(emisija.osobe);
        }
    }

    public Emisija(String redDatotekeEmisije) throws IllegalArgumentException {
        String[] atributi = redDatotekeEmisije.split("\\s*;\\s*");
        if (atributi.length > 2) {
            try {
                popuniAtribute(atributi);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Neispravan zapis u datoteci emisija: " + redDatotekeEmisije, e);
            }
        } else {
            throw new IllegalArgumentException("Neispravan zapis u datoteci emisija, nedovoljno atributa: " + redDatotekeEmisije);
        }
    }

    public Emisija dohvatiDekorator(Decorator decorator) {
        super.postaviDekorator(decorator);
        return this;
    }

    @Override
    public Prototype clone() {
        return new Emisija(this);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String decorate() {
        String printOsobe = osobe.isEmpty() ? "" : Arrays.toString(osobe.toArray());
        String ispis = String.format(" %-36s | %10s | %10s | %-92s | ",
                nazivEmisije, pocetak, kraj, printOsobe);
        return super.decorate() + ispis;
    }

    @Override
    public String decorateZaglavlje() {
        String ispis = String.format(" %-36s | %10s | %10s | %-92s | ",
                centrirajString(36, "Naziv emisije"),
                centrirajString(10, "Pocetak"),
                centrirajString(10, "Kraj"),
                centrirajString(92, "Osobe sa ulogama"));
        return super.decorateZaglavlje() + ispis;
    }

    @Override
    public int compareTo(Emisija emisija) {
        return this.pocetak.compareTo(emisija.pocetak);
    }

    @Override
    public void update(String imeOsobe, Uloga staraUloga, Uloga novaUloga) {
        osobe.stream()
                .filter(osoba -> osoba.getImePrezime().equals(imeOsobe))
                .filter(osoba -> osoba.getUloga().equals(staraUloga))
                .forEach(osoba -> osoba.setUloga(novaUloga));
    }

    public int getId() {
        return id;
    }

    public String getNazivEmisije() {
        return nazivEmisije;
    }

    public long getTrajanje() {
        return trajanje;
    }

    public List<Osoba> getOsobe() {
        return osobe;
    }

    public VrstaEmisije getVrstaEmisije() {
        return vrstaEmisije;
    }

    public LocalTime getPocetak() {
        return pocetak;
    }

    public void setPocetakIKraj(LocalTime pocetak) {
        this.pocetak = pocetak;
        this.kraj = pocetak.plusMinutes(getTrajanje());
    }

    public LocalTime getKraj() {
        return kraj;
    }

    public void dodajUlogeOsobama(String[] osobeUloge) {
        List<Osoba> osobeSaNovimUlogama = Stream.of(osobeUloge)
                .map(osobaUloga -> osobaUloga.split("\\s*-\\s*"))
                .map(this::dodjeliOsobiUlogu)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        osobe.addAll(osobeSaNovimUlogama);
    }

    private void popuniAtribute(String[] atributi) {
        id = Integer.parseInt(atributi[0]);
        nazivEmisije = atributi[1];
        vrstaEmisije = DohvacanjeEntiteta.dohvatiVrstuPremaId(Integer.parseInt(atributi[2]))
                .orElse(null);
        trajanje = Long.parseLong(atributi[3]);
        if (atributi.length > 4) {
            String[] osobeUloge = atributi[4].split("\\s*,\\s*");
            dodajUlogeOsobama(osobeUloge);
        }
    }

    private Optional<Osoba> dodjeliOsobiUlogu(String[] osobaUloga) throws IllegalArgumentException {
        if (osobaUloga.length > 1) {
            Optional<Osoba> osoba = DohvacanjeEntiteta.dohvatiOsobuPremaId(Integer.parseInt(osobaUloga[0]));
            Optional<Uloga> uloga = DohvacanjeEntiteta.dohvatiUloguPremaId(Integer.parseInt(osobaUloga[1]));
            osoba.ifPresent(osoba1 -> uloga.ifPresent(osoba1::setUloga));
            return osoba;
        } else {
            throw new IllegalArgumentException();
        }
    }

}
