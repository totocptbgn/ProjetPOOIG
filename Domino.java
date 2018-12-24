import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Domino extends Jeu {
	private boolean estVide;
	private ArrayList <PieceDomino> pieces; // Toutes les pièces qui existent
	ArrayList <PieceDomino> paquet []; // Tableau comportant la pioche et les Dominos non-posés de chaque joueurs.
	// Taille de paquet = Nombre de joueurs + 1, [0] correspond à la pioche.

	@Override
	public void lancerPartie(){ // Initialise et lance la partie.
		System.out.println("-- Partie de Domino --" + "\n");

		// Création des Joueurs
		this.setJoueur();

		// Création du plateau
		this.plateau = new PlateauDomino(20);
		this.estVide = true;

		// Création des Dominos
		this.pieces = new ArrayList<>();
		for (int i = 0; i < 7 ; i++ ) {
			for (int j = i; j < 7 ; j++ ) {
				this.pieces.add(new PieceDomino(i, j));
			}
		}

		// Distribution des Dominos
		this.paquet = new ArrayList[participants.length + 1];
		for (int i = 0; i < paquet.length; i++) {
			this.paquet[i] = new ArrayList<>();
		}
		Random rand = new Random();
		int dist;
		if (participants.length == 2){
			dist = 7;
		} else {
			dist = 6;
		}
		for (int i = 1; i < paquet.length; i++) {
			for (int j = 0; j < dist; j++) {
				PieceDomino p = pieces.remove(rand.nextInt(pieces.size()));
				p.setProprio(participants[i-1]);
				this.paquet[i].add(p);
			}
		}
		while(!pieces.isEmpty()){
			this.paquet[0].add(pieces.remove(0));
		}

		// Affichage du plateau
		plateau.afficher();

		// Affichage des dominos
		this.afficherDominos();
	}

	public void afficherDominos(){ // Affiche les dominos de chaque participant.
		for (int i = 1; i < paquet.length; i++) {
			System.out.print( participants[i-1].getNom() + " : ");
			for (int j = 0; j < paquet[i].size(); j++) {
				System.out.print(paquet[i].get(j) + " ");
			}
			System.out.println();
		}
	}

	public void afficherDominos(Joueur joueur){ // Affiche les dominos d'un joueur en particulier.
		int i = joueur.getId() + 1;
		System.out.print( participants[i-1].getNom() + " : ");
		for (int j = 0; j < paquet[i].size(); j++) {
			System.out.print("(" + (j + 1) + ")-" + paquet[i].get(j) + " ");
		}
		System.out.println();
	}

	public boolean placerDomino(int i, int j, int dir, PieceDomino p){ // Reçoit ecoit une coordonnée et pose le domino si la position est juste.

		/*
		 * i, j : position dans le tableau
		 * dir : 0 = vers la droite,
		 *       1 = vers le bas,
		 *       2 = vers le gauche,
		 *       3 = vers le haut.
		 */

		boolean pose = false;

		CaseDomino caseDomino1 = (CaseDomino) plateau.getCase(i, j);
		CaseDomino caseDomino2;

		switch (dir){
			case 0: caseDomino2 = (CaseDomino) plateau.getCase(i,j + 1);
				break;
			case 1: caseDomino2 = (CaseDomino) plateau.getCase(i + 1, j);
				break;
			case 2: caseDomino2 = (CaseDomino) plateau.getCase(i, j - 1);
				break;
			case 3: caseDomino2 = (CaseDomino) plateau.getCase(i - 1, j + 1);
				break;
			default:
				return false;
		}

		// Vérification des pièces cibles et adjacentes
		// À faire !

		// Placement des Pièces
		if (pose){
			int id = p.getProprio().getId();
			caseDomino1.PoserPiece(p, 0);
			caseDomino2.PoserPiece(p, 1);
			paquet[id + 1].remove(p);
			this.estVide = false;
		}
		return pose;
	}

	@Override
	public void joueUnTour(Joueur j) {

	}

	@Override
	public void setJoueur() { // Met en place la création des joueurs.
		boolean b = true;
		while (b){
			System.out.println("Combien de joueurs participent ?");
			Scanner sc = new Scanner(System.in);
			int nbrJ = 1;
			try {
				nbrJ = sc.nextInt();
				if (nbrJ < 2 || nbrJ > 4) {
					System.err.println("Rentrez un chiffre entre 2 et 4 inclus.");
				} else {
					this.participants = new Joueur[nbrJ];
					b = false;
				}
			} catch (InputMismatchException e){
				System.err.println("Rentrez un nombre valide.");
			}
		}
		for (int i = 0; i < participants.length; i++) {
			System.out.println("Quel est le nom du joueur n°" + (i+1) + " ?");
			participants[i] = new Joueur(i);
		}
	}

	public void poserDomino(Joueur joueur){ // Demande au joueur la position pour poser le domino puis appel placerDomino().
		String nom = joueur.getNom();
		int dom = -1;
		int i = -1;
		int j = -1;
		int d = -1;
		String dir = "";
		boolean b;
		int input;

		System.out.println("\nAu tour de " + nom + " de poser un Domino :\n");
		this.afficherDominos(joueur);

		b = true;
		while (b) {
			System.out.print("Donnez le domino à poser : ");
			Scanner sc = new Scanner(System.in);
			try {
				input = sc.nextInt();
				input--;
				if (input >= 0 && input < paquet[joueur.getId() + 1].size()){
					dom = input;
					b = false;
				} else {
					System.err.println("Rentrez un nombre correspondant à un domino.");
				}
			} catch (InputMismatchException e){
				System.err.println("Rentrez un nombre valide.");
				input = -1;
			}
		}

		b = true;
		while (b) {
			System.out.print("Donnez la ligne : ");
			Scanner sc = new Scanner(System.in);
			try {
				input = sc.nextInt();
				input--;
				if (input >= 0 && input < plateau.hauteur){
					j = input;
					b = false;
				} else {
					System.err.println("Rentrez un nombre correspondant à une ligne.");
				}
			} catch (InputMismatchException e){
				System.err.println("Rentrez un nombre valide.");
				input = -1;
			}
		}

		b = true;
		while (b) {
			System.out.print("Donnez la colonne : ");
			Scanner sc = new Scanner(System.in);
			try {
				input = sc.nextInt();
				input--;
				if (input >= 0 && input < plateau.longueur){
					i = input;
					b = false;
				} else {
					System.err.println("Rentrez un nombre correspondant à une colonne.");
				}
			} catch (InputMismatchException e){
				System.err.println("Rentrez un nombre valide.");
				input = -1;
			}
		}

		b = true;
		while (b) {
			System.out.print("Donnez la direction (\"haut\", \"bas\", \"droite\", \"gauche\") : ");
			Scanner sc = new Scanner(System.in);
			dir = sc.nextLine();
			b = false;
			switch (dir) {
				case "droite":
					d = 0; break;
				case "bas":
					d = 1; break;
				case "gauche":
					d = 2; break;
				case "haut":
					d = 3; break;
				default:
					System.err.println("Donnez une direction valable.");
					b = true;
					break;
			}
		}

		System.out.println("\n Recap : ");
		System.out.println("	Domino : " + paquet[joueur.getId() + 1].get(dom));
		System.out.println("	Position : en " + i + ", " + j + ".");
		System.out.println("	Direction : " + dir  + ".");

		b = true;
		while (b) {
			System.out.println("\n Valider ? (y/n) ");
			Scanner sc = new Scanner(System.in);
			String rep = sc.nextLine();
			if (rep.equals("y")){
				b = false;
				placerDomino(i, j, d, paquet[joueur.getId() + 1].get(dom));
			} else if (rep.equals("n")){
				b = false;
				this.poserDomino(joueur);
			} else {
				System.err.println("y / n");
			}
		}
	}
}
