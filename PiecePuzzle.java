public class PiecePuzzle extends Piece {
	String image; // Contient le chemin de l'image.
	int id; // ID de la pièce qui la différencie des autres.

	public PiecePuzzle(String image, int id) {
		this.image = image;
		this.id = id;
	}
}
