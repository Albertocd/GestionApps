
package reservashotel.persistence.dao;

import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import reservashotel.business.vo.BusquedaHabitacionFiltro;
import reservashotel.business.vo.generic.ConstantesFiltro;
import reservashotel.business.vo.HabitacionFiltro;
import reservashotel.persistence.dao.generic.ConstantesDAO;
import reservashotel.persistence.dao.generic.GenericDAO;
import reservashotel.persistence.entities.Habitacion;


/**
 * @author alberto
 * Clase DAO para la entidad Habitacion.
 */
public class HabitacionDAO extends GenericDAO<Habitacion,Integer> {
    
    /**
     * Lista entidades Habitacion en base al filtro recibido.
     * @param sesion Sesion 
     * @param filtro Filtro
     * @param ordenarPor Criterio de ordenación
     * @return List< Cargo >
     */
    public List<Habitacion> listar(Session sesion, HabitacionFiltro filtro, String ordenarPor) {
        Boolean bValor = null;
        List<Habitacion> lista = null;
        
        try {
        
            Criteria crit = sesion.createCriteria(Habitacion.class);
        
            if (filtro != null) {
                
                if (filtro.getCodigo() != null && filtro.getCodigo().length() > 0) {
                    crit.add(Restrictions.ilike(ConstantesDAO.HABITACION_CODIGO, filtro.getCodigo(), MatchMode.ANYWHERE));
                }
                if (filtro.getNombre() != null && filtro.getNombre().length() > 0) {
                    crit.add(Restrictions.ilike(ConstantesDAO.HABITACION_NOMBRE, filtro.getNombre(), MatchMode.ANYWHERE));
                }
                if (filtro.getExteriorSn() != 0) {
                    if (filtro.getExteriorSn() == ConstantesFiltro.HAB_EXTERIOR_SI) {
                        bValor = Boolean.TRUE;
                    } else if (filtro.getExteriorSn() == ConstantesFiltro.HAB_EXTERIOR_NO) {
                        bValor = Boolean.FALSE;
                    }
                    crit.add(Restrictions.eq(ConstantesDAO.HABITACION_EXTERIORSN, bValor));
                }
                if (filtro.getFumadorSn() != 0) {
                    if (filtro.getFumadorSn() == ConstantesFiltro.HAB_FUMADOR_SI) {
                        bValor = Boolean.TRUE;
                    } else if (filtro.getFumadorSn() == ConstantesFiltro.HAB_FUMADOR_NO) {
                        bValor = Boolean.FALSE;
                    }
                    crit.add(Restrictions.eq(ConstantesDAO.HABITACION_FUMADORSN, bValor));
                }
                if (filtro.getMovReducidaSn() != 0) {
                    if (filtro.getMovReducidaSn() == ConstantesFiltro.HAB_MOV_REDUCIDA_SI) {
                        bValor = Boolean.TRUE;
                    } else if (filtro.getMovReducidaSn() == ConstantesFiltro.HAB_MOV_REDUCIDA_NO) {
                        bValor = Boolean.FALSE;
                    }
                    crit.add(Restrictions.eq(ConstantesDAO.HABITACION_MOVREDUCIDASN, bValor));
                }
                if (filtro.getCamaSuplSn() != 0) {
                    if (filtro.getCamaSuplSn() == ConstantesFiltro.HAB_CAMA_SUPL_SI) {
                        bValor = Boolean.TRUE;
                    } else if (filtro.getCamaSuplSn() == ConstantesFiltro.HAB_CAMA_SUPL_NO) {
                        bValor = Boolean.FALSE;
                    }
                    crit.add(Restrictions.eq(ConstantesDAO.HABITACION_CAMASUPSN, bValor));
                }
                if (filtro.getActivoSn() != 0) {
                    if (filtro.getActivoSn() == ConstantesFiltro.ACTIVO_SI) {
                        bValor = Boolean.TRUE;
                    } else if (filtro.getActivoSn() == ConstantesFiltro.ACTIVO_NO) {
                        bValor = Boolean.FALSE;
                    }
                    crit.add(Restrictions.eq(ConstantesDAO.HABITACION_ACTIVOSN, bValor));
                }
                if (filtro.getTipoHabitacion() != null) {
                    crit.add(Restrictions.eq(ConstantesDAO.HABITACION_ID_TIPOHABITACION, filtro.getTipoHabitacion()));
                }
                    
                crit.addOrder(Order.asc(ordenarPor));

                lista = crit.list();
            }
        } catch (Exception e) {
            throw e;
        }
    
        return lista;
    }
    
            
    /**
     * Busca las habitaciones libres según las condiciones del filtro.
     * @param sesion
     * @param filtro
     * @return lista de habitaciones libres
     */
    public List<Habitacion> listarLibres(Session sesion, BusquedaHabitacionFiltro filtro) {
        int tipoHab = 0, exteriorHab = 0, fumadorHab = 0, movReducidaHab  = 0, camaSuplHab = 0;
        Boolean lbExteriorSn = null, lbFumadorSn = null, lbReducidaSn = null, lbCamaSuplSn = null;
        String codigoHab    = null;
        List<Habitacion> lista = null;

        try {
            Date fechaEntrada = filtro.getReserva().getFechaEntrada();
            Date fechaSalida  = filtro.getReserva().getFechaSalida();

            if (filtro.getHabitacion().getTipoHabitacion() != null) {
                tipoHab = filtro.getHabitacion().getTipoHabitacion();
            }
            if (filtro.getHabitacion().getCodigo() != null) {
                codigoHab = filtro.getHabitacion().getCodigo();
            }
            if (filtro.getHabitacion().getExteriorSn() != 0) {
                exteriorHab = filtro.getHabitacion().getExteriorSn();
            }
            if (filtro.getHabitacion().getFumadorSn() != 0) {
                fumadorHab  = filtro.getHabitacion().getFumadorSn();
            }
            if (filtro.getHabitacion().getMovReducidaSn() != 0) {
                movReducidaHab  = filtro.getHabitacion().getMovReducidaSn();
            }
            if (filtro.getHabitacion().getCamaSuplSn() != 0) {
                camaSuplHab  = filtro.getHabitacion().getCamaSuplSn();
            }
            
            String queryString = "SELECT hab " +
                    "FROM Habitacion hab JOIN hab.tipoHabitacion tipo " +
                    "WHERE hab.idHabitacion NOT IN " +
                    "(SELECT det.habitacion.idHabitacion FROM ReservaCab as cab " +
                    "INNER JOIN cab.detalles as det " +
                    "WHERE (:fechaEntrada BETWEEN cab.fechaEntrada AND cab.fechaSalida) OR " +
                    "(:fechaSalida BETWEEN cab.fechaEntrada AND cab.fechaSalida) OR " +
                    "((:fechaEntrada < cab.fechaEntrada) AND (:fechaSalida > cab.fechaEntrada)) OR " +
                    "((:fechaSalida < cab.fechaSalida) AND (:fechaEntrada < cab.fechaSalida))" +
                    "AND cab.estadoReserva.idEstadoReserva IN (1,2)) " +
                    "AND hab.activoSn = 1";

            if (tipoHab > 0) {
                queryString = queryString + " AND hab.tipoHabitacion.idTipoHabitacion = :tipoHab";
            }
            if (codigoHab != null) {
                queryString = queryString + " AND hab.codigo LIKE :codigoHab";
            }
            if (exteriorHab > 0) {
                lbExteriorSn = (exteriorHab == 1) ? Boolean.TRUE : Boolean.FALSE;
                queryString = queryString + " AND hab.exteriorSn = :lbExteriorSn";
            }
            if (fumadorHab > 0) {
                lbFumadorSn = (fumadorHab == 1) ? Boolean.TRUE : Boolean.FALSE;
                queryString = queryString + " AND hab.fumadorSn = :lbFumadorSn";
            }
            if (movReducidaHab > 0) {
                lbReducidaSn = (movReducidaHab == 1) ? Boolean.TRUE : Boolean.FALSE;
                queryString = queryString + " AND hab.movReducidaSn = :lbReducidaSn";
            }
            if (camaSuplHab > 0) {
                lbCamaSuplSn = (camaSuplHab == 1) ? Boolean.TRUE : Boolean.FALSE;
                queryString = queryString + " AND hab.camaSuplSn = :lbCamaSuplSn";
            }

            queryString = queryString + " ORDER BY hab.codigo";
            Query query = sesion.createQuery(queryString);
            
            query.setParameter("fechaEntrada", fechaEntrada); 
            query.setParameter("fechaSalida", fechaSalida); 
            if (tipoHab > 0) {
                query.setParameter("tipoHab", tipoHab); 
            }
            if (codigoHab != null) {
                query.setParameter("codigoHab", codigoHab); 
            }
            if (exteriorHab > 0) {
                query.setParameter("lbExteriorSn", lbExteriorSn); 
            }
            if (fumadorHab > 0) {
                query.setParameter("lbFumadorSn", lbFumadorSn); 
            }
            if (movReducidaHab > 0) {
                query.setParameter("lbReducidaSn", lbReducidaSn); 
            }
            if (camaSuplHab > 0) {
                query.setParameter("lbCamaSuplSn", lbCamaSuplSn); 
            }
            
            lista = query.list();
        } catch (Exception e) {
            throw e;
        }
    
        return lista;
    }
}
