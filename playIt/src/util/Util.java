package util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.Set;
import classes.Regra1;
import classes.Regra2;
import classes.Regra3;
import classes.RegraDeOrdenacao;
import classes.Som;
import classes.Usuario;

/**
 * Metodos uteis para manipulacao de dados em todo o sistema da rede social
 * @author Flavia Gangorra, Ana Lucia, Diego Tavares
 *
 */
public class Util {
	
	private static Calendar calendario = new GregorianCalendar();

	/**
	 * Gera o idDaSessao na hora que o usuario loga no sistema
	 * @return idSessao
	 */
	public static String gerarIdSessao(String nome){
		int tamanhoDoNum = 3; //o q me da 10x10x10	= 1000 possibilidades de id
		String idGerado = "";
		Random rand = new Random();
		for (int i = 0; i < tamanhoDoNum; i++) {
			idGerado = rand.nextInt(10) + "";
		}
		return nome+idGerado;
	}
	
	/**
	 * Metodo que gera o idDoSom quando um novo som eh postado no sistema
	 * @return - idDoSom
	 */
	public static  String gerarIdDoSom(String login) {
		String idGerado = "Som";
		int tamanhoDoNum = 3; //o q me da 10x10x10	= 1000 possibilidades de id
		Random rand = new Random();
		for (int i = 0; i < tamanhoDoNum; i++) {
			idGerado = rand.nextInt(1000) + "";
		}
		return idGerado;
		//return login + calendario.getTimeInMillis()+ idGerado;
	}
	
	/**
	 * Verifica se algum atributo passado na hora de criar o usuario eh invalido, ou seja, vazio ou nulo
	 * @param login
	 * @param nome
	 * @param email
	 * @return - string dizendo qual dos atributos passados eh invalido.
	 */
	public static String verificaAtributosInvalidos(String login, String nome, String email){
		String atributoInvalido = "";
		if (login == null || login.equals("")) {
			atributoInvalido = "Login ";
		}else if (nome == null || nome.equals("")) {
			atributoInvalido = "Nome ";
		}else if (email == null || email.equals("")) {
			atributoInvalido = "Email ";
		}
		
		return atributoInvalido;
	}
	
	/**
	 * Verifica se o link passado na hora de postar o som eh valido
	 * @param link
	 * @return verdadeiro ou falso
	 */
	public static boolean linkEhValido(String link) {
		if (link.contains("http://") || link.contains("https://")) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * Verifica se a data passada eh invalida, ou seja, se a data ja passou ou eh uma data absurda
	 * @param data
	 * @return verdadeiro ou falso
	 * @throws Exception caso a data seja invalida, n�o sendo possivel sua convers�o para uma data valida,
	 * ou se a data ja tenha passado
	 */
	public static boolean dataInvalida(String data) throws Exception{
		boolean retorno = false;
		
		Calendar calendario = new GregorianCalendar();
		String[] listaData = data.split("/");
		int dia = 0, mes = 0, ano = 0;
		try {
			dia = Integer.parseInt(listaData[0]);
			mes = Integer.parseInt(listaData[1]);
			ano = Integer.parseInt(listaData[2]);
		} catch (Exception e) {
			retorno = true;
			throw new Exception("Data de Cria��o inv�lida");
		}
		calendario.set(ano, (mes - 1), dia);
		
		if (!converteCalendarEmStringData(calendario).equals(data)) {
			retorno = true;
			throw new Exception("Data de Criacao invalida");
		}
		if (jahPassou(calendario)) {
			retorno = true;
			throw new Exception("Data de Criacao invalida");
		}
		return retorno;
	}
	
	/**
	 * Converte uma data do tipo Calendar em string para comparar com a data passada ao sistema
	 * na hora de postar o som a fim de verificar se esta esta em um formato valido
	 * @param calendario - data do tipo Calendar
	 * @return - a data no formato string
	 */
	public static String converteCalendarEmStringData(Calendar calendario) {
		String retorno = "";

		retorno = String.format("%02d", calendario.get(calendario.DATE)) + "/"
				+ String.format("%02d", (calendario.get(calendario.MONTH) + 1))
				+ "/" + String.format("%02d", calendario.get(calendario.YEAR));

		return retorno;
	}
	
	
	/**
	 * Converter uma data em formato de string para o formato
	 * calendar (calendario) 
	 * @param data
	 * @return a data em forma de calendar
	 */
	public static Calendar converteStringDataEmCalendar(String data){
		Calendar calendario = new GregorianCalendar();
		String[] listaData = data.split("/");
		int dia = 0, mes = 0, ano = 0;
		
		/*nao preciso fazer try catch como esse metodo eh utilizado
		 *na ordenacao dos sons pelas regras, sabemos que se o som foi postado
		 * sua data estava correta */
		dia = Integer.parseInt(listaData[0]);
		mes = Integer.parseInt(listaData[1]);
		ano = Integer.parseInt(listaData[2]);
		
		calendario.set(ano, (mes - 1), dia);
		return calendario;
		
	}
	

	/**
	 * Verifica se a data ja passou, esta no passado
	 * @param calendario - uma data do tipo Calendar
	 * @return verdadeiro ou falso
	 */
	private static boolean jahPassou(Calendar calendario) {
		GregorianCalendar calendarioAtual = new GregorianCalendar();
		return calendarioAtual.getTime().after(calendario.getTime());
	}
	
	
	/**Verifica se algum atributo digitado eh vazio
	 * 
	 * @param string
	 * @return verdadeiro ou falso
	 */
	public static boolean checaEhVazio(String str) {
		return str == null || str.equals("");
	}

	/**
	 * Transforma um conjunto set em uma lista list
	 * @param <T>
	 * @param set
	 * @return
	 */
	public static <T> List<T> transformaSetEmList(Set<T> set) {
		String[] chaves = set.toArray(new String[0]);
		List<T> retorno = new ArrayList<T>();
		for (int i = 0; i < chaves.length; i++) {
			retorno.add(i, (T) chaves[i]);
		}
		
		return retorno;
	}

	
	/**
	 * clona uma list de sons para que na hora de ordenar nao seja modificada
	 * a lista original passada como parametro ao metodo
	 * @param sons
	 * @return clone da list
	 */
	public static List<Som> cloneDeList(List<Som> sons) {
		List<Som> retorno = new ArrayList<Som>();
		for (int i = 0; i < sons.size(); i++) {
			retorno.add(i, sons.get(i));
		}
		return retorno;
	}
	
	
	
	public static List<String> getIdsDosSons(List<Som> sons) {
		List<String> ids = new ArrayList<String>();
		for (int i = 0; i < sons.size(); i++) {
			ids.add(i, sons.get(i).getIdSom());
		}
		return ids;
	}

	public static List<String> cloneDeListString(List<String> lista) {
		List<String> retorno = new ArrayList<String>();
		for (int i = 0; i < lista.size(); i++) {
			retorno.add(i, lista.get(i));
		}
		return retorno;
	
	}

	public static boolean verificaSeRegraEhInexistente(String regra) {
		RegraDeOrdenacao r1 = new Regra1(), r2 = new Regra2(), r3 = new Regra3();
		boolean retorno = ((regra.equals(r1.getDescricaoDaRegra()))) || ((regra.equals(r2.getDescricaoDaRegra()))) || ((regra.equals(r3.getDescricaoDaRegra())));
		return !retorno;
	}
	
	
	public static boolean usuarioSemFavoritosEFonteDeSons(Usuario user){
		return (user.getFonte().size() == 0) && (user.getSonsFavoritos().size() == 0);
	}
	
	
	/**
	 * Ordena por ordem de postagem mais recente
	 * @param lista
	 * @return
	 */
	public static List<String> ordemTimeLine(List<String> lista ){
			List<String> retornoCopy = new ArrayList<String>(); //coloca na ordem de postagem - pilha
			//retornoCopy.addAll(lista);
			for (int i = 0; i < lista.size(); i++) {
				retornoCopy.add(i, lista.get(i));
			}
			Collections.reverse(retornoCopy);
			
			return retornoCopy;
	}
}
