package thiago.giovane.chat;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Aluno on 26/12/2016.
 */

public class Hora {
    static String hora;
    static String minuto;
    static String segundo;
    static String horaCompleta = hora+"/"+minuto+"/"+segundo;

    public Hora() {
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMinuto() {
        return minuto;
    }

    public void setMinuto(String minuto) {
        this.minuto = minuto;
    }

    public String getSegundo() {
        return segundo;
    }

    public void setSegundo(String segundo) {
        this.segundo = segundo;
    }

    public String getHoraCompleta() {
        return horaCompleta;
    }

    public void setHoraCompleta(String horaCompleta) {
        this.horaCompleta = horaCompleta;
    }

    @Override
    public String toString() {
        return "Horário :"+horaCompleta;
    }
}//fecha classe
