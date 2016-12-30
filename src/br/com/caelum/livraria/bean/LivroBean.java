package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.AutorDAO;
import br.com.caelum.livraria.dao.LivroDAO;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.LivroDataModel;

@Named
@ViewScoped
public class LivroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Livro livro = new Livro();

	private Integer autorId;

	private Integer livroId;

	private List<Livro> livros;
	
	@Inject
	private LivroDataModel livroDataModel;
	
	@Inject
	private LivroDAO dao;
	
	@Inject
	private AutorDAO autorDAO;

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public Integer getAutorId() {
		return autorId;
	}

	public Livro getLivro() {
		return livro;
	}

	public Integer getLivroId() {
		return livroId;
	}

	public void setLivroId(Integer livroId) {
		this.livroId = livroId;
	}

	public List<Livro> getLivros() {
		if(this.livros == null) this.livros = this.dao.listaTodos();
		return livros;
	}

	public List<Autor> getAutores() {
		return autorDAO.listaTodos();
	}

	public List<Autor> getAutoresDoLivro() {
		return this.getLivro().getAutores();
	}

	public void gravarAutor() {
		Autor autor = autorDAO.buscaPorId(this.autorId);
		this.getLivro().adicionaAutor(autor);
		System.out.println("Escrito por: " + autor.getNome());
	}

	public void gravar() {
		System.out.println("Gravando livro " + this.getLivro().getTitulo());

		if (getLivro().getAutores().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage("autor",
					new FacesMessage("Livro deve ter pelo menos um Autor."));
			return;
		}

		if (this.getLivro().getId() == null) {
			this.dao.adiciona(this.getLivro());
			this.livros = dao.listaTodos();
		} else {
			this.dao.atualiza(this.getLivro());
		}

		this.setLivro(new Livro());
	}

	public void carregar(Livro livro) {
		System.out.println("Carregando livro " + livro.getTitulo());
		this.setLivro(livro);
	}

	public void remover(Livro livro) {
		System.out.println("Removendo livro " + livro.getTitulo());
		this.dao.remove(livro);
	}

	public void removerAutorDoLivro(Autor autor) {
		this.getLivro().removeAutor(autor);
	}

	public String formAutor() {
		System.out.println("Chamanda do formulário do Autor.");
		return "autor?faces-redirect=true";
	}

	public void comecaComDigitoUm(FacesContext fc, UIComponent component,
			Object value) throws ValidatorException {

		String valor = value.toString();
		if (!valor.startsWith("1")) {
			throw new ValidatorException(new FacesMessage(
					"ISBN deveria começar com 1"));
		}

	}

	public void carregarLivroPelaId() {
		this.setLivro(this.dao.buscaPorId(livroId));
	}

	public boolean filtrarPreco(Object valorColuna, Object valorFiltro, Locale locale) {
		
		String textoDigitado = (valorFiltro == null) ? null : valorFiltro.toString().trim();

        System.out.println("Filtrando pelo " + textoDigitado + ", Valor do elemento: " + valorColuna);

        if (textoDigitado == null || textoDigitado.equals("")) {
            return true;
        }

        if (valorColuna == null) {
            return false;
        }

        try {
            Double precoDigitado = Double.valueOf(textoDigitado);
            Double precoColuna = (Double) valorColuna;

            return precoColuna.compareTo(precoDigitado) < 0;

        } catch (NumberFormatException e) {
            return false;
        }
		
	}

	public LivroDataModel getLivroDataModel() {
		return livroDataModel;
	}

	public void setLivroDataModel(LivroDataModel livroDataModel) {
		this.livroDataModel = livroDataModel;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}
	
}
