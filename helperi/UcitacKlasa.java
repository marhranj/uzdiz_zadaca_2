package marhranj_zadaca_2.helperi;

import marhranj_zadaca_2.entiteti.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UcitacKlasa {

    private UpravljacDatotekama upravljacDatotekama;

    public UcitacKlasa(UpravljacDatotekama upravljacDatotekama) {
        this.upravljacDatotekama = upravljacDatotekama;
    }

    public List<Osoba> ucitajOsobe() {
        return ucitajSadrzajDatotekeUKlasu(upravljacDatotekama.getDatotekaOsobe(), Osoba.class);
    }

    public List<Uloga> ucitajUloge() {
        return ucitajSadrzajDatotekeUKlasu(upravljacDatotekama.getDatotekaUloge(), Uloga.class);
    }

    public List<Emisija> ucitajEmisije() {
        return ucitajSadrzajDatotekeUKlasu(upravljacDatotekama.getDatotekaEmisije(), Emisija.class);
    }

    public List<Program> ucitajPrograme() {
        return ucitajSadrzajDatotekeUKlasu(upravljacDatotekama.getDatotekaTvKuca(), Program.class);
    }

    public List<VrstaEmisije> ucitajVrsteEmisija() {
        return ucitajSadrzajDatotekeUKlasu(upravljacDatotekama.getDatotekaVrste(), VrstaEmisije.class);
    }

    private <T> List<T> ucitajSadrzajDatotekeUKlasu(String sadrzajDatoteke, Class<T> klasa) {
        String[] redoviZapisa = sadrzajDatoteke.split("\\r?\\n");
        redoviZapisa = Arrays.copyOfRange(redoviZapisa, 1, redoviZapisa.length);
        return Stream.of(redoviZapisa)
                .map(zapisDatoteke -> {
                    try {
                        return klasa.getDeclaredConstructor(String.class).newInstance(zapisDatoteke);
                    } catch (Exception e) {
                        String message;
                        if (e.getCause().getCause() != null) {
                            message = e.getCause().getLocalizedMessage() + ": " + e.getCause().getCause();
                        } else {
                            message = e.getCause().getLocalizedMessage();
                        }
                        System.err.println(message);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}
