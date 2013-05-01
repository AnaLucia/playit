package classes;

import java.io.Serializable;

public class FuturosAmigos implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3015256113333299560L;


	private Usuario idUser1, idUser2;

	public FuturosAmigos(Usuario User1, Usuario User2){
		this.idUser1 = User1;
		this.idUser2 = User2;
	}

	public Usuario getIdUser1() {
		return idUser1;
	}

	public void setIdUser1(Usuario idUser1) {
		this.idUser1 = idUser1;
	}

	public Usuario getIdUser2() {
		return idUser2;
	}

	public void setIdUser2(Usuario idUser2) {
		this.idUser2 = idUser2;
	}




}