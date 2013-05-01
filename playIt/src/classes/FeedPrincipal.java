package classes;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

//import javax.ws.rs.GET;

public class FeedPrincipal implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	RegraDeOrdenacao regra = null;
	List<Som> sons;
	
	public FeedPrincipal() {
		this.sons = new ArrayList<Som>();
	}
	
	
	public void setFeedPrincipal(List<Som> sons){
		this.sons = sons;
	}
	
	
	
	public List<Som> getFeedPrincipal(){
		return sons;
	}

	
	
	public void setRegra(String regra){
		if (regra.equals("PRIMEIRO OS SONS POSTADOS MAIS RECENTEMENTE PELAS FONTES DE SONS")) {
			this.regra = new Regra1();
		}else if (regra.equals("PRIMEIRO OS SONS COM MAIS FAVORITOS")) {
			this.regra = new Regra2();
		}else if (regra.equals("PRIMEIRO SONS DE FONTES DAS QUAIS FAVORITEI SONS NO PASSADO")) {
			this.regra = new Regra3();
		}
	}
	
	public RegraDeOrdenacao getRegraDeOrdenacao(){
		return regra;
	}
	
	public List<Som> ordenaPelaRegra(){
		return regra.ordena(sons);
		
	}


	public List<Som> getSons() {
		// TODO Auto-generated method stub
		return this.sons;
	}


	public int getTamanho() {
		// TODO Auto-generated method stub
		return sons.size();
	}


	
	
}
