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
		// Affichage de la grille
		System.out.print("      ");
		for (int j = 1; j < longueur + 1; j++) {
			System.out.print("  ");
			if (j < 10){
				System.out.print(" " + j + " ");
			} else {
				System.out.print(" " + j);
			}
			System.out.print(" ");
		}
		System.out.println();
		for (int i = 0; i < hauteur; i++) {
			System.out.print("      ");
			for (int j = 0; j < longueur; j++) {
				System.out.print("+-----");
			}
			System.out.println("+");
			if ((i + 1) < 10){
				System.out.print("   " + (i + 1) + "  ");
			} else {
				System.out.print("  " + (i + 1) + "  ");
			}
			for (int j = 0; j < longueur; j++) {
				System.out.print("|  ");
				if (getCase(i, j).estOccupee()){
					int val = ((PiecePuzzle) getCase(i, j).getPiece()).getId();
					if (val != 8){
						System.out.print(val);
					} else {
						System.out.print(" ");
					}
				} else {
					System.out.print(" ");
				}
				System.out.print("  ");
			}
			System.out.println("|");
		}
		System.out.print("      ");
		for (int j = 0; j < longueur; j++) {
			System.out.print("+-----");
		}
		System.out.println("+");
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
