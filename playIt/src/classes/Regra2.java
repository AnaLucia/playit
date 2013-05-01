package classes;

import java.util.List;

import util.Util;


/**
 * Ordena o feedPrincipal de maneira que os sons mais favoritados
 * sejam visualizados primeiro
 * @author Flavia Gangorra
 *
 */
public class Regra2 implements RegraDeOrdenacao{

	/**
	 * Ordena o feedPrincipal colocando os sons mais favoritados pelos usuarios
	 * do sistema para serem visualizados primeiro
	 * 
	 * @return lista ordenada pela regra
	 */
	public List<Som> ordena(List<Som> sons) {
		
		List<Som> retorno = Util.cloneDeList(sons);
		int numFavoritadas1, numFavoritadas2;
		for(int i =0 ;i <= retorno.size();i++ ){
			for(int j = 0; j <= retorno.size() - i;j++){
				
				numFavoritadas1 = retorno.get(j).getNumDeFavoritadas();
				numFavoritadas2 = retorno.get(j+1).getNumDeFavoritadas();
				
				if(numFavoritadas1 < numFavoritadas2){
					Som temp = retorno.get(i);
					retorno.set(i, retorno.get(j));
					retorno.set(j,temp);
				}
			}
		}
		return retorno;
	}

	
	public String getDescricaoDaRegra(){
		return "PRIMEIRO OS SONS COM MAIS FAVORITOS";
	}
}
