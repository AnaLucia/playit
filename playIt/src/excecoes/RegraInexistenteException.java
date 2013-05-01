package excecoes;

public class RegraInexistenteException extends Exception {
	
	public RegraInexistenteException(){
		super("Regra de composição inexistente");
	}

}
