public abstract class Jeu {
	protected Plateau plateau;
	protected Joueur [] participants;  // Tableau des participants

	public abstract void lancerPartie(); // Lance et initialise la partie.
	public abstract void joueUnTour(Joueur j); // Lance un tour pour le joueur correspondant.
	public abstract void setJoueur(); // Mets en place les joueurs.
	public abstract void afficheIntro();
}
