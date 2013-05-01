package testes;

import java.util.List;


import classes.Sistema;


/**
 * Classe que faz a "ponte" entre os scripts dos testes de aceitacao e o sistema da rede social
 * @author Flavia Gangorra
 *
 */
public class FachadaTestesAceitacao {
	
	Sistema sistema ;
	
	
	public FachadaTestesAceitacao(){
		new Sistema();
		sistema = Sistema.getInstance();
	}
	
	
	public void zerarSistema(){
		sistema.zerarSistema();
    }
	
	
	public void criarUsuario(String login, String senha, String nome, String email) throws Exception {
		sistema.criarUsuario(login, senha, nome, email);
		
		
	}
	
	public String abrirSessao(String login, String senha) throws Exception{
		return sistema.abrirSessao(login, senha);
	}

	
	public String getAtributoUsuario(String login, String atributo) throws Exception{
		return sistema.getAtributoUsuario(login, atributo);
	}
	
	
	//nesse cenario ja estarei logado no sistema, logo e responsabilidade do sistema
	//me retornar o perfil musical
	public String getPerfilMusical(String idSessao){
		List<String> retorno = sistema.getPerfilMusical(idSessao);
		return  retorno.toString().replace("[", "{").replace("]", "}").replace(", ", ",");
	}
	
	
	public String postarSom(String idSessao, String link, String data) throws Exception{
		return sistema.postarSom(idSessao, link, data);
	}
	
	
	public String getAtributoSom(String idDoSom, String atributo) throws Exception{
		return sistema.getAtributoSom(idDoSom, atributo);
	}
	
	
	public void encerrarSessao(String login){
		sistema.encerrarSessao(login);
	}
	
	public void encerrarSistema(){
		sistema.encerrarSistema();
	}
	
	 public void reiniciarSistema(){ 
		 sistema = new Sistema();
		 }
	
	//US02
	
	public String getIDUsuario(String idSessao){
		return sistema.getIDUsuario(idSessao);
	}
	
	
	/*public String enviarSolicitacaoAmizade(String idSessao, String login) throws Exception{
		return sistema.enviarSolicitacaoAmizade(idSessao, login);
	}
	
	public void aceitarSolicitacaoAmizade(String idSessao, String idSolicitacao) throws Exception{
		sistema.aceitarSolicitacaoAmizade(idSessao, idSolicitacao);
	}*/
	
	public String getFontesDeSons (String idSessao) throws Exception{
		List<String> retorno = sistema.getFontesDeSons(idSessao);
		return  retorno.toString().replace("[", "{").replace("]", "}").replace(", ", ",");
	}
	
	
	public String getVisaoDosSons(String idSessao) throws Exception{
		List<String> retorno = sistema.getVisaoDosSons(idSessao);
		return retorno.toString().replace("[", "{").replace("]", "}").replace(", ", ",");
	}


	public int getNumeroDeSeguidores(String idSessao) throws Exception{
		return sistema.getNumeroDeSeguidores(idSessao);
	}
	
	
	public void seguirUsuario(String idSessao, String login) throws Exception{
		sistema.seguirUsuario(idSessao, login);
	}
	
	public String getListaDeSeguidores(String idSessao) throws Exception{
		List<String> retorno = sistema.getListaDeSeguidores(idSessao);
		return retorno.toString().replace("[", "{").replace("]", "}").replace(", ", ",");
	}
	
	/*
	 * Sons favoritos dos usuario
	 */
	public String getSonsFavoritos(String idSessao) throws Exception{
		List<String> retorno = sistema.getSonsFavoritos(idSessao);
		return retorno.toString().replace("[", "{").replace("]", "}").replace(", ", ",");
	}
	
	/*
	 * Sons favoritos das pessoas que eu sigo 
	 */
	public String getFeedExtra(String idSessao) throws Exception{
		List<String> retorno = sistema.getFeedExtra(idSessao);
		return retorno.toString().replace("[", "{").replace("]", "}").replace(", ", ",");
	}
	
	public void favoritarSom(String idSessao, String idSom) throws Exception{
		sistema.favoritarSom(idSessao, idSom);
	}
	
	
	/**
	 * Retorna a descricao da regra1 de ordenacao
	 * @return a descricao da regra1 de ordenacao 
	 */
	public String getFirstCompositionRule(){
		return sistema.getFirstCompositionRule();
	}
	
	
	/**
	 * Retorna a descricao da regra2 de ordenacao
	 * @return a descricao da regra2 de ordenacao 
	 */
	public String getSecondCompositionRule(){
		return sistema.getSecondCompositionRule();
	}

	/**
	 * Retorna a descricao da regra3 de ordenacao
	 * @return a descricao da regra3 de ordenacao 
	 */
	public String getThirdCompositionRule(){
		return sistema.getThirdCompositionRule();
	}
	
	public String getMainFeed(String idSessao) throws Exception{
		List<String> retorno = sistema.getFeedPrincipal(idSessao);;
		return retorno.toString().replace("[", "{").replace("]", "}").replace(", ", ",");	
	}


	public void setMainFeedRule(String idSessao, String regra) throws Exception{
		sistema.setRegraDeOrdenacao(idSessao, regra);
	}
	
	public int getNumFavoritosEmComum(String idSessao, String idUsuario){
		return sistema.getNumFavoritosEmComum(idSessao, idUsuario);
	}
	
	public int getNumFontesEmComum(String idSessao, String idUsuario){
		return sistema.getNumFontesEmComum(idSessao, idUsuario);
	}

	public String getFontesDeSonsRecomendadas(String idSessao){
		List<String> retorno = sistema.getFontesDeSonsRecomendadas(idSessao);
		return retorno.toString().replace("[", "{").replace("]", "}").replace(", ", ",");
	}
	
	public String criarLista(String idSessao, String nomeDaLista) throws Exception{
		return sistema.criarLista(idSessao, nomeDaLista);
	}

	public void adicionarUsuario(String idSessao, String nomeDaLista, String idUsuario) throws Exception{
		sistema.adicionarUsuario(idSessao, nomeDaLista, idUsuario);
	}
	
	public String getSonsEmLista(String idSessao, String nomeDaLista) throws Exception{
		List<String> retorno = sistema.getSonsEmLista(idSessao, nomeDaLista);
		return retorno.toString().replace("[", "{").replace("]", "}").replace(", ", ",");
	
	}
	
	
}
