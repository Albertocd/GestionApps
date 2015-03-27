
package gestionclases.business.vo;

import gestionclases.persistence.entity.Horario;
import gestionclases.persistence.entity.TipoClase;

/**
 *
 * @author alberto
 */
public class ClaseFiltro {
    
    private Horario     horario;
    private TipoClase   tipoClase;

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public TipoClase getTipoClase() {
        return tipoClase;
    }

    public void setTipoClase(TipoClase tipoClase) {
        this.tipoClase = tipoClase;
    }
    
    
}
