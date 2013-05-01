package beans;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import classes.PlayIt;
import classes.Usuario;

@SessionScoped
@ManagedBean(name = "Usuario")
public class UsuarioBean {
	private String login;
	private String senha;
	private String confirmarSenha;
	private String nome;
	private String email;
	private PlayIt sistema;
	private String idSessao;
	private Usuario usuarioLogado;
	private String seguir;
	private String link;
	private List<String> seguindo;
	private List<String> seguidores;
	private List<String> perfilMusical;
	private List<String> feedExtra;
	private List<String> feedPrincipal;
	private List<String> favoritos;

	@PostConstruct
	public void init(){
		this.sistema = PlayIt.getInstance();
	}
	
	public String logout(){
		sistema.encerrarSistema();
		System.out.println("logout");
		return "logout.xhtml";
	}
	
	public String voltar(){
		return "index.xhtml";
	}
	
	public String verificaLogin() {
		FacesContext context = FacesContext.getCurrentInstance();
		String page = "";
		if (login == null || senha == null || login.length() == 0 || senha.length() == 0) {
			context.addMessage(null, new FacesMessage("Digite login e senha para entrar"));
		} else {
			try {
				setIdSessao(sistema.abrirSessao(login, senha));
				putUserInSession(this.sistema.getUsuarioIdSessao(getSessionID()));
				page = "timeline.xhtml";
			} catch (Exception e) {
				context.addMessage(null, new FacesMessage(e.getMessage()));
			}
		}
		return page;
	}

	public String criarConta(){
		FacesContext context = FacesContext.getCurrentInstance();
		String page = ""; 
		if (nome == null || login == null || senha == null || confirmarSenha == null || email == null
			|| nome.length() == 0 || login.length() == 0 || senha.length() == 0 || confirmarSenha.length() == 0 || email.length() == 0){ 
			context.addMessage(null, new FacesMessage("Todos os campos são obrigatórios."));
		}else if (!(senha.equals(confirmarSenha))){ 
			context.addMessage(null, new FacesMessage("As senhas não correspondem"));
		}else{ 
			try{
				System.out.println("registerbean.createAccount "+ login +"/" + nome +"/"+ email +"/"+ senha);
				sistema.criarUsuario(login, senha, nome, email);
				putUserInSession(this.sistema.getUsuarioIdSessao(getSessionID()));//*****************************
				sistema.abrirSessao(login, senha); //logar 
				context.addMessage(null,new FacesMessage("Conta criada com sucesso"));
			}catch (Exception e){
				context.addMessage(null, new FacesMessage(e.getMessage()));
			}
		}
		return page;
	}

	public String seguirUsuario(){
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			sistema.seguirUsuario(getIdSessao(), seguir);
		} catch (Exception e) {
			context.addMessage(null, new FacesMessage(e.getMessage()));
		}
		return "";
	}
	
	public String postarSom(){
		FacesContext context = FacesContext.getCurrentInstance();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try {
			sistema.postarSom(getIdSessao(), getLink(), format.format(new Date()));
		} catch (Exception e) {
			context.addMessage(null, new FacesMessage(e.getMessage()));
		}
		return "";
	}
	
	public List<String> getSeguindo(){
		return sistema.buscarUsuarioPorLogin(login).getFonte();
	}
	
	public List<String> getSegudores(){
		return sistema.buscarUsuarioPorLogin(login).getSeguidores();
	}
	
	public List<String> getPerfilMusical(){
		return sistema.getPerfilMusical(sistema.buscarUsuarioPorLogin(login).getIdSessao());
	}
	
	public void setPerfilMusical(List<String> perfilMusical) {
		this.perfilMusical = perfilMusical;
	}
	
	public List<String> getFeedExtra(){
		return sistema.buscarUsuarioPorLogin(login).getFeedExtra();
	}
	
	public List<String> getFeedPrincipal(){
		return sistema.buscarUsuarioPorLogin(login).getFeedPrincipal();
	}
	
	public List<String> getFavoritos(){
		return sistema.buscarUsuarioPorLogin(login).getSonsFavoritos();
	}
	
	public void favoritar(String idSom){
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			sistema.favoritarSom(sistema.buscarUsuarioPorLogin(login).getIdSessao(), idSom);
		} catch (Exception e) {
			context.addMessage(null, new FacesMessage(e.getMessage()));
		}
	}
	
	public void setFavoritos(List<String> favoritos) {
		this.favoritos = favoritos;
	}

	public void setFeedPrincipal(List<String> feedPrincipal) {
		this.feedPrincipal = feedPrincipal;
	}

	public void setFeedExtra(List<String> feedExtra) {
		this.feedExtra = feedExtra;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		Usuario user = sistema.buscarUsuarioPorLogin(login);
		if (user == null){
			return "";
		}else{
			return user.getNome();
		}
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		Usuario user = sistema.buscarUsuarioPorLogin(login);
		if (user == null){
			return "";
		}else{
			return user.getEmail();
		}
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getIdSessao() {
		Usuario user = sistema.buscarUsuarioPorLogin(login);
		if (user == null){
			return "";
		}else{
			return user.getIdSessao();
		}
	}

	public void setIdSessao(String idSessao) {
		this.idSessao = idSessao;
	}

	public String getConfirmarSenha() {
		return confirmarSenha;
	}

	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
	}
	private void putInSession(String objetoChave, Object obj) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(objetoChave, obj);
	}
	
	public void putUserInSession(Usuario user) {
		putInSession("usuario_logado", user);
	}
	
	public Usuario getUsuarioLogado() {
		Usuario usuarioLogado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario_logado");
		System.out.println(">>>>"+usuarioLogado.getNome());
		return usuarioLogado; 
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		//this.usuarioLogado = usuarioLogado;
	}
	
	public String getSessionID() {
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
		return session.getId();
	}

	public String getSeguir() {
		return seguir;
	}

	public void setSeguir(String seguir) {
		this.seguir = seguir;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	public void apagarLink(){
		setLink("");
	}

	public void setSeguindo(List<String> seguindo) {
		this.seguindo = seguindo;
	}

	public List<String> getSeguidores() {
		return seguidores;
	}

	public void setSeguidores(List<String> seguidores) {
		this.seguidores = seguidores;
	}

}
