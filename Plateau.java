public abstract class Plateau {
	protected Case [][] plateau;
	protected int hauteur; // Correspond à i.
	protected int longueur; // Correspond à j.

	/*
	 * Le plateau étant abstract pour pouvoir recevoir les différentes Cases des différents jeux,
	 * on ne rempli pas le plateau de case dans le constructeur. On fait plutôt appelle à la
	 * fonction setPlateau() dans le jeu (avec des cases héritant de la classe Case).
	 * Le constructeur met seulement en place les attributs hauteur et longueur.
	 */

	public Plateau(int hauteur, int longueur){
		if (hauteur > 99) { // On limite la taille du plateau à 100...
			hauteur = 99;
		}
		if (longueur > 99) {
			longueur = 99;
		}
		this.hauteur = hauteur;
		this.longueur = longueur;
	}

	public Plateau(int hauteur){ // Constructeur à 1 argument pour les plateaux carrés.
		this(hauteur, hauteur);
	}

	public Case getCase(int i, int j){ // Getter de la case correspondant à la position donnée.
		return plateau[i][j];
	}

	public void setCase(int i, int j, Case c){ // Setter de la case correspondant à la position donnée.
		this.plateau[i][j] = c;
	}

	public void setPlateau(Case[][] plateau) { // Rempli le plateau avec des cases héritant de Case
		this.plateau = plateau;
	}

	public abstract void afficher(); // Affiche le plateau dans le terminal (à implémenter dans les classes héritant de Plateau)
}
