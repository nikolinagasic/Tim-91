package rs.zis.app.zis.dto;

public class ReportDTO {
    private double[] daniPrihod;
    private double[] mesecPrihod;
    private double[] godinaPrihod;
    private double[] daniPregledi;
    private double[] mesecPregledi;
    private double[] godinaPregledi;

    public ReportDTO(double[] daniPrihod, double[] mesecPrihod, double[] godinaPrihod, double[] daniPregledi, double[] mesecPregledi, double[] godinaPregledi) {
        this.daniPrihod = daniPrihod;
        this.mesecPrihod = mesecPrihod;
        this.godinaPrihod = godinaPrihod;
        this.daniPregledi = daniPregledi;
        this.mesecPregledi = mesecPregledi;
        this.godinaPregledi = godinaPregledi;
    }

    public double[] getDaniPrihod() {
        return daniPrihod;
    }
    public void setDaniPrihod(double[] daniPrihod) {
        this.daniPrihod = daniPrihod;
    }

    public double[] getMesecPrihod() {
        return mesecPrihod;
    }

    public void setMesecPrihod(double[] mesecPrihod) {
        this.mesecPrihod = mesecPrihod;
    }

    public double[] getGodinaPrihod() {
        return godinaPrihod;
    }

    public void setGodinaPrihod(double[] godinaPrihod) {
        this.godinaPrihod = godinaPrihod;
    }

    public double[] getDaniPregledi() {
        return daniPregledi;
    }

    public void setDaniPregledi(double[] daniPregledi) {
        this.daniPregledi = daniPregledi;
    }

    public double[] getMesecPregledi() {
        return mesecPregledi;
    }

    public void setMesecPregledi(double[] mesecPregledi) {
        this.mesecPregledi = mesecPregledi;
    }

    public double[] getGodinaPregledi() {
        return godinaPregledi;
    }

    public void setGodinaPregledi(double[] godinaPregledi) {
        this.godinaPregledi = godinaPregledi;
    }
}
