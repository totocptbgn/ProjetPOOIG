import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Saboteur extends Jeu {
    ArrayList<CarteSaboteur> paquet []; //meme chose que pour domino, la pioche est à l'indice 0 du paquet
    Plateau plateau;

    public void setJoueur() { // Mets en place la création des joueurs, elle est appelée en début de partie.
        boolean b = true;
        while (b){
            System.out.println("Combien de joueurs participent ?");
            Scanner sc = new Scanner(System.in);
            int nbrJ = 1;
            try {
                nbrJ = sc.nextInt();
                if (nbrJ < 2 || nbrJ > 4) {
                    System.err.println("Rentrez un chiffre entre 2 et 4 inclus.");
                    b = true;
                } else {
                    this.participants = new Joueur[nbrJ];
                    b = false;
                }
            } catch (InputMismatchException e){
                System.err.println("Rentrez un nombre valide.");
                b = true;
            }
        }
        for (int i = 0; i < participants.length; i++) {
            System.out.println("Quel est le nom du joueur n°" + (i+1) + " ?");
            participants[i] = new Joueur(i);
        }
    }

    @Override
    public void lancerPartie() {
        this.setJoueur();

    }

    @Override
    public void joueUnTour(Joueur j) {

    }

}
