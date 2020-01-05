package marhranj_zadaca_2.helperi;

import marhranj_zadaca_2.composite.Component;
import marhranj_zadaca_2.entiteti.Dan;
import marhranj_zadaca_2.entiteti.Program;
import marhranj_zadaca_2.entiteti.Raspored;
import marhranj_zadaca_2.entiteti.TvKuca;

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
                    System.out.println("Izbornik (za prekid upisite 0): ");
                    Program program = dohvatiOdabraniProgram(scanner, programi);
                    Dan dan = dohvatiOdabraniDan(scanner, program);
                    prikaziOpcijeZaDan(scanner, dan);
                } catch (NumberFormatException e){
                    System.err.println("Morate unijeti broj");
                }
            }
        } else {
            System.err.println("Niti jedan program nije inicijaliziran");
        }
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
//            System.out.println("2. Prikaz statistike za dan: " + dan.getNaziv());
            odabir = Integer.parseInt(scanner.nextLine());
             if (odabir < 0 || odabir > 2) {
                System.err.println("Niste unijeli ispravan odabir");
            }
        } while (odabir < 0 || odabir > 2);

        if (odabir == 0) {
            System.exit(0);
        } else {
            System.out.println(dan.dohvatiTermineZaDan());
        }
    }

}
