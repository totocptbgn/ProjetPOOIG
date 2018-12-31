public class CasePuzzle extends Case {
	private int id; // Id de la case, la pièce posé dessus doit avoir le même id pour considérer qu'elle est à sa place !

	public CasePuzzle(int id, PiecePuzzle pz) {
		this.id = id;
		this.piece = pz;
	}

	@Override
	public int getValeur() { // Retourne l'id de la Case
		return id;
	}
}
