package marhranj_zadaca_2.composite;

import marhranj_zadaca_2.iterator.Container;
import marhranj_zadaca_2.iterator.Iterator;

import java.util.ArrayList;
import java.util.List;

public class Composite<T extends Component> implements Component<T>, Container<T> {

    private List<T> listovi = new ArrayList<>();
    private T roditelj;

    @Override
    public void dodajDijete(T component) {
        listovi.add(component);
        component.postaviRoditelja(this);
    }

    @Override
    public void dodajSvuDjecu(List<T> components) {
        listovi.addAll(components);
        components.forEach(component -> component.postaviRoditelja(this));
    }

    @Override
    public T dohvatiDijetePremaIndexu(int index) {
        return listovi.get(index);
    }

    public List<T> dohvatiSvuDjecu() {
        return listovi;
    }

    @Override
    public void postaviRoditelja(T roditelj) {
        this.roditelj = roditelj;
    }

    @Override
    public T dohvatiRoditelja() {
        return roditelj;
    }

    @Override
    public Iterator<T> dohvatiIteratorDjece() {
        return new IteratorDjece();
    }

    private class IteratorDjece implements Iterator<T> {

        int index;

        @Override
        public boolean hasNext() {
            return index < listovi.size();
        }

        @Override
        public T next() {
            if (this.hasNext()){
                return listovi.get(index++);
            }
            return null;
        }
    }

}
