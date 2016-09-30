/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controlador;

import com.sv.udb.ejb.GruposFacadeLocal;
import com.sv.udb.modelo.Grupos;
import com.sv.udb.modelo.GruposAlumnos;
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
 * @author Mauricio
 */
@Named(value = "gruposBean")
@ViewScoped
public class GruposBean implements Serializable{

    @EJB
    private GruposFacadeLocal FCDEGrupos;
    private Grupos objeGrupos;
    private List<Grupos> listGrup;
    private boolean guardar;
    private String estado = "none";
    private Logs<Grupos> lgs = new Logs<Grupos>(Grupos.class) {
    };
    private Logger log = lgs.getLog();
    /**
     * Creates a new instance of GruposBean
     */
    public GruposBean() {
    }

    public Grupos getObjeGrupos() {
        return objeGrupos;
    }

    public void setObjeGrupos(Grupos objeGrupos) {
        this.objeGrupos = objeGrupos;
    }

    public List<Grupos> getListGrup() {
        return listGrup;
    }

    public void setListGrup(List<Grupos> listGrup) {
        this.listGrup = listGrup;
    }

    public boolean isGuardar() {
        return guardar;
    }

    public void setGuardar(boolean guardar) {
        this.guardar = guardar;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
    
    @PostConstruct
    public void init()
    {
        this.limpForm();
        this.consTodo();
        log.debug("Se ha inicializado un modelo de Grupos");
    }
    
    public void limpForm() {
        this.objeGrupos = new Grupos();
        this.guardar = true;
        this.estado = "none";
        log.debug("Se ha limpiado un modelo de Grupos");
    }
    
    public void guar() {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try {
            FCDEGrupos.create(this.objeGrupos);
            this.listGrup.add(this.objeGrupos);
            this.limpForm();
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos guardados')");
            log.info("Se ha guardo un registro en Grupos");
        } catch (Exception ex) {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al guardar ')");
            log.error("Ocurrio un error al momento de guardar en Grupos");
        } finally {

        }
    }
    
    public void modi() {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try {
            this.listGrup.remove(this.objeGrupos); //Limpia el objeto viejo
            FCDEGrupos.edit(this.objeGrupos);
            this.listGrup.add(this.objeGrupos); //Agrega el objeto modificado
            this.limpForm();
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos Modificados')");
            log.info("Se ha modificado en Grupos");
        } catch (Exception ex) {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al modificar ')");
            log.error("Ocurrio un error al momento de modificar en Grupos");
        } finally {

        }
    }
    
    public void elim()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            FCDEGrupos.remove(this.objeGrupos);
            this.listGrup.remove(this.objeGrupos);
            this.limpForm();
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos Eliminados')");
            log.info("Se ha eliminado en Grupos");
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al eliminar')");
            log.error("Ocurrio un error al momento de eliminar en Grupos");
        }
        finally
        {
            
        }
    }
    
    public void consTodo()
    {
        try
        {
            log.info("Se ha consultado todo en Grupos");
            this.listGrup = FCDEGrupos.findAll();
        }
        catch(Exception ex)
        {
            log.error("Ocurrio un error al momento de consultar todo en Grupos");
            ex.printStackTrace();
        }
        finally
        {
            
        }
    }
    public void cons()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        int codi = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codiGrupPara"));
        try
        {
            log.info("Se ha consultado en Grupos");
            this.objeGrupos = FCDEGrupos.find(codi);
            this.guardar = false;this.estado = "block";
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Consultado ')");
        }
        catch(Exception ex)
        {
            log.error("Ocurrio un error al momento de consultar en Grupos");
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al consultar')");
        }
        finally
        {
            
        }
    }
    
    
    
}
