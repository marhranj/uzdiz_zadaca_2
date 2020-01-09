package marhranj_zadaca_2.observer;

import marhranj_zadaca_2.entiteti.Emisija;
import marhranj_zadaca_2.entiteti.Uloga;

import java.util.ArrayList;
import java.util.List;

public class EmisijaPublisher implements Subject {

    private List<Observer> observers = new ArrayList<>();

    @Override
    public void dodajObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void obrisiObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void obavijesti(String imeOsobe, Uloga staraUloga, Uloga novaUloga) {
        observers.forEach(observer -> observer.update(imeOsobe, staraUloga, novaUloga));
    }

}
