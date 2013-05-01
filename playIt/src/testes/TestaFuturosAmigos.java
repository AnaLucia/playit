package testes;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import classes.FuturosAmigos;
import classes.Usuario;

/**
 * Testes de unidade da classe FuturosAmigos
 * @author Flavia Gangorra
 *
 */
public class TestaFuturosAmigos {
	Usuario user1, user2, user3;
	FuturosAmigos friends1, friends2;
	
	@Before
	public void init(){
		user1 = new Usuario("joh", "joao", "jojo@yahoo.com", "abc");
		user2 = new Usuario("linda", "lindanete", "lind123@bol.com", "aeioou");
		user3 = new Usuario("geo", "geovanessa", "vanessageo@hotmail.com", "bahia");
		friends1 = new FuturosAmigos(user1, user2);
		friends2 = new FuturosAmigos(user2, user3);
	}

	@Test
	public void testaGetsSets(){
		Assert.assertEquals("joao", friends1.getIdUser1().getNome());
		Assert.assertEquals("lindanete", friends1.getIdUser2().getNome());
		

		Assert.assertEquals("lindanete", friends2.getIdUser1().getNome());
		Assert.assertEquals("geovanessa", friends2.getIdUser2().getNome());
		
		friends1.setIdUser1(user3);
		Assert.assertEquals("geovanessa", friends1.getIdUser1().getNome());
		Assert.assertEquals("lindanete", friends1.getIdUser2().getNome());
		
		friends2.setIdUser2(user1);
		Assert.assertEquals("lindanete", friends2.getIdUser1().getNome());
		Assert.assertEquals("joao", friends2.getIdUser2().getNome());
	}

}
