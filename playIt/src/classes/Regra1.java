package classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import util.Util;

/**
 * Ordena pelos sons postados mais recentes pelas fontes de som do usuario
 * @author Flavia Gangorra
 *
 */
public class Regra1 implements RegraDeOrdenacao, Serializable{

	
	private static final long serialVersionUID = -953681047420168198L;


	/**
	 * Ordena o feedPrincipal colocando os sons postados mais recentes
	 * para serem visualizados primeiro
	 * @return lista ordenada de acordo com a regra
	 */
	public List<Som> ordena(List<Som> sons) {
		
		List<Som> copySom = Util.cloneDeList(sons); //para nao alterar a list original (sem efeito colateral)
		Calendar data1, data2; 
		for(int i =0 ;i <= copySom.size();i++ ){
			for(int j = 0; j <= copySom.size() - i;j++){
				
				data1 = Util.converteStringDataEmCalendar(copySom.get(j).getData());
				data2 = Util.converteStringDataEmCalendar(copySom.get(j+1).getData());
				
				if(data1.before(data2)){
					Som temp = copySom.get(i);
					copySom.set(i, copySom.get(j));
					copySom.set(j,temp);
				}
			}
		}
		return copySom;
	}

	
	public String getDescricaoDaRegra(){
		return "PRIMEIRO OS SONS POSTADOS MAIS RECENTEMENTE PELAS FONTES DE SONS";
	}
	

}
