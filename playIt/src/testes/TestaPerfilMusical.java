package testes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import classes.PerfilMusical;
import classes.Som;


/**
 * Testes de unidade da classe PerfilMusical
 * @author Flavia Gangorra
 *
 */

public class TestaPerfilMusical {

	PerfilMusical perfil1, perfil2;
	Som som1, som2, som3;
	
	@Before
	public void init(){
		perfil1 = new PerfilMusical();
		perfil2 = new PerfilMusical();
		som1 = new Som("https://www.youtube.com", "09/04/13");
		som2 =  new Som("https://www.youtube.com", "09/04/13");
		som3 =  new Som("https://www.youtube.com", "09/04/13");
		
	}
	
	@Test
	public void testaGetsSets(){
		Assert.assertEquals(true, perfil1.ehVazio());
		Assert.assertEquals(true, perfil2.ehVazio());
		
		perfil1.addSom("song1", som1);
		Assert.assertEquals(false, perfil1.ehVazio());
		
		perfil2.addSom("song2", som2);
		Assert.assertEquals(false, perfil2.ehVazio());
		
		Assert.assertEquals("[song1]", perfil1.getPerfil().toString());
		Assert.assertEquals("[song2]", perfil2.getPerfil().toString());
		
		List<Som> newPerfil = new ArrayList<Som>();
		Som som3 = new Som("https://youtube.com", "20/02/13");
		som3.setIdSom("song3");
		newPerfil.add(som3);
		
		perfil1.setPerfil(newPerfil);
		Assert.assertEquals("[song3]", perfil1.getPerfil().toString());
		
		
	}
	
	@Test
	public void addRemoverSom(){
		perfil1.addSom("song1", som1);
		Assert.assertEquals("[song1]", perfil1.getPerfil().toString());
		perfil1.addSom("song2", som2);
		Assert.assertEquals("[song2, song1]", perfil1.getPerfil().toString());
		perfil1.addSom("song3", som3);
		Assert.assertEquals("[song3, song2, song1]", perfil1.getPerfil().toString());
		Assert.assertEquals("[song3, song2, song1]", perfil1.getPerfil().toString());
		
		perfil1.removerSom("song2");
		Assert.assertEquals("[song3, song1]", perfil1.getPerfil().toString());
		perfil1.removerSom("song1");
		Assert.assertEquals("[song3]", perfil1.getPerfil().toString());
		perfil1.removerSom("song3");
		Assert.assertEquals("[]", perfil1.getPerfil().toString());
		Assert.assertEquals(true, perfil1.ehVazio());
		
		perfil1.removerSom("song7");//nao acontece nenhum erro apenas nao retira nada ja que nao tem
		Assert.assertEquals("[]", perfil1.getPerfil().toString());
	}
}
