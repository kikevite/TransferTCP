package cat.kikevite.transfertcp;

public class Missatge {

    String cos;
    boolean enviat;
    String data;
    String origen;
    String desti;

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDesti() {
        return desti;
    }

    public void setDesti(String desti) {
        this.desti = desti;
    }

    public String getCos() {
        return cos;
    }

    public void setCos(String cos) {
        this.cos = cos;
    }

    public boolean isEnviat() {
        return enviat;
    }

    public void setEnviat(boolean enviat) {
        this.enviat = enviat;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
