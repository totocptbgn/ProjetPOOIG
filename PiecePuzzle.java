public class PiecePuzzle extends Piece {
	private String image; // Contient le chemin de l'image.
	private int id; // ID de la pièce qui la différencie des autres et qui informe sur quelle pièce se placer.

	public PiecePuzzle(String image, int id) {
		this.image = image;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getImage() {
		return image;
	}
}
