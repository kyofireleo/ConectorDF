/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package complementos.nominas;

/**
 *
 * @author Abe
 */
public class Incapacidad{
    private Integer diasIncapacidad;
    private String tipoIncapacidad;
    private Double descuento;
    
    public Integer getDiasIncapacidad() {
        return diasIncapacidad;
    }

    public void setDiasIncapacidad(Integer diasIncapacidad) {
        this.diasIncapacidad = diasIncapacidad;
    }

    public String getTipoIncapacidad() {
        return tipoIncapacidad;
    }

    public void setTipoIncapacidad(String tipoIncapacidad) {
        this.tipoIncapacidad = tipoIncapacidad;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }
}
