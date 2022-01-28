/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conectordf;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import static conectordf.ConectorDF.log;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import mx.grupocorasa.sat.cfd._40.Comprobante;
import mx.grupocorasa.sat.cfd._40.Comprobante.CfdiRelacionados;
import mx.grupocorasa.sat.cfd._40.Comprobante.Complemento;
import mx.grupocorasa.sat.cfdi.v4.CFDv4;
import mx.grupocorasa.sat.cfdi.v4.CFDv40;
import mx.grupocorasa.sat.common.CartaPorte20.CartaPorte;
import mx.grupocorasa.sat.common.CartaPorte20.CartaPorte.FiguraTransporte;
import mx.grupocorasa.sat.common.CartaPorte20.CartaPorte.Mercancias;
import mx.grupocorasa.sat.common.CartaPorte20.CartaPorte.Ubicaciones;
import mx.grupocorasa.sat.common.NamespacePrefixMapperImpl;
import mx.grupocorasa.sat.common.Pagos10.Pagos;
import mx.grupocorasa.sat.common.catalogos.CClaveUnidad;
import mx.grupocorasa.sat.common.catalogos.CEstado;
import mx.grupocorasa.sat.common.catalogos.CFormaPago;
import mx.grupocorasa.sat.common.catalogos.CImpuesto;
import mx.grupocorasa.sat.common.catalogos.CMetodoPago;
import mx.grupocorasa.sat.common.catalogos.CMoneda;
import mx.grupocorasa.sat.common.catalogos.CPais;
import mx.grupocorasa.sat.common.catalogos.CRegimenFiscal;
import mx.grupocorasa.sat.common.catalogos.CTipoDeComprobante;
import mx.grupocorasa.sat.common.catalogos.CTipoFactor;
import mx.grupocorasa.sat.common.catalogos.CTipoRelacion;
import mx.grupocorasa.sat.common.catalogos.CUsoCFDI;
import mx.grupocorasa.sat.common.catalogos.CartaPorte.CClaveTipoCarga;
import mx.grupocorasa.sat.common.catalogos.CartaPorte.CClaveUnidadPeso;
import mx.grupocorasa.sat.common.catalogos.CartaPorte.CCodigoTransporteAereo;
import mx.grupocorasa.sat.common.catalogos.CartaPorte.CConfigAutotransporte;
import mx.grupocorasa.sat.common.catalogos.CartaPorte.CConfigMaritima;
import mx.grupocorasa.sat.common.catalogos.CartaPorte.CContenedor;
import mx.grupocorasa.sat.common.catalogos.CartaPorte.CContenedorMaritimo;
import mx.grupocorasa.sat.common.catalogos.CartaPorte.CCveTransporte;
import mx.grupocorasa.sat.common.catalogos.CartaPorte.CDerechosDePaso;
import mx.grupocorasa.sat.common.catalogos.CartaPorte.CFiguraTransporte;
import mx.grupocorasa.sat.common.catalogos.CartaPorte.CNumAutorizacionNaviero;
import mx.grupocorasa.sat.common.catalogos.CartaPorte.CParteTransporte;
import mx.grupocorasa.sat.common.catalogos.CartaPorte.CSubTipoRem;
import mx.grupocorasa.sat.common.catalogos.CartaPorte.CTipoCarro;
import mx.grupocorasa.sat.common.catalogos.CartaPorte.CTipoDeServicio;
import mx.grupocorasa.sat.common.catalogos.CartaPorte.CTipoDeTrafico;
import mx.grupocorasa.sat.common.catalogos.CartaPorte.CTipoEmbalaje;
import mx.grupocorasa.sat.common.catalogos.CartaPorte.CTipoEstacion;
import mx.grupocorasa.sat.common.catalogos.CartaPorte.CTipoPermiso;
import mx.grupocorasa.sat.common.catalogos.Nomina.CBanco;
import mx.grupocorasa.sat.common.catalogos.Nomina.CPeriodicidadPago;
import mx.grupocorasa.sat.common.catalogos.Nomina.CRiesgoPuesto;
import mx.grupocorasa.sat.common.catalogos.Nomina.CTipoContrato;
import mx.grupocorasa.sat.common.catalogos.Nomina.CTipoDeduccion;
import mx.grupocorasa.sat.common.catalogos.Nomina.CTipoHoras;
import mx.grupocorasa.sat.common.catalogos.Nomina.CTipoIncapacidad;
import mx.grupocorasa.sat.common.catalogos.Nomina.CTipoJornada;
import mx.grupocorasa.sat.common.catalogos.Nomina.CTipoNomina;
import mx.grupocorasa.sat.common.catalogos.Nomina.CTipoOtroPago;
import mx.grupocorasa.sat.common.catalogos.Nomina.CTipoPercepcion;
import mx.grupocorasa.sat.common.catalogos.Nomina.CTipoRegimen;
import mx.grupocorasa.sat.common.donat11.Donatarias;
import mx.grupocorasa.sat.common.implocal10.ImpuestosLocales;
import mx.grupocorasa.sat.common.nomina12.Nomina;
import mx.grupocorasa.sat.common.nomina12.Nomina.Percepciones.Percepcion.HorasExtra;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class ConstruirXML {
    
    List<String> layout;
    String noCertificado;
    utils.Utils util = new utils.Utils(log);
    
    private boolean estado;
    private Long transId;
    private String uuid, rfcEmisor, fechaExp, fechaTim, nombreEmisor, folio, serie;
    private String nombreReceptor, rfcReceptor, tipoComprobante, metodoPago, numEmpleado;
    private int posiConceptos, posfConceptos, posiTraslados, posfTraslados, posiRetenidos;
    private int posiOtrosPagos, posfOtrosPagos, posfRetenidos, posiPercepciones, posfPercepciones;
    private int posiDeducciones, posfDeducciones, posiConTraslados, posfConTraslados;
    private int posiConRetenciones, posfConRetenciones, posiLocalTraslados, posfLocalTraslados;
    private int posiLocalRetenciones, posfLocalRetenciones;
    private int posiPagos, posfPagos, posiDocPagos, posfDocPagos;
    /*Carta Porte [B]*/
    private int posiCartaPorte, posfCartaPorte;
    /*Carta Porte [E]*/
    private String xmlTimbrado, pathPro, nameXml, nameXmlTimbrado, xmlNoTimbrado, layoutCadena;
    private BigDecimal total;
    private String leyenda, tipoComprobanteLayout, xslt;
    private String registroPatronal;
    private String calleRe, noExteriorRe, noInteriorRe, coloniaRe, localidadRe, municipioRe, estadoRe, paisRe, cpRe;
    private File layoutFile;
    private boolean conUUID;
    public String jsonDomicilios;
    private JsonObject json;
    private String sello;
    
    private CTipoDeComprobante cTipoComp;
    
    public String getNameXmlTimbrado() {
        return nameXmlTimbrado;
    }
    
    public boolean getConUUID() {
        return !get("OCULTAR_UUID:").equals("1");
    }
    
    public void setNameXmlTimbrado(String nameXmlTimbrado) {
        this.nameXmlTimbrado = nameXmlTimbrado;
    }
    
    public String getUuid() {
        return uuid;
    }
    
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
    public String getXmlTimbrado() {
        return xmlTimbrado;
    }
    
    public void setXmlTimbrado(String xmlTimbrado) {
        this.xmlTimbrado = xmlTimbrado;
    }
    
    public String getCalleRe() {
        return calleRe;
    }
    
    public String getNoExteriorRe() {
        return noExteriorRe;
    }
    
    public String getNoInteriorRe() {
        return noInteriorRe;
    }
    
    public String getColoniaRe() {
        return coloniaRe;
    }
    
    public String getLocalidadRe() {
        return localidadRe;
    }
    
    public String getMunicipioRe() {
        return municipioRe;
    }
    
    public String getEstadoRe() {
        return estadoRe;
    }
    
    public String getPaisRe() {
        return paisRe;
    }
    
    public String getCpRe() {
        return cpRe;
    }
    
    public String getRegistroPatronal() {
        return registroPatronal;
    }
    
    public String getMetodoPago() {
        return metodoPago;
    }
    
    public String getTipoComprobante() {
        return tipoComprobante;
    }
    
    public String getTipoComprobanteLayout() {
        return tipoComprobanteLayout;
    }
    
    public String getLeyenda() {
        return leyenda;
    }
    
    public String getNombreReceptor() {
        return nombreReceptor;
    }
    
    public String getRfcReceptor() {
        return rfcReceptor;
    }
    
    public ConstruirXML(String estructuraNombre, List<String> layout, String noCertificado) {
        this.layout = layout;
        this.noCertificado = noCertificado;
        layoutCadena = toString(layout);
        rfcEmisor = get("RFC1:");
        rfcReceptor = get("RFC2:");
        serie = get("SERIE:");
        folio = get("FOLIO:");
        nameXml = serie + "_" + folio + "_" + rfcEmisor + "_" + rfcReceptor;
        json = new JsonObject();
    }
    
    public ConstruirXML(String estructuraNombre, List<String> layout) {
        this.layout = layout;
        layoutCadena = toString(layout);
        rfcEmisor = get("RFC1:");
        rfcReceptor = get("RFC2:");
        serie = get("SERIE:");
        folio = get("FOLIO:");
        nameXml = serie + "_" + folio + "_" + rfcEmisor + "_" + rfcReceptor;
        registroPatronal = get("REGISTROPATRONAL:");
        numEmpleado = "";
        json = new JsonObject();
    }
    
    public ConstruirXML(String estructuraNombre, List<String> layout, File lay) throws Exception {
        this.layout = layout;
        layoutCadena = toString(layout);
        rfcEmisor = get("RFC1:");
        rfcReceptor = get("RFC2:");
        serie = get("SERIE:");
        String fo = get("FOLIO:");
        if (fo.isEmpty()) {
            throw new Exception("El layout no cuenta folio");
        }
        folio = fo;
        nameXml = FilenameUtils.removeExtension(lay.getName());
        layoutFile = lay;
        registroPatronal = get("REGISTROPATRONAL:");
        numEmpleado = "";
        json = new JsonObject();
    }
    
    public ConstruirXML(String xml) {
        this.xmlNoTimbrado = xml;
        
    }
    
    private String toString(List<String> lista) {
        StringBuilder sb = new StringBuilder();
        for (String x : lista) {
            sb.append(x + "\r\n");
        }
        return sb.toString();
    }
    
    public void setFileLayout(File layoutFile) {
        this.layoutFile = layoutFile;
    }
    
    public File getFileLayout() {
        return layoutFile;
    }
    
    private BigDecimal redondear(double num) {
        return new BigDecimal(num).setScale(2, RoundingMode.HALF_UP);
    }
    
    private BigDecimal redondear(BigDecimal num) {
        return num.setScale(2, RoundingMode.HALF_UP);
    }
    
    public Nomina getNomina() throws ParseException, DatatypeConfigurationException {
        Nomina nomi = new Nomina();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.time.LocalDate x;
        GregorianCalendar cal = new GregorianCalendar();
        nomi.setOtrosPagos(getOtrosPagos());
        nomi.setDeducciones(getDeducciones());
        nomi.setPercepciones(getPercepciones());
        nomi.setIncapacidades(getIncapacidades());
        double per = new Double(get("TOTAL_PER:"));
        double ded = new Double(get("TOTAL_DEC:"));
        double otp = new Double(get("TOTAL_OTP:"));
        if (per > 0) {
            nomi.setTotalPercepciones(util.redondear(new BigDecimal(per)));
        }
        if (ded > 0) {
            nomi.setTotalDeducciones(util.redondear(new BigDecimal(ded)));
        }
        if (otp > 0 || (nomi.getOtrosPagos() != null && nomi.getOtrosPagos().getOtroPago().size() > 0)) {
            nomi.setTotalOtrosPagos(util.redondear(new BigDecimal(otp)));
        }
        
        nomi.setEmisor(getEmisorNomina());
        nomi.setReceptor(getEmpleado());
        
        String tipo = get("TIPO_NOMINA:");
        switch (tipo) {
            case "O":
                nomi.setTipoNomina(CTipoNomina.O);
                break;
            case "E":
                nomi.setTipoNomina(CTipoNomina.E);
                break;
            default:
                nomi.setTipoNomina(CTipoNomina.O);
                break;
        }
        
        cal.setTimeInMillis(format.parse(get("FECHA_FINAL_PAGO:")).getTime());
        x = java.time.LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
        nomi.setFechaFinalPago(x);
        
        cal.setTimeInMillis(format.parse(get("FECHA_INICIAL_PAGO:")).getTime());
        x = java.time.LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
        nomi.setFechaInicialPago(x);
        
        cal.setTimeInMillis(format.parse(get("FECHA_PAGO:")).getTime());
        x = java.time.LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
        nomi.setFechaPago(x);
        
        nomi.setNumDiasPagados(new BigDecimal(get("NUM_DIAS_PAGADOS:")));
        nomi.setVersion("1.2");
        
        return nomi;
    }
    
    private Nomina.Receptor getEmpleado() throws ParseException, DatatypeConfigurationException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.time.LocalDate x;
        GregorianCalendar cal = new GregorianCalendar();
        Nomina.Receptor empleado = new Nomina.Receptor();
        Nomina.Receptor.SubContratacion sub = new Nomina.Receptor.SubContratacion();

        /*
        sub.setRfcLabora("");
        sub.setPorcentajeTiempo(BigDecimal.ZERO);
        
        empleado.getSubContratacion().add(sub);
         */
        empleado.setNumEmpleado(get("NUMEMPLEADO:"));
        
        if (!get("BANCO:").isEmpty() /*&& get("CLABE:").isEmpty()*/) {
            empleado.setBanco(CBanco.fromValue(get("BANCO:")));
        }
        if (!get("CLABE:").isEmpty()) {
            empleado.setCuentaBancaria(get("CLABE:"));
        }
        
        empleado.setCurp(get("CURP:"));
        
        if (!get("DEPARTAMENTO:").isEmpty()) {
            empleado.setDepartamento(get("DEPARTAMENTO:"));
        }
        
        empleado.setClaveEntFed(CEstado.SIN); //Hay que configurar esto

        String frl = get("FECHA_INICIAL_REL_LABORAL:");
        if (!frl.isEmpty()) {
            cal.setTimeInMillis(format.parse(frl).getTime());
            x = java.time.LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
            empleado.setFechaInicioRelLaboral(x);
        }
        
        if (!get("ANTIGUEDAD:").isEmpty()) {
            empleado.setAntigüedad(get("ANTIGUEDAD:"));
        }
        if (!get("NSS:").isEmpty()) {
            empleado.setNumSeguridadSocial(get("NSS:"));
        }
        
        empleado.setPeriodicidadPago(CPeriodicidadPago.fromValue(get("PERIODICIDAD_PAGO:")));
        
        if (!get("PUESTO:").isEmpty()) {
            empleado.setPuesto(get("PUESTO:"));
        }
        if (!get("RIESGO_PUESTO:").isEmpty()) {
            empleado.setRiesgoPuesto(CRiesgoPuesto.fromValue(get("RIESGO_PUESTO:")));
        }
        
        String sb = get("SUELDO_BASE:");
        String sd = get("SALARIO_DIARIO_INT:");
        
        if (!sb.isEmpty()) {
            empleado.setSalarioBaseCotApor(redondear(new BigDecimal(sb)));
        }
        if (!sd.isEmpty()) {
            empleado.setSalarioDiarioIntegrado(redondear(new BigDecimal(sd)));
        }
        
        if (!get("TIPO_CONTRATO:").isEmpty()) {
            empleado.setTipoContrato(CTipoContrato.fromValue(get("TIPO_CONTRATO:")));
        }
        if (!get("TIPO_JORNADA:").isEmpty()) {
            empleado.setTipoJornada(CTipoJornada.fromValue(get("TIPO_JORNADA:")));
        }
        
        empleado.setTipoRegimen(CTipoRegimen.fromValue(get("TIPO_REGIMEN:")));
        
        return empleado;
    }
    
    private Nomina.Emisor getEmisorNomina() {
        Nomina.Emisor emiNomi = new Nomina.Emisor();
        int tipocon = new Integer(get("TIPO_CONTRATO:"));
        if (tipocon <= 8) {
            emiNomi.setRegistroPatronal(get("REGISTROPATRONAL:"));
        }
        if (!get("CURPE:").isEmpty()) {
            emiNomi.setCurp(get("CURPE:"));
        }
        return emiNomi;
    }
    
    private Nomina.OtrosPagos getOtrosPagos() {
        Nomina.OtrosPagos ops = null;
        Nomina.OtrosPagos.OtroPago o;
        
        if (posiOtrosPagos < posfOtrosPagos) {
            ops = new Nomina.OtrosPagos();
            for (int i = posiOtrosPagos; i < posfOtrosPagos; i++) {
                o = new Nomina.OtrosPagos.OtroPago();
                String x = layout.get(i);
                int pos = x.indexOf(":");
                //Tipo@Clave@Concepto@Importe
                String oo[] = x.substring(pos + 1).split("@");
                
                o.setTipoOtroPago(CTipoOtroPago.fromValue(oo[0].trim()));
                o.setClave(oo[1].trim());
                o.setConcepto(oo[2].trim());
                o.setImporte(util.redondear(new BigDecimal(oo[3].trim())));
                ops.getOtroPago().add(o);
                
                if (o.getTipoOtroPago().equals(CTipoOtroPago.VALUE_2)) {
                    o.setSubsidioAlEmpleo(getSubsidioAlEmpleo(o.getImporte()));
                }
            }
        }
        return ops;
    }
    
    private Nomina.OtrosPagos.OtroPago.SubsidioAlEmpleo getSubsidioAlEmpleo(BigDecimal importe) {
        Nomina.OtrosPagos.OtroPago.SubsidioAlEmpleo sem = new Nomina.OtrosPagos.OtroPago.SubsidioAlEmpleo();
        sem.setSubsidioCausado(importe);
        return sem;
    }
    
    private Nomina.Percepciones getPercepciones() {
        Nomina.Percepciones per = null;
        Nomina.Percepciones.Percepcion p;
        //Seteamos Percepciones
        if (posiPercepciones < posfPercepciones) {
            double gra = 0.0;
            double exe = 0.0;
            per = new Nomina.Percepciones();
            for (int i = posiPercepciones; i < posfPercepciones; i++) {
                p = new Nomina.Percepciones.Percepcion();
                String x = layout.get(i);
                int pos = x.indexOf(":");
                //Tipo@Clave@Concepto@Gravado@Exento
                String pp[] = x.substring(pos + 1).split("@");
                gra += new Double(pp[3]);
                exe += new Double(pp[4]);
                
                p.setTipoPercepcion(CTipoPercepcion.fromValue(pp[0].trim()));
                p.setClave(pp[1].trim());
                p.setConcepto(pp[2].trim());
                p.setImporteGravado(util.redondear(new BigDecimal(pp[3].trim())));
                p.setImporteExento(util.redondear(new BigDecimal(pp[4].trim())));
                per.getPercepcion().add(p);
            }
            
            per.setTotalExento(util.redondear(new BigDecimal(exe)));
            per.setTotalGravado(util.redondear(new BigDecimal(gra)));
            per.setTotalSueldos(util.redondear(new BigDecimal(exe + gra)));
            
        }
        return per;
    }
    
    private Nomina.Deducciones getDeducciones() {
        Nomina.Deducciones dec = null;
        Nomina.Deducciones.Deduccion d;
        //Seteamos Deducciones
        if (posiDeducciones < posfDeducciones) {
            double ret = 0.0;
            double exe = 0.0;
            dec = new Nomina.Deducciones();
            for (int i = posiDeducciones; i < posfDeducciones; i++) {
                d = new Nomina.Deducciones.Deduccion();
                String x = layout.get(i);
                int pos = x.indexOf(":");
                //Tipo@Clave@Concepto@Gravado@Exento
                String pp[] = x.substring(pos + 1).split("@");
                if (pp[3].trim().equals("0.0") || pp[3].trim().equals("0")) {
                    continue;
                }
                if (pp[0].trim().equals("002")) {
                    ret += new Double(pp[3]);
                } else {
                    exe += new Double(pp[3]);
                }
                
                d.setTipoDeduccion(CTipoDeduccion.fromValue(pp[0].trim()));
                d.setClave(pp[1].trim());
                d.setConcepto(pp[2].trim());
                d.setImporte(util.redondear(new BigDecimal(pp[3].trim())));
                dec.getDeduccion().add(d);
            }
            if (exe > 0) {
                dec.setTotalOtrasDeducciones(util.redondear(new BigDecimal(exe)));
            }
            if (ret > 0) {
                dec.setTotalImpuestosRetenidos(util.redondear(new BigDecimal(ret)));
            }
        }
        return dec;
    }
    
    private Nomina.Incapacidades getIncapacidades() {
        Nomina.Incapacidades incas = null;
        Nomina.Incapacidades.Incapacidad inc;

        //Seteamos incapacidad
        if (layout.contains("[INCAPACIDAD]") && !get("DIAS_INCAPACIDAD:").isEmpty()) {
            incas = new Nomina.Incapacidades();
            inc = new Nomina.Incapacidades.Incapacidad();
            inc.setTipoIncapacidad(CTipoIncapacidad.fromValue(get("TIPO_INCAPACIDAD:")));
            inc.setDiasIncapacidad(new Integer(get("DIAS_INCAPACIDAD:")));
            inc.setImporteMonetario(util.redondear(new BigDecimal(get("DESCUENTO_INCAPACIDAD:"))));
            incas.getIncapacidad().add(inc);
        }
        return incas;
    }
    
    private HorasExtra getHorasExtras() {
        HorasExtra horas = null;
        //Seteamos horas extra
        if (layout.contains("[HORAS_EXTRA]")) {
            horas = new HorasExtra();
            horas.setDias(new Integer(get("DIAS:")));
            horas.setHorasExtra(new Integer(get("NUM_HORAS_EXTRA:")));
            horas.setTipoHoras(CTipoHoras.fromValue(get("TIPO_HORAS:")));
            horas.setImportePagado(util.redondear(new BigDecimal(get("IMPORTE_PAGADO:"))));
        }
        return horas;
    }

    /**
     * *************CARTA PORTE*****************
     */
    private CartaPorte getCartaPorte() throws Exception {
        mx.grupocorasa.sat.common.CartaPorte20.ObjectFactory ob = new mx.grupocorasa.sat.common.CartaPorte20.ObjectFactory();
        CartaPorte cp = ob.createCartaPorte();
        boolean isInternational = false;
        
        if (posiCartaPorte < posfCartaPorte) {
            StringBuilder text = new StringBuilder();
            JsonObject jsonCarta;
            
            for (int i = posiCartaPorte; i < posfCartaPorte; i++) {
                text.append(layout.get(i));
            }
            
            jsonCarta = JsonParser.parseString(text.toString()).getAsJsonObject();
            
            cp.setVersion("2.0");
            cp.setTotalDistRec(jsonCarta.get("TotalDistRec").getAsBigDecimal());
            cp.setTranspInternac(getAsString(jsonCarta.get("TranspInternac")));
            
            if (cp.getTranspInternac().equals("Sí")) {
                cp.setPaisOrigenDestino(CPais.fromValue(jsonCarta.get("PaisOrigenDestino").getAsString()));
                cp.setEntradaSalidaMerc(getAsString(jsonCarta.get("EntradaSalidaMerc")));
                cp.setViaEntradaSalida(CCveTransporte.fromValue(jsonCarta.get("ViaEntradaSalida").getAsString()));
                isInternational = true;
            }
            
            cp.setFiguraTransporte(getFiguraTransporte(ob, jsonCarta.get("FiguraTransporte"))); //implementar metodo
            cp.setMercancias(getMercancias(ob, jsonCarta.get("Mercancias"), isInternational));
            cp.setUbicaciones(getUbicaciones(ob, jsonCarta.get("Ubicaciones")));
        }
        
        return cp;
    }
    
    private Ubicaciones getUbicaciones(mx.grupocorasa.sat.common.CartaPorte20.ObjectFactory ob, JsonElement jsonUbi) throws Exception {
        Ubicaciones ubi = null;
        Ubicaciones.Ubicacion u;
        
        JsonArray arrUbi;
        JsonObject ubica;
        
        DateTimeFormatter dtf;
        
        if (!jsonUbi.isJsonNull() && jsonUbi.isJsonArray() && jsonUbi.getAsJsonArray().size() > 0) {
            arrUbi = jsonUbi.getAsJsonArray();
            ubi = ob.createCartaPorteUbicaciones();
            dtf = DateTimeFormatter.ofPattern("yyyy-MM-ddTHH:mm:ss");
            
            for (int i = 0; i < arrUbi.size(); i++) {
                ubica = arrUbi.get(i).getAsJsonObject();
                u = ob.createCartaPorteUbicacionesUbicacion();
                
                u.setTipoUbicacion(getAsString(ubica.get("TipoUbicacion")));
                u.setIDUbicacion(getAsString(ubica.get("IDUbicacion")));
                u.setRFCRemitenteDestinatario(getAsString(ubica.get("RFCRemitenteDestinatario")));
                u.setNombreRemitenteDestinatario(getAsString(ubica.get("NombreRemitenteDestinatario")));
                u.setFechaHoraSalidaLlegada(LocalDateTime.parse(ubica.get("FechaHoraSalidaLlegada").getAsString(), dtf));
                if (u.getTipoUbicacion().equals("Destino")) {
                    u.setDistanciaRecorrida(ubica.get("DistanciaRecorrida").getAsBigDecimal());
                }
                
                if (ubica.has("TipoEstacion")) {
                    u.setTipoEstacion(CTipoEstacion.fromValue(ubica.get("TipoEstacion").getAsString()));
                }
                if (ubica.has("NumEstacion")) {
                    u.setNumEstacion(getAsString(ubica.get("NumEstacion")));
                }
                if (ubica.has("NombreEstacion")) {
                    u.setNombreEstacion(getAsString(ubica.get("NombreEstacion")));
                }
                if (ubica.has("NavegacionTrafico")) {
                    u.setNavegacionTrafico(getAsString(ubica.get("NavegacionTrafico")));
                }
                
                if (u.getRFCRemitenteDestinatario() != null && u.getRFCRemitenteDestinatario().equalsIgnoreCase("XEXX010101000")) {
                    if (ubica.has("NumRegIdTrib")) {
                        u.setNumRegIdTrib(getAsString(ubica.get("NumRegIdTrib")));
                        u.setResidenciaFiscal(CPais.fromValue(ubica.get("ResidenciaFiscal").getAsString()));
                    }
                }
                
                JsonObject ubiDom = ubica.has("Domicilio") ? ubica.get("Domicilio").getAsJsonObject() : null;
                if (ubiDom != null) {
                    Ubicaciones.Ubicacion.Domicilio d = ob.createCartaPorteUbicacionesUbicacionDomicilio();
                    
                    if (ubiDom.has("Calle")) {
                        d.setCalle(getAsString(ubiDom.get("Calle")));
                    }
                    if (ubiDom.has("NumeroExterior")) {
                        d.setNumeroExterior(getAsString(ubiDom.get("NumeroExterior")));
                    }
                    if (ubiDom.has("NumeroInterior")) {
                        d.setNumeroInterior(getAsString(ubiDom.get("NumeroInterior")));
                    }
                    if (ubiDom.has("Colonia")) {
                        d.setColonia(getAsString(ubiDom.get("Colonia")));
                    }
                    if (ubiDom.has("Localidad")) {
                        d.setLocalidad(getAsString(ubiDom.get("Localidad")));
                    }
                    if (ubiDom.has("Municipio")) {
                        d.setMunicipio(getAsString(ubiDom.get("Municipio")));
                    }
                    if (ubiDom.has("Referencia")) {
                        d.setReferencia(getAsString(ubiDom.get("Referencia")));
                    }
                    
                    d.setEstado(getAsString(ubiDom.get("Estado")));
                    d.setPais(CPais.fromValue(ubiDom.get("Pais").getAsString()));
                    d.setCodigoPostal(getAsString(ubiDom.get("CodigoPostal")));
                    
                    u.setDomicilio(d);
                }
                
                ubi.getUbicacion().add(u);
            }
        }
        
        return ubi;
    }
    
    private Mercancias getMercancias(mx.grupocorasa.sat.common.CartaPorte20.ObjectFactory ob, JsonElement jsonMer, boolean isInternational) {
        Mercancias mr = null;
        
        JsonObject merca;
        JsonArray arrMer;
        
        boolean isMaterialPeligroso = false;
        
        if (!jsonMer.isJsonNull() && jsonMer.isJsonObject()) {
            mr = ob.createCartaPorteMercancias();
            merca = jsonMer.getAsJsonObject();
            
            mr.setPesoBrutoTotal(merca.get("PesoBrutoTotal").getAsBigDecimal());
            mr.setUnidadPeso(CClaveUnidadPeso.fromValue(merca.get("UnidadPeso").getAsString()));
            mr.setNumTotalMercancias(merca.get("NumTotalMercancias").getAsInt());
            
            if (merca.has("PesoNetoTotal")) {
                mr.setPesoNetoTotal(merca.get("PesoNetoTotal").getAsBigDecimal());
            }
            if (merca.has("CargoPorTasacion")) {
                mr.setCargoPorTasacion(merca.get("CargoPorTasacion").getAsBigDecimal());
            }
            
            arrMer = merca.has("Mercancia") && merca.get("Mercancia").isJsonArray() ? merca.get("Mercancia").getAsJsonArray() : null;
            if (arrMer != null && arrMer.size() > 0) {
                Mercancias.Mercancia m;
                JsonObject mer;
                
                for (int i = 0; i < arrMer.size(); i++) {
                    m = ob.createCartaPorteMercanciasMercancia();
                    mer = arrMer.get(i).getAsJsonObject();
                    
                    m.setBienesTransp(getAsString(mer.get("BienesTransp")));
                    m.setDescripcion(getAsString(mer.get("Descripcion")));
                    m.setCantidad(mer.get("Cantidad").getAsBigDecimal());
                    m.setClaveUnidad(CClaveUnidad.fromValue(mer.get("ClaveUnidad").getAsString()));
                    m.setPesoEnKg(mer.get("PesoEnKg").getAsBigDecimal());
                    
                    if (mer.has("Unidad")) {
                        m.setUnidad(getAsString(mer.get("Unidad")));
                    }
                    if (mer.has("Dimensiones")) {
                        m.setDimensiones(getAsString(mer.get("Dimensiones")));
                    }
                    if (mer.has("MaterialPeligroso")) {
                        m.setMaterialPeligroso(getAsString(mer.get("MaterialPeligroso")));
                        if (m.getMaterialPeligroso().equals("Sí")) {
                            m.setCveMaterialPeligroso(getAsString(mer.get("CveMaterialPeligroso")));
                            if (mer.has("Embalaje") && !mer.get("Embalaje").isJsonNull()) {
                                m.setEmbalaje(CTipoEmbalaje.fromValue(mer.get("Embalaje").getAsString()));
                                m.setDescripEmbalaje(getAsString(mer.get("DescripEmbalaje")));
                            }
                            isMaterialPeligroso = true;
                        }
                    }
                    
                    if (mer.has("ValorMercancia")) {
                        m.setValorMercancia(mer.get("ValorMercancia").getAsBigDecimal());
                        m.setMoneda(CMoneda.fromValue(mer.get("Moneda").getAsString()));
                    }
                    
                    if (mer.has("ClaveSTCC")) {
                        m.setClaveSTCC(getAsString(mer.get("ClaveSTCC")));
                    }
                    
                    if (isInternational) {
                        m.setFraccionArancelaria(getAsString(mer.get("FraccionArancelaria")));
                        m.setUUIDComercioExt(getAsString(mer.get("UUIDComercioExt")));
                        
                        if (mer.has("Pedimentos") && mer.get("Pedimentos").isJsonArray() && mer.get("Pedimentos").getAsJsonArray().size() > 0) {
                            JsonArray arrPed = mer.get("Pedimentos").getAsJsonArray();
                            Mercancias.Mercancia.Pedimentos p;
                            
                            for (int j = 0; j < arrPed.size(); j++) {
                                p = ob.createCartaPorteMercanciasMercanciaPedimentos();
                                
                                p.setPedimento(getAsString(arrPed.get(j)));
                                
                                if (p.getPedimento() != null) {
                                    m.getPedimentos().add(p);
                                }
                            }
                        }
                    }
                    
                    if (mer.has("Detalle")) {
                        JsonObject merDet = mer.get("Detalle").getAsJsonObject();
                        Mercancias.Mercancia.DetalleMercancia dm = ob.createCartaPorteMercanciasMercanciaDetalleMercancia();
                        
                        dm.setPesoBruto(merDet.get("PesoBruto").getAsBigDecimal());
                        dm.setPesoNeto(merDet.get("PesoNeto").getAsBigDecimal());
                        dm.setPesoTara(merDet.get("PesoTara").getAsBigDecimal());
                        dm.setUnidadPesoMerc(CClaveUnidadPeso.fromValue(merDet.get("UnidadPesoMerc").getAsString()));
                        
                        if (merDet.has("NumPiezas")) {
                            dm.setNumPiezas(merDet.get("NumPiezas").getAsInt());
                        }
                        
                        m.setDetalleMercancia(dm);
                    }
                    
                    if (mer.has("CantidadTransporta")) {
                        JsonObject merCT = mer.get("CantidadTransporta").getAsJsonObject();
                        Mercancias.Mercancia.CantidadTransporta ct = ob.createCartaPorteMercanciasMercanciaCantidadTransporta();
                        
                        ct.setCantidad(merCT.get("Cantidad").getAsBigDecimal());
                        ct.setIDDestino(getAsString(merCT.get("IDDestino")));
                        ct.setIDOrigen(getAsString(merCT.get("IDOrigen")));
                        if (merCT.has("CvesTransporte")) {
                            ct.setCvesTransporte(CCveTransporte.fromValue(merCT.get("CvesTransporte").getAsString()));
                        }
                        
                        m.getCantidadTransporta().add(ct);
                    }
                    
                    if (mer.has("GuiasIdentificacion")) {
                        JsonObject merGI = mer.get("GuiasIdentificacion").getAsJsonObject();
                        Mercancias.Mercancia.GuiasIdentificacion gi = ob.createCartaPorteMercanciasMercanciaGuiasIdentificacion();
                        gi.setDescripGuiaIdentificacion(getAsString(merGI.get("DescripGuiaIdentificacion")));
                        gi.setNumeroGuiaIdentificacion(getAsString(merGI.get("NumeroGuiaIdentificacion")));
                        gi.setPesoGuiaIdentificacion(merGI.get("PesoGuiaIdentificacion").getAsBigDecimal());
                        
                        m.getGuiasIdentificacion().add(gi);
                    }
                }
            }
            
            if (merca.has("Autotransporte")) {
                JsonElement auto = merca.get("Autotransporte");
                mr.setAutotransporte(getMercaAutotransporte(ob, auto, isMaterialPeligroso));
            } else if (merca.has("TransporteAereo")) {
                JsonElement aero = merca.get("TransporteAereo");
                mr.setTransporteAereo(getMercaTransporteAereo(ob, aero));
            } else if (merca.has("TransporteFerroviario")) {
                JsonElement ferro = merca.get("TransporteFerroviario");
                mr.setTransporteFerroviario(getMercaTransporteFerroviario(ob, ferro));
            } else if (merca.has("TransporteMaritimo")) {
                JsonElement barco = merca.get("TransporteMaritimo");
                mr.setTransporteMaritimo(getMercaTransporteMaritimo(ob, barco));
            }
        }
        return mr;
    }
    
    private Mercancias.Autotransporte getMercaAutotransporte(mx.grupocorasa.sat.common.CartaPorte20.ObjectFactory ob, JsonElement jsonAuto, boolean isMaterialPeligroso) {
        Mercancias.Autotransporte at = null;
        
        if (!jsonAuto.isJsonNull() && jsonAuto.isJsonObject()) {
            JsonObject auto = jsonAuto.getAsJsonObject();
            at = ob.createCartaPorteMercanciasAutotransporte();
            
            at.setPermSCT(CTipoPermiso.fromValue(auto.get("PermSCT").getAsString()));
            at.setNumPermisoSCT(getAsString(auto.get("NumPermisoSCT")));
            
            if (auto.has("IdentificacionVehicular")) {
                JsonObject id = auto.get("IdentificacionVehicular").getAsJsonObject();
                Mercancias.Autotransporte.IdentificacionVehicular ident = ob.createCartaPorteMercanciasAutotransporteIdentificacionVehicular();
                
                ident.setConfigVehicular(CConfigAutotransporte.fromValue(id.get("ConfigVehicular").getAsString()));
                ident.setPlacaVM(getAsString(id.get("PlacaVM")));
                ident.setAnioModeloVM(id.get("AnioModeloVM").getAsInt());
                
                at.setIdentificacionVehicular(ident);
            }
            
            if (auto.has("Seguros")) {
                JsonObject segu = auto.get("Seguros").getAsJsonObject();
                Mercancias.Autotransporte.Seguros seg = ob.createCartaPorteMercanciasAutotransporteSeguros();
                
                seg.setAseguraRespCivil(getAsString(segu.get("AseguraRespCivil")));
                seg.setPolizaRespCivil(getAsString(segu.get("PolizaRespCivil")));
                
                if (isMaterialPeligroso) {
                    seg.setAseguraMedAmbiente(getAsString(segu.get("AseguraMedAmbiente")));
                    seg.setPolizaMedAmbiente(getAsString(segu.get("PolizaMedAmbiente")));
                }
                
                if (segu.has("AseguraCarga")) {
                    seg.setAseguraCarga(getAsString(segu.get("AseguraCarga")));
                    seg.setPolizaCarga(getAsString(segu.get("PolizaCarga")));
                }
                
                seg.setPrimaSeguro(segu.get("PrimaSeguro").getAsBigDecimal());
                
                at.setSeguros(seg);
            }
            
            if (auto.has("Remolques") && auto.get("Remolques").isJsonArray() && auto.get("Remolques").getAsJsonArray().size() > 0) {
                JsonArray arrRem = auto.get("Remolques").getAsJsonArray();
                Mercancias.Autotransporte.Remolques remol = ob.createCartaPorteMercanciasAutotransporteRemolques();
                
                for (int i = 0; i < arrRem.size(); i++) {
                    JsonObject remo = arrRem.get(i).getAsJsonObject();
                    Mercancias.Autotransporte.Remolques.Remolque r = ob.createCartaPorteMercanciasAutotransporteRemolquesRemolque();
                    
                    r.setSubTipoRem(CSubTipoRem.fromValue(remo.get("SubTipoRem").getAsString()));
                    r.setPlaca(getAsString(remo.get("Placa")));
                    
                    remol.getRemolque().add(r);
                }
                
                at.setRemolques(remol);
            }
        }
        
        return at;
    }
    
    private Mercancias.TransporteAereo getMercaTransporteAereo(mx.grupocorasa.sat.common.CartaPorte20.ObjectFactory ob, JsonElement jsonAero) {
        Mercancias.TransporteAereo aereo = null;
        
        if (!jsonAero.isJsonNull() && jsonAero.isJsonObject()) {
            JsonObject aero = jsonAero.getAsJsonObject();
            aereo = ob.createCartaPorteMercanciasTransporteAereo();
            
            aereo.setCodigoTransportista(CCodigoTransporteAereo.fromValue(aero.get("CodigoTransportista").getAsString()));
            aereo.setLugarContrato(getAsString(aero.get("LugarContrato")));
            aereo.setNumeroGuia(getAsString(aero.get("NumeroGuia")));
            aereo.setMatriculaAeronave(getAsString(aero.get("MatriculaAeronave")));
            aereo.setNumPolizaSeguro(getAsString(aero.get("NumPolizaSeguro")));
            aereo.setNombreAseg(getAsString(aero.get("NombreAseg")));
            aereo.setPermSCT(CTipoPermiso.fromValue(aero.get("PermSCT").getAsString()));
            aereo.setNumPermisoSCT(getAsString(aero.get("NumPermisoSCT")));
            aereo.setRFCEmbarcador(getAsString(aero.get("RFCEmbarcador")));
            aereo.setNombreEmbarcador(getAsString(aero.get("NombreEmbarcador")));
            aereo.setNumRegIdTribEmbarc(getAsString(aero.get("NumRegIdTribEmbarc")));
            aereo.setResidenciaFiscalEmbarc(CPais.fromValue(aero.get("ResidenciaFiscalEmbarc").getAsString()));
        }
        
        return aereo;
    }
    
    private Mercancias.TransporteFerroviario getMercaTransporteFerroviario(mx.grupocorasa.sat.common.CartaPorte20.ObjectFactory ob, JsonElement jsonFerro) {
        Mercancias.TransporteFerroviario tren = null;
        
        if (!jsonFerro.isJsonNull() && jsonFerro.isJsonObject()) {
            JsonObject ferro = jsonFerro.getAsJsonObject();
            tren = ob.createCartaPorteMercanciasTransporteFerroviario();
            
            tren.setNombreAseg(folio);
            tren.setNumPolizaSeguro(folio);
            tren.setTipoDeServicio(CTipoDeServicio.TS_01);
            tren.setTipoDeTrafico(CTipoDeTrafico.TT_01);
            
            if (ferro.has("Carro") && ferro.get("Carro").isJsonArray() && ferro.get("Carro").getAsJsonArray().size() > 0) {
                JsonArray arrCarro = ferro.get("Carro").getAsJsonArray();
                Mercancias.TransporteFerroviario.Carro car;
                
                for (int i = 0; i < arrCarro.size(); i++) {
                    JsonObject carro = arrCarro.get(i).getAsJsonObject();
                    car = ob.createCartaPorteMercanciasTransporteFerroviarioCarro();
                    
                    car.setGuiaCarro(getAsString(carro.get("GuiaCarro")));
                    car.setMatriculaCarro(getAsString(carro.get("MatriculaCarro")));
                    car.setTipoCarro(CTipoCarro.fromValue(carro.get("TipoCarro").getAsString()));
                    car.setToneladasNetasCarro(carro.get("ToneladasNetasCarro").getAsBigDecimal());
                    
                    if (carro.has("Contenedor") && carro.get("Contenedor").isJsonArray() && carro.get("Contenedor").getAsJsonArray().size() > 0) {
                        JsonArray arrCon = carro.get("Contenedor").getAsJsonArray();
                        Mercancias.TransporteFerroviario.Carro.Contenedor conte;
                        
                        for (int j = 0; j < arrCon.size(); j++) {
                            JsonObject con = arrCon.get(j).getAsJsonObject();
                            conte = ob.createCartaPorteMercanciasTransporteFerroviarioCarroContenedor();
                            
                            conte.setTipoContenedor(CContenedor.fromValue(con.get("TipoContenedor").getAsString()));
                            conte.setPesoContenedorVacio(con.get("PesoContenedorVacio").getAsBigDecimal());
                            conte.setPesoNetoMercancia(con.get("PesoNetoMercancia").getAsBigDecimal());
                            
                            car.getContenedor().add(conte);
                        }
                    }
                    
                    tren.getCarro().add(car);
                }
            }
            
            if (ferro.has("DerechosDePaso") && ferro.get("DerechosDePaso").isJsonArray() && ferro.get("DerechosDePaso").getAsJsonArray().size() > 0) {
                JsonArray arrPaso = ferro.get("DerechosDePaso").getAsJsonArray();
                Mercancias.TransporteFerroviario.DerechosDePaso der;
                
                for (int i = 0; i < arrPaso.size(); i++) {
                    JsonObject paso = arrPaso.get(i).getAsJsonObject();
                    der = ob.createCartaPorteMercanciasTransporteFerroviarioDerechosDePaso();
                    
                    der.setTipoDerechoDePaso(CDerechosDePaso.fromValue(paso.get("TipoDerechoDePaso").getAsString()));
                    der.setKilometrajePagado(paso.get("KilometrajePagado").getAsBigDecimal());
                    
                    tren.getDerechosDePaso().add(der);
                }
            }
        }
        
        return tren;
    }
    
    private Mercancias.TransporteMaritimo getMercaTransporteMaritimo(mx.grupocorasa.sat.common.CartaPorte20.ObjectFactory ob, JsonElement jsonMari) {
        Mercancias.TransporteMaritimo barco = null;
        JsonObject mari;
        
        if (!jsonMari.isJsonNull() && jsonMari.isJsonObject()) {
            mari = jsonMari.getAsJsonObject();
            barco = ob.createCartaPorteMercanciasTransporteMaritimo();
            
            barco.setAnioEmbarcacion(mari.get("AnioEmbarcacion").getAsInt());
            barco.setCalado(mari.get("Calado").getAsBigDecimal());
            barco.setEslora(mari.get("Eslora").getAsBigDecimal());
            barco.setLineaNaviera(getAsString(mari.get("LineaNaviera")));
            barco.setManga(mari.get("Manga").getAsBigDecimal());
            barco.setMatricula(getAsString(mari.get("Matricula")));
            barco.setNacionalidadEmbarc(CPais.fromValue(mari.get("NacionalidadEmbarc").getAsString()));
            barco.setNombreAgenteNaviero(getAsString(mari.get("NombreAgenteNaviero")));
            barco.setNombreAseg(getAsString(mari.get("NombreAseg")));
            barco.setNombreEmbarc(getAsString(mari.get("NombreEmbarc")));
            barco.setNumAutorizacionNaviero(CNumAutorizacionNaviero.fromValue(mari.get("NumAutorizacionNaviero").getAsString()));
            barco.setNumCertITC(getAsString(mari.get("NumCertITC")));
            barco.setNumConocEmbarc(getAsString(mari.get("NumConocEmbarc")));
            barco.setNumPermisoSCT(getAsString(mari.get("NumPermisoSCT")));
            barco.setNumPolizaSeguro(getAsString(mari.get("NumPolizaSeguro")));
            barco.setNumViaje(getAsString(mari.get("NumViaje")));
            barco.setNumeroOMI(getAsString(mari.get("NumeroOMI")));
            barco.setPermSCT(CTipoPermiso.fromValue(mari.get("PermSCT").getAsString()));
            barco.setTipoCarga(CClaveTipoCarga.fromValue(mari.get("TipoCarga").getAsString()));
            barco.setTipoEmbarcacion(CConfigMaritima.fromValue(mari.get("TipoEmbarcacion").getAsString()));
            barco.setUnidadesDeArqBruto(mari.get("UnidadesDeArqBruto").getAsBigDecimal());
            
            if (mari.has("Contenedor") && mari.get("Contenedor").isJsonArray() && mari.get("Contenedor").getAsJsonArray().size() > 0) {
                JsonArray arrCon = mari.get("Contenedor").getAsJsonArray();
                Mercancias.TransporteMaritimo.Contenedor conte;
                
                for (int i = 0; i < arrCon.size(); i++) {
                    JsonObject con = arrCon.get(i).getAsJsonObject();
                    conte = ob.createCartaPorteMercanciasTransporteMaritimoContenedor();
                    
                    conte.setTipoContenedor(CContenedorMaritimo.fromValue(con.get("TipoContenedor").getAsString()));
                    conte.setMatriculaContenedor(getAsString(con.get("MatriculaContenedor")));
                    conte.setNumPrecinto(getAsString(con.get("NumPrecinto")));
                    
                    barco.getContenedor().add(conte);
                }
            }
        }
        
        return barco;
    }
    
    private FiguraTransporte getFiguraTransporte(mx.grupocorasa.sat.common.CartaPorte20.ObjectFactory ob, JsonElement jsonFt) throws Exception {
        FiguraTransporte ft = null;
        
        FiguraTransporte.TiposFigura tif;
        String nombreFigura,
                numLicencia,
                numRegIdFigura,
                rfcFigura,
                resiFiscalFigura,
                tipoFigura;
        
        FiguraTransporte.TiposFigura.Domicilio dom;
        JsonArray arrFt;
        
        if (!jsonFt.isJsonNull() && jsonFt.isJsonArray() && jsonFt.getAsJsonArray().size() > 0) {
            ft = ob.createCartaPorteFiguraTransporte();
            arrFt = jsonFt.getAsJsonArray();
            JsonObject fig;
            
            for (int i = 0; i < arrFt.size(); i++) {
                tif = ob.createCartaPorteFiguraTransporteTiposFigura();
                fig = arrFt.get(i).getAsJsonObject();
                
                tipoFigura = fig.get("TipoFigura").getAsString();
                nombreFigura = getAsString(fig.get("NombreFigura"));
                numLicencia = getAsString(fig.get("NumLicencia"));
                rfcFigura = getAsString(fig.get("RFCFigura"));
                numRegIdFigura = fig.has("NumRegIdFigura") ? getAsString(fig.get("NumRegIdFigura")) : null;
                resiFiscalFigura = fig.has("ResidenciaFiscalFigura") ? getAsString(fig.get("ResidenciaFiscalFigura")) : null;
                
                tif.setTipoFigura(CFiguraTransporte.fromValue(tipoFigura));
                tif.setRFCFigura(rfcFigura);
                tif.setNumLicencia(numLicencia);
                tif.setNombreFigura(nombreFigura);
                tif.setNumRegIdTribFigura(numRegIdFigura);
                
                if (numRegIdFigura != null) {
                    tif.setResidenciaFiscalFigura(CPais.fromValue(resiFiscalFigura));
                }
                
                if (tipoFigura.equals("02") || tipoFigura.equals("03")) {
                    if (fig.has("PartesTransporte") && fig.get("PartesTransporte").isJsonArray() && fig.get("PartesTransporte").getAsJsonArray().size() > 0) {
                        JsonArray arrPt = fig.get("PartesTransporte").getAsJsonArray();
                        FiguraTransporte.TiposFigura.PartesTransporte pt;
                        
                        for (int j = 0; j < arrPt.size(); j++) {
                            pt = ob.createCartaPorteFiguraTransporteTiposFiguraPartesTransporte();
                            pt.setParteTransporte(CParteTransporte.fromValue(arrPt.get(j).getAsString()));
                            
                            tif.getPartesTransporte().add(pt);
                        }
                    }
                }
                
                if (fig.has("Domicilios")) {
                    JsonObject domi = fig.get("Domicilios").getAsJsonObject();
                    dom = ob.createCartaPorteFiguraTransporteTiposFiguraDomicilio();
                    
                    if (domi.has("Calle")) {
                        dom.setCalle(getAsString(domi.get("Calle")));
                    }
                    if (domi.has("NumeroExterior")) {
                        dom.setNumeroExterior(getAsString(domi.get("NumeroExterior")));
                    }
                    if (domi.has("NumeroInterior")) {
                        dom.setNumeroInterior(getAsString(domi.get("NumeroInterior")));
                    }
                    if (domi.has("Colonia")) {
                        dom.setColonia(getAsString(domi.get("Colonia")));
                    }
                    if (domi.has("Localidad")) {
                        dom.setLocalidad(getAsString(domi.get("Localidad")));
                    }
                    if (domi.has("Referencia")) {
                        dom.setReferencia(getAsString(domi.get("Referencia")));
                    }
                    if (domi.has("Municipio")) {
                        dom.setMunicipio(getAsString(domi.get("Municipio")));
                    }
                    
                    dom.setEstado(getAsString(domi.get("Estado")));
                    dom.setPais(CPais.fromValue(domi.get("Pais").getAsString()));
                    dom.setCodigoPostal(getAsString(domi.get("CodigoPostal")));
                    
                    tif.setDomicilio(dom);
                }
                
                ft.getTiposFigura().add(tif);
            }
        }
        
        return ft;
    }
    
    private String getAsString(JsonElement je) {
        return je.isJsonNull() ? null : je.getAsString().trim();
    }

    /**
     * *************FIN CARTA PORTE***************
     */
    private String getINE() {
        StringBuilder sb = new StringBuilder();
        sb.append("<ine:INE xmlns:ine=\"http://www.sat.gob.mx/ine\" Version=\"1.0\" ");
        sb.append("TipoProceso=\"").append(get("TIPO_PROCESO:")).append("\" >");
        sb.append("<ine:Entidad ClaveEntidad=\"").append(get("CLAVE_ENTIDAD:")).append("\" Ambito=\"").append(get("AMBITO:")).append("\">");
        sb.append("<ine:Contabilidad IdContabilidad=\"").append(get("ID_CONTA:")).append("\"/>");
        sb.append("</ine:Entidad>");
        sb.append("</ine:INE>");
        return sb.toString();
    }
    
    private ImpuestosLocales getImpuestosLocales() {
        if (!get("TOTALTRASLADOSLOCALES:").isEmpty()) {
            mx.grupocorasa.sat.common.implocal10.ObjectFactory of = new mx.grupocorasa.sat.common.implocal10.ObjectFactory();
            ImpuestosLocales il = of.createImpuestosLocales();
            il.setVersion("1.0");
            il.setTotaldeTraslados(util.redondear(new BigDecimal(get("TOTALTRASLADOSLOCALES:"))));
            il.setTotaldeRetenciones(util.redondear(new BigDecimal(get("TOTALRETENCIONESLOCALES:"))));
            
            if (posiLocalTraslados < posfLocalTraslados) {
                for (int i = posiLocalTraslados; i < posfLocalTraslados; i++) {
                    ImpuestosLocales.TrasladosLocales ilt = of.createImpuestosLocalesTrasladosLocales();
                    String t[] = layout.get(i).split(":")[1].trim().split("@");
                    ilt.setImpLocTrasladado(t[0]);
                    ilt.setTasadeTraslado(util.redondear(new BigDecimal(t[1])));
                    ilt.setImporte(util.redondear(new BigDecimal(t[2])));
                    
                    il.getRetencionesLocalesAndTrasladosLocales().add(ilt);
                }
            }
            
            if (posiLocalRetenciones < posfLocalRetenciones) {
                for (int i = posiLocalRetenciones; i < posfLocalRetenciones; i++) {
                    ImpuestosLocales.RetencionesLocales ilr = of.createImpuestosLocalesRetencionesLocales();
                    String r[] = layout.get(i).split(":")[1].trim().split("@");
                    ilr.setImpLocRetenido(r[0]);
                    ilr.setTasadeRetencion(util.redondear(new BigDecimal(r[1])));
                    ilr.setImporte(util.redondear(new BigDecimal(r[2])));
                    
                    il.getRetencionesLocalesAndTrasladosLocales().add(ilr);
                }
            }
            
            return il;
        } else {
            return null;
        }
    }
    
    private Pagos getPagos() {
        if (posiPagos > 0) {
            mx.grupocorasa.sat.common.Pagos10.ObjectFactory obj = new mx.grupocorasa.sat.common.Pagos10.ObjectFactory();
            Pagos pagos = obj.createPagos();
            pagos.setVersion("1.0");
            int cont = 0;
            for (int i = posiPagos; i < posfPagos; i++) {
                cont++;
                try {
                    String[] rowPay = layout.get(i).split(":")[1].trim().split("@");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    
                    Date fechaAuto = sdf.parse(rowPay[8]);
                    GregorianCalendar c = new GregorianCalendar();
                    c.setTime(fechaAuto);
                    java.time.LocalDateTime xc = java.time.LocalDateTime.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND));
                    
                    Pagos.Pago p = obj.createPagosPago();
                    //p.setCadPago(folio);
                    //p.setCertPago(null);
                    p.setCtaBeneficiario(rowPay[0].equals(".") ? null : rowPay[0]);
                    p.setCtaOrdenante(rowPay[2].equals(".") ? null : rowPay[2]);
                    p.setFechaPago(xc);
                    p.setFormaDePagoP(CFormaPago.fromValue(rowPay[7]));
                    p.setMonedaP(CMoneda.fromValue(rowPay[5]));
                    p.setMonto(redondear(new BigDecimal(rowPay[4])));
                    //p.setNomBancoOrdExt(folio);
                    //p.setNumOperacion(folio);
                    p.setRfcEmisorCtaBen(rowPay[0].equals(".") ? null : rowPay[1]);
                    p.setRfcEmisorCtaOrd(rowPay[2].equals(".") ? null : rowPay[3]);
                    //p.setSelloPago(null);
                    //p.setTipoCadPago(folio);
                    p.setTipoCambioP(rowPay[6].equals(".") ? null : redondear(new BigDecimal(rowPay[6])));
                    
                    for (int h = posiDocPagos; h < posfDocPagos; h++) {
                        Pagos.Pago.DoctoRelacionado dr = obj.createPagosPagoDoctoRelacionado();
                        String[] rowDoc = layout.get(h).split(":")[1].trim().split("@");
                        if (rowDoc[0].equalsIgnoreCase("P" + cont)) {
                            dr.setIdDocumento(rowDoc[10]);
                            dr.setFolio(rowDoc[1]);
                            dr.setSerie(rowDoc[2]);
                            dr.setImpPagado(redondear(new BigDecimal(rowDoc[4])));
                            dr.setImpSaldoAnt(redondear(new BigDecimal(rowDoc[5])));
                            dr.setImpSaldoInsoluto(redondear(new BigDecimal(rowDoc[3])));
                            dr.setMetodoDePagoDR(CMetodoPago.fromValue(rowDoc[7]));
                            dr.setMonedaDR(CMoneda.fromValue(rowDoc[8]));
                            dr.setNumParcialidad(new BigInteger(rowDoc[6]));
                            dr.setTipoCambioDR(rowDoc[9].equals(".") ? null : redondear(new BigDecimal(rowDoc[9])));
                            
                            p.getDoctoRelacionado().add(dr);
                        }
                    }
                    
                    pagos.getPago().add(p);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                    log.error("Error al obtener los pagos: ", ex);
                }
            }
            return pagos;
        } else {
            return null;
        }
    }

    /*public transport getCartaPorte(){
        return null;
    }*/
    public void crearXml() throws Exception {
        this.generarXml(ConectorDF.unidad + ":\\Facturas\\C_Interpretados\\");
    }
    
    public void crearXml(String destino) throws Exception {
        this.generarXml(destino);
    }
    
    private void generarXml(String pathInt) throws Exception {
        //Obtenemos posiciones de tipo de datos
        posiConceptos = layout.indexOf("[CONCEPTOS]") + 1;
        posfConceptos = layout.indexOf("[/CONCEPTOS]");
        posiTraslados = layout.indexOf("[IMPUESTOS_TRASLADADOS]") + 1;
        posfTraslados = layout.indexOf("[/IMPUESTOS_TRASLADADOS]");
        posiRetenidos = layout.indexOf("[IMPUESTOS_RETENIDOS]") + 1;
        posfRetenidos = layout.indexOf("[/IMPUESTOS_RETENIDOS]");
        posiOtrosPagos = layout.indexOf("[OTROS_PAGOS]") + 1;
        posfOtrosPagos = layout.indexOf("[/OTROS_PAGOS]");
        posiPercepciones = layout.indexOf("[PERCEPCIONES]") + 1;
        posfPercepciones = layout.indexOf("[/PERCEPCIONES]");
        posiDeducciones = layout.indexOf("[DEDUCCIONES]") + 1;
        posfDeducciones = layout.indexOf("[/DEDUCCIONES]");
        
        posiConTraslados = layout.indexOf("[TRASLADADOS_CONCEPTOS]") + 1;
        posfConTraslados = layout.indexOf("[/TRASLADADOS_CONCEPTOS]");
        
        posiConRetenciones = layout.indexOf("[RETENCIONES_CONCEPTOS]") + 1;
        posfConRetenciones = layout.indexOf("[/RETENCIONES_CONCEPTOS]");
        
        posiLocalTraslados = layout.indexOf("[TRASLADOS_LOCALES]") + 1;
        posfLocalTraslados = layout.indexOf("[/TRASLADOS_LOCALES]");
        
        posiLocalRetenciones = layout.indexOf("[TRASLADOS_LOCALES]") + 1;
        posfLocalRetenciones = layout.indexOf("[/TRASLADOS_LOCALES]");
        
        posiPagos = layout.indexOf("[PAGOS]") + 1;
        posfPagos = layout.indexOf("[/PAGOS]");
        
        posiDocPagos = layout.indexOf("[DOCTOS_PAGOS]") + 1;
        posfDocPagos = layout.indexOf("[/DOCTOS_PAGOS]");

        /**
         * ********CARTA PORTE [B]************
         */
        posiCartaPorte = layout.indexOf("[CARTA_PORTE]") + 1;
        posfCartaPorte = layout.indexOf("[/CARTA_PORTE]");
        /**
         * ********CARTA PORTE [E]************
         */

        //Objetos para crear CFDv33
        mx.grupocorasa.sat.cfd._40.ObjectFactory of = new mx.grupocorasa.sat.cfd._40.ObjectFactory();
        Comprobante comp = crearComprobante(of);

        //Creamos un CFDv33
        Thread.currentThread().setContextClassLoader(new ClassLoader() {
        });
        CFDv4 cfd = null;
        
        tipoComprobante = get("TIPO_COMPROBANTE:");
        
        try {
            String folder;
            String context;
            
            if (!ConectorDF.unidad.contains(":")) {
                folder = ":\\Facturas\\config\\";
            } else {
                folder = "\\Facturas\\config\\";
            }
            
            switch (tipoComprobante) {
                case "N":
                    context = "mx.grupocorasa.sat.common.nomina12";
                    xslt = ConectorDF.unidad + folder + "nomina12.xslt";
                    break;
                case "F":
                    context = "mx.grupocorasa.sat.common.ine10";
                    xslt = ConectorDF.unidad + folder + "ine10.xslt";
                    break;
                case "T":
                    context = "mx.grupocorasa.sat.common.CartaPorte20";
                    xslt = ConectorDF.unidad + folder + "CartaPorte20.xslt";
                    break;
                case "P":
                    context = "mx.grupocorasa.sat.common.Pagos20";
                    xslt = ConectorDF.unidad + folder + "Pagos20.xslt";
                    break;
                default:
                    context = "mx.grupocorasa.sat.cfd._40";
                    xslt = ConectorDF.unidad + folder + "cadenaoriginal_4_0.xslt";
                    break;
            }
            
            cfd = new CFDv40(comp, context);

            /**
             * ****Se crea el string del json para domicilios*****
             */
            jsonDomicilios = json.toString();
            /**
             * ***************************************************
             */
            
            FileInputStream archivoKey = new FileInputStream(ConectorDF.unidad + folder + getRfcEmisor() + ".key");
            FileInputStream archivoCer = new FileInputStream(ConectorDF.unidad + folder + getRfcEmisor() + ".cer");
            String contra = util.leerXml(ConectorDF.unidad + folder + getRfcEmisor() + "_pass.txt").trim();
            PrivateKey key = KeyLoader.loadPKCS8PrivateKey(archivoKey, contra);
            X509Certificate cert = KeyLoader.loadX509Certificate(archivoCer);
            
            try {
                cfd.sellar(key, cert);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
                log.error("Error al sellar el comprobante: ", e);
                util.printError("Error al sellar el comprobante: " + e.getMessage());
                Runtime.getRuntime().gc();
                return;
            }
            
            ErrorHandler eh = new ErrorHandler() {
                
                @Override
                public void warning(SAXParseException exception) throws SAXException {
                    print("Warning: " + exception.getMessage());
                }
                
                @Override
                public void error(SAXParseException exception) throws SAXException {
                    print("Error: " + exception.getMessage());
                }
                
                @Override
                public void fatalError(SAXParseException exception) throws SAXException {
                    print("Fatal Error: " + exception.getMessage());
                }
            };
            
            this.sello = cfd.getSelloString();
            try {
                FileOutputStream fos = new FileOutputStream(pathInt + nameXml + ".xml");
                cfd.guardar(fos, true);
                fos.close();
            } catch (IOException | JAXBException e) {
                e.printStackTrace();
                log.error("Error al guardar el comprobante sellado: ", e);
            }
            Runtime.getRuntime().gc();
            
            xmlNoTimbrado = (util.leerXml(pathInt + nameXml + ".xml"));
        } catch (Exception ex) {
            System.out.println("Excepcion al crear el comprobante: \n");
            ex.printStackTrace();
            ConectorDF.log.error("Excepcion al crear el comprobante: " + ex.getMessage(), ex);
        }

//                cfd.validar(eh);
        //SERIE_FOLIO_RFCEMISOR_RFCRECEPTOR
        /*try {
            if (tipoComprobante.equalsIgnoreCase("D")) {
                String xml = util.leerXml(pathInt + nameXml + ".xml");
                String xsiValue = "http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd  http://www.sat.gob.mx/donat  http://www.sat.gob.mx/sitio_internet/cfd/donat/donat11.xsd";

                xml = xml.replace("xmlns:ns3", "xmlns:donat");
                xml = xml.replace("ns3:Donatarias", "donat:Donatarias");
                xml = xml.replace("http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd", xsiValue);

                util.escribirArchivo(xml, pathInt, nameXml + ".xml");
            } else if (tipoComprobante.equalsIgnoreCase("N")) {
                String xml = util.leerXml(pathInt + nameXml + ".xml");
                String xsiValue = "http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd  http://www.sat.gob.mx/nomina12  http://www.sat.gob.mx/sitio_internet/cfd/nomina/nomina12.xsd";
                xml = xml.replace("ns3:", "nomina12:");
                xml = xml.replace("xmlns:ns3", "xmlns:nomina12");
                xml = xml.replace("http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd", xsiValue);
                util.escribirArchivo(xml, pathInt, nameXml + ".xml");
            } else if (tipoComprobante.equalsIgnoreCase("F")) {
                String xml = util.leerXml(pathInt + nameXml + ".xml").replace("</cfdi:Comprobante>", "<cfdi:Complemento>" + getINE() + "</cfdi:Complemento></cfdi:Comprobante>");
                String xsiValue = "xsi:schemaLocation=\"http://www.sat.gob.mx/cfd/3  http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv32.xsd  http://www.sat.gob.mx/ine http://www.sat.gob.mx/sitio_internet/cfd/ine/ine10.xsd\"";
                String xmlns = "xmlns:ine=\"http://www.sat.gob.mx/ine\"";
                xml = xml.replace("xsi:schemaLocation=\"http://www.sat.gob.mx/cfd/3  http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv32.xsd\"", xsiValue + " " + xmlns);
                xml = xml.replace("sello=\"\"", "sello=\"" + comp.getSello() + "\"");
                xml = xml.replace("certificado=\"\"", "certificado=\"" + comp.getCertificado() + "\"");
                util.escribirArchivo(xml, pathInt, nameXml + ".xml");
            } else if(tipoComprobante.equalsIgnoreCase("I") && getImpuestosLocales() != null){
                String xml = util.leerXml(pathInt + nameXml + ".xml");
                String xsiValue = "http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd  http://www.sat.gob.mx/implocal http://www.sat.gob.mx/sitio_internet/cfd/implocal/implocal.xsd";
                xml = xml.replace("http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd", xsiValue);
                util.escribirArchivo(xml, pathInt, nameXml + ".xml");
            }
        } catch (Exception ex) {
            System.out.println("Excepcion: No se pudo crear el archivo xml en la ruta especificada: " + ex.getMessage());
            ex.printStackTrace();
            ConectorDF.log.error("Excepcion: No se pudo crear el archivo xml en la ruta especificada: " + ex.getMessage());
        }*/
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException ex) {
//            System.out.println("Excepcion en el Thread.sleep de la clase ConstruirXML: " + ex.getMessage());
//            ConectorDF.log.error("Excepcion en el Thread.sleep de la clase ConstruirXML: " + ex.getMessage());
//        }
    }
    
    public String getNumEmpleado() {
        return numEmpleado;
    }
    
    public Comprobante crearComprobante(mx.grupocorasa.sat.cfd._40.ObjectFactory of) throws Exception {
        Comprobante comp = of.createComprobante();
        Complemento comple = of.createComprobanteComplemento();
        Date date = new Date();
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        java.time.LocalDateTime xgc = java.time.LocalDateTime.of(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH) + 1, gc.get(Calendar.DAY_OF_MONTH), gc.get(Calendar.HOUR_OF_DAY), gc.get(Calendar.MINUTE), gc.get(Calendar.SECOND));
        
        boolean mostrarLeyenda = !get("CONDICIONPAGO:").toLowerCase().contains("recibo");
        //Seteamos Comprobante
        tipoComprobante = get("TIPO_COMPROBANTE:");
        tipoComprobanteLayout = get("TIPO_COMPROBANTE:");
        
        switch (tipoComprobante) {
            case "I":
                tipoComprobante = "I";
                cTipoComp = CTipoDeComprobante.I;
                if (getImpuestosLocales() != null) {
                    comple.getAny().add(getImpuestosLocales());
                    comp.setComplemento(comple);
                }
                break;
            case "E":
                tipoComprobante = "E";
                cTipoComp = CTipoDeComprobante.E;
                break;
            case "D":
                tipoComprobante = "I";
                cTipoComp = CTipoDeComprobante.I;
                comple.getAny().add(crearDonatarias(mostrarLeyenda));
                comp.setComplemento(comple);
                break;
            case "N":
                tipoComprobante = "N";
                cTipoComp = CTipoDeComprobante.N;
                comple.getAny().add(getNomina());
                comp.setComplemento(comple);
                break;
            case "recibo de nomina":
                tipoComprobante = "N";
                cTipoComp = CTipoDeComprobante.N;
                comple.getAny().add(getNomina());
                comp.setComplemento(comple);
                break;
            case "F":
                tipoComprobante = "I";
                cTipoComp = CTipoDeComprobante.I;
                break;
            case "T":
                tipoComprobante = "T";
                cTipoComp = CTipoDeComprobante.T;
                break;
            case "P":
                tipoComprobante = "P";
                cTipoComp = CTipoDeComprobante.P;
                comple.getAny().add(getPagos());
                comp.setComplemento(comple);
                break;
        }
        
        comp.setTipoDeComprobante(cTipoComp);
        comp.setFecha(xgc);
        fechaExp = formatFecha(date);
        comp.setVersion("4.0");
        comp.setEmisor(getEmisor(of));
        comp.setReceptor(getReceptor(of));
        comp.setConceptos(getConceptos(of));
        
        comp.getCfdiRelacionados().add(getCfdiRelacionados());

        /*String condicionPago = get("CONDICIONPAGO:");
        if (!condicionPago.isEmpty()) {
            comp.setCondicionesDePago(condicionPago);
        } else {
            comp.setCondicionesDePago("_");
        }*/
        String txt = get("DESCUENTO:");
        if (!(txt.isEmpty() || txt.equals("_"))) {
            Double desc = Double.parseDouble(txt);
            if (desc > 0.0) {
                comp.setDescuento(util.redondear(new BigDecimal(desc)));
                //comp.setMotivoDescuento(get("MOTIVODESCUENTO"));
            }
        }
        
        metodoPago = get("METODOPAGO:");
        
        if (tipoComprobanteLayout.equalsIgnoreCase("N")) {
            numEmpleado = get("NUMEMPLEADO:");
        }
        
        comp.setLugarExpedicion(get("LUGAREXPEDICION:"));
        comp.setMetodoPago(get("TIPO_COMPROBANTE:").equals("T") || get("TIPO_COMPROBANTE:").equals("P") ? null : CMetodoPago.fromValue(metodoPago));
        comp.setFormaPago(get("TIPO_COMPROBANTE:").equals("T") || get("TIPO_COMPROBANTE:").equals("P") ? null : CFormaPago.fromValue(get("FORMAPAGO:")));
        String moneda = get("MONEDA:");
        String tipoCambio = get("TIPOCAMBIO:");
        
        if (!moneda.equalsIgnoreCase("MXN") && !moneda.equalsIgnoreCase("XXX")) {
            comp.setMoneda(CMoneda.fromValue(moneda));
            comp.setTipoCambio(get("TIPO_COMPROBANTE:").equals("P") ? null : new BigDecimal(tipoCambio));
        } else {
            comp.setMoneda(CMoneda.fromValue(moneda));
        }
        
        serie = get("SERIE:");
        comp.setSerie(serie);
        folio = get("FOLIO:");
        comp.setFolio(folio);
        comp.setSubTotal(tipoComprobanteLayout.equalsIgnoreCase("P") ? BigDecimal.ZERO : util.redondear(new BigDecimal(get("SUBTOTAL:"))));
        total = tipoComprobanteLayout.equalsIgnoreCase("P") ? BigDecimal.ZERO : util.redondear(new BigDecimal(get("TOTALNETO:")));
        comp.setTotal(total);
        
        comp.setImpuestos(getImpuestos(of));
        
        comp.setNoCertificado(noCertificado);
        comp.setSello("");
        comp.setCertificado("");
        leyenda = get("LEYENDA:");
//        String cta = get("NUMCTAPAGO");
//
//        if (!(cta.isEmpty() || cta.equals("_"))) {
//            comp.set
//            comp.setNumCtaPago(cta);
//        }

        //comp.setComplemento(comple);
        return comp;
    }
    
    private CfdiRelacionados getCfdiRelacionados() throws Exception {
        CfdiRelacionados cfd = new CfdiRelacionados();
        String sd = get("RELACIONCFDI:");
        if (!sd.isEmpty()) {
            cfd.setTipoRelacion(CTipoRelacion.fromValue(get("TIPORELACION:")));
            String[] docs = sd.split(",");
            for (String c : docs) {
                CfdiRelacionados.CfdiRelacionado cf = new CfdiRelacionados.CfdiRelacionado();
                cf.setUUID(c);
                cfd.getCfdiRelacionado().add(cf);
            }
            return cfd;
        } else {
            return null;
        }
    }
    
    private Donatarias crearDonatarias(boolean mostrarLeyenda) throws Exception {
        Donatarias don = new Donatarias();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        if (mostrarLeyenda) {
            don.setLeyenda("Este comprobante ampara un donativo, el cual será destinado por la donataria a los fines propios de su objeto social. "
                    + "En el caso de que los bienes donados hayan sido deducidos previamente para los efectos del impuesto sobre la renta, este donativo no es deducible. "
                    + "La reproducci\u00f3n no autorizada de este comprobante constituye un delito en los t\u00e9rminos de las disposiciones fiscales.");
        } else {
            don.setLeyenda("");
        }
        don.setVersion("1.1");
        
        java.time.LocalDate cal;
        /**
         * ******************CERNAS*******************
         */
//        cal.setDay(4);
//        cal.setMonth(6);
//        cal.setYear(2007);
//        cal.setHour(0);
//        cal.setMinute(0);
//        cal.setSecond(0);
//
//        don.setFechaAutorizacion(cal);
//        don.setNoAutorizacion("325-SAT-09-IV-E-73507");

        Date fechaAuto = sdf.parse(get("FECHAAUTO:"));
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(fechaAuto);
        cal = java.time.LocalDate.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
        don.setFechaAutorizacion(cal);
        don.setNoAutorizacion(get("NOAUTO:"));

        //COBAES
//        cal.setDay(23);
//        cal.setMonth(10);
//        cal.setYear(2015);
//        cal.setHour(0);
//        cal.setMinute(0);
//        cal.setSecond(0);
//
//        don.setFechaAutorizacion(cal);
//        don.setNoAutorizacion("GOBIERNO");325-SAT-09-IV-E-73507
        return don;
    }
    
    private Comprobante.Receptor getReceptor(mx.grupocorasa.sat.cfd._40.ObjectFactory of) {
        Comprobante.Receptor rec = of.createComprobanteReceptor();
        rec.setUsoCFDI(CUsoCFDI.fromValue(get("USOCFDI:")));
        
        nombreReceptor = get("NOMBRE2:");
        rec.setNombre(nombreReceptor);
        rfcReceptor = get("RFC2:");
        rec.setRfc(rfcReceptor);
        rec.setDomicilioFiscalReceptor(get("DOMICILIOFISCAL:"));
        rec.setRegimenFiscalReceptor(CRegimenFiscal.fromValue(get("REGIMENFISCAL2:")));
        rec.setNumRegIdTrib(null);
        if (rec.getNumRegIdTrib() != null && rfcReceptor.equalsIgnoreCase("XEXX010101000")) {
            rec.setResidenciaFiscal(CPais.fromValue(get("RESIDENCIAFISCAL:")));
        }
        if (!get("CALLE2:").isEmpty()) {
            JsonObject receptor = new JsonObject();
            receptor.addProperty("calle", get("CALLE2:"));
            receptor.addProperty("noexterior", get("NOEXTERIOR2:"));
            receptor.addProperty("nointerior", get("NOINTERIOR2:"));
            receptor.addProperty("colonia", get("COLONIA2:"));
            receptor.addProperty("localidad", get("LOCALIDAD2:"));
            receptor.addProperty("municipio", get("MUNICIPIO2:"));
            receptor.addProperty("estado", get("ESTADO2:"));
            receptor.addProperty("pais", get("PAIS2:"));
            receptor.addProperty("cp", get("CP2:"));
            
            json.add("receptor", receptor);
        }
        return rec;
    }
    
    private Comprobante.Emisor getEmisor(mx.grupocorasa.sat.cfd._40.ObjectFactory of) {
        Comprobante.Emisor emi = of.createComprobanteEmisor();
        
        String regi = get("REGIMENFISCAL:");
        emi.setRegimenFiscal(CRegimenFiscal.fromValue(regi));
        
        nombreEmisor = get("NOMBRE1:");
        emi.setNombre(nombreEmisor);
        rfcEmisor = get("RFC1:");
        emi.setRfc(rfcEmisor);
        //emi.setExpedidoEn(expedicion);
        if (!get("CALLE1:").isEmpty()) {
            JsonObject emisor = new JsonObject();
            emisor.addProperty("calle", get("CALLE1:"));
            emisor.addProperty("noexterior", get("NOEXTERIOR1:"));
            emisor.addProperty("nointerior", get("NOINTERIOR1:"));
            emisor.addProperty("colonia", get("COLONIA1:"));
            emisor.addProperty("localidad", get("LOCALIDAD1:"));
            emisor.addProperty("municipio", get("MUNICIPIO1:"));
            emisor.addProperty("estado", get("ESTADO1:"));
            emisor.addProperty("pais", get("PAIS1:"));
            emisor.addProperty("cp", get("CP1:"));
            
            json.add("emisor", emisor);
        }
        
        return emi;
    }
    
    private Comprobante.Conceptos getConceptos(mx.grupocorasa.sat.cfd._40.ObjectFactory of) {
        Comprobante.Conceptos cons = of.createComprobanteConceptos();
        Comprobante.Conceptos.Concepto con;
        int cont = 1;
        final int CLAVEPRODSERV = 0;
        final int CLAVEUNIDAD = 1;
        final int UNIDAD = 2;
        final int NOIDENTIFICACION = 3;
        final int CANTIDAD = 4;
        final int DESCRIPCION = 5;
        final int PRECIO = 6;
        final int DESCUENTO = 7;
        final int IMPORTE = 8;

        //       0             1       2        3               4        5            6          7          8
        //ClaveProdServ@ClaveUnidad@Unidad@NoIdentificacion@Cantidad@Descripcion@ValorUnitario@Descuento@Importe
        for (int i = posiConceptos; i < posfConceptos; i++) {
            con = of.createComprobanteConceptosConcepto();
            String temp = layout.get(i);
            int pos = temp.indexOf(":");
            List<String> c = Arrays.asList(temp.substring(pos + 1).trim().split("@"));
            BigDecimal cant = new BigDecimal(c.get(CANTIDAD));
            BigDecimal precio = new BigDecimal(c.get(PRECIO));
            BigDecimal descuento = new BigDecimal(c.get(DESCUENTO));
            BigDecimal importe = new BigDecimal(c.get(IMPORTE));
            
            if (tipoComprobanteLayout.equalsIgnoreCase("N") || tipoComprobanteLayout.equalsIgnoreCase("P")) {
                con.setCantidad(cant.setScale(0, RoundingMode.HALF_UP));
            } else {
                //con.setCantidad(cant.setScale(3, RoundingMode.HALF_UP));
                con.setCantidad(cant.setScale(2, RoundingMode.HALF_UP));
            }
            con.setComplementoConcepto(null);
            //con.setCuentaPredial(null);
            con.setDescripcion(c.get(DESCRIPCION).trim());
            con.setClaveProdServ(c.get(CLAVEPRODSERV).trim());
            con.setClaveUnidad(CClaveUnidad.fromValue(c.get(CLAVEUNIDAD).trim()));
            if (!c.get(UNIDAD).trim().isEmpty() && !c.get(UNIDAD).trim().equals(".")) {
                con.setUnidad(c.get(UNIDAD).trim());
            }

            //log.info(con.getClaveProdServ() + " - " + con.getClaveUnidad());
            Comprobante.Conceptos.Concepto.Impuestos imp = of.createComprobanteConceptosConceptoImpuestos();

            //Traslados
            if (posiConTraslados > -1) {
                if (posfConTraslados > posiConTraslados) {
                    Comprobante.Conceptos.Concepto.Impuestos.Traslados traslados = of.createComprobanteConceptosConceptoImpuestosTraslados();
                    for (int j = posiConTraslados; j < posfConTraslados; j++) {
                        String tra = layout.get(j);
                        int postr = tra.indexOf(":");
                        List<String> t = Arrays.asList(tra.substring(postr + 1).trim().split("@"));
                        if (t.get(0).trim().equalsIgnoreCase("C" + cont)) {
                            Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado tras = of.createComprobanteConceptosConceptoImpuestosTrasladosTraslado();
                            tras.setBase(util.redondear(new BigDecimal(t.get(1).trim())));
                            tras.setImpuesto(CImpuesto.fromValue(t.get(2).trim()));
                            tras.setTipoFactor(CTipoFactor.fromValue(t.get(3).trim()));
                            tras.setTasaOCuota(new BigDecimal(t.get(4).trim()).setScale(6, RoundingMode.HALF_UP));
                            tras.setImporte(util.redondear(new BigDecimal(t.get(5).trim())));
                            
                            traslados.getTraslado().add(tras);
                        }
                    }
                    if (!traslados.getTraslado().isEmpty()) {
                        imp.setTraslados(traslados);
                    }
                }
            }

            //Retenciones
            if (posiConRetenciones > -1) {
                if (posfConRetenciones > posiConRetenciones) {
                    Comprobante.Conceptos.Concepto.Impuestos.Retenciones retenciones = of.createComprobanteConceptosConceptoImpuestosRetenciones();
                    for (int j = posiConRetenciones; j < posfConRetenciones; j++) {
                        String ret = layout.get(j);
                        int postr = ret.indexOf(":");
                        List<String> r = Arrays.asList(ret.substring(postr + 1).trim().split("@"));
                        if (r.get(0).trim().equalsIgnoreCase("C" + cont)) {
                            Comprobante.Conceptos.Concepto.Impuestos.Retenciones.Retencion rete = of.createComprobanteConceptosConceptoImpuestosRetencionesRetencion();
                            rete.setBase(util.redondear(new BigDecimal(r.get(1))));
                            rete.setImpuesto(CImpuesto.fromValue(r.get(2)));
                            rete.setTipoFactor(CTipoFactor.fromValue(r.get(3)));
                            rete.setTasaOCuota(new BigDecimal(r.get(4)).setScale(6, RoundingMode.HALF_UP));
                            rete.setImporte(util.redondear(new BigDecimal(r.get(5))));
                            
                            retenciones.getRetencion().add(rete);
                        }
                    }
                    if (!retenciones.getRetencion().isEmpty()) {
                        imp.setRetenciones(retenciones);
                    }
                }
            }
            
            if (!tipoComprobanteLayout.equalsIgnoreCase("N") && !tipoComprobanteLayout.equalsIgnoreCase("P")) {
                con.setNoIdentificacion(c.get(NOIDENTIFICACION));
            }
            
            if (imp.getTraslados() != null || imp.getRetenciones() != null) {
                con.setImpuestos(imp);
            }
            
            if (tipoComprobanteLayout.equalsIgnoreCase("P")) {
                con.setValorUnitario(precio.setScale(0, RoundingMode.HALF_UP));
            } else {
                con.setValorUnitario(precio.setScale(2, RoundingMode.HALF_UP));
            }
            if (descuento.doubleValue() > 0) {
                con.setDescuento(descuento.setScale(2, RoundingMode.HALF_UP));
            }
            if (tipoComprobanteLayout.equalsIgnoreCase("P")) {
                con.setImporte(importe.setScale(0, RoundingMode.HALF_UP));
            } else {
                con.setImporte(importe.setScale(2, RoundingMode.HALF_UP));
            }
            cons.getConcepto().add(con);
            cont++;
        }
        
        return cons;
    }
    
    private Comprobante.Impuestos getImpuestos(mx.grupocorasa.sat.cfd._40.ObjectFactory of) {
        Comprobante.Impuestos impuestos = of.createComprobanteImpuestos();
        Comprobante.Impuestos.Retenciones rets = of.createComprobanteImpuestosRetenciones();
        Comprobante.Impuestos.Traslados tras = of.createComprobanteImpuestosTraslados();
        Comprobante.Impuestos.Retenciones.Retencion ret;
        Comprobante.Impuestos.Traslados.Traslado tra;
        
        if (!(tipoComprobanteLayout.equalsIgnoreCase("N") || tipoComprobanteLayout.equalsIgnoreCase("recibo de nomina") || tipoComprobanteLayout.equalsIgnoreCase("D") || tipoComprobanteLayout.equalsIgnoreCase("recibo de donativo") || tipoComprobanteLayout.equalsIgnoreCase("T") || tipoComprobanteLayout.equalsIgnoreCase("P"))) {
            //Seteamos Retenciones
            for (int i = posiRetenidos; i < posfRetenidos; i++) {
                ret = of.createComprobanteImpuestosRetencionesRetencion();
                String[] temp = layout.get(i).split(":")[1].split("@");
                ret.setImpuesto(CImpuesto.fromValue(temp[0].trim()));
                ret.setImporte(new BigDecimal(temp[1].trim()));
                
                if (ret.getImporte().doubleValue() > 0.0) {
                    rets.getRetencion().add(ret);
                }
            }

            //Seteamos Traslados
            for (int i = posiTraslados; i < posfTraslados; i++) {
                tra = of.createComprobanteImpuestosTrasladosTraslado();
                String[] temp = layout.get(i).split(":")[1].split("@");
                tra.setImpuesto(CImpuesto.fromValue(temp[0].trim()));
                tra.setTasaOCuota(new BigDecimal(temp[2].trim()).setScale(6, RoundingMode.HALF_UP));
                tra.setImporte(new BigDecimal(temp[3].trim()));
                tra.setTipoFactor(CTipoFactor.fromValue(temp[1].trim()));
                tra.setBase(new BigDecimal(temp[4].trim()));
                
                tras.getTraslado().add(tra);
            }

            //Seteamos Impuestos
            if (!tras.getTraslado().isEmpty()) {
                impuestos.setTotalImpuestosTrasladados(new BigDecimal(get("TOTALTRASLADOS:")));
                impuestos.setTraslados(tras);
            }
            if (!rets.getRetencion().isEmpty()) {
                impuestos.setTotalImpuestosRetenidos(new BigDecimal(get("TOTALRETENIDOS:")));
                impuestos.setRetenciones(rets);
            }
            
            return impuestos;
        } else {
            return null;
        }
    }
    
    private void print(String msg) {
        JOptionPane.showMessageDialog(null, msg);
    }
    
    private String get(String dato) {
        if ((xmlNoTimbrado == null || xmlNoTimbrado.isEmpty()) || (dato.compareTo(dato.toUpperCase()) == 0)) {
            String valor = null;
            for (int i = 0; i < layout.size(); i++) {
                if (layout.get(i).contains(dato)) {
                    valor = "";
                    String tmp[] = layout.get(i).split(":");
                    if (tmp.length > 1) {
                        for (int j = 1; j < tmp.length; j++) {
                            valor += tmp[j] + " ";
                        }
                    }
                    break;
                }
            }
            if (valor == null) {
                valor = "";
            } else if (valor.isEmpty()) {
                valor = "";
            }
            
            return valor.trim();
        } else {
            return getDatoXml(dato);
        }
    }
    
    private String getDatoXml(String dato) {
        String padre;
        if (dato.contains("1")) {
            padre = "cfdi:Emisor";
        } else if (dato.contains("2")) {
            padre = "cfdi:Receptor";
        }
        return "";
    }
    
    public String getAddendaKlyns() {
        StringBuilder sb = new StringBuilder();
        sb.append("<cfdi:Addenda>");
        sb.append("<cfdi:Klyns>");
        sb.append("<cfdi:Pedido folio=\"").append(get("KLYNS_FOLIO:")).append("\"></cfdi:Pedido>");
        sb.append("</cfdi:Klyns>");
        sb.append("</cfdi:Addenda>");
        return sb.toString();
    }
    
    private String getDato(Document doc, String element, String dato) {
        doc.getDocumentElement().normalize();
        NodeList lista = doc.getElementsByTagName(element);
        
        Node nodo = lista.item(0);
        
        if (nodo.getNodeType() == Node.ELEMENT_NODE) {
            org.w3c.dom.Element elementoReceptor = (org.w3c.dom.Element) nodo;
            
            return (getTagValue(dato, elementoReceptor));
        } else {
            return "";
        }
    }
    
    private String getTagValue(String sTag, org.w3c.dom.Element eElement) {
        String valor = eElement.getAttribute(sTag).trim();
        return valor;
    }
    
    public String getNameXml() {
        return nameXml;
    }
    
    public String getPathProduccion() {
        return pathPro;
    }
    
    public String getFolio() {
        return folio;
    }
    
    public String getRfcEmisor() {
        return rfcEmisor;
    }
    
    public String getFechaExp() {
        
        return fechaExp;
    }
    
    public String getFechaTim() {
        return formatFecha(new Date());
    }
    
    public String getXmlNoTimbrado() {
        if (xmlNoTimbrado != null) {
            if (xmlNoTimbrado.contains("xmlns:tfd=\"http://www.sat.gob.mx/TimbreFiscalDigital\"")) {
                xmlNoTimbrado = xmlNoTimbrado.replace("xmlns:tfd=\"http://www.sat.gob.mx/TimbreFiscalDigital\" ", "");
            }
        }
        return xmlNoTimbrado;
    }
    
    public BigDecimal getTotal() {
        return total;
    }
    
    public String getNombreEmisor() {
        return nombreEmisor;
    }
    
    public String getLayoutCadena() {
        return layoutCadena;
    }
    
    public String getSerie() {
        return serie;
    }
    
    public String getNoCertificado() {
        return noCertificado;
    }
    
    public void setNoCertificado(String noCertificado) {
        this.noCertificado = noCertificado;
    }
    
    public String getSello() {
        return this.sello;
    }
    
    private String formatFecha(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        return format.format(date);
    }
    
    private Comprobante sellar(Comprobante document, PrivateKey key, X509Certificate cert) throws Exception {
        
        cert.checkValidity();
        String signature = getSignature(key);
        document.setSello(signature);
        byte[] bytes = cert.getEncoded();
        Base64 b64 = new Base64(-1);
        String certStr = b64.encodeToString(bytes);
        document.setCertificado(certStr);
        BigInteger bi = cert.getSerialNumber();
        document.setNoCertificado(new String(bi.toByteArray()));
        return document;
    }
    
    private String getSignature(PrivateKey key) throws Exception {
        byte[] bytes = getCadenaOriginal();
        Signature sig = Signature.getInstance("SHA1withRSA");
        sig.initSign(key);
        sig.update(bytes);
        byte[] signed = sig.sign();
        Base64 b64 = new Base64(-1);
        return b64.encodeToString(signed);
    }
    
    private byte[] getCadenaOriginal() {
        try {
            File xslt_file = new File(xslt);
            String xml = xmlNoTimbrado;
            StreamSource sourceXML = new StreamSource(new StringReader(xml));
            StreamSource sourceXSL = new StreamSource(xslt_file);
            // crear el procesador XSLT que nos ayudará a generar la cadena original
            // con base en las reglas del archivo XSLT
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer(sourceXSL);

            // aplicar las reglas del XSLT con los datos del CFDI y escribir el resultado en output
            StreamResult sr = new StreamResult(new StringWriter());
            transformer.transform(sourceXML, sr);
            StringWriter sw = (StringWriter) sr.getWriter();
            StringBuffer sb = sw.getBuffer();
            return sb.toString().getBytes();
        } catch (TransformerException ex) {
            ex.printStackTrace();
            log.error("Excepcion al transformar el xml en cadena original: ", ex);
            return null;
        }
    }
}
