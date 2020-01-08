package marhranj_zadaca_2.helperi;

import marhranj_zadaca_2.composite.Component;
import marhranj_zadaca_2.decorator.DecoratorImpl;
import marhranj_zadaca_2.entiteti.*;
import marhranj_zadaca_2.iterator.ContainerEmisija;
import marhranj_zadaca_2.iterator.Iterator;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

import static marhranj_zadaca_2.Konstante.INDEX_PLANA_PROGRAMA;

public class Izbornik {

    public Izbornik() {
        prikaziIzbornik();
    }

    private void prikaziIzbornik() {
        Scanner scanner = new Scanner(System.in);
        List<Program> programi = TvKuca.dajInstancu().dohvatiDijetePremaIndexu(INDEX_PLANA_PROGRAMA)
                .dohvatiSvuDjecu();
        if (!programi.isEmpty()) {
            while (true) {
                try {
                    prikaziIzbornikSaOpcijama(scanner, programi);
                } catch (NumberFormatException e){
                    System.err.println("Morate unijeti broj");
                }
            }
        } else {
            System.err.println("Niti jedan program nije inicijaliziran");
        }
    }

    private void prikaziIzbornikSaOpcijama(Scanner scanner, List<Program> programi) {
        System.out.println("Izbornik (za prekid upisite 0): ");
        int odabir = prikaziOpcije(scanner);
        if (odabir == 1) {
            Program program = dohvatiOdabraniProgram(scanner, programi);
            Dan dan = dohvatiOdabraniDan(scanner, program);
            prikaziOpcijeZaDan(scanner, dan);
        } else if (odabir == 2) {
            VrstaEmisije odabranaVrsta = odabirVrsteEmisija(scanner);
            Iterator<Emisija> emisijeSaOdabranomVrstom = new ContainerEmisija(odabranaVrsta).dohvatiIterator();
            ispisEmisija(emisijeSaOdabranomVrstom);
        } else {

        }
    }

    private int prikaziOpcije(Scanner scanner) {
        int odabir;
        do {
            System.out.println("1. Programi: ");
            System.out.println("2. Vrsta emisije za koju se želi tjedni plan: ");
            System.out.println("3. Osobu za koju se želi promjeniti uloga: ");
            odabir = Integer.parseInt(scanner.nextLine());
            if (odabir < 0 || odabir > 3) {
                System.err.println("Niste unijeli ispravan broj");
            }
        } while (odabir < 0 || odabir > 3);

        if (odabir == 0) {
            System.exit(0);
        }
        return odabir;
    }

    private Program dohvatiOdabraniProgram(Scanner scanner, List<Program> programi) {
        int odabir;
        do {
            System.out.println("Odaberite program: ");
            IntStream.range(0, programi.size())
                    .forEach(i -> System.out.println(i + 1 + ". " + programi.get(i).getNaziv()));
            odabir = Integer.parseInt(scanner.nextLine());
            if (odabir < 0 || odabir > programi.size()) {
                System.err.println("Niste unijeli ispravan broj");
            }
        } while (odabir < 0 || odabir > programi.size());

        if (odabir == 0) {
            System.exit(0);
        }

        return programi.get(odabir - 1);
    }

    private Dan dohvatiOdabraniDan(Scanner scanner, Program program) {
        int odabir;
        Raspored raspored;
        do {
            System.out.println("Odaberite dan za koji zelite ispis programa: ");
            raspored = (Raspored) (Component) program.dohvatiDijetePremaIndexu(0);
            for (int i = 1; i <= 7; i++) {
                System.out.println(i + ". " + ((Dan) (Component) raspored.dohvatiDijetePremaIndexu(i - 1)).getNaziv());
            }
            odabir = Integer.parseInt(scanner.nextLine());
            if (odabir < 0 || odabir > 7) {
                System.err.println("Niste unijeli ispravan dan");
            }
        } while (odabir < 0 || odabir > 7);

        if (odabir == 0) {
            System.exit(0);
        }

        return (Dan) (Component) raspored.dohvatiDijetePremaIndexu(odabir - 1);
    }

    private void prikaziOpcijeZaDan(Scanner scanner, Dan dan) {
        int odabir;
        do {
            System.out.println("Odaberite opciju koju zelite za dan: ");
            System.out.println("1. Prikaz rasporeda za dan: " + dan.getNaziv());
            System.out.println("2. Prikaz prihoda od reklama (min) za dan: " + dan.getNaziv());
            odabir = Integer.parseInt(scanner.nextLine());
             if (odabir < 0 || odabir > 2) {
                System.err.println("Niste unijeli ispravan odabir");
            }
        } while (odabir < 0 || odabir > 2);

        if (odabir == 0) {
            System.exit(0);
        } else if (odabir == 1) {
            Iterator<Emisija> iteratorEmisija = new ContainerEmisija(dan).dohvatiIterator();
            ispisEmisija(iteratorEmisija);
        } else {
            ispisPrihodaZaDan(dan);
        }
    }

    private void ispisPrihodaZaDan(Dan dan) {
        ispisZaglavljaZaDan();
        String ispis = dan.dohvatiDekorator(new DecoratorImpl()).decorate();
        System.out.println(ispis);
        ispisLinijeTablice(39);
    }

    private VrstaEmisije odabirVrsteEmisija(Scanner scanner) {
        List<VrstaEmisije> vrstaEmisija = TvKuca.dajInstancu().dohvatiInicijalneVrsteEmisija();
        int odabir;
        do {
            System.out.println("Odaberite vrstu emisije");
            for (int i = 0; i < vrstaEmisija.size(); i++) {
                System.out.println(i + 1 + " " + vrstaEmisija.get(i).getNaziv());
            }
            odabir = Integer.parseInt(scanner.nextLine());
            if (odabir < 0 || odabir > vrstaEmisija.size()) {
                System.err.println("Niste unijeli ispravan broj");
            }
        } while (odabir < 0 || odabir > vrstaEmisija.size());
        return vrstaEmisija.get(odabir  - 1);
    }

    private void ispisEmisija(Iterator<Emisija> iteratorEmisija) {
        ispisZaglavljaTabliceEmisija();
        while (iteratorEmisija.hasNext()) {
            Emisija emisija = iteratorEmisija.next();
            Dan dan = (Dan) (Component) emisija.dohvatiRoditelja();
            Raspored raspored = (Raspored) (Component) dan.dohvatiRoditelja();
            Program program = (Program) (Component) raspored.dohvatiRoditelja();
            String ispis = program.dohvatiDekorator(
                    dan.dohvatiDekorator(
                            emisija.dohvatiDekorator(
                                    emisija.getVrstaEmisije().dohvatiDekorator(
                                            new DecoratorImpl()))))
                    .decorate();
            System.out.println(ispis);
            ispisLinijeTablice(274);
        }
    }

    private void ispisLinijeTablice(int brojCrtica) {
        System.out.print(" ");
        IntStream.range(0, brojCrtica)
                .forEach(i -> System.out.print("-"));
        System.out.println();
    }

    private void ispisZaglavljaTabliceEmisija() {
        ispisLinijeTablice(274);
        String ispis = new Program().dohvatiDekorator(
                new Dan().dohvatiDekorator(
                        new Emisija().dohvatiDekorator(
                                new VrstaEmisije().dohvatiDekorator(
                                        new DecoratorImpl())))).decorateZaglavlje();
        System.out.println(ispis);
        ispisLinijeTablice(274);
    }

    private void ispisZaglavljaZaDan() {
        ispisLinijeTablice(39);
        String ispis = new Dan().dohvatiDekorator(new DecoratorImpl()).decorateZaglavlje();
        System.out.println(ispis);
        ispisLinijeTablice(39);
    }

}
