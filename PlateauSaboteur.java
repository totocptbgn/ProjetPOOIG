public class PlateauSaboteur extends Plateau {
	private int hauteur;

	public PlateauSaboteur(int hauteur){
		super(hauteur);
		this.setPlateau(new CaseSaboteur[hauteur][hauteur]);
		for (int i = 0; i < hauteur; i++) {
			for (int j = 0; j < hauteur; j++) {
				this.setCase(i, j, new CaseSaboteur(null));
			}
		}
	}

	@Override
	public void afficher() {
		//on affiche seulement les cases découvertes et les cases d'arrivé et de départ
		boolean[] depart = {true,true,true,true};
		CarteChemin d = new CarteChemin(depart);
		/*for(int i = 0;i<30;i++){
		    System.out.print('_');
        }
		for(int i = 0;i<30;i++){
		    System.out.println('|');
        }
        for(int i = 0;i<30;i++){

        }*/
		for(int i = 0;i<hauteur/2;i++){
		    System.out.println();
        }
        for(int i = 0;i<hauteur/6;i++){
            System.out.print(" ");
        }
        System.out.println(d.toString());
	}
}
