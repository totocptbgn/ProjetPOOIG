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
		String espaces = "";

			espaces = "         ";
			System.out.print("          ");
			for(int i = 0;i < plateau.length;i++){
				System.out.print("   " +(i+1) + "     ");
			}
			System.out.println();
		System.out.print("        ");
		for(int i = 0;i < plateau.length-1;i++){
			System.out.print("+---------");
		}
		System.out.println('+');
		for(int i = 0;i<plateau.length-1;i++){
			System.out.print("        ");
			for(int j = 0;j<plateau.length-1;j++) {
				if(!plateau[i][j].estOccupee()) {
					System.out.print("|" + espaces );
				}
				else{
					System.out.print('|');
					CarteChemin c = (CarteChemin)plateau[i][j].piece;
					System.out.print(c.toString());
				}

			}
			System.out.println('|');

		}
		System.out.print("        ");
		for(int i = 0;i < plateau.length-1;i++){
			System.out.print("+---------");
		}
		System.out.println('+');
	}
}
