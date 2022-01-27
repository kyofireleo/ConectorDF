/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package complementos.nominas;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Abe
 */
public class Empleado {
    private String curp, nss, departamento, clabe, banco, puesto, tipoContrato, tipoJornada, periodicidadPago, riesgoPuesto, tipoRegimen;
    private String numEmpleado;
    private int idCliente;
    private Date fechaInicialRelLaboral;
    private BigDecimal salarioDiarioInt;
    private BigDecimal salarioBaseCotApor;

    public BigDecimal getSalarioDiarioInt() {
        return salarioDiarioInt;
    }

    public void setSalarioDiarioInt(BigDecimal salarioDiarioInt) {
        this.salarioDiarioInt = salarioDiarioInt;
    }

    public BigDecimal getSalarioBaseCotApor() {
        return salarioBaseCotApor;
    }

    public void setSalarioBaseCotApor(BigDecimal salarioBaseCotApor) {
        this.salarioBaseCotApor = salarioBaseCotApor;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getTipoRegimen() {
        return tipoRegimen;
    }

    public void setTipoRegimen(String tipoRegimen) {
        this.tipoRegimen = tipoRegimen;
    }

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getClabe() {
        return clabe;
    }

    public void setClabe(String clabe) {
        this.clabe = clabe;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public String getTipoJornada() {
        return tipoJornada;
    }

    public void setTipoJornada(String tipoJornada) {
        this.tipoJornada = tipoJornada;
    }

    public String getPeriodicidadPago() {
        return periodicidadPago;
    }

    public void setPeriodicidadPago(String periodicidadPago) {
        this.periodicidadPago = periodicidadPago;
    }

    public String getRiesgoPuesto() {
        return riesgoPuesto;
    }

    public void setRiesgoPuesto(String riesgoPuesto) {
        this.riesgoPuesto = riesgoPuesto;
    }

    public String getNumEmpleado() {
        return numEmpleado;
    }

    public void setNumEmpleado(String numEmpleado) {
        this.numEmpleado = numEmpleado;
    }

    public Date getFechaInicialRelLaboral() {
        return fechaInicialRelLaboral;
    }

    public void setFechaInicialRelLaboral(Date fechaInicialRelLaboral) {
        this.fechaInicialRelLaboral = fechaInicialRelLaboral;
    }
}
