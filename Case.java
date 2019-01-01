public abstract class Case {
	protected Piece piece;

	public Case(){
		this.piece = null;
	}

	public boolean estOccupee() { // Informe si la case est occupée par une pièce ou non.
		return this.piece != null;
	}

	public void poserPiece(Piece p){ // Setter de pièce.
		this.piece = p;
	}

	public Piece getPiece() { // Getter de pièce.
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public abstract int getValeur(); // Renvoie la "valeur" de la pièce sur la case (dépends du jeu, et donc abstract).
}
