package marhranj_zadaca_2.entiteti;

import marhranj_zadaca_2.composite.Component;
import marhranj_zadaca_2.composite.Composite;
import marhranj_zadaca_2.helperi.DohvacanjeEntiteta;
import marhranj_zadaca_2.helperi.VremenaUtils;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Raspored extends Composite<Raspored> {

    private static final String OSOBA_ULOGA_REGEX = "^([0-9]*-([0-9]+)(,[0-9]*-[0-9]+)*)$";
    private static final String VRIJEME_REGEX = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$";
    private static final String DAN_IZVODENJA_REGEX = "^([0-9]*(,[0-9]+)+)|([0-9]*-[0-9]+)|([0-9]*)$";
    private static final String ID_REGEX = "^[0-9]*$";

    public Raspored() {}

    public void popuniRaspored(String sadrzajDatotekeRasporeda) {
        String[] redoviZapisa = sadrzajDatotekeRasporeda.split("\\r?\\n");
        redoviZapisa = Arrays.copyOfRange(redoviZapisa, 1, redoviZapisa.length);
        String[] emisijeSaZadanimPocetkom = dohvatiRedoveZapisaPremaBrojuAtributa(redoviZapisa, 3);
        redoviZapisa = removeSubArray(redoviZapisa, emisijeSaZadanimPocetkom);
        String[] emisijeBezZadanogPocetka = dohvatiRedoveZapisaPremaBrojuAtributa(redoviZapisa, 2);
        redoviZapisa = removeSubArray(redoviZapisa, emisijeBezZadanogPocetka);
        String[] emisijeBezZadanogDana = dohvatiRedoveZapisaPremaBrojuAtributa(redoviZapisa, 1);
        redoviZapisa = removeSubArray(redoviZapisa, emisijeBezZadanogDana);

        popuniRasporedEmisijamaSaZadanimPocetkom(emisijeSaZadanimPocetkom);
        popuniRasporedEmisijamaBezZadanogPocetka(emisijeBezZadanogPocetka);
        popuniRasporedEmisijamaBezZadanogDana(emisijeBezZadanogDana);

        if (redoviZapisa.length > 0) {
            System.err.println("Greske u sljedecim zapisima programa: " + Arrays.toString(redoviZapisa));
        }
    }

    private void popuniRasporedEmisijamaSaZadanimPocetkom(String[] emisijeSaZadanimPocetkom) {
        Stream.of(emisijeSaZadanimPocetkom)
                .forEach(this::popuniRasporedSaEmisijomSaZadanimPocetkom);
    }

    private void popuniRasporedEmisijamaBezZadanogPocetka(String[] emisijeBezZadanogPocetka) {
        Stream.of(emisijeBezZadanogPocetka)
                .forEach(this::popuniRasporedEmisijomBezZadanogPocetka);
    }

    private void popuniRasporedEmisijamaBezZadanogDana(String[] emisijeBezZadanogDana) {
        Stream.of(emisijeBezZadanogDana)
                .forEach(this::popuniRasporedEmisijomBezZadanogDana);
    }

    private void popuniRasporedSaEmisijomSaZadanimPocetkom(String emisijaSaZadanimPocetkom) {
        String[] atributi = emisijaSaZadanimPocetkom.split("\\s*;\\s*");

        int idEmisije = Integer.parseInt(atributi[0]);
        String dani = atributi[1];
        LocalTime pocetakEmisije = LocalTime.parse(VremenaUtils.postaviFormatVremena(atributi[2]));
        String[] osobeUloge = atributi.length > 3 ? atributi[3].split("\\s*,\\s*") : new String[] {};

        DohvacanjeEntiteta.dohvatiEmisijuPremaId(idEmisije).ifPresent(emisija -> {
            emisija.dodajUlogeOsobama(osobeUloge);
            dodajEmisijuDanima(dani, pocetakEmisije, emisija);
        });
    }

    private void popuniRasporedEmisijomBezZadanogPocetka(String emisijaBezZadanogPocetka) {
        String[] atributi = emisijaBezZadanogPocetka.split("\\s*;\\s*");

        int idEmisije = Integer.parseInt(atributi[0]);
        String dani = atributi[1];
        String[] osobeUloge = atributi.length > 2 ? atributi[2].split("\\s*,\\s*") : new String[] {};

        DohvacanjeEntiteta.dohvatiEmisijuPremaId(idEmisije).ifPresent(emisija -> {
            emisija.dodajUlogeOsobama(osobeUloge);
            dodajEmisijuDanima(dani, null, emisija);
        });
    }

    private void popuniRasporedEmisijomBezZadanogDana(String emisijaBezZadanogDana) {
        String[] atributi = emisijaBezZadanogDana.split("\\s*;\\s*");

        int idEmisije = Integer.parseInt(atributi[0]);
        String[] osobeUloge = atributi.length > 1 ? atributi[1].split("\\s*,\\s*") : new String[] {};

        DohvacanjeEntiteta.dohvatiEmisijuPremaId(idEmisije).ifPresent(emisija -> {
            emisija.dodajUlogeOsobama(osobeUloge);
            int i = 1;
            while (i <= 7 && !dodajEmisijuDanu(null, emisija, i)) {
                i++;
            }
        });
    }

    private void dodajEmisijuDanima(String dani, LocalTime pocetakEmisije, Emisija emisija) {
        if (nizDanova(dani)) {
            int prviDan = Character.getNumericValue(dani.charAt(0));
            int zadnjiDan = Character.getNumericValue(dani.charAt(2));
            for (int i = prviDan; i <= zadnjiDan; i++) {
                dodajEmisijuDanu(pocetakEmisije, emisija, i);
            }
        } else if (nabrajanjeDanova(dani)) {
            String[] nabrajaniDani = dani.split("\\s*,\\s*");
            Stream.of(nabrajaniDani)
                    .forEach(indexDana -> dodajEmisijuDanu(pocetakEmisije, emisija, Integer.parseInt(indexDana)));
        } else {
            dodajEmisijuDanu(pocetakEmisije, emisija, Integer.parseInt(dani));
        }
    }

    private boolean dodajEmisijuDanu(LocalTime pocetakEmisije, Emisija emisija, int indexDana) {
        boolean uspjesnoDodano = false;
        Dan dan = (Dan) (Component) dohvatiSvuDjecu().get(indexDana - 1);
        if (dan != null) {
            if (pocetakEmisije != null) {
                uspjesnoDodano = dan.dodajEmisiju(pocetakEmisije, emisija);
            } else {
                uspjesnoDodano = dan.dodajEmisiju(emisija);
            }
        } else {
            System.err.println("Ne postoji dan sa indexom: " + indexDana + " u datoteci programa");
        }
        return uspjesnoDodano;
    }

    private boolean nizDanova(String dan) {
        return dan.matches("^[0-9]*-[0-9]+$");
    }

    private boolean nabrajanjeDanova(String dan) {
        return dan.matches("[0-9]*(,[0-9]+)+");
    }

    private String[] dohvatiRedoveZapisaPremaBrojuAtributa(String[] redoviZapisa, int brojAtributa) {
        return Stream.of(redoviZapisa)
                .filter(filtrirajZapisePremaBrojuAtributa(brojAtributa))
                .filter(filtrirajZapisePremaIspravnostiAtributa(brojAtributa))
                .toArray(String[]::new);
    }

    private Predicate<String> filtrirajZapisePremaBrojuAtributa(int brojPotrebnihAtributa) {
        return redZapisa -> {
            String[] atributi = redZapisa.split("\\s*;\\s*") ;
            int brojAtributa = zavrsavaZapisSaOsobomUlogom(redZapisa)
                    ? brojPotrebnihAtributa + 1
                    : brojPotrebnihAtributa;
            return atributi.length == brojAtributa;
        };
    }

    private boolean zavrsavaZapisSaOsobomUlogom(String redZapisa) {
        String[] atributi = redZapisa.split("\\s*;\\s*");
        return !redZapisa.endsWith(";")
                && atributi[atributi.length - 1].matches(OSOBA_ULOGA_REGEX);
    }

    private Predicate<String> filtrirajZapisePremaIspravnostiAtributa(int brojPotrebnihAtributa) {
        return redZapisa -> {
            String[] atributi = redZapisa.split("\\s*;\\s*");

            boolean ispravno = true;
            int brojAtributa = brojPotrebnihAtributa;

            if (brojAtributa-- == 3) {
                ispravno = atributi[2].matches(VRIJEME_REGEX);
            }
            if (brojAtributa-- == 2) {
                ispravno = ispravno && atributi[1].matches(DAN_IZVODENJA_REGEX);
            }
            if (brojAtributa == 1) {
                ispravno = ispravno && atributi[0].matches(ID_REGEX);
            }
            return ispravno;
        };
    }

    private String[] removeSubArray(String[] array, String[] subArray) {
        return Stream.of(array)
                .filter(elem -> !Arrays.asList(subArray).contains(elem))
                .toArray(String[]::new);
    }

}
