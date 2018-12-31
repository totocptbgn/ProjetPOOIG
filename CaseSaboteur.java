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
		// Attention, il y a déjà un getPiece() dans Case !!!
		// (Faut éviter de faire des doublons et utiliser les classes abstraite un maximum...)
		return carte;
	}

	public String toString(){
		return carte.toString();
	}
}
