package classes;

import java.io.Serializable;




/**
 * Classe que modela o objeto som na rede social PlayIt
 * @author Flavia Gangorra, Ana Lucia, Diego Tavares
 *
 */
public class Som implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7893731487055072907L;
	private String link, data;
	private int numeroDeFavoritadas = 0;
	private String id;
	
	
	public String getIdSom() {
		return id;
	}


	public void setIdSom(String id) {
		this.id = id;
	}


	/**
	 * Instancia um novo som
	 * @param link
	 * @param data
	 */
	public Som (String link, String data){
		
		this.link = link;
		this.data = data;
		
	}


	/**
	 * Retorna o link do som
	 * @return
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Modifica o link do som
	 * @param link - novo link do som
	 */
	public void setLink(String link) {
		this.link = link;
	}


	/**
	 * Retorna a data de postagem do som
	 * @return
	 */
	public String getData() {
		return data;
	}

	/**
	 * Modifica a data de postagem do som
	 * @param data - nova data de postagem
	 */
	public void setData(String data) {
		this.data = data;
	}


	public void addFavoritada() {
		numeroDeFavoritadas++;
		
	}
	
	public int getNumDeFavoritadas(){
		return numeroDeFavoritadas;
	}


	
}
