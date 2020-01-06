package marhranj_zadaca_2.entiteti;

import marhranj_zadaca_2.composite.Composite;

public class VrstaEmisije extends Composite<VrstaEmisije> {

    private int id;
    private String naziv;
    private boolean imaReklame;
    private long maksTrajanjeReklami;

    public VrstaEmisije(String redDatotekeVrste) throws IllegalArgumentException {
        String[] atributi = redDatotekeVrste.split("\\s*;\\s*");
        if (atributi.length > 1) {
            try {
                id = Integer.parseInt(atributi[0]);
                naziv = atributi[1];
                imaReklame = parsirajIntUBoolean(Integer.parseInt(atributi[2]));
                if (imaReklame) {
                    maksTrajanjeReklami = Long.parseLong(atributi[3]);
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Neispravan zapis u datoteci vrste: " + redDatotekeVrste, e);
            }
        } else {
            throw new IllegalArgumentException("Neispravan zapis u datoteci vrste: " + redDatotekeVrste);
        }
    }

    public int getId() {
        return id;
    }

    public String getNaziv() {
        return naziv;
    }

    public boolean imaReklame() {
        return imaReklame;
    }

    public long getMaksTrajanjeReklami() {
        return maksTrajanjeReklami;
    }

    private boolean parsirajIntUBoolean(int i) throws IllegalArgumentException {
        if (i == 0) {
            return false;
        } else if (i == 1) {
            return true;
        } else {
            throw new IllegalArgumentException(String.format("Nije moguce parsirati: %d u boolean", i));
        }
    }

}
