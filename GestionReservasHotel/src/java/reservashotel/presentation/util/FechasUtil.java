
package reservashotel.presentation.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author alberto
 * Clase con funcionalidades específicas para fechas.
 */
public class FechasUtil {
    
    /**
     * Obtiene la fecha actual.
     * @return 
     */
    public static Date fechaActual() {
        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }
    
    /**
     * Incrementa/decrementa los dias indicados en el parámetro
     * a la fecha también indicada.
     * @param fecha Fecha
     * @param dias Días
     * @return Date
     */
    public static Date fechaMasMenosDias(Date fecha, int dias) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); 
        calendar.add(Calendar.DAY_OF_YEAR, dias);  

        return calendar.getTime();
    }
    
    /**
     * Calcula la diferencia en dias entre las fechas.
     * @param fechaInicial Fecha inicial.
     * @param fechaFinal Fecha final.
     * @return número de días.
     */
    public static int diferenciaDias(Date fechaInicial, Date fechaFinal) {

        long millsecs = 24 * 60 * 60 * 1000;
        int dias = (int)((int)(fechaFinal.getTime() - fechaInicial.getTime())/ millsecs);
        
        return dias;
    }
    
    /**
     * Obtiene el año de una fecha.
     * @param fecha
     * @return año
     */
    public static int anoFecha(Date fecha){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        return calendar.get(Calendar.YEAR);
    }
    
    /**
     * Obtiene el mes de una fecha.
     * @param fecha
     * @return mes
     */
    public static int mesFecha(Date fecha){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        return calendar.get(Calendar.MONTH)+1;
    }

        /**
     * Obtiene el dia de una fecha.
     * @param fecha
     * @return dia
     */
    public static int diaFecha(Date fecha){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * Elimina los datos de hora, minutos... de la fecha.
     * @param fecha
     * @return fecha sin horas.
     * @throws java.text.ParseException
     */
    public static Date quitaHorasFecha(Date fecha) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String fechaString = formato.format(fecha);
        Date fechaSinHora = formato.parse(fechaString);

        return fechaSinHora;
    }
}
