package interfaceTexto;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import classes.FuturosAmigos;
import classes.Sistema;
import classes.Usuario;
import excecoes.LoginException;

public class InterfaceTexto {

	/**
	 * Interface Texto
	 * 
	 */
	static Scanner scInt = new Scanner(System.in);
	static int opcaoInt;
	static String opcaoString;
	static Scanner sc = new Scanner(System.in);
	static Calendar calendario = new GregorianCalendar();
	private static final String newLine = System.getProperty("line.separator")
			.toString();
	static Date data;

	public static void main(String[] args) {
		System.out.println("Play It");
		/*Sistema sistema = new Sistema().getInstance();

		int opcao = 0;

		do {
			System.out.println("Escolha uma opcao: ");
			System.out.println("1. Cadastrar Novo Usuario" + newLine
					+ "2. Login" + newLine + "3. Sair");
			System.out.print("> ");
			opcao = sc.nextInt();
			switch (opcao) {
			case 1: {
				System.out.println("--------------------------------");
				System.out.print("Insira o nome: ");
				String nome = sc.next();
				System.out.print(newLine + "Insira o login: ");
				String login = sc.next();
				System.out.print(newLine + "Insira o email: ");
				String email = sc.next();
				System.out.print(newLine + "Insira a senha: ");
				String senha = sc.next();
				try {
					sistema.criarUsuario(login, senha, nome, email);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				String idSessao = null;
				try {
					idSessao = sistema.abrirSessao(login, senha);
				} catch (LoginException e1) {
					System.out.println(e1.getMessage());
					e1.printStackTrace();
				}
				try {
					menuUsuarioLogado(idSessao);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				break;
			}
			case 2: {

				System.out.println("Insira o login: ");
				String login = sc.next();
				System.out.println("Insira a senha: ");
				String senha = sc.next();
				String idSessao = null;
				try {
					idSessao = sistema.abrirSessao(login, senha);
				} catch (LoginException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				try {
					menuUsuarioLogado(idSessao);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				break;
			}
			case 3: {
				break;
			}

			default: {
				System.out.println("Opcao invalida.");
				break;
			}
			}
		} while (opcao != 3);

	}

	public static void menuUsuarioLogado(String idSessao) throws Exception {

		System.out.println("--- PlayIt ---");
		System.out.println("O que deseja fazer?");
		System.out.println("1. Postar Som");
		System.out.println("2. Procurar Amigo");
		System.out.println("3. Visualizar Timeline");
		System.out.println("4. Solicitacoes de amizades");
		System.out.println("5. Sair");
		System.out.println("Opcao: ");

		opcaoInt = scInt.nextInt();

		switch (opcaoInt) {
		case 1:
			menuPostaSom(idSessao);
			break;
		case 2:
			menuProcuraAmigo(idSessao);
			break;
		case 3:
			menuTimeline(idSessao);
			break;
		case 4:
			menuVerificaSolicitacao(idSessao);
		case 5:
			break;

		default:
			break;
		}

	}

	public static void menuVerificaSolicitacao(String idSessao)
			throws Exception {
		System.out.println("Suas Solicitacoes: ");
		Usuario usuario = Sistema.getUsuarioIdSessao(idSessao);
		Map<String, FuturosAmigos> solicitacoes = usuario
				.getSolicitacoesDeAmizade();
		if (!(solicitacoes == null)) {
			// System.out.println(solicitacoes);

			String solic = "";
			Iterator it = solicitacoes.keySet().iterator();
			while (it.hasNext()) {
				String idSolicitacao = (String) it.next();
				FuturosAmigos amg = solicitacoes.get(idSolicitacao);// retorna o
																	// objeto
																	// futuros
																	// amigos
				solic += idSolicitacao + ", " + amg.getIdUser2(); // coloca na
																// string o
																// idDaSolicitacao
																// e o nome do
																// amigo que
																// solicitou a
																// amizade
				System.out.println(solic);
			}

			System.out.println("1. Aceita agora? ");
			System.out.println("2. Decidir depois? ");
			System.out.println("opcao: ");
			opcaoInt = scInt.nextInt();

			switch (opcaoInt) {
			case 1:
				Sistema.aceitarSolicitacaoAmizade(idSessao, solic);
				break;
			case 2:
				menuUsuarioLogado(idSessao);
				break;

			default:
				break;
			}

		}
		System.out.println("Voce nao tem solicitacoes");

	}

	// private static void menuVerificaSolicitacao(String idSessao)
	// throws Exception {
	// System.out.println("Suas Solicitacoes: ");
	// Usuario usuario = Sistema.getUsuarioIdSessao(idSessao);
	// Map<String, FuturosAmigos> solicitacoes = usuario
	// .getSolicitacoesDeAmizade();
	// if (!(solicitacoes.size() == 0)) {
	// System.out.println(solicitacoes);
	//
	//
	//
	// System.out.println("1. Aceita agora? ");
	// System.out.println("2. Decidir depois? ");
	// System.out.println("opcao: ");
	// opcaoInt = scInt.nextInt();
	//
	// switch (opcaoInt) {
	// case 1:
	// //Sistema.aceitarSolicitacaoAmizade(idSessao, idSolicitacao);
	// break;
	// case 2:
	// menuUsuarioLogado(idSessao);
	// break;
	//
	// default:
	// break;
	// }
	//
	// }
	// System.out.println("Voce nao tem solicitacoes");
	// menuUsuarioLogado(idSessao);
	//
	// }

	private static void menuTimeline(String idSessao) {
		System.out.println("O que deseja visualizar:");
		System.out.println("1. Minhas postagens");
		System.out.println("2. Postagens dos meus amigos");
		System.out.println("3. Postagens de quem eu sigo.");
		System.out.println("4. Sair");
		System.out.println("Opcao: ");

		opcaoInt = scInt.nextInt();

		switch (opcaoInt) {
		case 1:
			System.out.println(" --- Minhas postagens ---");
			List<String> perfilMusical = Sistema.getPerfilMusical(idSessao);
			System.out.println(perfilMusical);
			menuTimeline(idSessao);
			break;
		case 2:
			System.out.println(" --- Postagens dos meus amigos ---");
			try {
				List<String> visaoSons = Sistema.getVisaoDosSons(idSessao);
				System.out.println(visaoSons);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			menuTimeline(idSessao);

			break;

		case 3:
			System.out.println("Postagens de quem eu sigo");
			try {
				List<String> sons = Sistema.getFeedExtra(idSessao);
				System.out.println(sons);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			menuTimeline(idSessao);

			break;
		case 4:
			try {
				menuUsuarioLogado(idSessao);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}

			break;
		default:
			break;
		}

	}

	private static void menuProcuraAmigo(String idSessao) {
		System.out.println("1. Procurar Amigos.");
		System.out.println("2. Sair");
		opcaoInt = scInt.nextInt();

		switch (opcaoInt) {
		case 1:
			System.out.println("Digite o login a ser procurado.");
			opcaoString = sc.next();

			Usuario user = Sistema.buscarUsuarioPorLogin(opcaoString);

			System.out.println("Nome do Usuario: " + user.getNome());
			System.out.println("Login do Usuario: " + user.getLogin());

			System.out.println("1. Adicionar como amigo");
			System.out.println("2. Seguir");
			System.out.println("3. Sair");

			opcaoInt = sc.nextInt();

			switch (opcaoInt) {
			case 1:
				try {
					Sistema.enviarSolicitacaoAmizade(idSessao, user.getLogin());
					System.out.println("Solicitação enviada.");
					menuProcuraAmigo(idSessao);
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
					e1.printStackTrace();
				}
				break;

			case 2:
				try {
					System.out.println("Seguindo: " + user.getLogin());
					Sistema.seguirUsuario(idSessao, user.getLogin());
					menuProcuraAmigo(idSessao);
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
					e1.printStackTrace();
				}
				break;
			case 3:
				menuProcuraAmigo(idSessao);

			default:
				break;
			}

			break;
		case 2:
			try {
				menuUsuarioLogado(idSessao);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}

		default:
			break;
		}

	}

	private static void menuPostaSom(String idSessao) throws Exception {
		System.out.println("1. Digite o link desejado?");
		System.out.println("2. Sair");
		System.out.println("Opcao: ");

		opcaoInt = sc.nextInt();

		switch (opcaoInt) {
		case 1:
			System.out.println("Link: ");
			opcaoString = sc.next();

			data = calendario.getTime();
			String dt = pegaData(data);
			Sistema.postarSom(idSessao, opcaoString, dt);

			break;
		case 2:
			menuUsuarioLogado(idSessao);
		default:
			break;
		}

	}

	private static String getHoraPostagem(Date dt) {
		String[] hour = dt.toString().split(" ");
		return hour[0] + " " + hour[2] + "/" + hour[1] + "/" + hour[5] + " "
				+ hour[3];
	}

	public static String pegaData(Date dt) {
		String[] data = getHoraPostagem(dt).split(" ");
		return dataProntaParaImpressao(data[1]);

	}

	private static String dataProntaParaImpressao(String str) {
		String[] partes = str.split("/");
		String mes = partes[1], dia = partes[0], ano = partes[2];

		if (mes.equalsIgnoreCase("Jan")) {
			mes = "01";
		} else if (mes.equalsIgnoreCase("Feb")) {
			mes = "02";
		} else if (mes.equalsIgnoreCase("Mar")) {
			mes = "03";
		} else if (mes.equalsIgnoreCase("Apr")) {
			mes = "04";
		} else if (mes.equalsIgnoreCase("May")) {
			mes = "05";
		} else if (mes.equalsIgnoreCase("Jun")) {
			mes = "06";
		} else if (mes.equalsIgnoreCase("Jul")) {
			mes = "07";
		} else if (mes.equalsIgnoreCase("Aug")) {
			mes = "08";
		} else if (mes.equalsIgnoreCase("Sep")) {
			mes = "09";
		} else if (mes.equalsIgnoreCase("Oct")) {
			mes = "10";
		} else if (mes.equalsIgnoreCase("Nov")) {
			mes = "11";
		} else if (mes.equalsIgnoreCase("Dec")) {
			mes = "12";
		}

		return dia + "/" + mes + "/" + ano;
	}

	private static String pegaData(GregorianCalendar data) {

		String[] hour = data.toString().split(" ");
		return hour[2] + "/" + hour[1] + "/" + hour[5];
	}

	public static String getNewline() {
		return newLine;
	}
*/
}}