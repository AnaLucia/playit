package testes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import classes.FonteDeSons;

/**
 * Testes de unidade da classe FonteDeSons
 * @author Flavia Gangorra
 *
 */
public class TestaFonteDeSons {
	
	FonteDeSons font1, font2, font3;
	
	@Before
	public void init(){
		font1 = new FonteDeSons();
		font2 = new FonteDeSons();
		font3 = new FonteDeSons();
	}
	
	@Test
	public void testaGetSetFonte(){
		Assert.assertEquals(true, font1.ehVazio());
		Assert.assertEquals(true, font2.ehVazio());
		Assert.assertEquals(true, font3.ehVazio());
		List<String> newFonte = new ArrayList<String>();
		newFonte.add("joaozinho");
		Assert.assertEquals(false, newFonte.isEmpty());
		
		font1.setFonte(newFonte);
		Assert.assertEquals(false, font1.ehVazio());
		
		font2.setFonte(font1.getFonte());
		Assert.assertEquals(false, font2.ehVazio());
		
		font3.setFonte(font2.getFonte());
		Assert.assertEquals(false, font3.ehVazio());
	}
	
	
	@Test
	public void testaAddSom(){
		Assert.assertEquals(true, font1.ehVazio());
		
		font1.addFonte("flavinha");
		Assert.assertEquals(false, font1.ehVazio());
		Assert.assertEquals("[flavinha]", font1.getFonte().toString());
		
		font1.addFonte("lipinho");
		Assert.assertEquals("[flavinha, lipinho]", font1.getFonte().toString());
		
		font1.addFonte("outra pessoa");
		Assert.assertEquals("[flavinha, lipinho, outra pessoa]", font1.getFonte().toString());
		
		font1.removeFonte("outra pessoa");
		Assert.assertEquals("[flavinha, lipinho]", font1.getFonte().toString());
	}

	@Test
	public void testaToString(){
		font1.addFonte("idUsuario");
		font1.addFonte("idUsuario1");
		
		font2.addFonte("idUsuario2");
		
		font3.addFonte("idUsuario3");
		font3.addFonte("idUsuario4");
		
		Assert.assertEquals("[idUsuario idUsuario1]", font1.toString());
		Assert.assertEquals("[idUsuario2]", font2.toString());
		Assert.assertEquals("[idUsuario3 idUsuario4]", font3.toString());

		
		

		
		
	}
	
}
