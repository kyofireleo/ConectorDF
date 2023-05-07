/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package complementos.nominas;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Abe
 */
public class Percepciones{
    private BigDecimal totalGravado, totalExento, totalSueldos;
    private List<Percepcion> percepciones;

    public BigDecimal getTotalSueldos() {
        return totalSueldos;
    }

    public void setTotalSueldos(BigDecimal totalSueldos) {
        this.totalSueldos = totalSueldos;
    }

    public BigDecimal getTotalGravado() {
        return totalGravado;
    }

    public void setTotalGravado(BigDecimal totalGravado) {
        this.totalGravado = totalGravado;
    }

    public BigDecimal getTotalExento() {
        return totalExento;
    }

    public void setTotalExento(BigDecimal totalExento) {
        this.totalExento = totalExento;
    }

    public List<Percepcion> getPercepciones() {
        return percepciones;
    }

    public void setPercepciones(List<Percepcion> percepciones) {
        this.percepciones = percepciones;
    }
    
    public Percepcion getClase(){
        return new Percepcion();
    }
    
    public class Percepcion{
        private String tipoPercepcion, clave, concepto;
        private BigDecimal importeGravado, importeExento;

        public String getTipoPercepcion() {
            return tipoPercepcion;
        }

        public void setTipoPercepcion(String tipoPercepcion) {
            this.tipoPercepcion = tipoPercepcion;
        }

        public String getClave() {
            return clave;
        }

        public void setClave(String clave) {
            this.clave = clave;
        }

        public String getConcepto() {
            return concepto;
        }

        public void setConcepto(String concepto) {
            this.concepto = concepto;
        }

        public BigDecimal getImporteGravado() {
            return importeGravado;
        }

        public void setImporteGravado(BigDecimal importeGravado) {
            this.importeGravado = importeGravado;
        }

        public BigDecimal getImporteExento() {
            return importeExento;
        }

        public void setImporteExento(BigDecimal importeExento) {
            this.importeExento = importeExento;
        }
    }
}
