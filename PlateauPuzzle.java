public class PlateauPuzzle extends Plateau {
	public PlateauPuzzle() {
		super(3);

		// Création du plateau
		this.setPlateau(new CasePuzzle[hauteur][longueur]);

		// Remplissage du plateau avec des cases vides
		int n = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				this.setCase(i, j, new CasePuzzle(n, null));
				n++;
			}
		}
	}

	@Override
	public void afficher() {
		// Cette fonction est inutile ici car le jeu est entièrement en interface graphique ...
	}
}
