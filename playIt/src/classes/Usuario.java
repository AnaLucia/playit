package classes;

import util.Util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Classe que representa um usuario da rede social PlayIt
 * @author Flavia Gangorra, Ana Lucia, Diego Tavares
 *
 */

public class Usuario implements Serializable {

	private static final long serialVersionUID = 2690603538725073290L;
	private String login, nome, senha,  email;
	private PerfilMusical perfil;
	private FonteDeSons fonte;
	private List<String> sonsFavoritos, feedExtra, seguidores;
	private String IdSessao = "";  //se vazio nao esta logado
	private FeedPrincipal feedPrincipal;
	private Map<String, List<String>> listasDeUsuarios;

	
	/**
	 * Instancia um novo usuario
	 * @param login
	 * @param nome
	 * @param email
	 * @param senha
	 */
	public Usuario(String login, String nome, String email, String senha) {
		this.login = login;
		this.nome = nome;
		this.email = email;
		this.senha = senha;

		this.perfil = new PerfilMusical(); //inicializa o perfil musical vazio
		this.fonte = new FonteDeSons(); //lista dos usuarios que eu sigo, minhas fontes de sons
		this.seguidores = new Stack<String>();
		this.sonsFavoritos = new ArrayList<String>();
		this.feedExtra = new ArrayList<String>();
		
		this.feedPrincipal = new FeedPrincipal();
		this.listasDeUsuarios = new HashMap<String, List<String>>();
	}

	public void setFeedPrincipal(List<Som> sons){
		this.feedPrincipal.setFeedPrincipal(sons);
		
	}
	
	
	public void setRegraDeOrdenacao(String regra){
		this.feedPrincipal.setRegra(regra);
	}
	
	public List<String> getFeedPrincipal(){
		if (feedPrincipal.getTamanho() > 0) {
			List<Som> musicas = this.feedPrincipal.ordenaPelaRegra();
			return Util.getIdsDosSons(musicas);
		}
		
		List<String> lista = new ArrayList<String>();
		return lista; //gambiarra feia
		
	}
	
	/*private List<Som> getSons(FonteDeSons fonte) {
		for (String amigo : fonte.getFonte()) {
			Usuario user = util
		}
		return null;
	}*/

	/**
	 * Retorna a lista dos sons favoritos dos usuarios que eu sigo
	 * @return a lista dos sons favoritos dos usuarios que eu sigo
	 */
	public List<String> getFeedExtra() {
		return feedExtra;
	}

	/**
	 * Modifica o FeedExtra
	 * @param feedExtra
	 */
	public void setFeedExtra(List<String> feedExtra) {
		this.feedExtra = feedExtra;
	}

	/**
	 * Adicionar um novo som ao feed Extra
	 * @param idSom
	 */
	public void addFeedExtra(String idSom){
		this.feedExtra.add(idSom);
	}

	/**
	 * Remover um som do feedExtra
	 * @param idSom
	 */
	public void removeFeedExtra(String idSom){
		this.feedExtra.remove(idSom);
	}

	/** 
	 * Retorna a lista dos sons favoritados pelo usuario
	 * @return lista dos sons favoritados pelo usuario
	 */
	public List<String> getSonsFavoritos() {
		return sonsFavoritos;
	}

	/**
	 * Modifica a lista dos sons favoritos
	 * @param sonsFavoritos - uma nova lista de sons favoritos
	 */
	public void setSonsFavoritos(List<String> sonsFavoritos) {
		this.sonsFavoritos = sonsFavoritos;
	}

	/**
	 * Adicionar um som favorito a sua sua lista de sons favoritos
	 * @param idSom
	 */
	public void addSomFavorito(String idSom){
		this.sonsFavoritos.add(idSom);

	}


	/**
	 * Remover um som da sua lista de favoritos
	 * @param idSom
	 */
	public void removeSomFavorito(String idSom){
		this.sonsFavoritos.remove(idSom);
	}

	/**
	 * Retorna a lista com os ids dos usuarios que sao suas fontes de som, 
	 * ou seja, os usuarios que eu sigo na rede social
	 * @return - lista contendo suas fontes de som
	 */
	public List<String> getFonte() {
		return fonte.getFonte();
	}

	/**
	 * Modifica sua fonte se sons
	 * @param fonte
	 */
	public void setFonte(FonteDeSons fonte) {
		this.fonte = fonte;
	}

	/**   
	 * Retorna a senha do usuario
	 * @return senha
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * Modifica a senha do usuario
	 * @param senha - nova senha
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}

	/**
	 * Retorna o login do usuario
	 * @return login do usuario
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Modifica o login do usuario
	 * @param login - novo login
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Retorna o nome do usuario
	 * @return - o nome do usuario
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * Modifica o nome do usuario
	 * @param nome - novo nome do usuario
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Retorna o email do usuario
	 * @return - o email do usuario
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Modifica o email do usuario
	 * @param email - novo email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Usuario [login=" + login + ", nome=" + nome + ", senha="
				+ senha + ", email=" + email + "]";
	}


	///

	/**
	 * Retorna o perfil musical do usuario, ou seja, 
	 * os sons postados pelo proprio usuario na rede social
	 * @return o perfil musical do usuario
	 */
	public PerfilMusical getPerfilmusical(){
		return perfil;
	}

	/**
	 * Modifica o perfil musical do usuario
	 * @param newPerfil
	 */
	public void setPerfilMusical(PerfilMusical newPerfil){
		this.perfil = newPerfil;
	}

	/**
	 * Postar um novo som na rede social
	 * @param link - do som
	 * @param data - da postagem
	 * @return - o id do som postado
	 */
	public String postarSom(String link, String data){
		String idSom = null;
		if (!((link == null)|| (data == null))) {
			idSom = Util.gerarIdDoSom(login);
			Som som = new Som(link, data);
			perfil.addSom(idSom, som);
			som.setIdSom(idSom);
		}
		return idSom;
	}
	
	public Som getSomPostado(String idDoSom){
		return perfil.getSomPostado(idDoSom);
	}

	/**
	 * Retorna o id do usuario
	 * @return - o id do usuario
	 */
	public String getID(){
		return this.login;
	}

	/**
	 * Retorna a quantidade de seguidores que o usuario tem na 
	 * rede social.
	 * @return
	 */
	public int getNumeroDeSeguidores(){
		return seguidores.size();
	}
	
	/**
	 * Seguir um outro usuario na rede social
	 * @param idUsuario a ser seguido
	 */
	public void seguirUsuario(String idUsuario) {
		fonte.addFonte(idUsuario);

	}

	/**
	 * Adicionar um novo seguidor ao usuario
	 * @param idUsuario id do seguidor
	 */
	public void addSeguidor(String idUsuario) {
		seguidores.add(idUsuario);

	}
	/**
	 * Retorna a lista dos ids dos seguidores do usuario
	 * @return lista dos seguidores do usuario
	 */
	public List<String> getSeguidores() {
		return seguidores;
	}

	/**
	 * Modifica a lista de seguidores do usuario
	 * @param seguidores - nova lista de seguidores
	 */
	public void setSeguidores(Stack<String> seguidores) {
		this.seguidores = seguidores;
	}

	/**
	 * Verifica se a senha de um usuario eh a mesma que ele tem cadastrada 
	 * na rede social, afim de fazer a verificacao na hora de logar no 
	 * sistema
	 * @param senha
	 * @return verdadeiro ou falso caso as senhas casem
	 */
	public boolean verificaSenha(String senha) {
		return this.getSenha().equals(senha);
	}

	/**
	 * Logar na rede social
	 * @return - o id da sessao aberta apos logar
	 */
	public String abrirSessao() {
		IdSessao =  Util.gerarIdSessao(nome);
		return IdSessao;
	}

	/**
	 * Retorna o id da sessao do usuario
	 * @return
	 */
	public String getIdSessao() {
		return IdSessao;
	}

	/**
	 * Modifica o id sessao do usuario
	 * @param idSessao - novo id sessao
	 */
	public void setIdSessao(String idSessao) {
		IdSessao = idSessao;
	}

	public int getNumFavoritosEmComum(Usuario user) {
		
		List<String> euFavoritos = this.getSonsFavoritos() ,user2Favoritos = user.getSonsFavoritos();
		
		int numFavoritosEmComum = 0;
		
		
		if (euFavoritos.size() <= user2Favoritos.size()) {
			//verifico qual lista eh menor pra nao dar null pointer
			for (int i = 0; i < euFavoritos.size(); i++) {
				if (user2Favoritos.contains(euFavoritos.get(i))) {
					numFavoritosEmComum++;
				}
			}
		}else if (user2Favoritos.size() <= euFavoritos.size()) {
			for (int i = 0; i < user2Favoritos.size(); i++) {
				if (euFavoritos.contains(user2Favoritos.get(i))) {
					numFavoritosEmComum++;
				}
			}
		}
		
		return numFavoritosEmComum;
		
	}

	
	
	public int getNumFontesEmComum(Usuario user) {
		int numFontesComum = 0;
		
		List<String> minhasFontes = this.getFonte() ,user2Fontes = user.getFonte();
		
		if (minhasFontes.size() < user2Fontes.size()) {
			//verifico qual lista eh menor pra nao dar null pointer
			for (int i = 0; i < minhasFontes.size(); i++) {
				if (user2Fontes.contains(minhasFontes.get(i))) {
					numFontesComum++;
				}
			}
			}else if (user2Fontes.size() < minhasFontes.size()) {
			for (int i = 0; i < user2Fontes.size(); i++) {
				if (minhasFontes.contains(user2Fontes.get(i))) {
					numFontesComum++;
				}
			}
		}
		return numFontesComum;
	}

	public String criarLista(String nomeDaLista) {
		if (!this.listasDeUsuarios.containsKey(nomeDaLista)) {
			this.listasDeUsuarios.put(nomeDaLista, new ArrayList<String>());
			return nomeDaLista; //o id da lista eh o proprio nome passado
		}
		
		return null;
	}

	public void adicionarUsuario(String nomeDaLista, String idUsuario) {
		if (!listasDeUsuarios.containsKey(nomeDaLista)) {
			this.listasDeUsuarios.put(nomeDaLista, new ArrayList<String>());
		}
		this.listasDeUsuarios.get(nomeDaLista).add(idUsuario);
	}
	

	public List<String> getLista(String nomeDaLista) {
		if (listasDeUsuarios.containsKey(nomeDaLista)) {
			return listasDeUsuarios.get(nomeDaLista);
		}
		return null;
	}

	public List<String> getNomeDasListas() {
		List<String> retorno = Util.transformaSetEmList(listasDeUsuarios.keySet());
		return retorno;
	}

	
	

}