/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sv.udb.controlador;

import com.sv.udb.ejb.CursosFacadeLocal;
import com.sv.udb.modelo.Cursos;
import com.sv.udb.utils.Logs;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Laboratorio
 */
@ManagedBean
@RequestScoped
public class CursoBean {
    @EJB
    private CursosFacadeLocal FCDECurs;
    private Cursos objeCurs;
    private List<Cursos> listCurs;
    private boolean guardar;
    private String estado = "none";
    private Logs<CursoBean> lgs = new Logs<CursoBean>(CursoBean.class) {
    };
    private Logger log = lgs.getLog();

    public List<Cursos> getListCurs() {
        return listCurs;
    }

    public void setListCurs(List<Cursos> listCurs) {
        this.listCurs = listCurs;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public Cursos getObjeCurs() {
        return objeCurs;
    }

    public void setObjeCurs(Cursos objeCurs) {
        this.objeCurs = objeCurs;
    }
    
    public boolean isGuardar() {
        return guardar;
    }
    
    /**
     * Creates a new instance of CursoBean
     */
    public CursoBean() {
    }
    
    @PostConstruct
    public void init()
    {
        this.limpForm();
        this.consTodo();
        log.debug("Se ha inicializado un modelo de curso");
    }
    
    public void limpForm()
    {
        this.objeCurs = new Cursos();
        this.guardar = true;    
        this.estado = "none";
        log.debug("Se ha limpiado un modelo de curso");
    }
    
    public void guar()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            FCDECurs.create(this.objeCurs);
            this.listCurs.add(this.objeCurs);
            this.limpForm();
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos guardados')");
            log.info("Se ha guardo un registro en Curso");
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al guardar ')");
            log.error("Ocurrio un error al momento de guardar en Curso");
        }
        finally
        {
            
        }
    }
    
    public void modi()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            this.listCurs.remove(this.objeCurs); //Limpia el objeto viejo
            FCDECurs.edit(this.objeCurs);
            this.listCurs.add(this.objeCurs); //Agrega el objeto modificado
            this.limpForm();
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos Modificados')");
            log.info("Se ha modificado en Curso");
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al modificar ')");
            log.error("Ocurrio un error al momento de modificar en Curso");
        }
        finally
        {
            
        }
    }
    
    public void elim()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            FCDECurs.remove(this.objeCurs);
            this.listCurs.remove(this.objeCurs);
            this.limpForm();
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos Eliminados')");
            log.info("Se ha eliminado en Curso");
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al eliminar')");
            log.error("Ocurrio un error al momento de eliminar en Curso");
        }
        finally
        {
            
        }
    }
    
    public void consTodo()
    {
        try
        {
            log.info("Se ha consultado todo en Curso");
            this.listCurs = FCDECurs.findAll();
        }
        catch(Exception ex)
        {
            log.error("Ocurrio un error al momento de consultar todo en Curso");
            ex.printStackTrace();
        }
        finally
        {
            
        }
    }
    
    public void cons()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        int codi = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codiCursPara"));
        try
        {
            log.info("Se ha consultado en Curso");
            this.objeCurs = FCDECurs.find(codi);
            this.guardar = false;this.estado = "block";
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Consultado a " + 
                    String.format("%s", this.objeCurs.getNombCurs()) + "')");
        }
        catch(Exception ex)
        {
            log.error("Ocurrio un error al momento de consultar en Curso");
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al consultar')");
        }
        finally
        {
            
        }
    }
    
}
