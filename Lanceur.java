import javax.swing.*;
import java.awt.*;

/*
 * Projet POOIG :
 * @author Thomas Copt-Bignon
 * @author Hind Hamila
 */

public class Lanceur extends JFrame {

	public Lanceur() {
		this.setTitle("Projet POOIG");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container cont = this.getContentPane();
		JPanel panel = new JPanel();
		panel.add(new JLabel("Choisissez votre jeu :"));
		cont.add(panel);

		JButton domino = new JButton();
		panel.add(domino);
		domino.setText("Domino");
		domino.addActionListener((e) -> {
			this.setVisible(false);
			Domino d = new Domino();
			d.lancerPartie();
			setVisible(false);
		});

		JButton saboteur = new JButton();
		panel.add(saboteur);
		saboteur.setText("Saboteur");
		saboteur.addActionListener((e) -> {
			this.setVisible(false);
			Saboteur s = new Saboteur();
			s.lancerPartie();
			setVisible(false);
		});

		JButton puzzle = new JButton();
		panel.add(puzzle);
		puzzle.setText("Puzzle");
		puzzle.addActionListener((e) -> {
			this.setVisible(false);
			Puzzle p = new Puzzle();
			p.lancerPartie();
			setVisible(false);
		});

		JButton dominoGommette = new JButton();
		panel.add(dominoGommette);
		dominoGommette.setText("Domino-Gommette");
		dominoGommette.addActionListener((e) -> {
			this.setVisible(false);
			DominoGommette d = new DominoGommette();
			d.lancerPartie();
			setVisible(false);
		});


		setLocationRelativeTo(null);
		setVisible(true);
		pack();
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(()->{
			Lanceur l = new Lanceur();
		});

	}
}
