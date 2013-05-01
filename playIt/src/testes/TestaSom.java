package testes;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import classes.Som;

/**
 * Testes de unidade da classe Som
 * @author Flavia Gangorra
 *
 */
public class TestaSom {

	Som song1, song2;
	
	@Before
	public void init(){
		//na horaDaPostagem por enquanto to colocando a data
		
		song1 = new Som("http://www.youtube.com/djavan", "26/03/2013");
		song2 = new Som("https://www.vagalume.com.br/vanessadamata", "02/05/2013");
				
	}
	
	@Test
	public void testaGetsSets(){
		Assert.assertEquals("http://www.youtube.com/djavan", song1.getLink());
		Assert.assertEquals("https://www.vagalume.com.br/vanessadamata", song2.getLink());
	
		Assert.assertEquals("26/03/2013", song1.getData());
		Assert.assertEquals("02/05/2013", song2.getData());
		
		song1.setLink("https://terra.letras.com/revelecao");
		Assert.assertEquals("https://terra.letras.com/revelecao", song1.getLink());
		
		song2.setData("14/06/2013");
		Assert.assertEquals("14/06/2013", song2.getData());
		
	}
	
}
