package marhranj_zadaca_2.observer;

import marhranj_zadaca_2.entiteti.Uloga;

public interface Subject {

    void dodajObserver(Observer observer);
    void obrisiObserver(Observer observer);
    void obavijesti(String imeOsobe, Uloga staraUloga, Uloga novaUloga);

}
