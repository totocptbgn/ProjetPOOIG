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
	 * Cette version du domino est la même que celle de Domino.java mais
	 * les chiffres sont remplacés par des couleurs.
	 *
	 * NE FONCTIONNE PAS CAR NON FINI
	 *
	 * Fonctionnnement normal :
	 * 		Jeu de domino du même fonctionnement que Domino mais avec interface graphique.
	 * 		On aurait au début afficher plusieurs fenêtres pour demander le nombre de joueurs et leurs nom.
	 * 		Puis à chaque tour on affiche dans des fênetres différentes (ou pas ?) le plateau avec les dominos,
	 * 		et on demande à chaque fois les mêmes question que dans joueUnTour(j)
	 *
	 */

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

	@Override
	public void lancerPartie() {

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
		System.out.println("Voici les dominos de chaque joueur :");
		for (int i = 1; i < paquet.length; i++) {
			System.out.print( participants[i-1].getNom() + " : ");
			for (int j = 0; j < paquet[i].size(); j++) {
				System.out.print(paquet[i].get(j) + " ");
			}
			System.out.println();
		}

		// Création des couleurs à utiliser
		this.colors = new Color[7];
		colors[0] = Color.MAGENTA;
		colors[1] = Color.CYAN;
		colors[2] = Color.RED;
		colors[3] = Color.ORANGE;
		colors[4] = Color.YELLOW;
		colors[5] = Color.GREEN;
		colors[6] = Color.BLUE;

		while (true) {
			for (Joueur participant : participants) {
				if (!gagnant[participant.getId()]) {
					joueUnTour(participant);
				}
			}
		}
	}

	@Override
	public void setJoueur() { // Met en place la création des joueurs.

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
			// Si
		}
	}
}
