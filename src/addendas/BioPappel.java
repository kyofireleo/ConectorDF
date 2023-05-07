
package addendas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author esque
 */
public class BioPappel {
    private String version = "1.0";
    private int idProveedor;
    private OrdenCompra ordenCompra;

    public String getVersion() {
        return version;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public OrdenCompra getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(OrdenCompra ordenCompra) {
        this.ordenCompra = ordenCompra;
    }
    
    public class OrdenCompra{
        private String numeroOC;
        private Recepciones recepciones;

        public String getNumeroOC() {
            return numeroOC;
        }

        public void setNumeroOC(String numeroOC) {
            this.numeroOC = numeroOC;
        }

        public Recepciones getRecepciones() {
            return recepciones;
        }

        public void setRecepciones(Recepciones recepciones) {
            this.recepciones = recepciones;
        }
        
        public class Recepciones{
            private List<Recepcion> recepcionList;
            
            public Recepciones(){
                recepcionList = new ArrayList();
            }
            
            public List<Recepcion> getRecepcionList(){
                return recepcionList;
            }
            
            public class Recepcion{
                private String noRemision;
                private String idRecepcion;
                private List<Concepto> conceptoList;
                
                public Recepcion(){
                    conceptoList = new ArrayList();
                }

                public List<Concepto> getConceptoList() {
                    return conceptoList;
                }

                public String getNoRemision() {
                    return noRemision;
                }

                public void setNoRemision(String noRemision) {
                    this.noRemision = noRemision;
                }

                public String getIdRecepcion() {
                    return idRecepcion;
                }

                public void setIdRecepcion(String idRecepcion) {
                    this.idRecepcion = idRecepcion;
                }
                
                public class Concepto{
                    private String noIdentificacion;
                    private String descripcion;
                    private String unidad;
                    private BigDecimal cantidad;
                    private BigDecimal valorUnitario;
                    private BigDecimal importe;
                    private int posOC;

                    public String getUnidad() {
                        return unidad;
                    }

                    public void setUnidad(String unidad) {
                        this.unidad = unidad;
                    }

                    public String getNoIdentificacion() {
                        return noIdentificacion;
                    }

                    public void setNoIdentificacion(String noIdentificacion) {
                        this.noIdentificacion = noIdentificacion;
                    }

                    public String getDescripcion() {
                        return descripcion;
                    }

                    public void setDescripcion(String descripcion) {
                        this.descripcion = descripcion;
                    }

                    public BigDecimal getCantidad() {
                        return cantidad;
                    }

                    public void setCantidad(BigDecimal cantidad) {
                        this.cantidad = cantidad;
                    }

                    public BigDecimal getValorUnitario() {
                        return valorUnitario;
                    }

                    public void setValorUnitario(BigDecimal valorUnitario) {
                        this.valorUnitario = valorUnitario;
                    }

                    public BigDecimal getImporte() {
                        return importe;
                    }

                    public void setImporte(BigDecimal importe) {
                        this.importe = importe;
                    }

                    public int getPosOC() {
                        return posOC;
                    }

                    public void setPosOC(int posOC) {
                        this.posOC = posOC;
                    }
                }
            }
        }
    }
}
