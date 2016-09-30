/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controlador;

import com.sv.udb.ejb.ProfesoresFacadeLocal;
import com.sv.udb.modelo.Profesores;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author oscar
 */
@Named(value = "profesoresBean")
@RequestScoped
public class ProfesoresBean {

    @EJB
    private ProfesoresFacadeLocal FCDEProfe;
    private Profesores objeProfe;
    private List<Profesores> listProfe;
    private boolean guardar;
    private String estado = "none";

    public Profesores getObjeProfe() {
        return objeProfe;
    }

    public void setObjeProfe(Profesores objeProfe) {
        this.objeProfe = objeProfe;
    }

    public List<Profesores> getListProfe() {
        return listProfe;
    }

    public void setListProfe(List<Profesores> listProfe) {
        this.listProfe = listProfe;
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

    /**
     * Creates a new instance of ProfesoresBean
     */
    public ProfesoresBean() {
    }

    @PostConstruct
    public void init() {
        this.limpForm();
        this.consTodo();
    }

    public void limpForm() {
        this.objeProfe = new Profesores();
        this.guardar = true;
        this.estado= "none";
    }

    public void guar() {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try {
            FCDEProfe.create(this.objeProfe);
            this.listProfe.add(this.objeProfe);
            this.limpForm();
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos guardados')");
        } catch (Exception ex) {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al guardar ')");
        } finally {

        }
    }

    public void modi() {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try {
            this.listProfe.remove(this.objeProfe); //Limpia el objeto viejo
            FCDEProfe.edit(this.objeProfe);
            this.listProfe.add(this.objeProfe); //Agrega el objeto modificado
            this.limpForm();
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos Modificados')");
        } catch (Exception ex) {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al modificar ')");
        } finally {

        }
    }
    
    public void elim()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            FCDEProfe.remove(this.objeProfe);
            this.listProfe.remove(this.objeProfe);
            this.limpForm();
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos Eliminados')");
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al eliminar')");
        }
        finally
        {
            
        }
    }
    
    public void consTodo()
    {
        try
        {
            this.listProfe = FCDEProfe.findAll();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            
        }
    }
    
    public void cons()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        int codi = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codiProfePara"));
        try
        {
            this.objeProfe = FCDEProfe.find(codi);
            this.guardar = false;this.estado = "block";
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Consultado ')");
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al consultar')");
        }
        finally
        {
            
        }
    }

}
