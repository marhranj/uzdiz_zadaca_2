package marhranj_zadaca_2.entiteti;

import marhranj_zadaca_2.helperi.VremenaUtils;
import marhranj_zadaca_2.helperi.UpravljacDatotekama;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class Program {

    private int id;
    private String naziv;
    private LocalTime pocetak;
    private LocalTime kraj;
    private String nazivDatotekeRasporeda;
    private Raspored raspored;

    public Program(String redDatotekeTvKuca) {
        String[] atributi = redDatotekeTvKuca.split("\\s*;\\s*");
        if (atributi.length > 4) {
            try {
                popuniAtribute(atributi);
                raspored = new Raspored(pocetak, kraj, new UpravljacDatotekama().procitajDatoteku(nazivDatotekeRasporeda));
            } catch (IllegalArgumentException | DateTimeParseException | IOException e) {
                throw new IllegalArgumentException("Neispravan zapis u datoteci tv kuce: " + redDatotekeTvKuca, e);
            }
        } else {
            throw new IllegalArgumentException("Neispravan zapis u datoteci tv kuce, nedovoljno atributa: " + redDatotekeTvKuca);
        }
    }

    public Raspored getRaspored() {
        return raspored;
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
