package br.com.caelum.livraria.tx;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

@Transactional
@Interceptor
public class GerenciadorDeTransacao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;
	
	@AroundInvoke
	public Object executaTx(InvocationContext context) {
		
		Object result = null;
		
		try {
			// abre transacao
			em.getTransaction().begin();
			System.out.println("Abrindo transação.");
			
			result = context.proceed();
			
			// commita a transacao
			em.getTransaction().commit();
			System.out.println("Comitando transação.");
		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		}
		
		return result;

	}
	
}
