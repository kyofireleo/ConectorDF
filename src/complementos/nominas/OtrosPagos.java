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
public class OtrosPagos{
    private BigDecimal totalOtrosPagos;
    private List<OtroPago> otrosPagos;

    public BigDecimal getTotalOtrosPagos() {
        return totalOtrosPagos;
    }

    public void setTotalOtrosPagos(BigDecimal totalOtrosPagos) {
        this.totalOtrosPagos = totalOtrosPagos;
    }

    public List<OtroPago> getOtrosPagos() {
        return otrosPagos;
    }

    public void setOtrosPagos(List<OtroPago> otrosPagos) {
        this.otrosPagos = otrosPagos;
    }
    
    public OtroPago getClase(){
        return new OtroPago();
    }
    
    public class OtroPago{
        private String tipoOtroPago, clave, concepto;
        private BigDecimal importe;

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

        public BigDecimal getImporte() {
            return importe;
        }

        public void setImporte(BigDecimal importe) {
            this.importe = importe;
        }
    }
}
