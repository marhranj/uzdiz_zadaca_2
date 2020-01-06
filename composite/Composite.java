package marhranj_zadaca_2.composite;

import marhranj_zadaca_2.decorator.TablicniDekorator;

import java.util.ArrayList;
import java.util.List;

public class Composite<T extends Component> extends TablicniDekorator implements Component<T> {

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

}
