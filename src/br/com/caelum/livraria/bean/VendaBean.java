package br.com.caelum.livraria.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.Venda;

@ManagedBean
@ViewScoped
public class VendaBean {

	public BarChartModel getVendasModel() {
	    BarChartModel model = new BarChartModel();
	 
	    ChartSeries vendasChart2016 = new ChartSeries();
	    vendasChart2016.setLabel("Vendas 2016");
	    
	    List<Venda> vendas = getVendas();
	    
	    for (Venda venda : vendas) {
			vendasChart2016.set(venda.getLivro().getTitulo(), venda.getQuantidade());
		}
	 
	    model.addSeries(vendasChart2016);

	    ChartSeries vendasChart2015 = new ChartSeries();
	    vendasChart2015.setLabel("Vendas 2015");
         
	    vendas = getVendas();
	    
	    for (Venda venda : vendas) {
	    	vendasChart2015.set(venda.getLivro().getTitulo(), venda.getQuantidade());
	    }
	    model.addSeries(vendasChart2015);
	    model.setAnimate(true);

	    return model;
    }
	
	public List<Venda> getVendas() {
		
		List<Livro> livros = new DAO<Livro>(Livro.class).listaTodos();
		List<Venda> vendas = new ArrayList<Venda>();

		Random random = new Random();
		for (Livro livro : livros) {
			Integer quantidade = random.nextInt(500);
			vendas.add(new Venda(livro, quantidade));
		}
		
		return vendas;
	}
	
}
