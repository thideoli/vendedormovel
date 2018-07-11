package br.com.thideoli.vendedormovel.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Date {

    public static String pegaDataHoraAtual() {
        Calendar cal = new GregorianCalendar(Calendar.getInstance().getTimeZone());
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return df.format(cal.getTime());
    }

    public static String pegaDataHoraSegundoAtual() {
        Calendar cal = new GregorianCalendar(Calendar.getInstance().getTimeZone());
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return df.format(cal.getTime());
    }

}
