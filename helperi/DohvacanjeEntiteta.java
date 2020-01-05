package marhranj_zadaca_2.helperi;

import marhranj_zadaca_2.entiteti.*;

import java.util.Optional;

public final class DohvacanjeEntiteta {

    private DohvacanjeEntiteta() {}

    public static Optional<Osoba> dohvatiOsobuPremaId(int idOsobe) {
        Optional<Osoba> osobaId = TvKuca.dajInstancu().dohvatiInicijalneOsobe()
                .stream()
                .filter(osoba -> osoba.getId() == idOsobe)
                .map(osoba -> (Osoba) osoba.clone())
                .findFirst();
        if (!osobaId.isPresent()) {
            System.err.println("Nije pronađena osoba sa id " + osobaId);
        }
        return osobaId;
    }

    public static Optional<Uloga> dohvatiUloguPremaId(int idUloge) {
        Optional<Uloga> ulogaId = TvKuca.dajInstancu().dohvatiInicijalneUloge()
                .stream()
                .filter(uloga -> uloga.getId() == idUloge)
                .findFirst();
        if (!ulogaId.isPresent()) {
            System.err.println("Nije pronađena uloga sa id " + ulogaId);
        }
        return ulogaId;
    }

    public static Optional<Emisija> dohvatiEmisijuPremaId(int idEmisije) {
        Optional<Emisija> emisijaSaId = TvKuca.dajInstancu().dohvatiInicijalneEmisije()
                .stream()
                .filter(emisija -> emisija.getId() == idEmisije)
                .map(emisija -> (Emisija) emisija.clone())
                .findFirst();
        if (!emisijaSaId.isPresent()) {
            System.err.println("Nije pronađena emisija sa id " + idEmisije);
        }
        return emisijaSaId;
    }

    public static Optional<VrstaEmisije> dohvatiVrstuPremaId(int idVrste) {
        Optional<VrstaEmisije> vrstaSaId = TvKuca.dajInstancu().dohvatiInicijalneVrsteEmisija()
                .stream()
                .filter(vrstaEmisije -> vrstaEmisije.getId() == idVrste)
                .findFirst();
        if (!vrstaSaId.isPresent()) {
            System.err.println("Nije pronađena vrsta emisije sa id " + idVrste);
        }
        return vrstaSaId;
    }

}
