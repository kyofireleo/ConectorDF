/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package complementos.nominas;

import java.math.BigDecimal;

/**
 *
 * @author Abe
 */
public class Nominas {
    private String fechaPago, fechaInicialPago, fechaFinalPago;
    private String registroPatronal;
    
    private Integer numDiasPagados;
    private Integer antiguedad;
    
    private OtrosPagos otrosPagos;
    private Deducciones deducciones;
    private Percepciones percepciones;
    private HorasExtra horasExtra;
    private Incapacidad incapacidad;
    private Empleado empleado;
    
    private String tipoNomina;
    
    private BigDecimal totalDeducciones;
    private BigDecimal totalPercepciones;
    private BigDecimal totalOtrosPagos;

    public BigDecimal getTotalOtrosPagos() {
        return totalOtrosPagos;
    }

    public void setTotalOtrosPagos(BigDecimal totalOtrosPagos) {
        this.totalOtrosPagos = totalOtrosPagos;
    }

    public OtrosPagos getOtrosPagos() {
        return otrosPagos;
    }

    public void setOtrosPagos(OtrosPagos otrosPagos) {
        this.otrosPagos = otrosPagos;
    }

    public BigDecimal getTotalDeducciones() {
        return totalDeducciones;
    }

    public void setTotalDeducciones(BigDecimal totalDeducciones) {
        this.totalDeducciones = totalDeducciones;
    }

    public BigDecimal getTotalPercepciones() {
        return totalPercepciones;
    }

    public void setTotalPercepciones(BigDecimal totalPercepciones) {
        this.totalPercepciones = totalPercepciones;
    }

    public String getTipoNomina() {
        if(tipoNomina == null){
            tipoNomina = "O";
        }
        return tipoNomina;
    }

    public void setTipoNomina(String tipoNomina) {
        this.tipoNomina = tipoNomina;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public String getRegistroPatronal() {
        return registroPatronal;
    }

    public void setRegistroPatronal(String registroPatronal) {
        this.registroPatronal = registroPatronal;
    }
    
    public Deducciones getDeducciones() {
        return deducciones;
    }

    public void setDeducciones(Deducciones deducciones) {
        this.deducciones = deducciones;
    }

    public Percepciones getPercepciones() {
        return percepciones;
    }

    public void setPercepciones(Percepciones percepciones) {
        this.percepciones = percepciones;
    }

    public HorasExtra getHorasExtra() {
        return horasExtra;
    }

    public void setHorasExtra(HorasExtra horasExtra) {
        this.horasExtra = horasExtra;
    }

    public Incapacidad getIncapacidad() {
        return incapacidad;
    }

    public void setIncapacidad(Incapacidad incapacidad) {
        this.incapacidad = incapacidad;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getFechaInicialPago() {
        return fechaInicialPago;
    }

    public void setFechaInicialPago(String fechaInicialPago) {
        this.fechaInicialPago = fechaInicialPago;
    }

    public String getFechaFinalPago() {
        return fechaFinalPago;
    }

    public void setFechaFinalPago(String fechaFinalPago) {
        this.fechaFinalPago = fechaFinalPago;
    }

    public Integer getNumDiasPagados() {
        return numDiasPagados;
    }

    public void setNumDiasPagados(Integer numDiasPagados) {
        this.numDiasPagados = numDiasPagados;
    }

    public Integer getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(Integer antiguedad) {
        this.antiguedad = antiguedad;
    }
}


