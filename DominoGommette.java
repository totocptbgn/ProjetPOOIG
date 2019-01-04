import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class DominoGommette extends Domino {

	/*
	 *  Cette version du domino est la même que celle de Domino.java mais
	 *  les chiffres sont remplacés par des couleurs (ou des symboles pour l'interface textuel).
	 *
	 *  INTERFACE GRAPHIQUE NE FONCTIONNE PAS CAR NON FINI !
	 *
	 *  Il aurait fallu faire un choix entre interface graphique et textuels, mais l'interface graphique étant non
	 *  fini, on ne la propose pas...
	 *
	 *  Fonctionnnement normal (Graphique):
	 *
	 * 		Jeu de domino du même fonctionnement que Domino mais avec interface graphique en plus.
	 * 		On aurait au début afficher plusieurs fenêtres pour demander le nombre de joueurs et leurs nom.
	 * 		Puis à chaque tour on affiche dans des fênetres différentes (ou pas ?) le plateau avec les dominos,
	 * 		et on demande à chaque fois les mêmes questions que dans joueUnTour(j) en ouvrant des fenêtres comme
	 * 		dans setJoueur().
	 *
	 * 	Pour l'interface textuel :
	 *
	 * 		Même chose que Domino, sauf que ce sont des symboles qu'il faut afficher à la place des chiffres.
	 *
	 */

	// La fonction qui "traduit" les chiffres du domino en symbole :
	public static String toSymbol (int i){
		String symbols [] = {"★", "✖", "☻", "⚑", "⚙", "☯", "❄", "⚡"};
		return symbols[i];
	}

	@Override
	public void lancerPartie(){ // Initialise et lance la partie
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
				System.out.print("[" + toSymbol(paquet[i].get(j).getValeur(0)) + "," + toSymbol(paquet[i].get(j).getValeur(1)) + "]" + " ");
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

	@Override
	public void joueUnTour(Joueur joueur) { // Meme fonction que Domino mais légerement modifié pour changer l'affichage...

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
		((PlateauDomino)plateau).afficherGommette();

		// On affiche ses dominos
		System.out.println("\n Au tour de " + joueur.getNom() + " de poser un Domino :\n");
		int id = joueur.getId() + 1;
		System.out.print( participants[id-1].getNom() + " : ");
		for (int k = 0; k < paquet[id].size(); k++) {
			System.out.print("(" + (k + 1) + ")-" + "[" + DominoGommette.toSymbol(paquet[id].get(k).getValeur(0)) + "," + DominoGommette.toSymbol(paquet[id].get(k).getValeur(1)) + "]" + " ");
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
			System.out.println("	Domino : " + "[" + DominoGommette.toSymbol(paquet[joueur.getId() + 1].get(dom).getValeur(0)) + "," + DominoGommette.toSymbol(paquet[joueur.getId() + 1].get(dom).getValeur(1)) + "]");
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
	public void afficheIntro() {
		System.out.println("+---------------------------------------------------------------------------------------------------------------+");
		System.out.println("|                                                                                                               |");
		System.out.println("|                             ██████╗   ██████╗  ███╗   ███╗ ██╗ ███╗   ██╗  ██████╗                            |");
		System.out.println("|                             ██╔══██╗ ██╔═══██╗ ████╗ ████║ ██║ ████╗  ██║ ██╔═══██╗                           |");
		System.out.println("|                             ██║  ██║ ██║   ██║ ██╔████╔██║ ██║ ██╔██╗ ██║ ██║   ██║                           |");
		System.out.println("|                             ██║  ██║ ██║   ██║ ██║╚██╔╝██║ ██║ ██║╚██╗██║ ██║   ██║                           |");
		System.out.println("|                             ██████╔╝ ╚██████╔╝ ██║ ╚═╝ ██║ ██║ ██║ ╚████║ ╚██████╔╝                           |");
		System.out.println("|                             ╚═════╝   ╚═════╝  ╚═╝     ╚═╝ ╚═╝ ╚═╝  ╚═══╝  ╚═════╝                            |");
		System.out.println("|                                                                     Version : GOMMETTES                       |");
		System.out.println("|               Règles :                                                                                        |");
		System.out.println("|                                                                                                               |");
		System.out.println("|                 Les joueurs jouent dans l'ordre de leurs numéros chacun leur tour. Le premier                 |");
		System.out.println("|                 joueur pose le domino de son choix au centre du plateau. Le joueur suivant                    |");
		System.out.println("|                 doit à son tour poser un domino ayant le même symbole sur au moins un côté                    |");
		System.out.println("|                 du domino précédemment posé. Si le joueur ne peut plus poser de domino,                       |");
		System.out.println("|                 il pioche. Les dominos forment ainsi un chaine. Le premier joueur n'ayant plus                |");
		System.out.println("|                 de domino gagne. Il se peut que le jeu soit bloqué. Alors le joueur ayant le                  |");
		System.out.println("|                 moins de points est déclaré vainqueur.                                                        |");
		System.out.println("|                                                                                                               |");
		System.out.println("|                 Amusez vous bien !                                                                            |");
		System.out.println("|                                                                                                               |");
		System.out.println("+---------------------------------------------------------------------------------------------------------------+");
	}

	// TOUT CE QUI SUIS EST LIÉ A L'INTERFACE GRAPHIQUE ET N'EST PAS FINI...

	private Color [] colors;

	/* Tableau des couleurs, chaque couleurs fait références au chiffre à son index
	 * dans le tableau :
	 *
	 * 0 = Violet,
	 * 1 = Bleu Clair,
	 * 2 = Rouge,
	 * 3 = Orange,
	 * 4 = Jaune,
	 * 5 = Vert,
	 * 6 = Bleu Foncé.
	 *
	 */

	public void lancerPartieGUI(){
		lancerPartie();

		// Création des couleurs à utiliser
		this.colors = new Color[7];
		colors[0] = Color.MAGENTA;
		colors[1] = Color.CYAN;
		colors[2] = Color.RED;
		colors[3] = Color.ORANGE;
		colors[4] = Color.YELLOW;
		colors[5] = Color.GREEN;
		colors[6] = Color.BLUE;
	}

	public void setJoueurGUI() { // Met en place la création des joueurs.

		// On crée une fenêtre JFrame
		JFrame frame = new JFrame();
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Domino-Gommette - NON FINI, NE FONCTIONNE PAS");
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);

		// Emplacement pour afficher une question
		JLabel text = new JLabel("");
		frame.add(text);

		// Emplacement pour y répondre
		JTextField field = new JTextField(10);
		frame.add(field);

		// Bouton pour valider
		JButton ok = new JButton("Ok");
		frame.add(ok);

		// Actualisation de la question et affichage de la fenêtre
		text.setText("Combien de joueurs participent ? ");
		frame.pack();
		frame.setVisible(true);

		place = 1;
		final boolean[] b = {true};
		while (b[0]){
			ok.addActionListener((event) -> {
				int nbrJ;
				try {
					String s = field.getText();
					nbrJ = Integer.parseInt(s);
					if (nbrJ < 2 || nbrJ > 4) {
						text.setText("Combien de joueurs participent ? (Rentrez un chiffre entre 2 et 4 inclus.)");
						frame.pack();
					} else {
						this.participants = new Joueur[nbrJ];
						this.gagnant = new boolean[nbrJ];
						for (int i = 0; i < gagnant.length; i++) {
							this.gagnant[i] = false;
						}
						b[0] = false;
					}
				} catch (NumberFormatException e){
					text.setText("Combien de joueurs participent ? Rentrez un nombre valide.");
					frame.pack();
				}
			});
		}

		for (int i = 0; i < participants.length; i++) {
			text.setText("Quel est le nom du joueur n°" + (i+1) + " ? ");
			field.setText("");
			final int id = i;
			ok.addActionListener((e) ->{
				participants[id] = new Joueur(field.getText(), id);
			});
		}
	}

	private class VueDominoGommette extends JFrame { // Classe interne qui gère l'interface graphique
		private Container cont;
		private ArrayList<JPanel> panels;

		VueDominoGommette(){
			// On met en place la fenêtre
			setPreferredSize(new Dimension(1000, 700));
			setTitle("Domino-Gommette - NON FINI, NE FONCTIONNE PAS");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			this.cont = getContentPane();
			BorderLayout bl = new BorderLayout(0, 0);
			cont.setLayout(bl);

			// On crée et ajoute des JPanels pour les cases du plateau de domino (PAS FINI)

			/*
			this.panels = new ArrayList<>();
			for (int i = 0; i < 9; i++) {
				DominoGommette.JPanelCase p = new DominoGommette.JPanelCase(i, j);
				p.addMouseListener(new MouseAdapter(){});
				panels.add(p);
				cont.add(p);
			}
			*/

			pack();
			setResizable(false);
			setLocationRelativeTo(null);
			setVisible(true);
			updateView();
		}

		private void updateView(){
			// Pour chaque case, afficher dans la JPanel correspondante la couleur qui correspond
			// au chiffre dans la pièce posée sur la case

			for (JPanel p : panels){
				p.repaint();
				((JPanelCase) p).majColor();
			}
		}
	}

	private class JPanelCase extends JPanel {
		// JPanel modifiée pour être utilisé dans le DominoGommette en tant que CaseDomino graphique

		private int i;
		private int j;

		JPanelCase(int i, int j) {
			super();
			this.i = i;
			this.j = j;
		}

		public void majColor(){
			// On va chercher la valeur du domino et on mets la couleur correspondante en fond de la JPanel.
		}
	}
}