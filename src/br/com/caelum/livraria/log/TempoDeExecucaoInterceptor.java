package br.com.caelum.livraria.log;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

@Log
@Interceptor
public class TempoDeExecucaoInterceptor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;
	
	@AroundInvoke
	public Object executaInterceptor(InvocationContext context) throws Exception {
		
		long antes = System.currentTimeMillis();
		
		String metodo = context.getMethod().getName();
		
		em.getTransaction().begin();
		Object result = context.proceed();
		
		long depois = System.currentTimeMillis();
		em.getTransaction().commit();
		System.out.println("Método :" + metodo + "\nTempo de execução: " + (depois - antes) + "ms");
		
		return result;
		
	}
	
}
