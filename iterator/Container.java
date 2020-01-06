package marhranj_zadaca_2.iterator;

import marhranj_zadaca_2.composite.Component;

public interface Container<T extends Component> {

    Iterator<T> dohvatiIteratorDjece();

}
