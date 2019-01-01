import java.util.ArrayList;

public class PlateauPuzzle extends Plateau {
	public PlateauPuzzle() {
		super(3);

		// Création du plateau
		this.setPlateau(new CasePuzzle[hauteur][longueur]);

		// Remplissage du plateau avec des cases vides
		int n = 0;
		for (int i = 0; i < longueur; i++) {
			for (int j = 0; j < hauteur; j++) {
				this.setCase(i, j, new CasePuzzle(n, null));
				n++;
			}
		}
	}

	@Override
	public void afficher() {
		// Cette fonction est inutile ici car le jeu est entièrement en interface graphique ...
	}

	public ArrayList<CasePuzzle> getAllCases(){ // Renvoie un ArrayList de toutes cases du plateau
		ArrayList<CasePuzzle> l = new ArrayList<>();
		for (int i = 0; i < longueur; i++) {
			for (int j = 0; j < hauteur; j++) {
				l.add((CasePuzzle) plateau[i][j]);
			}
		}
		return l;
	}
}
