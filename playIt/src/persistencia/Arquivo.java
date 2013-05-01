package persistencia;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import classes.Som;
import classes.Usuario;

/**
 * 
 * Persistencia de informacoes
 * @author Ana Lucia
 * 
 */

public class Arquivo  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private FileReader arq = null;
	private BufferedReader buffer = null;
	private List<String> linhas = new ArrayList<String>();

	/**
	 * Metodo: abreLerArquivo - Abre o arquivo passada como parametro, onde
	 * serao gravados as informacoes dos usuarios do sistema, faz a leitura das
	 * informacoes e retorna uma lista contendo os usuarios cadastrados.
	 * 
	 * @param arquivo
	 *            Arquivo com as informacoes dos usuarios
	 * @return Lista de com informacoes dos usuarios;
	 */

	public static List<Usuario> abreLerArquivo(String arquivo) {
		List<Usuario> listaRetorno = new ArrayList<Usuario>();
		ObjectInputStream ler = null;
		Usuario user;

		try {
			ler = new ObjectInputStream(new BufferedInputStream(
					new FileInputStream(arquivo)));
			Object obj = ler.readObject();
			while (obj != null) {
				user = (Usuario) obj;
				listaRetorno.add(user);

				obj = ler.readObject();
			}

		} catch (ClassNotFoundException e) {
			System.err.print(e.getMessage());
		} catch (IOException e) {
			if (e.getMessage() != null) {
				System.err.print(e.getMessage());
			}

		} finally {
			try {
				ler.close();
			} catch (NullPointerException e) {
				System.out.print(e.getMessage());
			} catch (IOException e) {
				System.err.print(e.getMessage());
			}
		}
		return listaRetorno;
	}// Fim do metodo 'abreLerArquivo'

	/**
	 * Metodo: escreveArquivo - Grava em um arquivo uma lista contendo as
	 * informacoes dos usuarios cadastrados no sistema.
	 * 
	 * @param arquivo
	 *            Arquivo onde sera armazenado as informacoes.
	 * @param listaUsuarios
	 *            Lista com os usuarios a serem gravados no arquivo.
	 */

	public void escreveArquivo(String arquivo, List<Usuario> listaUsuarios) {

		ObjectOutputStream escreve = null;
		try {
			escreve = new ObjectOutputStream(new BufferedOutputStream(
					new FileOutputStream(arquivo)));
			for (Usuario user : listaUsuarios) {
				escreve.writeObject(user);
			}

		} catch (IOException e) {
			System.err.print(e.getMessage());
		} finally {
			try {
				escreve.close();
			} catch (IOException e) {
				System.err.print(e.getMessage());
			}
		}
	}// Fim do metodo 'escreveArquivo'


	/**Metodo: escreveArquivoSom - Grava em um arquivo uma lista contendo as
	 * informacoes dos sons cadastrados no sistema.
	 * 
	 * @param arquivo =  Arquivo onde sera armazenado as informacoes.
	 * @param listaSons = Lista de com informacoes dos usuarios
	 */
	public void escreveArquivoSom(String arquivo, List<Som> listaSons) {

		ObjectOutputStream escreve = null;
		try {
			escreve = new ObjectOutputStream(new BufferedOutputStream(
					new FileOutputStream(arquivo)));
			for (Som som : listaSons) {
				escreve.writeObject(som);
			}

		} catch (IOException e) {
			System.err.print(e.getMessage());
		} finally {
			try {
				escreve.close();
			} catch (IOException e) {
				System.err.print(e.getMessage());
			}
		}
	}// Fim do metodo 'escreveArquivo'

	
	
	/**
	 * Metodo: abreLerArquivoSom - Abre o arquivo passada como parametro, onde
	 * serao gravados as informacoes dos sons do sistema, faz a leitura das
	 * informacoes e retorna uma lista contendo os sons cadastrados.
	 * 
	 * @param arquivo
	 *            Arquivo com as informacoes dos sons
	 * @return Lista de com informacoes dos sons
	 */
	public static List<Som> abreLerArquivoSom(String arquivo) {
		List<Som> listaRetorno = new ArrayList<Som>();
		ObjectInputStream ler = null;
		Som som;

		try {
			ler = new ObjectInputStream(new BufferedInputStream(
					new FileInputStream(arquivo)));
			Object obj = ler.readObject();
			while (obj != null) {
				som = (Som) obj;
				listaRetorno.add(som);

				obj = ler.readObject();
			}

		} catch (ClassNotFoundException e) {
			System.err.print(e.getMessage());
		} catch (IOException e) {
			if (e.getMessage() != null) {
				System.err.print(e.getMessage());
			}

		} finally {
			try {
				ler.close();
			} catch (NullPointerException e) {
				System.out.print(e.getMessage());
			} catch (IOException e) {
				System.err.print(e.getMessage());
			}
		}
		return listaRetorno;
	}// Fim do metodo 'abreLerArquivo'

	
	
	
	
	
	
	public FileReader getArq() {
		return arq;
	}

	public void setArq(FileReader arq) {
		this.arq = arq;
	}

	public BufferedReader getBuffer() {
		return buffer;
	}

	public void setBuffer(BufferedReader buffer) {
		this.buffer = buffer;
	}

	public List<String> getLinhas() {
		return linhas;
	}

	public void setLinhas(List<String> linhas) {
		this.linhas = linhas;
	}

	
	/**
	 * Zerar o arquivo (repositorio)
	 */
	public void zerarArquivo(String arq){
		PrintWriter escreve = null;
		try {
			escreve =  new PrintWriter(arq);
			String texto = "";
			 escreve.println(texto);
			 

		} catch (IOException e) {
			System.err.print(e.getMessage());
		} finally {
			escreve.close();
		}
	}
	
}// Fim da classe arquivo