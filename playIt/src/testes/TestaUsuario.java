package testes;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import classes.Usuario;

/**
 * Testes de unidade da classe Usuario
 * @author Flavia Gangorra
 *
 */

public class TestaUsuario {

	Usuario user1,user2, user3;
	
	
	/**
	 * Inicia os objetos
	 */
	@Before
	public void init(){
		user1 = new Usuario("maria1", "maria", "maria@ig.com", "senha");
		user2 = new Usuario("jj", "Joao", "joao@ccc.com", "senha");
		user3 = new Usuario("jose", "jose", "jose@ig.com", "senha");
	}
	
	/**
	 * Testa os atributos dos usuarios criados
	 */
	@Test
	public void TestaNovoUsuario(){
		
		
		Assert.assertEquals("maria1", user1.getLogin());
		Assert.assertEquals("jj", user2.getLogin());
		Assert.assertEquals("maria", user1.getNome());
		Assert.assertEquals("Joao", user2.getNome());
		Assert.assertEquals("maria@ig.com", user1.getEmail());
		Assert.assertEquals("joao@ccc.com", user2.getEmail());
		
	}
	
	/**
	 * 
	 */
	@Test
	public void TesteSetsGets(){
		user1.setNome("Maria");
		Assert.assertEquals("Maria", user1.getNome());
		
		Assert.assertEquals("maria@ig.com", user1.getEmail());
		user1.setEmail("maria127@uol.com");
		Assert.assertEquals("maria127@uol.com", user1.getEmail());
		
		Assert.assertEquals("jj", user2.getLogin());
		user2.setLogin("@JJ");
		Assert.assertEquals("@JJ", user2.getLogin());
		
		Assert.assertEquals("senha", user2.getSenha());
		user2.setSenha("123");
		Assert.assertEquals("123", user2.getSenha());
		
	}
	
	
	
	@Test
	public void TestaGetID(){
		
		Assert.assertEquals("jose", user3.getID());
		Assert.assertEquals("jj", user2.getID());
		Assert.assertEquals("maria1", user1.getID());
		
		user3.setLogin("zezinho");
		Assert.assertEquals("zezinho", user3.getID());
	}
	
//	@Test
//	public void TestaPostarSom(){
//		Assert.assertEquals(true, user1.getPerfilmusical().ehVazio());
//		String idSom1 = user1.postarSom("https://www.youtube.com", "09/04/13");
//		Assert.assertEquals(false, user1.getPerfilmusical().ehVazio());
//		Assert.assertEquals("[" + idSom1 +"]", user1.getPerfilmusical().getPerfil().toString());
//		String idSom2 = user1.postarSom("https://www.youtube.com", "09/04/13");
//		Assert.assertEquals("[" + idSom2 + ", "+ idSom1  +"]", user1.getPerfilmusical().getPerfil().toString());
//		
//		//user1.postarSom("");
//		
//		user1.postarSom(null, null);
//		Assert.assertEquals("[" + idSom2 + ", "+ idSom1  +"]", user1.getPerfilmusical().getPerfil().toString());
//	}

}
