public class PlateauSaboteur extends Plateau {
	public PlateauSaboteur(int hauteur){
		super(hauteur);
		this.setPlateau(new CaseSaboteur[hauteur][hauteur]);
		for (int i = 0; i < hauteur; i++) {
			for (int j = 0; j < hauteur; j++) {
				this.setCase(i, j, new CaseSaboteur(null));
			}
		}
		boolean[] d = {true,true,true,true};
		boolean[] virdroite = {false,false,true,true};
		boolean[] virgauche = {true,false,false,true};
		//this.getCase(hauteur/2,hauteur/2).poserPiece(new CarteChemin(d));
		this.plateau[hauteur/2][hauteur/2].piece = new CarteChemin(d);
		this.plateau[hauteur/2-hauteur/3][hauteur/3].piece = new CarteChemin(virdroite);
		this.plateau[hauteur-4][3].piece = new CarteChemin(virgauche);
	}

	@Override
	public void afficher() {
		// On veut seulement afficher les cases découvertes et les cases d'arrivée et de départ.

		// Affichage de la grille

		System.out.print("      ");
		for (int j = 1; j < longueur + 1; j++) {
			System.out.print("  ");
			if (j < 10) {
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
			if ((i + 1) < 10) {
				System.out.print("   " + (i + 1) + "  ");
			} else {
				System.out.print("  " + (i + 1) + "  ");
			}
			for (int j = 0; j < longueur; j++) {
				//System.out.println("|  ");
				if (getCase(i, j).estOccupee()) {
					CarteChemin c = (CarteChemin) this.plateau[i][j].piece;
					System.out.print(c.toString());
				} else {
					System.out.print(" ");
				}
				System.out.print("  ");
			}
			//System.out.println("|");
		}
		System.out.print("      ");
		for (int j = 0; j < longueur; j++) {
			System.out.print("+-----");
		}
		System.out.println("+");
	}
}
