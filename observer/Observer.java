package marhranj_zadaca_2.observer;

import marhranj_zadaca_2.entiteti.Uloga;

public interface Observer {

    void update(String imeOsobe, Uloga staraUloga, Uloga novaUloga);

}
