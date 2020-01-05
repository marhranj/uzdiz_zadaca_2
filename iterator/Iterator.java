package marhranj_zadaca_2.iterator;

import marhranj_zadaca_2.composite.Component;

public interface Iterator<T extends Component> {

    boolean hasNext();
    T next();

}
