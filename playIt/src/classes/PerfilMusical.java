
package classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import util.Util;
/**
 * Perfil Musical, lista dos ids dos sons que o usuario posta no sistema, cada usuario tem
 * seu proprio perfil musical
 * 
 * 
 * @author Flavia Gangorra
 *
 */

public class PerfilMusical implements Serializable {
	
	/**
	 * Perfil musical do usuario guarda os idsDosSons que ele postou no sistema
	 */
	private static final long serialVersionUID = -9086598318322099057L;
	//private List<String> perfil;
	
	private List<Som> perfil;
	
	
	/**
	 * Inicializa um novo perfil musical
	 */
	public PerfilMusical (){
		perfil = new ArrayList<Som>();		
	}
	
	/**
	 * Adiciona um novo som no perfil musical, na verdade o que se armazena 
	 * eh o idDoSom
	 * @param idDoSom
	 */
	public void addSom(String idDoSom, Som som){
		perfil.add(som);	
		som.setIdSom(idDoSom);
	}
	

	/**
	 * Remove um som do seu perfil musical passando o idDoSom como parametro
	 * @param idDoSom
	 */
	public void removerSom(String idDoSom){
		Som song = buscaSomPorId(idDoSom);
		perfil.remove(song);		
	}
	
	private Som buscaSomPorId(String idDoSom) {
		for(Som s : perfil){
			if (s.getIdSom().equals(idDoSom)) {
				return s;
			}
		}
		return null;
	}

	/**
	 * Verifica se o perfil musical esta vazio 
	 * @return verdadeiro ou falso
	 */
	public boolean ehVazio(){
		return perfil.isEmpty();
	}

	/**
	 * Retorna uma lista de strings contendo os ids dos sons postandos pelo 
	 * usuario em seu perfil musical
	 * @return lista com os ids dos sons postados
	 */
	
	public List<String> getPerfil() {
		List<String> sons = new ArrayList<String>();
		for (Som som : perfil) {
			sons.add(som.getIdSom());
		}
		return Util.ordemTimeLine(sons);
	}

	/**
	 * Modifica o perfil musical do usuario o trocando por outro que eh passado como parametro
	 * @param novo perfil (List<String>)
	 */
	public void setPerfil(List<Som> perfil) {
		this.perfil = perfil;
	}
	
	public Som getSomPostado(String idDoSom){
		return buscaSomPorId(idDoSom);
	}
	
	
	/*@Override
	public String toString() {
		String retorno = "[";
		for (int i = 0; i < perfil.size(); i++) {
			String som = perfil.get(i);
			if (!som.equals("")) {
				retorno += som + ", ";
			}
		}
		return retorno.trim().substring(0, (retorno.lastIndexOf(",") - 2)) + "]";
		
	}*/
	
}
