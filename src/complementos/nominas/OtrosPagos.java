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
public class OtrosPagos{
    private Double totalOtrosPagos;
    private List<OtroPago> deducciones;

    public Double getTotalOtrosPagos() {
        return totalOtrosPagos;
    }

    public void setTotalOtrosPagos(Double totalOtrosPagos) {
        this.totalOtrosPagos = totalOtrosPagos;
    }

    public List<OtroPago> getOtrosPagos() {
        return deducciones;
    }

    public void setOtrosPagos(List<OtroPago> deducciones) {
        this.deducciones = deducciones;
    }
    
    public OtroPago getClase(){
        return new OtroPago();
    }
    
    public class OtroPago{
        private String tipoOtroPago, clave, concepto;
        private Double importe;

        public String getTipoOtroPago() {
            return tipoOtroPago;
        }

        public void setTipoOtroPago(String tipoOtroPago) {
            this.tipoOtroPago = tipoOtroPago;
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
