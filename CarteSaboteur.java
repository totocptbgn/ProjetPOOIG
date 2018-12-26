public class CarteSaboteur extends Piece {
	private int ID;
	private static int compt;

	public CarteSaboteur(){
		super();
		ID = increment();
		// Mettre l'id en autoincrement?
	}

	public int getID() {
		return ID;
	}

	 static int increment(){
		compt++;
		return compt;
	}
}
