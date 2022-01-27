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
public class Deducciones{
    private Double totalOtras, totalRetenido;
    private List<Deduccion> deducciones;

    public Double getTotalOtras() {
        return totalOtras;
    }

    public void setTotalOtras(Double totalOtras) {
        this.totalOtras = totalOtras;
    }

    public Double getTotalRetenido() {
        return totalRetenido;
    }

    public void setTotalRetenido(Double totalRetenido) {
        this.totalRetenido = totalRetenido;
    }

    public List<Deduccion> getDeducciones() {
        return deducciones;
    }

    public void setDeducciones(List<Deduccion> deducciones) {
        this.deducciones = deducciones;
    }
    
    public Deduccion getClase(){
        return new Deduccion();
    }
    
    public class Deduccion{
        private String tipoDeduccion, clave, concepto;
        private Double importe;

        public String getTipoDeduccion() {
            return tipoDeduccion;
        }

        public void setTipoDeduccion(String tipoDeduccion) {
            this.tipoDeduccion = tipoDeduccion;
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

        public Double getImporte() {
            return importe;
        }

        public void setImporte(Double importe) {
            this.importe = importe;
        }
    }
}
