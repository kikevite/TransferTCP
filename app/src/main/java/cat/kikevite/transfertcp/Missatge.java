package cat.kikevite.transfertcp;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Missatge {

    String cos;
    boolean enviat;
    Date fecha;
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

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        return formatter.format(fecha);
    }

    public String getAny() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        return formatter.format(fecha);
    }

    public String getMes() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM");
        return formatter.format(fecha);
    }

    public String getDia() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd");
        return formatter.format(fecha);
    }

}
