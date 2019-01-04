import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/*
 * 				@author Thomas
 * 				Jeu du domino, entièrment textuel, sur le terminal.
 * 				Pour voir le fonctionnement du jeu, lire le README.
 */
	
public class Domino extends Jeu {

	protected boolean premierTour; // True au début de partie tant qu'aucun domino n'est posé.
	protected boolean gagnant []; // Tableau de boolean correspondants au joueurs, si un joueur n'as gagné il est à false, sinon il est à true.
	protected static int place;
	protected ArrayList <PieceDomino> paquet []; // Tableau comportant la pioche et les Dominos non-posés de chaque joueurs.
	// Taille de paquet = Nombre de joueurs + 1, [0] correspond à la pioche.


	@Override
	public void lancerPartie(){ // Initialise et lance la partie.
		afficheIntro();

		// Création des Joueurs
		setJoueur();

		// Création du plateau
		this.plateau = new PlateauDomino(15);
		this.premierTour = true;

		// Création des Dominos
		ArrayList<PieceDomino> pieces = new ArrayList<>();
		for (int i = 0; i < 7 ; i++ ) {
			for (int j = i; j < 7 ; j++ ) {
				pieces.add(new PieceDomino(i, j));
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

		// Affichage des dominos
		System.out.println(" Voici les dominos de chaque joueur :");
		for (int i = 1; i < paquet.length; i++) {
			System.out.print( participants[i-1].getNom() + " : ");
			for (int j = 0; j < paquet[i].size(); j++) {
				System.out.print(paquet[i].get(j) + " ");
			}
			System.out.println();
		}

		while (true) {
			for (Joueur participant : participants) {
				if (!gagnant[participant.getId()]) {
					joueUnTour(participant);
				}
			}
		}
	}

	public boolean placerDomino(int i, int j, int dir, PieceDomino p){ // Reçoit une coordonnée et pose le domino si la position est juste.

		/*
		 * i, j : position dans le tableau,
		 *
		 * dir : 0 = vers la droite,
		 *       1 = vers le bas,
		 *       2 = vers la gauche,
		 *       3 = vers le haut.
		 */

		CaseDomino caseDomino0 = (CaseDomino) plateau.getCase(i, j);
		CaseDomino caseDomino1;

		switch (dir){
			case 0: caseDomino1 = (CaseDomino) plateau.getCase(i,j + 1);
				break;
			case 1: caseDomino1 = (CaseDomino) plateau.getCase(i + 1, j);
				break;
			case 2: caseDomino1 = (CaseDomino) plateau.getCase(i, j - 1);
				break;
			case 3: caseDomino1 = (CaseDomino) plateau.getCase(i - 1, j);
				break;
			default:
				return false;
		}

		// Vérification des pièces cibles
		if (caseDomino0.estOccupee() || caseDomino1.estOccupee()){
			System.err.println(" La case est déjà occupée.");
			return false;
		}

		// Vérification des pièces adjacentes
		boolean ok = false;

		if (!premierTour) { // Si ce n'est pas le premier tour, on vérifie les cases adjacentes à caseDomino0 & caseDomino1.
			int val0 = p.getValeur(0);
			int val1 = p.getValeur(1);

			if (dir == 0){ // CaseDomino à droite
				if (verifCase(i-1, j, val0)){
					ok = true;
				}
				if (verifCase(i, j-1, val0)){
					ok = true;
				}
				if (verifCase(i+1, j, val0)){
					ok = true;
				}
				if (verifCase(i-1, j+1, val1)){
					ok = true;
				}
				if (verifCase(i, j+2, val1)){
					ok = true;
				}
				if (verifCase(i+1, j+1, val1)){
					ok = true;
				}
			} else if (dir == 1){ // CaseDomino en bas
				if (verifCase(i-1, j, val0)){
					ok = true;
				}
				if (verifCase(i, j-1, val0)){
					ok = true;
				}
				if (verifCase(i, j+1, val0)){
					ok = true;
				}
				if (verifCase(i+1, j-1, val1)){
					ok = true;
				}
				if (verifCase(i+2, j, val1)){
					ok = true;
				}
				if (verifCase(i+1, j+1, val1)){
					ok = true;
				}
			} else if (dir == 2){ // CaseDomino à gauche
				if (verifCase(i-1, j, val0)){
					ok = true;
				}
				if (verifCase(i, j+1, val0)){
					ok = true;
				}
				if (verifCase(i+1, j, val0)){
					ok = true;
				}
				if (verifCase(i-1, j-1, val1)){
					ok = true;
				}
				if (verifCase(i+1, j-1, val1)){
					ok = true;
				}
				if (verifCase(i, j-2, val1)){
					ok = true;
				}
			} else if (dir == 3){ // CaseDomino en haut
				if (verifCase(i, j-1, val0)){
					ok = true;
				}
				if (verifCase(i, j+1, val0)){
					ok = true;
				}
				if (verifCase(i+1, j, val0)){
					ok = true;
				}
				if (verifCase(i-1, j-1, val1)){
					ok = true;
				}
				if (verifCase(i-2, j, val1)){
					ok = true;
				}
				if (verifCase(i-1, j+1, val1)){
					ok = true;
				}
			}

			if (!ok){
				System.out.println();
				return false;
			}
		} else { // Si c'est le premier tour, pas besoin de vérifier.
		}

		// Placement de la pièce
		int id = p.getProprio().getId();
		caseDomino0.poserPiece(p, 0);
		caseDomino1.poserPiece(p, 1);
		paquet[id + 1].remove(p);
		premierTour = false;

		return true;
	}

	private boolean verifCase(int i, int j, int valeur){ // Vérifie si la case à la position i, j contient une pièce ayant la valeur 'valeur'
		try {
			if (((CaseDomino) this.plateau.getCase(i, j)).getValeur() == valeur) {
				return true;
			}
		} catch (ArrayIndexOutOfBoundsException e){}
		return false;
	}

	@Override
	public void joueUnTour(Joueur joueur) {

		int dom = -1;
		int i = -1;
		int j = -1;
		int d = -1;
		int input;
		boolean b;
		boolean pose = false;
		String dir = "";

		// On affiche le plateau
		System.out.println();
		plateau.afficher();

		// On affiche ses dominos
		System.out.println("\n Au tour de " + joueur.getNom() + " de poser un Domino :\n");
		int id = joueur.getId() + 1;
		System.out.print( participants[id-1].getNom() + " : ");
		for (int k = 0; k < paquet[id].size(); k++) {
			System.out.print("(" + (k + 1) + ")-" + paquet[id].get(k) + " ");
		}
		System.out.println();

		// On propose de piocher ou de poser
		if (paquet[0].size() == 0){
			pose = true;
		} else {
			b = true;
			while (b) {
				System.out.print(" Voulez vous poser un domino ? (Sinon piocher en un) ? [y/n] ");
				Scanner sc = new Scanner(System.in);
				String answer = sc.next();
				if (answer.equals("y") || answer.equals("Y")){
					b = false;
					pose = true;
				} else if (answer.equals("n") || answer.equals("N")){
					b = false;
					pose = false;
					Random rand = new Random();
					PieceDomino p = paquet[0].remove(rand.nextInt(paquet[0].size()));
					p.setProprio(joueur);
					this.paquet[joueur.getId() + 1].add(p);
				} else {
					System.err.println();
				}
			}
		}

		// Si le joueur n'as pas pioché, il pose son domino
		if (pose) {
			b = true;
			while (b) {
				System.out.print(" Donnez le domino à poser : ");
				Scanner sc = new Scanner(System.in);
				try {
					input = sc.nextInt();
					input--;
					if (input >= 0 && input < paquet[joueur.getId() + 1].size()){
						dom = input;
						b = false;
					} else {
						System.err.println(" Rentrez un nombre correspondant à un domino.\n");
					}
				} catch (InputMismatchException e){
					System.err.println(" Rentrez un nombre valide.\n");
					input = -1;
				}
			}

			// On demande la ligne
			b = true;
			while (b) {
				System.out.print(" Donnez la ligne : ");
				Scanner sc = new Scanner(System.in);
				try {
					input = sc.nextInt();
					input--;
					if (input >= 0 && input < plateau.hauteur){
						i = input;
						b = false;
					} else {
						System.err.println(" Rentrez un nombre correspondant à une ligne.");
					}
				} catch (InputMismatchException e){
					System.err.println(" Rentrez un nombre valide.");
					input = -1;
				}
			}

			// On demande la colonne
			b = true;
			while (b) {
				System.out.print(" Donnez la colonne : ");
				Scanner sc = new Scanner(System.in);
				try {
					input = sc.nextInt();
					input--;
					if (input >= 0 && input < plateau.longueur){
						j = input;
						b = false;
					} else {
						System.err.println(" Rentrez un nombre correspondant à une colonne.");
					}
				} catch (InputMismatchException e){
					System.err.println(" Rentrez un nombre valide.");
					input = -1;
				}
			}

			// On demande la direction
			b = true;
			while (b) {
				System.out.print(" Donnez la direction (Droite, Bas, Gauche ou Haut) : ");
				Scanner sc = new Scanner(System.in);
				dir = sc.nextLine();
				b = false;
				switch (dir) {
					case "droite":
						d = 0; break;
					case "Droite":
						d = 0; break;
					case "bas":
						d = 1; break;
					case "Bas":
						d = 1; break;
					case "gauche":
						d = 2; break;
					case "Gauche":
						d = 2; break;
					case "Haut":
						d = 3; break;
					case "haut":
						d = 3; break;
					default:
						System.err.println("Donnez une direction valable.");
						b = true;
						break;
				}
			}

			// On récapitule les infos, et on demande la validation
			System.out.println("\n Recap : ");
			System.out.println("	Domino : " + paquet[joueur.getId() + 1].get(dom));
			System.out.println("	Position : en " + (i+1) + ", " + (j+1) + ".");
			System.out.println("	Direction : " + dir  + ".");

			b = true;
			while (b) {
				System.out.print("\n Valider ? (y/n) ");
				Scanner sc = new Scanner(System.in);
				String rep = sc.nextLine();
				if (rep.equals("y")){
					b = false;
					if (!placerDomino(i, j, d, paquet[joueur.getId() + 1].get(dom))){
						System.err.println("Veuillez rejouer : ");
						this.joueUnTour(joueur);
					}
				} else if (rep.equals("n")){
					b = false;
					this.joueUnTour(joueur);
				} else {
					System.err.println("y / n");
				}
			}
		}

		// On test si le joueur à gagné
		if (paquet[joueur.getId() + 1].size() == 0){
			System.out.println("Félicitation, " + joueur.getNom() + " a gagné, il est à la place : " + place + " !");
			gagnant[joueur.getId()] = true;
		}

		// On vérifie si on doit continuer la partie
		int compt = 0;
		for (boolean b1 : gagnant) {
			if (!b1) {
				compt++;
			}
		}
		if (compt <= 1){
			System.out.println("Partie terminée !");
			System.exit(0);
		}
	}

	@Override
	public void setJoueur() { // Met en place la création des joueurs.
		place = 1;
		boolean b = true;
		while (b){
			System.out.print("Combien de joueurs participent ? ");
			Scanner sc = new Scanner(System.in);
			int nbrJ = 1;
			try {
				nbrJ = sc.nextInt();
				if (nbrJ < 2 || nbrJ > 4) {
					System.err.println("Rentrez un chiffre entre 2 et 4 inclus.\n");
				} else {
					this.participants = new Joueur[nbrJ];
					this.gagnant = new boolean[nbrJ];
					for (int i = 0; i < gagnant.length; i++) {
						this.gagnant[i] = false;
					}
					b = false;
				}
			} catch (InputMismatchException e){
				System.err.println("Rentrez un nombre valide.\n");
			}
		}
		for (int i = 0; i < participants.length; i++) {
			System.out.print("Quel est le nom du joueur n°" + (i+1) + " ? ");
			participants[i] = new Joueur(i);
		}
		System.out.println();
	}

	public void afficheIntro(){
		System.out.println("+---------------------------------------------------------------------------------------------------------------+");
		System.out.println("|                                                                                                               |");
		System.out.println("|                             ██████╗   ██████╗  ███╗   ███╗ ██╗ ███╗   ██╗  ██████╗                            |");
		System.out.println("|                             ██╔══██╗ ██╔═══██╗ ████╗ ████║ ██║ ████╗  ██║ ██╔═══██╗                           |");
		System.out.println("|                             ██║  ██║ ██║   ██║ ██╔████╔██║ ██║ ██╔██╗ ██║ ██║   ██║                           |");
		System.out.println("|                             ██║  ██║ ██║   ██║ ██║╚██╔╝██║ ██║ ██║╚██╗██║ ██║   ██║                           |");
		System.out.println("|                             ██████╔╝ ╚██████╔╝ ██║ ╚═╝ ██║ ██║ ██║ ╚████║ ╚██████╔╝                           |");
		System.out.println("|                             ╚═════╝   ╚═════╝  ╚═╝     ╚═╝ ╚═╝ ╚═╝  ╚═══╝  ╚═════╝                            |");
		System.out.println("|                                                                                                               |");
		System.out.println("|               Règles :                                                                                        |");
		System.out.println("|                                                                                                               |");
		System.out.println("|                 Les joueurs jouent dans l'ordre de leurs numéros chacun leur tour. Le premier                 |");
		System.out.println("|                 joueur pose le domino de son choix au centre du plateau. Le joueur suivant                    |");
		System.out.println("|                 doit à son tour poser un domino ayant le même nombre de points sur au moins un                |");
		System.out.println("|                 côté du domino précédemment posé. Si le joueur ne peut plus poser de domino,                  |");
		System.out.println("|                 il pioche. Les dominos forment ainsi un chaine. Le premier joueur n'ayant plus                |");
		System.out.println("|                 de domino gagne. Il se peut que le jeu soit bloqué. Alors le joueur ayant le                  |");
		System.out.println("|                 moins de points est déclaré vainqueur.                                                        |");
		System.out.println("|                                                                                                               |");
		System.out.println("|                 Amusez vous bien !                                                                            |");
		System.out.println("|                                                                                                               |");
		System.out.println("+---------------------------------------------------------------------------------------------------------------+");
		System.out.println();
	}
}
