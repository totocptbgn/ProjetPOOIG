public class CarteSaboteur extends Piece {
	private int ID;
	private static int compt;

	public CarteSaboteur(){
		super();
		ID = increment();
	}

	public int getID() {
		return ID;
	}

	 private static int increment(){
		compt++;
		return compt;
	}
}
