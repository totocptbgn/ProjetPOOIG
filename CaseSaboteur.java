public class CaseSaboteur extends Case {
	CarteChemin carte;

	public CaseSaboteur(CarteChemin c){
		super();
		this.carte =c;
	}

	@Override
	public int getValeur() {
		return 0;
	}

	public boolean[] getDirection(){
		if (carte != null) {
			return carte.getDirection();
		}
		return null;
	}

	public CarteChemin getPiece(){
		return carte;
	}
}
