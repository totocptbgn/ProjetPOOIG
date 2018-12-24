public class CaseDomino extends Case {
	int side; // 0 ou 1, corresponds à l'index du côté de la pièce (ayant elle-même 2 cases).

	public CaseDomino(){
		super();
		this.side = -1;
	}

	public void PoserPiece(PieceDomino p, int side){ // Setter de pièce et de side.
		super.PoserPiece(p);
		this.side = side;
	}

	@Override
	public int getValeur() { // Renvoie la valeur du domino posé sur la case.
		PieceDomino p = (PieceDomino) getPiece();
		if (p == null) {
			return -1;
		}
		return ((PieceDomino) getPiece()).getValeur(side);
	}
}
