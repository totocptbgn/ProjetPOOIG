import java.util.Scanner;

public class Joueur {
	private String nom;
	private int id;
	private int score;

	public Joueur(String nom, int id){ // Constructeur basique avec nom et id.
		this.nom = nom;
		this.score = 0;
		this.id = id;
	}

	public Joueur(int id){ // Constructeur avec seulement id, le nom étant obtenu avec le Scanner.
		Scanner sc = new Scanner(System.in);
		this.nom = sc.nextLine();
		this.score = 0;
		this.id = id;
	}

	public String getNom() { // Getter du nom.
		return nom;
	}

	public int getId() { // Getter de l'id.
		return id;
	}
}
