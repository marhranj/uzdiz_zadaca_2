package marhranj_zadaca_2.entiteti;

import marhranj_zadaca_2.composite.Composite;
import marhranj_zadaca_2.decorator.Decorator;
import marhranj_zadaca_2.helperi.VremenaUtils;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class Program extends Composite<Program> {

    private int id;
    private String naziv;
    private LocalTime pocetak;
    private LocalTime kraj;
    private String nazivDatotekeRasporeda;

    public Program() {
    }

    public Program(String redDatotekeTvKuca) {
        String[] atributi = redDatotekeTvKuca.split("\\s*;\\s*");
        if (atributi.length > 4) {
            try {
                popuniAtribute(atributi);
            } catch (IllegalArgumentException | DateTimeParseException e) {
                throw new IllegalArgumentException("Neispravan zapis u datoteci tv kuce: " + redDatotekeTvKuca, e);
            }
        } else {
            throw new IllegalArgumentException("Neispravan zapis u datoteci tv kuce, nedovoljno atributa: " + redDatotekeTvKuca);
        }
    }

    public Program dohvatiDekorator(Decorator decorator) {
        super.postaviDekorator(decorator);
        return this;
    }

    @Override
    public String decorate() {
        String ispis = String.format(" %-32s |", naziv);
        return super.decorate() + ispis;
    }

    @Override
    public String decorateZaglavlje() {
        String ispis = String.format(" %-32s |", centrirajString(32, "Naziv programa"));
        return super.decorateZaglavlje() + ispis;
    }

    public String getNaziv() {
        return naziv;
    }

    public int getId() {
        return id;
    }

    public LocalTime getPocetak() {
        return pocetak;
    }

    public LocalTime getKraj() {
        return kraj;
    }

    public String getNazivDatotekeRasporeda() {
        return nazivDatotekeRasporeda;
    }

    private void popuniAtribute(String[] atributi) {
        id = Integer.parseInt(atributi[0]);
        naziv = atributi[1];
        pocetak = LocalTime.parse(VremenaUtils.postaviFormatVremena(atributi[2]));
        kraj = atributi[3].isEmpty()
                ? LocalTime.of(23, 59)
                : LocalTime.parse(VremenaUtils.postaviFormatVremena(atributi[3]));
        nazivDatotekeRasporeda = atributi[4];
    }

}
