package classes;

import java.util.List;

public interface RegraDeOrdenacao {

	String getDescricaoDaRegra();
	
	List<Som> ordena(List<Som> sons);

}
