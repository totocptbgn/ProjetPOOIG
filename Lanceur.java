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
		this.setSize(new Dimension(300, 150));

		Container cont = this.getContentPane();
		JPanel panel = new JPanel();
		// panel.setLayout(new );
		panel.add(new JLabel("Launcher"));
		cont.add(panel);

		JButton domino = new JButton();
		panel.add(domino);
		domino.setText("Domino");
		domino.addActionListener((e) -> {
			this.setVisible(false);
			Domino d = new Domino();
			d.lancerPartie();
			System.exit(0);
		});

		JButton saboteur = new JButton();
		panel.add(saboteur);
		saboteur.setText("Saboteur");
		saboteur.addActionListener((e) -> {
			this.setVisible(false);
			Saboteur s = new Saboteur();
			s.lancerPartie();
			System.exit(0);
		});
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(()->{
			Lanceur l = new Lanceur();
			// l.pack();
			l.setVisible(true);
		});
	}
}
