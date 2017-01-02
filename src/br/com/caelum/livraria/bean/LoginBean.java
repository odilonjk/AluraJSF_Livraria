package br.com.caelum.livraria.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.UsuarioDAO;
import br.com.caelum.livraria.modelo.Usuario;

@Named
@ViewScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioDAO dao;
	
	@Inject
	private FacesContext context;
	
	private Usuario usuario = new Usuario();

    public Usuario getUsuario() {
        return usuario;
    }

    public String efetuaLogin() {

        boolean existe = dao.existe(this.usuario);

        if (existe) {
        	context.getExternalContext().getSessionMap().put("usuarioLogado", this.usuario);
            return "livro?faces-redirect=true";
        }
        
        context.addMessage(null, new FacesMessage("Login inválido."));
        context.getExternalContext().getFlash().setKeepMessages(true);        
        
        return "login?faces-redirect=true";
    }
    
    public String deslogar() {
    	context.getExternalContext().getSessionMap().remove("usuarioLogado");
    	return "login?faces-redirect=true";
    }
    
}
