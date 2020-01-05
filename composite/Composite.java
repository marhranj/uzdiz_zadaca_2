package marhranj_zadaca_2.composite;

import marhranj_zadaca_2.iterator.Container;
import marhranj_zadaca_2.iterator.Iterator;

import java.util.ArrayList;
import java.util.List;

public class Composite<T extends Component> implements Component<T>, Container<T> {

    private List<T> listovi = new ArrayList<>();

    @Override
    public void dodajDijete(T component) {
        listovi.add(component);
    }

    @Override
    public void dodajSvuDjecu(List<T> components) {
        listovi.addAll(components);
    }

    @Override
    public void obrisiDijete(T component) {
        listovi.remove(component);
    }

    @Override
    public T dohvatiDijetePremaIndexu(int index) {
        return listovi.get(index);
    }

    public List<T> dohvatiSvuDjecu() {
        return listovi;
    }

    @Override
    public Iterator<T> dohvatiIterator() {
        return new IteratorChildova();
    }

    private class IteratorChildova implements Iterator<T> {

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
