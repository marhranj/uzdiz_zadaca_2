package marhranj_zadaca_2.helperi;

import java.util.Map;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class UpravljacArgumentimaKmdLin {

    private String[] argumentiKomandneLinije;

    private String datotekaEmisije;
    private String datotekaOsobe;
    private String datotekaTvKuca;
    private String datotekaUloge;
    private String datotekaVrste;

    public UpravljacArgumentimaKmdLin(String[] argumentiKomandneLinije) {
        this.argumentiKomandneLinije = argumentiKomandneLinije;
    }

    public boolean popuniNaizveDatoteka() {
        Map<String, String> parametri = pretvoriUMapuParametarVrijednost(argumentiKomandneLinije);
        datotekaOsobe = parametri.get("o");
        datotekaEmisije = parametri.get("e");
        datotekaTvKuca = parametri.get("t");
        datotekaUloge = parametri.get("u");
        datotekaVrste = parametri.get("v");
        return Stream.of(datotekaOsobe, datotekaEmisije, datotekaTvKuca, datotekaUloge, datotekaVrste)
                .allMatch(Objects::nonNull);
    }

    public String getDatotekaEmisije() {
        return datotekaEmisije;
    }

    public String getDatotekaOsobe() {
        return datotekaOsobe;
    }

    public String getDatotekaTvKuca() {
        return datotekaTvKuca;
    }

    public String getDatotekaUloge() {
        return datotekaUloge;
    }

    public String getDatotekaVrste() {
        return datotekaVrste;
    }

    private Map<String, String> pretvoriUMapuParametarVrijednost(String[] argumenti) {
        BinaryOperator<String> ignorirajDuplikate = (vrijednostJedan, vrijednostDva) -> vrijednostDva;
        return IntStream.range(0, argumenti.length / 2)
                .boxed()
                .collect(Collectors.toMap(dohvatiKeyMapper(argumenti), dohvatiValueMapper(argumenti), ignorirajDuplikate));
    }

    private Function<Integer, String> dohvatiValueMapper(String[] argumenti) {
        return i -> argumenti[2 * i + 1];
    }

    private Function<Integer, String> dohvatiKeyMapper(String[] argumenti) {
        return i -> {
            String argument = argumenti[2 * i];
            IntPredicate filtrirajSveCrte = character ->
                    argument.indexOf(character) < 2 && (char) character == '-';
            return argument.chars()
                    .filter(filtrirajSveCrte.negate())
                    .collect(StringBuilder::new,
                            StringBuilder::appendCodePoint,
                            StringBuilder::append)
                    .toString()
                    .toLowerCase();
        };
    }

}
