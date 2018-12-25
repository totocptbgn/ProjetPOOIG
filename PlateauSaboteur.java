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

	}
}
