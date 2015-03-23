
package reservashotel.persistence.dao.generic;

/**
 * @author alberto
 * Clase con las constantes utilizadas en la capa de persistencia.
 */
public class ConstantesDAO {

    // Usuario
    public static final String USUARIO_NOMBREUSUARIO        = "nombreUsuario";
    
    // Cliente
    public static final String CLIENTE_NOMBRE               = "nombre";
    public static final String CLIENTE_APELLIDO1            = "apellido1";
    public static final String CLIENTE_APELLIDO2            = "apellido2";
    public static final String CLIENTE_DNI                  = "dni";
    public static final String CLIENTE_ID_PROVINCIA         = "provincia.id";
    
    // Cargo
    public static final String CARGO_CODIGO                 = "codigo";
    public static final String CARGO_NOMBRE                 = "nombre";
    public static final String CARGO_PRECIO                 = "precio";
    public static final String CARGO_ACTIVOSN               = "activoSn";
    
    // EstadoReserva
    public static final String ESTADORESERVA_ACTIVOSN       = "activoSn";
    
    // Habitacion
    public static final String HABITACION_CODIGO            = "codigo";
    public static final String HABITACION_NOMBRE            = "nombre";
    public static final String HABITACION_EXTERIORSN        = "exteriorSn";
    public static final String HABITACION_FUMADORSN         = "fumadorSn";
    public static final String HABITACION_MOVREDUCIDASN     = "movReducidaSn";
    public static final String HABITACION_CAMASUPSN         = "camaSuplSn";
    public static final String HABITACION_ACTIVOSN          = "activoSn";
    public static final String HABITACION_ID_TIPOHABITACION = "tipoHabitacion.id";
    
    // TipoHabitacion
    public static final String TIPOHABITACION_ACTIVOSN      = "activoSn";
    
    // ReservaCab
    public static final String RESERVACAB_REFERENCIA        = "referencia";
    public static final String RESERVACAB_FECHAREGISTRO     = "fechaRegistro";
    public static final String RESERVACAB_FECHAENTRADA      = "fechaEntrada";
    public static final String RESERVACAB_FECHASALIDA       = "fechaSalida";
    public static final String RESERVACAB_CLIENTE           = "cliente";
    public static final String RESERVACAB_ID_ESTADO         = "estadoReserva.id";
    
    // Provincia
    public static final String PROVINCIA_ID                 = "idProvincia";
}
