package br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.caelum.livraria.modelo.Livro;

public class LivroDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;
	
	private DAO<Livro> dao;

	@PostConstruct
	void init() {
		this.dao = new DAO<Livro>(this.em, Livro.class);
	}
	
	public List<Livro> listaTodos() {
		return this.dao.listaTodos();
	}

	public void adiciona(Livro livro) {
		this.dao.adiciona(livro);
	}

	public void atualiza(Livro livro) {
		this.dao.atualiza(livro);
	}

	public void remove(Livro livro) {
		this.dao.remove(livro);
	}

	public Livro buscaPorId(Integer livroId) {
		return this.dao.buscaPorId(livroId);
	}

	public int quantidadeElementos() {
		return this.dao.quantidadeElementos();
	}

	public List<Livro> listaTodosPaginada(int first, int pageSize) {
		return this.dao.listaTodosPaginada(first, pageSize);
	}
	
}
