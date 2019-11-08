package rs.zis.app.zis.model;

public class Izvestaj {
    private float ocena_klinike;
    private float ocena_lekara;
    private float prihod_klinike;

    public Izvestaj() {
    }

    public Izvestaj(float ocena_klinike, float ocena_lekara, float prihod_klinike) {
        this.ocena_klinike = ocena_klinike;
        this.ocena_lekara = ocena_lekara;
        this.prihod_klinike = prihod_klinike;
    }

    public float getOcena_klinike() {
        return ocena_klinike;
    }

    public void setOcena_klinike(float ocena_klinike) {
        this.ocena_klinike = ocena_klinike;
    }

    public float getOcena_lekara() {
        return ocena_lekara;
    }

    public void setOcena_lekara(float ocena_lekara) {
        this.ocena_lekara = ocena_lekara;
    }

    public float getPrihod_klinike() {
        return prihod_klinike;
    }

    public void setPrihod_klinike(float prihod_klinike) {
        this.prihod_klinike = prihod_klinike;
    }
}
