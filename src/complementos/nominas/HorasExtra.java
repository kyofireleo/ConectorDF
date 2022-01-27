/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package complementos.nominas;

/**
 *
 * @author Abe
 */
public class HorasExtra{
    private Integer dias, horasExtra;
    private String tipoHoras;
    private Double importePagado;

    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }

    public String getTipoHoras() {
        return tipoHoras;
    }

    public void setTipoHoras(String tipoHoras) {
        this.tipoHoras = tipoHoras;
    }

    public Integer getHorasExtra() {
        return horasExtra;
    }

    public void setHorasExtra(Integer horasExtra) {
        this.horasExtra = horasExtra;
    }

    public Double getImportePagado() {
        return importePagado;
    }

    public void setImportePagado(Double importePagado) {
        this.importePagado = importePagado;
    }
}
