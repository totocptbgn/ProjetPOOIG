public abstract class Case {
	private Piece piece;

	public Case(){
		this.piece = null;
	}

	public boolean estOccupee() { // Informe si la case est occupée par une pièce ou non.
		return this.piece != null;
	}

	public void PoserPiece(Piece p){ // Setter de pièce.
		this.piece = p;
	}

	public Piece getPiece() { // Getter de pièce.
		return piece;
	}

	public abstract int getValeur(); // Renvoie la "valeur" de la pièce sur la case (dépends du jeu, et donc abstract).
}
