/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package complementos.nominas;

import java.util.List;

/**
 *
 * @author Abe
 */
public class Percepciones{
    private Double totalGravado, totalExento, totalSueldos;
    private List<Percepcion> percepciones;

    public Double getTotalSueldos() {
        return totalSueldos;
    }

    public void setTotalSueldos(Double totalSueldos) {
        this.totalSueldos = totalSueldos;
    }

    public Double getTotalGravado() {
        return totalGravado;
    }

    public void setTotalGravado(Double totalGravado) {
        this.totalGravado = totalGravado;
    }

    public Double getTotalExento() {
        return totalExento;
    }

    public void setTotalExento(Double totalExento) {
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
        private Double importeGravado, importeExento;

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

        public Double getImporteGravado() {
            return importeGravado;
        }

        public void setImporteGravado(Double importeGravado) {
            this.importeGravado = importeGravado;
        }

        public Double getImporteExento() {
            return importeExento;
        }

        public void setImporteExento(Double importeExento) {
            this.importeExento = importeExento;
        }
    }
}
