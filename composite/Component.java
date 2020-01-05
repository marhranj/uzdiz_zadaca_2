package marhranj_zadaca_2.composite;

import java.util.List;

public interface Component<T> {

    void dodaj(T component);

    void dodajSve(List<T> components);

    void obrisi(T component);

    T dohvatiPremaIndexu(int index);

}
