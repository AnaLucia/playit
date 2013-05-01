package classes;

import java.util.List;


/**
 * Primeiro os sons dos usuarios os quais eu mais favoritei os sons postados
 * por eles, ou seja, se eu favoritei mais sons de B que de A os sons de B
 * aparecem primeiro no meu feedPrincipal
 * @author Flavia Gangorra
 *
 */
public class Regra3 implements RegraDeOrdenacao {

	public List<Som> ordena(List<Som> sons) {
		
		return null;
	}
	
	
	public String getDescricaoDaRegra(){
		return "PRIMEIRO SONS DE FONTES DAS QUAIS FAVORITEI SONS NO PASSADO";
	}

}
