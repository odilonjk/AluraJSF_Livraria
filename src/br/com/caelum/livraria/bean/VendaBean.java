package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.com.caelum.livraria.dao.VendaDAO;
import br.com.caelum.livraria.modelo.Venda;

@Named
@ViewScoped
public class VendaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private VendaDAO dao;

	public BarChartModel getVendasModel() {
	    BarChartModel model = new BarChartModel();
	 
	    ChartSeries vendasChart = new ChartSeries();
	    vendasChart.setLabel("Vendas 2016");
	    
	    List<Venda> vendas = this.dao.getVendas();
	    
	    for (Venda venda : vendas) {
			vendasChart.set(venda.getLivro().getTitulo(), venda.getQuantidade());
		}
	 
	    model.addSeries(vendasChart);
	    model.setAnimate(true);

	    return model;
    }
	
	
	
}
