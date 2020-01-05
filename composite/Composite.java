package marhranj_zadaca_2.composite;

import java.util.ArrayList;
import java.util.List;

public class Composite<T extends Component> implements Component<T> {

    private List<T> listovi = new ArrayList<>();

    @Override
    public void dodaj(T component) {
        listovi.add(component);
    }

    @Override
    public void dodajSve(List<T> components) {
        listovi.addAll(components);
    }

    @Override
    public void obrisi(T component) {
        listovi.remove(component);
    }

    @Override
    public T dohvatiPremaIndexu(int index) {
        return listovi.get(index);
    }

    public List<T> dohvatiListove() {
        return listovi;
    }

}
