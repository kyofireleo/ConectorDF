/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conectordf;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import log.Log;
import org.apache.log4j.Logger;
import org.datacontract.schemas._2004._07.timbradosoap.RespuestaAcuse;
import org.datacontract.schemas._2004._07.timbradosoap.RespuestaCancelacionCFDI;
import org.datacontract.schemas._2004._07.timbradosoap.RespuestaRecuperarXML;
import org.datacontract.schemas._2004._07.timbradosoap.RespuestaTFD33;

/**
 *
 * @author JoséAbelardo
 */
public class ConectorDF {

    public static Log logObject;
    public static Logger log;
    public static String unidad;
    private utils.Utils util;
    public ConstruirXML construir;
    public String xmlTimbrado;
    public String xmlNoTimbrado;
    public String fechaTimbrado;
    public String fechaExpedicion;
    public String uuid;
    private String mensajeError;
    private String user, pass;
    private static URL wsdl;
    private boolean produccion;
    private final String urlPro = "https://timbradosoap.solucionesdfacture.com/WSTimbradoSOAP.svc?wsdl";
    private final String urlPru = "http://timbradosoap33.testdfacture.com/WSTimbradoSOAP.svc?wsdl";

    public ConectorDF(boolean produccion, Logger logg) {
        this.produccion = produccion;
        ConectorDF.log = logg;
    }

    public ConectorDF(boolean produccion, String user, String pass, Logger logg) {
        this.produccion = produccion;
        this.user = user;
        this.pass = pass;
        log = logg;
        try {
            if (produccion) {
                wsdl = new URL(urlPro);
            } else {
                wsdl = new URL(urlPru);
            }
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            log.error("Excepcion en la URL de produccion: " + produccion);
        }
    }

    public ConectorDF(boolean produccion, String user, String pass, Logger logg, String unidad) {
        log = logg;
        util = new utils.Utils(log);
        ConectorDF.unidad = unidad;
        this.user = user;
        this.pass = pass;
        this.produccion = produccion;
        try {
            if (produccion) {
                wsdl = new URL(urlPro);
            } else {
                wsdl = new URL(urlPru);
            }
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            log.error("Excepcion en la URL de produccion: " + produccion);
        }
    }

    public ConectorDF(boolean produccion, String user, String pass, String xmlNoTimbrado, Logger logg) {
        log = logg;
        util = new utils.Utils(log);
        this.user = user;
        this.pass = pass;
        this.produccion = produccion;
        try {
            if (produccion) {
                wsdl = new URL(urlPro);
            } else {
                wsdl = new URL(urlPru);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            log.error("Excepcion en la URL de produccion: " + produccion);
        }
    }

    public ConectorDF(boolean produccion, String user, String pass, String pathLayout, String rfcEmisor, String noCertificado, String estructuraNombre) throws Exception {
        try {
            logObject = new Log(unidad + ":/Facturas/");
            log = logObject.getLog();
        } catch (IOException e) {
            e.printStackTrace();
        }
        util = new utils.Utils(log);
        construir = new ConstruirXML(estructuraNombre, util.leerTxt(pathLayout), estructuraNombre);
        construir.setNoCertificado(noCertificado);
        construir.crearXml();
        this.user = user;
        this.pass = pass;
        this.produccion = produccion;

        try {
            if (produccion) {
                wsdl = new URL(urlPro);
            } else {
                wsdl = new URL(urlPru);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ConectorDF(boolean produccion, String user, String pass, List<String> layout, Logger logg, String unidad, boolean isNombreLayout, String estructuraNombre) throws Exception {
        log = logg;
        ConectorDF.unidad = unidad;
        util = new utils.Utils(log);

        construir = new ConstruirXML(estructuraNombre, layout);
        
        this.user = user;
        this.pass = pass;
        this.produccion = produccion;

        try {
            if (produccion) {
                wsdl = new URL(urlPro);
            } else {
                wsdl = new URL(urlPru);
            }
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            log.error("Excepcion al formar la URL de timbrado, produccion = " + produccion);
        }
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public ConstruirXML getObjXml() {
        return construir;
    }

    public String getFechaTimbrado() {
        return fechaTimbrado;
    }

    public String getFechaExpedicion() {
        return fechaExpedicion;
    }

    public String getUuid() {
        return uuid;
    }

    public String getXmlTimbrado() {
        return xmlTimbrado;
    }

    public String getXmlNoTimbrado() {
        return xmlNoTimbrado;
    }

    public boolean timbrar(String path) throws Exception {
        xmlNoTimbrado = construir.getXmlNoTimbrado();

        RespuestaTFD33 respuesta = timbrarCFDI(user, pass, encodeStringToBase64Binary(xmlNoTimbrado));
        if (respuesta.isValidate() && respuesta.getCodigo().getValue().equals("100")) {
            xmlTimbrado = new String(Base64.decode(respuesta.getXml().getValue()), "UTF-8");
            fechaExpedicion = construir.getFechaExp();
            fechaTimbrado = construir.getFechaTim();
            uuid = respuesta.getUuid().getValue();
            if (construir.getConUUID()) {
                construir.setNameXmlTimbrado(construir.getNameXml() + "_" + uuid);
            } else {
                construir.setNameXmlTimbrado(construir.getNameXml());
            }
            //construir.setNameXmlTimbrado(construir.getNameXml());
            new File(path).mkdir();
            //FileManage.createFileFromString(xmlTimbrado, path, construir.getNameXmlTimbrado() + ".xml");
            util.escribirArchivo(xmlTimbrado, path, construir.getNameXmlTimbrado() + ".xml");
            log.info("Comprobante timbrado correctamente!!");
            System.out.println("Comprobante timbrado correctamente!! - " + construir.getFolio());
            return true;
        } else {
            mensajeError = "Error: " + respuesta.getCodigo().getValue() + " - " + respuesta.getMensaje().getValue();
            System.out.println(mensajeError);
            log.error(mensajeError);
            return false;
        }
    }

    public boolean timbrar(String path, String cfdi) throws Exception {
        if (cfdi.contains("ns3:Nomina")) {
            cfdi = cfdi.replaceAll("ns3:", "nomina:");
            cfdi = cfdi.replaceAll("xmlns:ns3", "xmlns:nomina");
            xmlNoTimbrado = cfdi;
        } else {
            xmlNoTimbrado = cfdi;
        }

        RespuestaTFD33 respuesta = timbrarCFDI(user, pass, cfdi);
        if (respuesta.isValidate() && respuesta.getCodigo().getValue().equals("100")) {
            xmlTimbrado = new String(Base64.decode(respuesta.getXml().getValue()), "UTF-8");
            fechaExpedicion = construir.getFechaExp();
            fechaTimbrado = construir.getFechaTim();
            uuid = respuesta.getUuid().getValue();
            new File(path).mkdir();
            construir.setNameXmlTimbrado(construir.getNameXml() /*+ "_" + uuid*/);
            util.escribirArchivo(xmlTimbrado, path, construir.getNameXmlTimbrado() + ".xml");
            log.info("Comprobante timbrado correctamente!!");
            System.out.println("Comprobante timbrado correctamente!! - " + construir.getFolio());
            return true;
        } else {
            mensajeError = respuesta.getMensaje().getValue();
            String msg = "No se pudo timbrar: " + mensajeError + ". Codigo de Error: " + respuesta.getCodigo().getValue();
            System.out.println(msg);
            log.error(msg);
            return false;
        }
    }

    public List<ConectorDF> timbrar(String path, String rfcEmisor, List<ConstruirXML> cons, List<String> cfdi) throws UnsupportedEncodingException {

        List<ConectorDF> respuestas = new ArrayList<ConectorDF>();
        List<RespuestaTFD33> resp = timbrarCFDI(user, pass, cfdi, false);

        for (int i = 0; i < cfdi.size(); i++) {
            ConstruirXML construct = cons.get(i);
            RespuestaTFD33 respuesta = resp.get(i);

            if (respuesta.isValidate() && respuesta.getCodigo().getValue().equals("100")) {
                fechaExpedicion = construct.getFechaExp();
                fechaTimbrado = construct.getFechaTim();
                uuid = respuesta.getUuid().getValue();
                new File(path).mkdir();
                try {
                    xmlTimbrado = new String(Base64.decode(respuesta.getXml().getValue()), "UTF-8");
                    //FileManage.createFileFromString(xmlTimbrado, path, construct.getNameXml() + ".xml");
                    util.escribirArchivo(xmlTimbrado, path, construct.getNameXml() + ".xml");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    log.error("Excepcion al crear el archivo de XML timbrado", ex);
                }
                System.out.println(construct.getFolio());
                respuestas.add(this);
            } else {
                if (respuesta.getCodigo().getValue().equals("0")) {
                    i = i - 1;
                    continue;
                }
                mensajeError = respuesta.getCodigo() + " - " + respuesta.getMensaje().getValue();
                respuestas.add(this);
                System.out.println("No se pudo timbrar: " + construct.getNameXml() + " - " + mensajeError);
                log.error("No se pudo timbrar: " + construct.getNameXml() + " - " + mensajeError);
            }
        }
        return respuestas;
    }

    public String cancelarCfdi(String rfcEmisor, String rfcReceptor, String total, String uuid, String pathXml, String nameXml, String motivo, String uuidRelacionado) throws Exception {
        if (!unidad.contains(":")) {
            unidad += ":";
        }
        File fileKey = new File(unidad + "\\Facturas\\config\\" + rfcEmisor + ".key");
        File fileCer = new File(unidad + "\\Facturas\\config\\" + rfcEmisor + ".cer");

        String certificadoEmisor = encodeFileToBase64Binary(fileCer);
        String llaveEmisor = encodeFileToBase64Binary(fileKey);
        String llaveEmisorPassword = FileManage.getStringFromFile(new File(unidad + "\\Facturas\\config\\" + rfcEmisor + "_pass.txt"));

        RespuestaCancelacionCFDI respuesta = cancelarCFDI(user, pass, rfcEmisor, rfcReceptor, uuid, total, certificadoEmisor, llaveEmisor, llaveEmisorPassword, motivo, uuidRelacionado);
        String codigo = respuesta.getCodigo().getValue();
        String jsonRes;

        String msg = respuesta.getMensaje().getValue() + " - Codigo: " + respuesta.getCodigo().getValue();
        System.out.println(msg);

        switch (codigo) {
            case "201":
                jsonRes = "{\"isCancelled\":true, \"isRequested\":false, \"estatus\": \"\", \"mensaje\":\"" + respuesta.getMensaje().getValue() + "\"}";
                log.info(msg);
                break;
            case "202":
                jsonRes = "{\"isCancelled\":false, \"isRequested\":true, \"estatus\": \"SOLICITADA\", \"mensaje\":\"" + respuesta.getMensaje().getValue() + "\"}";
                log.info(msg);
                break;
            default:
                jsonRes = "{\"isCancelled\":false, \"isRequested\":false, \"estatus\": \"\", \"mensaje\":\"" + respuesta.getMensaje().getValue() + "\"}";
                log.error(msg);
                mensajeError = msg;
                break;
        }

        return jsonRes;
    }

//    public void cancelarCfdi(String rfcEmisor, List<String> rfcReceptor, List<String> total, List<String> uuid) throws Exception {
//        if (!unidad.contains(":")) {
//            unidad += ":";
//        }
//        File fileKey = new File(unidad + "\\Facturas\\config\\" + rfcEmisor + ".key");
//        File fileCer = new File(unidad + "\\Facturas\\config\\" + rfcEmisor + ".cer");
//
//        String certificadoEmisor = encodeFileToBase64Binary(fileCer);
//        String llaveEmisor = encodeFileToBase64Binary(fileKey);
//        String llaveEmisorPassword = FileManage.getStringFromFile(new File(unidad + "\\Facturas\\config\\" + rfcEmisor + "_pass.txt"));
//
//        List<RespuestaCancelacionCFDI> respuestas = cancelarCFDI(user, pass, rfcEmisor, rfcReceptor, uuid, total, certificadoEmisor, llaveEmisor, llaveEmisorPassword);
//
//        for (RespuestaCancelacionCFDI respuesta : respuestas) {
//            if (produccion) {
//                if (respuesta.getCodigo().getValue().equals("201") || respuesta.getCodigo().getValue().equals("202")) {
//                    System.out.println("Respuesta " + respuesta.getCodigo().getValue());
//                } else {
//                    String msg = respuesta.getMensaje().getValue() + " - Codigo: " + respuesta.getCodigo().getValue();
//                    System.out.println(msg);
//                    log.error(msg);
//                    mensajeError = msg;
//                }
//            } else {
//                if (respuesta.getCodigo().getValue().equals("205")) {
//                    log.info(respuesta.getMensaje().getValue());
//                    System.out.println(respuesta.getMensaje().getValue() + " - Codigo: 205");
//                } else {
//                    String msg = respuesta.getMensaje().getValue() + " - Codigo: " + respuesta.getCodigo().getValue();
//                    System.out.println(msg);
//                    log.error(msg);
//                    mensajeError = msg;
//                }
//            }
//        }
//    }
    public boolean RecuperarXML(String uuid, String path, String name) throws UnsupportedEncodingException, IOException {
        RespuestaRecuperarXML rrx = recuperaXML(user, pass, uuid);
        if (rrx.getXml().getValue() != null) {
            String xml = new String(Base64.decode(rrx.getXml().getValue()), "UTF-8");
            FileManage.createFileFromString(xml, path, name);
            return true;
        } else {
            return false;
        }
    }

    public List<File> RecuperarXML(List<String> uuid, String path, List<String> name) throws UnsupportedEncodingException, IOException {
        List<RespuestaRecuperarXML> rrx = recuperaXML(user, pass, uuid);
        List<File> cfdis = new ArrayList<File>();
        for (int i = 0; i < rrx.size(); i++) {
            RespuestaRecuperarXML r = rrx.get(i);
            if (r.getXml().getValue() != null) {
                String xml = new String(Base64.decode(r.getXml().getValue()), "UTF-8");
                if (!name.get(i).toLowerCase().contains(".xml")) {
                    name.set(i, name.get(i) + ".xml");
                }
                cfdis.add(new File(path + name.get(i)));
                FileManage.createFileFromString(xml, path, name.get(i));
            }
        }
        return cfdis;
    }

    public List<File> RecuperarIsssteesinXML(List<String> uuid, String path) throws UnsupportedEncodingException, IOException {
        List<RespuestaRecuperarXML> rrx = recuperaXML(user, pass, uuid);
        List<File> cfdis = new ArrayList<File>();
        for (int i = 0; i < rrx.size(); i++) {
            RespuestaRecuperarXML r = rrx.get(i);
            if (r.getXml().getValue() != null) {
                String xml = new String(Base64.decode(r.getXml().getValue()), "UTF-8");
                System.out.println("Guardando XML: " + uuid.get(i) + ".xml");
                cfdis.add(new File(path + uuid.get(i) + ".xml"));
                FileManage.createFileFromString(xml, path, uuid.get(i) + ".xml");
            }
        }
        return cfdis;
    }

    private String encodeFileToBase64Binary(File file) throws IOException {
        byte[] bytes = loadFile(file);
        byte[] encoded = Base64.encode(bytes).getBytes("UTF8");
        String encodedString = new String(encoded, "UTF8");
        return encodedString;
    }

    private String encodeStringToBase64Binary(String text) throws UnsupportedEncodingException {
        byte[] bytes = text.getBytes("UTF8");
        byte[] encoded = Base64.encode(bytes).getBytes("UTF8");
        String encodedString = new String(encoded, "UTF8");
        return encodedString;
    }

    private static byte[] loadFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // File is too large

        }
        byte[] bytes = new byte[(int) length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }

        is.close();
        return bytes;
    }

    private List<RespuestaTFD33> timbrarCFDI(String user, String password, List<String> xml, Boolean onlyTFD) throws UnsupportedEncodingException {
        List<RespuestaTFD33> respuestas = new ArrayList<RespuestaTFD33>();
        RespuestaTFD33 r;
        org.tempuri.WSTimbradoSOAP service = new org.tempuri.WSTimbradoSOAP(wsdl);
        org.tempuri.IWSTimbradoSOAP port = service.getSoapHttpEndpoint();
        for (String x : xml) {
            if (x.contains("ns3:Nomina")) {
                x = x.replaceAll("ns3:", "nomina12:");
                x = x.replaceAll("xmlns:ns3", "xmlns:nomina12");
                xmlNoTimbrado = x;
            } else {
                xmlNoTimbrado = x;
            }
            x = this.encodeStringToBase64Binary(x);
            r = port.timbrarCFDI33(user, password, x);
            if (r.isValidate()) {
                System.out.println("Comprobante timbrado: " + r.getUuid().getValue());
            }
            respuestas.add(r);
        }
        return respuestas;
    }

    private static RespuestaCancelacionCFDI cancelarCFDI(java.lang.String user, java.lang.String password, java.lang.String rfcEmisor, java.lang.String rfcReceptor, java.lang.String uuid, java.lang.String total, java.lang.String certificado, java.lang.String llave, java.lang.String passwordLlave, String motivo, String uuidRelacionado) {
        org.tempuri.WSTimbradoSOAP service = new org.tempuri.WSTimbradoSOAP(wsdl);
        org.tempuri.IWSTimbradoSOAP port = service.getSoapHttpEndpoint();

        return port.cancelarCFDI(user, password, rfcEmisor, rfcReceptor, uuid, total, certificado, llave, passwordLlave, motivo, uuidRelacionado);
    }

    private List<RespuestaCancelacionCFDI> cancelarCFDI(String user, String password, String rfcEmisor, List<String> rfcReceptor, List<String> uuid, List<String> total, String certificado, String llave, String passwordLlave) {
        org.tempuri.WSTimbradoSOAP service = new org.tempuri.WSTimbradoSOAP(wsdl);
        org.tempuri.IWSTimbradoSOAP port = service.getSoapHttpEndpoint();
        List<RespuestaCancelacionCFDI> respuesta = new ArrayList<RespuestaCancelacionCFDI>();
        for (int i = 0; i < uuid.size(); i++) {
            String u = uuid.get(i);
            String rfcr = rfcReceptor.get(i);
            String tot = total.get(i);

            try {
                //port.cancelarCFDI(user, password, rfcEmisor, rfcr, u, tot, certificado, llave, passwordLlave);
                RespuestaCancelacionCFDI rcc = null;
                respuesta.add(rcc);
                String msg = "Comprobante " + u + " cancelado";
                log.info(msg);
                System.out.println(msg);
            } catch (Exception | Error e) {
                System.err.println("No se pudo cancelar, se agregara como nulo");
                respuesta.add(null);
                System.out.println("Error Posicion " + (respuesta.size() - 1) + "\t" + u);
            }
        }
        return respuesta;
    }

    private RespuestaAcuse acuseCancelacion(String user, String password, String uuid) {
        org.tempuri.WSTimbradoSOAP service = new org.tempuri.WSTimbradoSOAP(wsdl);
        org.tempuri.IWSTimbradoSOAP port = service.getSoapHttpEndpoint();
        return port.acuseCancelacion(user, password, uuid);
    }

    private RespuestaRecuperarXML recuperaXML(String user, String password, String uuid) {
        org.tempuri.WSTimbradoSOAP service = new org.tempuri.WSTimbradoSOAP(wsdl);
        org.tempuri.IWSTimbradoSOAP port = service.getSoapHttpEndpoint();
        return port.recuperarXML(user, password, uuid);
    }

    private List<RespuestaRecuperarXML> recuperaXML(String user, String password, List<String> uuid) {
        org.tempuri.WSTimbradoSOAP service = new org.tempuri.WSTimbradoSOAP(wsdl);
        org.tempuri.IWSTimbradoSOAP port = service.getSoapHttpEndpoint();
        List<RespuestaRecuperarXML> rrx = new ArrayList<RespuestaRecuperarXML>();
        int cont = 0;
        for (String u : uuid) {
            System.out.println("Descargando UUID: " + u);
            rrx.add(port.recuperarXML(user, password, u));
            cont++;
        }
        return rrx;
    }

    private static RespuestaTFD33 timbrarCFDI(java.lang.String user, java.lang.String password, java.lang.String xml) {
        org.tempuri.WSTimbradoSOAP service = new org.tempuri.WSTimbradoSOAP(wsdl);
        org.tempuri.IWSTimbradoSOAP port = service.getSoapHttpEndpoint();
        return port.timbrarCFDI33(user, password, xml);
    }
}
