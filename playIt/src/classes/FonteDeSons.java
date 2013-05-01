package classes;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FonteDeSons implements Serializable {
	private List<String> fonte;
	
	public FonteDeSons() {
		fonte = new ArrayList<String>(); //lista dos ids dos usuarios que são seus amigos, cada usuario tem um id unico no sistema
		//vou considerar como id do usuario seu login, ja que este eh unico
	}
	
	public void addFonte(String idUsuario){
		fonte.add(idUsuario);
	}
	
	public void removeFonte(String idUsuario){
		fonte.remove(idUsuario);
	}

	public List<String> getFonte() {
		return fonte;
	}

	public void setFonte(List<String> fonte) {
		this.fonte = fonte;
	}

	//nao consigo acessar esse metodo
	@Override
	public String toString() {
		String retorno = "[";
		for (int i = 0; i < fonte.size(); i++) {
			retorno += fonte.get(i) + " ";
		}
		return retorno.trim() + "]";
	}
	
	
	public boolean ehVazio(){
		return fonte.isEmpty();
	}
}
