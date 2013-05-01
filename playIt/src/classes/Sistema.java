package classes;



//Um sistema contem usuarios e seus perfis musicais
import util.Util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import persistencia.Arquivo;

import classes.Usuario;
import excecoes.DataException;
import excecoes.ListaInvalidaException;
import excecoes.LoginException;
import excecoes.RegraInexistenteException;
import excecoes.RegraInvalidaException;
import excecoes.SessaoInexistenteException;
import excecoes.SessaoInvalidaException;
import excecoes.SomException;
import excecoes.SomInexistenteException;
import excecoes.UsuarioInexistenteException;
import excecoes.UsuarioInvalidoException;

/**
 * Classe sistema, controla as acoes principais da rede social PlayIt.
 *  
 * @author Flavia Gangorra
 *
 */
public class Sistema {
	
	private Arquivo arq;
	
	private List<Usuario> users;
	private List<String> sessoesAbertas;
	private List<String> sonsPostados;
	
	static Sistema instance = null;
	
	
	
	/**
	 * Cria uma instancia do sistema central da rede social PlayIt.
	 */
	public Sistema(){
		arq = new Arquivo();
		users = new ArrayList<Usuario>();
		users = Arquivo.abreLerArquivo("usuarios-playIt.txt");
		sessoesAbertas = new ArrayList<String>();
		
		sonsPostados = new ArrayList<String>();
	}
	
	/**
	 * Retona a unica instancia que existe de sistema
	 * @return
	 */
	public static Sistema getInstance(){
		if (instance == null) {
			instance = new Sistema();	
		}return instance;
	}
	
	/**
	 * Encerra o sistema gravando os dados que precisam ser persistentes em arquivo.
	 */
	public void encerrarSistema(){
		//aqui salva os arquvivos e encerra tudo ;)
		arq.escreveArquivo("usuarios-playIt.txt", users); //**Ta salvando os usuarios no arquivo ja, so falta gravar os sons
		List<Som> sons = pegaOsObjetosSomDoSistema();
		arq.escreveArquivoSom("sons-playIt.txt", sons);
		
	}
	
	/**
	 * Pega os ids dos sons postados no sistema e retorna 
	 * uma lista com os objetos Som afim de gravar os objetos 
	 * em um arquivo para persistencia
	 * @return list de objetos som
	 */
	private List<Som> pegaOsObjetosSomDoSistema(){
		List<Som> retorno = new ArrayList<Som>();
		for (int i = 0; i < sonsPostados.size(); i++) {
			for(Usuario u : users){
				Som som = u.getSomPostado(sonsPostados.get(i));
				if (som != null) {
					retorno.add(som);
				}
			}
			
		}
		return retorno;
	}
	
	/**
	 * "Limpa" os dados do sistema.
	 */
	public void zerarSistema(){
		users.clear();
		sessoesAbertas.clear();
		arq.zerarArquivo("usuarios-playIt.txt");
		arq.zerarArquivo("sons-playIt.txt");
	}

	
	/**
	 * Metodo que retorna uma lista contento todos os usuarios cadastrados no sistema.
	 * @return List<Usuario>
	 */
	public List<Usuario> getUsuarios() {
		return users;
	}
	
	/**
	 * Encerra a sessao de um usuario logado no sistema atraves de seu login.
	 * @param login
	 */
	public void encerrarSessao(String login){
		Usuario user = buscarUsuarioPorLogin(login);
		String idSessao = user.getIdSessao();
		if (sessoesAbertas.contains(idSessao)) {//usuario esta logado no sistema
			sessoesAbertas.remove(idSessao); //retiro da lista de sessoes abertas
			user.setIdSessao(""); //reseto para vazio para mostrar que o usuario nao esta logado
		}
		
	}
	


	/**
	 * Retorna uma lista do perfilMusical do usuario que contem as musicas qu este postou no sistema.
	 * @param idSessao
	 * @return List<String> do perfilMusical do usuario 
	 */
	public  List<String> getPerfilMusical(String idSessao) {
		Usuario user = buscaUsuarioPorIDSessao(idSessao); 
		List<String> retorno = null;
		if (user != null) {
			retorno = user.getPerfilmusical().getPerfil();
		}
		/*if (sessoesAbertas.contains(idSessao)) {
			
			if (user != null) { //verificar para nao dar null pointer
				retorno = user.getPerfilmusical().getPerfil(); //isso pq o get perfil do usuario retorna o objeto perfil musical ja o metodo pra fachada tem que retornar o list preferi fazer assim pra deixar a informacao encapsulada ;)
			}
		}*/
		
		return ordemTimeLine(retorno);
	}
	
	
	/**
	 * Buscar usuario pelo id da sessao e retorna-lo
	 * @param idSessao
	 * @return
	 */
	private Usuario buscaUsuarioPorIDSessao(String idSessao) {
		Usuario usuarioRetornado = null;
		for (Usuario user : users) {
			if (user.getIdSessao().equals(idSessao)) { //se forem iguais, logo o usuario esta logado dado que se ele estiver deslogado o id sera vazio
				usuarioRetornado = user;
			}
		}
		return usuarioRetornado;
	}


	/**
	 * Metodo utiizado para postar um somno sistema
	 * @param idSessao
	 * @param link do som
	 * @param data de criacao
	 * @return idDoSom
	 * @throws Exception caso o link passado seja vazio ou a data passada seja invalida
	 */
	public  String postarSom(String idSessao, String link, String data) throws Exception{
		
		if (Util.checaEhVazio(link)) {
			throw new SomException();
		}else if (Util.dataInvalida(data)) {
			throw new DataException();
		}else if (!Util.linkEhValido(link)) {
			throw new SomException();
		}
		
		
		Usuario usuario = buscaUsuarioPorIDSessao(idSessao);
		String idDoSom = usuario.postarSom(link, data);
		
		sonsPostados.add(idDoSom);

		return idDoSom;
	} 
	

	/**
	 * Retorna o atributo do som desejado.
	 * @param idDoSom
	 * @param atributo desejado
	 * @return o atributo desejado
	 * @throws Exception caso o idDoSom passado seja nulo ou vazio, ou o atributo desejado nao seja um atributo disponivel
	 */
	public String getAtributoSom(String idDoSom, String atributo) throws Exception {
		if (Util.checaEhVazio(idDoSom)) {
			throw new SomException();
		}else if (Util.checaEhVazio(atributo)) { //vazio ou null
			throw new Exception("Atributo inválido");
		}else if (! (atributo.equals("dataCriacao"))) {
			throw new Exception("Atributo inexistente");
		}
		
		
		Som som = pegaSomPostado(idDoSom);
		String retorno = "";
		if (atributo.equals("dataCriacao")) {
			retorno = som.getData();
		}
		return retorno;
	}
	


	/**
	 * Retona um som que tenha sido postado no sistema
	 * @param idDoSom
	 * @return o som postado
	 */
	private Som pegaSomPostado(String idDoSom) {
		for (String idSessao : sessoesAbertas) {
			Usuario us = buscaUsuarioPorIDSessao(idSessao);
			if (us.getPerfilmusical().getPerfil().contains(idDoSom)) {
				return us.getSomPostado(idDoSom);
			}
		}
		return null;
	}


	/**
	 * Metodo cria(cadastra) um novo usuario no sistema.
	 * @param login
	 * @param senha
	 * @param nome
	 * @param email
	 * @throws Exception- caso o algum atributo passado seja invalido ou o email e/ou login passados ja estejam
	 * cadastrados no sistema com outro usuario.
	*/
	public void criarUsuario(String login, String senha, String nome, String email) throws Exception  {
		//verifico se algum atributo passado eh invalido (null ou vazio)
		String atributoInvalido = Util.verificaAtributosInvalidos(login, nome, email);
		if (atributoInvalido != "") {
				throw new LoginException(atributoInvalido + "inválido");
		}
		
		//verifico se o usuario ja eh cadastrado
		String atributoJaUsado = usuarioJaEhCadastrado(email, login); 
		if (atributoJaUsado != "") {
			throw new LoginException("Já existe um usuário com este " + atributoJaUsado);
		}
		
		Usuario usuario = new Usuario(login, nome, email, senha);
		users.add(usuario);
		
		
	}
	
	/**
	 * Verifica se o usuario ja eh cadastrado no sistema atraves de seu login e/ou email
	 * @param email
	 * @param login
	 * @return string com o atributo que ja esta cadastrado no sistema.
	 */
	private String usuarioJaEhCadastrado(String email, String login){
		String atributoJaUsado = "";
		if (buscarUsuarioPorLogin(login) != null) { //significa q ele ja existe
			atributoJaUsado = "login";
		}else if (buscaUsuarioPorEmail(email) != null) {
			atributoJaUsado = "email";
		}
		return atributoJaUsado;
	}
	

	/**
	 * Abrir a sessao de um usuario no sistema
	 * @param login
	 * @param senha
	 * @return idSessao
	 * @throws LoginException - caso o login passado seja vazio ou nulo, ou o login passado nao esteja cadastrado 
	 * o sistema ou entao a senha passada nao coincida com a que esta cadastrada com o login em questao.
	 */
	public String abrirSessao(String login, String senha) throws Exception {
		
		String idSessao = "";
		Usuario usuarioTemp = buscarUsuarioPorLogin(login);
		//verifica se o login esta cadastrado
		if (Util.checaEhVazio(login)) {
			throw new LoginException("Login inválido");
		}else if (usuarioTemp == null) {
			throw new UsuarioInexistenteException();
		}else if (usuarioTemp.verificaSenha(senha)) { //verifica se a senha do usuario encontrado eh a mesma da senha passada ao metodo
			idSessao = usuarioTemp.abrirSessao();
			/*
			 * Fiz assim porque nao vi sentido em o usuario receber o login e verificar se este esta cadastrado no sistema
			 * ja que quem tem a informacao dos usuarios cadastrados no sistema eh apenas
			 * o sistema, nesse caso usuario verifica se sua senha eh igual a recebida pelo metodo
			 * e caso sim o abrirSessao de usuario apenas ira gerar o id
			 */
			sessoesAbertas.add(idSessao);
		} else {
			throw new LoginException("Login inválido");
		}

		return idSessao;

	}
	
	
	 /**
	  * Busca na lista dos usuarios cadastrados no sistema o usuario pelo seu login
	  * 
	  * @param login
	  * @return Usuario se ele for cadastrado ou uma instancia nula caso contrario
	  */
	public Usuario buscarUsuarioPorLogin(String login) {
		Usuario retorno = null;
		for (Usuario usuario : users) {
			if (usuario.getLogin().equals(login)) {
				retorno = usuario;
			}
		}

		return retorno;
	}

	/**
	 * Busca na lista dos usuarios cadastrados no sistema o usuario pelo seu email
	 * @param email
	 * @return Usuario se ele for cadastrado ou uma instancia nula caso contrario
	 */
	private Usuario buscaUsuarioPorEmail(String email) {
		Usuario retorno = null;
		for (Usuario usuario : users) {
			if (usuario.getEmail().equals(email)) {
				retorno = usuario;
			}
		}
		return retorno;
	}
	
	
	/**
	 * Retorna o atributo desejado do usuario.
	 * @param login
	 * @param atributo desejado
	 * @return string com a informacao desejada
	 * @throws Exception- caso o login ou o atributo sejam nulos ou vazios, ou o atributo desejado seja inexistente 
	 * ou o login passado nao esteja cadastrado no sistema.
	 */
	public String getAtributoUsuario(String login, String atributo) throws Exception {
		String retorno = "";
		if (Util.checaEhVazio(login)) {
			throw new LoginException("Login inválido");
		}
		
		Usuario user = buscarUsuarioPorLogin(login);
		
		if (user == null) {
			throw new UsuarioInexistenteException();
		}
		
		if (Util.checaEhVazio(atributo)) {
			throw new LoginException("Atributo inválido");
		}if (! (atributo.equals("nome") || atributo.equals("email"))) {
			throw new LoginException("Atributo inexistente");
		}
		
		else if (atributo.equals("nome")) {
			retorno = user.getNome();
		}else if (atributo.equals("email")) {
			retorno = user.getEmail();
		}
		return retorno;
	}

	
	
	
	
	/**
	 * Retorna o id do usuario
	 * @param idSessao
	 * @return - o id do usuario
	 */
	public String getIDUsuario(String idSessao){
		//estou considerando como id do usuario no sistema seu login que eh unico
		Usuario user = buscaUsuarioPorIDSessao(idSessao);
		return user.getID();
	}
	
	
	

	/**
	 * Retorna uma lista contendo os ids dos usuarios que 
	 * sao fontes de sons do usuario
	 * @param idSessao
	 * @return - lista das fontes de sons do usuario
	 * @throws Exception - caso o id da sessao passada seja invalido ou inexistente
	 */
	public List<String> getFontesDeSons(String idSessao) throws Exception {
		
		Usuario user = buscaUsuarioPorIDSessao(idSessao);
		
		if (Util.checaEhVazio(idSessao)) {
			throw new SessaoInvalidaException();
		}else if (user == null) {
			throw new SessaoInexistenteException();
		}
		
		return user.getFonte(); //pq o usuario retorna o objeto FonteDeSons portanto sendo preciso fazer getFonte novamente a fim de obter a lista com os ids dos sons
										   //eh feito dessa forma para o som nao precisar ter muitos atributos e ficar pouco coeso
	}


	
	/**
	 * Retorna os sons postados por seus amigos no sistema
	 * @param idSessao - id da sessao do usuario
	 * @return - a lista dos sons postados por seus amigos
	 * @throws Exception - caso o id da sessao passado seja invalido ou inexistente. 
	 */
	public List<String> getVisaoDosSons(String idSessao) throws Exception {
		
		Usuario user = buscaUsuarioPorIDSessao(idSessao);
		
		if (Util.checaEhVazio(idSessao)) {
			throw new SessaoInvalidaException();
		}else if (user == null) {
			throw new SessaoInexistenteException();
		}
		
		
		
		//lista com os usuarios que sao seus amigos
		List<String> fontesDeSom = user.getFonte(); //id dos usuarios que sao suas fontes de som
		
		List<String> visaoDeSons = new ArrayList<String>();
		
		for (Usuario usuario : users) {
			for (int i = 0; i < fontesDeSom.size(); i++) {
				if (fontesDeSom.get(i).equals(usuario.getID())) {
					visaoDeSons.addAll(usuario.getPerfilmusical().getPerfil());
				}
			}
		}
		
						
		return ordemTimeLine(visaoDeSons);
		//return visaoDeSons;
	}
	
	/**
	 * Ordena por ordem de postagem mais recente
	 * @param lista
	 * @return
	 */
	private List<String> ordemTimeLine(List<String> lista ){
		//List<String> retornoCopy = new ArrayList<String>(); //coloca na ordem de postagem - pilha
		//retornoCopy.addAll(lista);
		Collections.reverse(lista);
		return lista;
		
		/*List<String> retornoCopy = new ArrayList<String>(); //coloca na ordem de postagem - pilha
		retornoCopy.addAll(lista);
		Collections.reverse(retornoCopy);
		return retornoCopy;	*/
	}
	
	
	
	/**
	 * Retorna a quantidade de seguidores que um usuario tem na rede social
	 * @param idSessao
	 * @return - quantidade de seguidores
	 * @throws Exception - caso a sessao passada seja invalida ou inexistente
	 */
	public int getNumeroDeSeguidores(String idSessao) throws Exception {
		if (Util.checaEhVazio(idSessao)) {
			throw new SessaoInvalidaException();
		}
		
		Usuario user = buscaUsuarioPorIDSessao(idSessao);
		
		if (user == null) {
			throw new SessaoInexistenteException();
		}
		return user.getNumeroDeSeguidores();
	}

	
	/**
	 * Usuario resolve seguir outro na rede social
	 * @param idSessao - do usuario
	 * @param login - do usuario a ser seguido
	 * @throws Exception - caso a sessao ou o login sejam invalidos ou inexistentes.
	 */
	public void seguirUsuario(String idSessao, String login) throws Exception {
		
		
		if (Util.checaEhVazio(idSessao)) {
			throw new SessaoInvalidaException();
		}else if (Util.checaEhVazio(login)) {
			throw new Exception("Login inválido");
		}else if (!sessoesAbertas.contains(idSessao)) { //se o id passado nao esta na lista
			throw new SessaoInexistenteException();
		}else if (buscarUsuarioPorLogin(login) == null) {
			throw new Exception("Login inexistente");
		}
		
		Usuario user = buscaUsuarioPorIDSessao(idSessao);
		
		if (user.getLogin().equals(login)) { //quer seguir a si mesmo 
			throw new Exception("Login inválido");
		}
		
		
		
		Usuario userSeguido = buscarUsuarioPorLogin(login);
		user.seguirUsuario(userSeguido.getID()); //usuario segue outro
		
		userSeguido.addSeguidor(user.getID());	//outro adiciona o usuario em sua lista de seguidores
	}
	
	/**
	 * Retorna a lista de seguidores de um usuario
	 * @param idSessao
	 * @return a lista de seguidores do usuario
	 * @throws Exception - caso a sessao seja invalida ou inexistente
	 */
	public List<String> getListaDeSeguidores(String idSessao) throws Exception {
		if (Util.checaEhVazio(idSessao)) {
			throw new SessaoInvalidaException();
		}
		
		Usuario user = buscaUsuarioPorIDSessao(idSessao);
		
		if (user == null) {
			throw new SessaoInexistenteException();
		}
		List<String> seguidores = user.getSeguidores();
		
		/*if (seguidores.size() > 0) {
			Collections.sort(seguidores , new Comparator<String>(){
				public int compare(String o1, String o2) 
				{
					String c1 = o1.toString();  
					String c2 = o2.toString(); 
				return c1.compareTo(c2);
				}
				}
			);
		}*/
		
		
		return seguidores;	
		
	}


	/**
	 * Retorna a lista de sons favoritos de um usuario
	 * @param idSessao
	 * @return - lista de sons favoritos do usuario
	 * @throws Exception - caso a sessao passada seja invalida ou inexistente
	 */
	public List<String> getSonsFavoritos(String idSessao) throws Exception{
		
		Usuario user = buscaUsuarioPorIDSessao(idSessao);
		if (Util.checaEhVazio(idSessao)) {
			throw new SessaoInvalidaException();
		}else if (user == null) {
			throw new SessaoInexistenteException();
		}
		
		return user.getSonsFavoritos();
	}


	/**
	 * Retorna  o feedExtra de um usuario, ou seja, os sons favoritos 
	 * das pessoas quem o usuario segue.
	 * @param idSessao
	 * @return - feed extra
	 * @throws Exception - caso a sessao passada seja invalida ou inexistente
	 */
	public  List<String> getFeedExtra(String idSessao) throws Exception {
		
		Usuario user = buscaUsuarioPorIDSessao(idSessao);
		
		if (Util.checaEhVazio(idSessao)) {
			throw new SessaoInvalidaException();
		}else if (user == null) {
			throw new SessaoInexistenteException();
		}
		
		
		return user.getFeedExtra();
	}
	
	
	/**
	 * Favoritar um som na rede social
	 * @param idSessao do usuario
	 * @param idSom a ser favoritado
	 * @throws Exception - caso a sessao ou o som seja invalido ou inexistente
	 */
	public void favoritarSom(String idSessao, String idSom) throws Exception{
		
		Usuario user = buscaUsuarioPorIDSessao(idSessao);
		
		if (Util.checaEhVazio(idSessao)) {
			throw new SessaoInvalidaException();
		}else if (Util.checaEhVazio(idSom)) {
			throw new SomException();
		}else if (user == null) {
			throw new SessaoInexistenteException();
		}else if (!sonsPostados.contains(idSom)) { //o id do som passado nao existe no sistema
			throw new SomInexistenteException();
		}
		
		user.addSomFavorito(idSom);
		Som som = pegaSomPostado(idSom);
		som.addFavoritada(); //conta quantas favoritadas o som teve pra depois ordenar pela regra2
		atualizarFeedExtraDosAmigos(user, idSom);
		
	}


	/**
	 * Caso um usuario favorite um som na rede social, eh preciso atualizar 
	 * o feedExtra das pessoas que o seguem.
	 * @param user - que favoritou um novo som
	 * @param idSom - favoritado
	 */
	private void atualizarFeedExtraDosAmigos(Usuario user, String idSom) {
		List<String> seguidores = user.getSeguidores();
		for (String seguidor : seguidores) {
			Usuario user2 = buscarUsuarioPorLogin(seguidor);
			user2.addFeedExtra(idSom);
		}
		
	}
	
	/**
	 * Retorna o usuario correspondente ao idSessao passado
	 * @param idSessao
	 * @return - usuario correspondente ao idSessao passado
	 */
	public Usuario getUsuarioIdSessao(String idSessao){
		return buscaUsuarioPorIDSessao(idSessao);
		
	}
	
	
	/**
	 * Retorna a descricao da regra1 de ordenacao
	 * @return a descricao da regra1 de ordenacao 
	 */
	public String getFirstCompositionRule(){
		RegraDeOrdenacao r1 = new Regra1();
		return r1.getDescricaoDaRegra();
	}
	
	
	/**
	 * Retorna a descricao da regra2 de ordenacao
	 * @return a descricao da regra2 de ordenacao 
	 */
	public String getSecondCompositionRule(){
		RegraDeOrdenacao r2 = new Regra2();
		return r2.getDescricaoDaRegra();
	}

	/**
	 * Retorna a descricao da regra3 de ordenacao
	 * @return a descricao da regra3 de ordenacao 
	 */
	public String getThirdCompositionRule(){
		RegraDeOrdenacao r3 = new Regra3();
		return r3.getDescricaoDaRegra();
	}


	/**
	 * Retorna o feedPrincipal do usuario, contendo todos os sons 
	 * postados pelas pessoas que ele segue no sistema
	 * @param idSessao do usuario
	 * @return lista com os sons de seu feed principal
	 * @throws Exception caso a sessao passada seja nula ou invalida
	 */
	public List<String> getFeedPrincipal(String idSessao) throws Exception {
		Usuario user = buscaUsuarioPorIDSessao(idSessao);

		if (Util.checaEhVazio(idSessao)) {
			throw new SessaoInvalidaException();
		}else if (user == null) {
			throw new SessaoInexistenteException();
		}
		return user.getFeedPrincipal(); //em usuario ele ja pega a lista de ids com o feed principal
	}


	/**
	 * Troca a regra de ordenacao do feedPrincipal
	 * @param idSessao do usuario
	 * @param regra 
	 * @throws Exception caso a sessao ou regra sejam invalidas ou nulas
	 */
	public void setRegraDeOrdenacao(String idSessao, String regra) throws Exception {
		
		boolean regraInexistente = Util.verificaSeRegraEhInexistente(regra); //nao sei onde colocar, enao vai pro util
		
		if (Util.checaEhVazio(idSessao)) {
			throw new SessaoInvalidaException();
		}else if (!sessoesAbertas.contains(idSessao)) {
			throw new SessaoInexistenteException();
		}else if (Util.checaEhVazio(regra)) {
			throw new RegraInvalidaException();
		}else if (regraInexistente) {
			throw new RegraInexistenteException();
		}
		
		Usuario user = buscaUsuarioPorIDSessao(idSessao);
		user.setRegraDeOrdenacao(regra);
	}

	/**
	 * Retorna o numero de sons favoritos entre dois usuarios
	 * @param idSessao do usuario
	 * @param idUsuario de outro usuario
	 * @return o numero de sons favoritos em comum ao dois
	 */
	public int getNumFavoritosEmComum(String idSessao, String idUsuario) {
		Usuario user1 = buscaUsuarioPorIDSessao(idSessao);
		Usuario user2 = buscarUsuarioPorLogin(idUsuario);
		
		return user1.getNumFavoritosEmComum(user2);
	}

	/**
	 * Retorna o numero de pessoas seguidas em comum entre dois 
	 * usuarios, ou seja, numero de fontes de som em comum
	 * @param idSessao do usuario
	 * @param idUsuario de outro usuario
	 * @return o numero de fontes em comum entre os dois
	 */
	public int getNumFontesEmComum(String idSessao, String idUsuario) {
		Usuario user1 = buscaUsuarioPorIDSessao(idSessao);
		Usuario user2 = buscarUsuarioPorLogin(idUsuario);
		return user1.getNumFontesEmComum(user2);
	}
	
	
	/**
	 * Retorna uma lista com as pessoas (fontes de som) recomendadas 
	 * para um usuario
	 * @param idSessao do usuario
	 * @return uma lista com fontes de sons recomendadas
	 */
	public List<String> getFontesDeSonsRecomendadas(String idSessao) {
		Usuario userLogado = buscaUsuarioPorIDSessao(idSessao);
		List<String> usuariosQuePodemSerRecomendados = new ArrayList<String>();
		
		if((userLogado.getFonte().size() == 0) && (userLogado.getSonsFavoritos().size() == 0)){
			//recomendar as fontes que mais têm sons favoritados
			//FALTA IMPLEMENTAR
		}else{
			for (Usuario user : users) {
				if (!userLogado.getFonte().contains(user.getID())) {//se o usuario ja nao esta na minha fonte de sons
					usuariosQuePodemSerRecomendados.add(user.getID());
			}
			}
		}
		
		//aqui tinha q ser o integer primeiro depois o satring, talvez
		//usar outra estrutura de dados q nao seja mapa
		Map<Integer, String> recomendacao = new HashMap<Integer, String>();
		
		for (String user : usuariosQuePodemSerRecomendados) {
			Usuario usuarioRecomendado = buscarUsuarioPorLogin(user);
			Integer grauDeAfinidade = new Integer(userLogado.getNumFavoritosEmComum(usuarioRecomendado) + userLogado.getNumFontesEmComum(usuarioRecomendado));
			recomendacao.put(grauDeAfinidade, user);
		}
		
		
		List<String> recomendados = new ArrayList<String>();
		int[] maisRecomendados = getMaisRecomendados(recomendacao.keySet());
		for (int i = 0; i < maisRecomendados.length; i++) {
			String usuarioRecomendado = recomendacao.get(maisRecomendados[i]);
			recomendados.add(usuarioRecomendado);
		}
		
		return recomendados;
	}


	/**
	 * Ordena as chaves d maior para a menor, para pegar os 
	 * mais recomendados
	 * @param keySet
	 * @return
	 */
	private int[] getMaisRecomendados(Set<Integer> keySet) {
		int[] retorno = new int[keySet.size()];
		 Object[] valores = keySet.toArray();
		
		 
		int[] retorno2 = retorno;
		for(int i = 0 ;i <= valores.length - 1;i++ ){
			for(int j = 0; j <= valores.length - i-1;j++){
				
				int val1 = Integer.parseInt(valores[j].toString());
				int val2 = Integer.parseInt(valores[j+1].toString());
				
				if(val1 < val2){
					int temp = Integer.parseInt(valores[j].toString());
					valores[j] = valores[j+1];
					valores[j+1] = temp;
					
					retorno2[j] = Integer.parseInt(valores[j+1].toString());
					retorno2[j+1] = temp;
				}
			}
		}
		return retorno2;
	}


	/**
	 * Usuario pode criar listas de usuarios
	 * @param idSessao do usuario
	 * @param nomeDaLista a ser criada
	 * @return id de identificacao da lista
	 * @throws Exception caso a sessao ou o nome da lista sejam invalidos ou nulos
	 */
	public String criarLista(String idSessao, String nomeDaLista) throws Exception {
		Usuario user = buscaUsuarioPorIDSessao(idSessao);
		
		if (Util.checaEhVazio(idSessao)) {
			throw new SessaoInvalidaException();
		}else if (!sessoesAbertas.contains(idSessao)) {
			throw new SessaoInexistenteException();
		}else if (Util.checaEhVazio(nomeDaLista)) {
			throw new Exception("Nome inválido");
		}else if (user.getNomeDasListas().contains(nomeDaLista)) {
			throw new Exception("Nome já escolhido");
		}
		
		return user.criarLista(nomeDaLista);
	}


	/**
	 * Usuario adiciona novos usuarios em suas lista de usuarios
	 * @param idSessao do usuario
	 * @param nomeDaLista onde o novo usuario sera adicionado
	 * @param idUsuario que sera adicionado
	 * @throws Exception caso alguma das informacoes passadas seja nula ou invalida
	 */
	public void adicionarUsuario(String idSessao, String nomeDaLista,
			String idUsuario) throws Exception {
		Usuario user = buscaUsuarioPorIDSessao(idSessao);
		
		if (Util.checaEhVazio(idSessao)) {
			throw new SessaoInvalidaException();
		}else if (!sessoesAbertas.contains(idSessao)) {
			throw new SessaoInexistenteException();
		}else if ((Util.checaEhVazio(idUsuario)) || (buscarUsuarioPorLogin(idUsuario) == null)) {
			throw new UsuarioInvalidoException();
		}else if (Util.checaEhVazio(nomeDaLista)) {
			throw new ListaInvalidaException();
		}else if (! user.getNomeDasListas().contains(nomeDaLista)) {
			throw new ListaInvalidaException();
		}else if (user.getID().equals(idUsuario)) {
			throw new Exception("Usuário não pode adicionar-se a própria lista");
		}else if (user.getLista(nomeDaLista).contains(idUsuario)) {
			throw new Exception("Usuário já existe nesta lista");
		}
		
		user.adicionarUsuario(nomeDaLista, idUsuario);
		
	}


	/**
	 * Retorna uma lista de sons postados pelos usuarios que o usuario da 
	 * sessao adicionou em uma de suas listas
	 * @param idSessao do usuario
	 * @param nomeDaLista a qual voce quer as informacoes
	 * @return lista com os sons postados pelos usuarios adicionados a lista
	 * @throws Exception caso o idSessao ou nomeDaLista sejam nulos ou invalidos
	 */
	public List<String> getSonsEmLista(String idSessao, String nomeDaLista) throws Exception {
		Usuario user = buscaUsuarioPorIDSessao(idSessao);
		
		if (Util.checaEhVazio(idSessao)) {
			throw new SessaoInvalidaException();
		}else if (!sessoesAbertas.contains(idSessao)) {
			throw new SessaoInexistenteException();
		}else if ((Util.checaEhVazio(nomeDaLista))  || (user.getLista(nomeDaLista) == null)) {
			throw new ListaInvalidaException();
		}
		
	
		List<String> amigosEmLista = user.getLista(nomeDaLista);
		
		List<String> sons = new ArrayList<String>();
		for (int i = 0; i < amigosEmLista.size(); i++) {
			Usuario us = buscarUsuarioPorLogin(amigosEmLista.get(i));
			List<String> perfil = us.getPerfilmusical().getPerfil();
			for(String som : ordemTimeLine(perfil)){//coloca todos os sons do usuario na lista
				sons.add(som);
			}
		}
		
		return sons;
	}

	
	

	
	
	
}
