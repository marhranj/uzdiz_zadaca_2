package marhranj_zadaca_2.composite;

import java.util.List;

public interface Component<T extends Component> {

    void dodajDijete(T component);

    void dodajSvuDjecu(List<T> components);

    T dohvatiDijetePremaIndexu(int index);

    List<T> dohvatiSvuDjecu();

    void postaviRoditelja(T roditelj);

    T dohvatiRoditelja();

}
