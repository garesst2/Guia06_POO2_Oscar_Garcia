/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controlador;

import com.sv.udb.ejb.AlumnosFacadeLocal;
import com.sv.udb.modelo.Alumnos;
import com.sv.udb.utils.Logs;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

/**
 *
 * @author REGISTRO
 */
@Named(value = "alumnosBean")
@ViewScoped
public class AlumnosBean implements Serializable {

    @EJB
    private AlumnosFacadeLocal FCDEAlum;
    private Alumnos objeAlum;
    private List<Alumnos> listAlum;
    private boolean guardar;
    private Logs<AlumnosBean> lgs = new Logs<AlumnosBean>(AlumnosBean.class) {
    };
    private Logger log = lgs.getLog();

    public Alumnos getObjeAlum() {
        return objeAlum;
    }

    public void setObjeAlum(Alumnos objeAlum) {
        this.objeAlum = objeAlum;
    }

    public boolean isGuardar() {
        return guardar;
    }

    public List<Alumnos> getListAlum() {
        return listAlum;
    }

    /**
     * Creates a new instance of AlumnosBean
     */
    public AlumnosBean() {
    }

    @PostConstruct
    public void init() {
        this.limpForm();
        this.consTodo();
        log.debug("Se ha inicializado un modelo de alumnos");
    }

    public void limpForm() {
        this.objeAlum = new Alumnos();
        this.guardar = true;
        log.debug("Se ha limpiado un modelo de alumnos");
    }

    public void guar() {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try {
            FCDEAlum.create(this.objeAlum);
            this.listAlum.add(this.objeAlum);
            this.limpForm();
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos guardados')");
            log.info("Se ha guardo un registro en Alumnos");
        } catch (Exception ex) {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al guardar ')");
            log.error("Ocurrio un error al momento de guardar en alumnos");
        } finally {

        }
    }

    public void modi() {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try {
            this.listAlum.remove(this.objeAlum); //Limpia el objeto viejo
            FCDEAlum.edit(this.objeAlum);
            this.listAlum.add(this.objeAlum); //Agrega el objeto modificado
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos Modificados')");
            this.limpForm();
            log.info("Se ha modificado en Alumnos");
        } catch (Exception ex) {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al modificar ')");
            log.error("Ocurrio un error al momento de modificar en alumnos");
        } finally {

        }
    }

    public void elim() {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try {
            FCDEAlum.remove(this.objeAlum);
            this.listAlum.remove(this.objeAlum);
            this.limpForm();
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos Eliminados')");
            log.info("Se ha eliminado en Alumnos");
        } catch (Exception ex) {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al eliminar')");
            log.error("Ocurrio un error al momento de eliminar en alumnos");
        } finally {

        }
    }

    public void consTodo() {
        try {
            log.info("Se ha consultado todo en Alumnos");
            this.listAlum = FCDEAlum.findAll();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Ocurrio un error al momento de consultar todo en alumnos");
        } finally {

        }
    }

    public void cons() {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        int codi = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codiAlumPara"));
        try {
            log.info("Se ha consultado en Alumnos");
            this.objeAlum = FCDEAlum.find(codi);
            this.guardar = false;
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Consultado a "
                    + String.format("%s %s", this.objeAlum.getNombAlum(), this.objeAlum.getApelAlum()) + "')");
        } catch (Exception ex) {
            log.error("Ocurrio un error al momento de consultar en alumnos");
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al consultar')");
        } finally {

        }
    }
}
