package marhranj_zadaca_2.iterator;

import marhranj_zadaca_2.composite.Component;
import marhranj_zadaca_2.entiteti.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static marhranj_zadaca_2.Konstante.INDEX_PLANA_PROGRAMA;

public class ContainerEmisija implements Container<Emisija> {

    private List<Emisija> emisije;

    public ContainerEmisija() {
        List<Program> programi = TvKuca.dajInstancu().dohvatiDijetePremaIndexu(INDEX_PLANA_PROGRAMA)
                .dohvatiSvuDjecu();
        emisije = programi.stream()
                .map(program -> (Raspored) (Component) program.dohvatiDijetePremaIndexu(0))
                .map(raspored -> (List<Dan>) (List) raspored.dohvatiSvuDjecu())
                .flatMap(Collection::stream)
                .map(dan -> (List<Emisija>) (List) dan.dohvatiSvuDjecu())
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public ContainerEmisija(VrstaEmisije vrstaEmisije) {
        this();
        emisije = emisije.stream()
                .filter(emisija -> emisija.getVrstaEmisije().equals(vrstaEmisije))
                .collect(Collectors.toList());
    }

    public ContainerEmisija(Dan dan) {
        emisije = (List<Emisija>) (List) dan.dohvatiSvuDjecu();
    }

    @Override
    public Iterator<Emisija> dohvatiIterator() {
        return new IteratorEmisija();
    }

    private class IteratorEmisija implements Iterator<Emisija> {

        int index;

        @Override
        public boolean hasNext() {
            return index < emisije.size();
        }

        @Override
        public Emisija next() {
            if (this.hasNext()){
                return emisije.get(index++);
            }
            return null;
        }
    }

}
