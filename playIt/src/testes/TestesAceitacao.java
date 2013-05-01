package testes;


import java.util.ArrayList;
import java.util.List;
import easyaccept.EasyAcceptFacade;

/**
 * 
 * Classe de teste. Esta classe executa todos os testes necessários chamando EasyAccept.
 * @author Flavia Gangorra
 */


public class TestesAceitacao {
	
	public static void main(String[] args) {

		List<String> files = new ArrayList<String>();
		
		String barra = getBarraDoSistema();
		files.add("testesDeAceitacao"+ barra + "US01.txt");
		files.add("testesDeAceitacao"+ barra + "US02.txt");
		files.add("testesDeAceitacao"+ barra + "US03.txt");
		files.add("testesDeAceitacao"+ barra + "US04.txt");
		files.add("testesDeAceitacao"+ barra + "US05.txt");
		files.add("testesDeAceitacao"+ barra + "US06.txt");
		//files.add("testesDeAceitacao"+ barra + "US07.txt");
		
		
		
		//... adicione todos os arquivos de teste.

		// classe de fachada para mediar o teste
		FachadaTestesAceitacao facade = new FachadaTestesAceitacao();

		EasyAcceptFacade eaFacade = new EasyAcceptFacade(facade, files);
		eaFacade.executeTests();

		//Mostrar resultados no console
		System.out.println(eaFacade.getCompleteResults());
	}

	private static String getBarraDoSistema() {
		String barra = "";
		String so = System.getProperty("os.name");
		if(so.toLowerCase().contains("windows")){
			barra = "//";
		}else {
			barra = "/";
		}
	
		return barra;
	}


}
