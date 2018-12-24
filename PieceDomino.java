public class PieceDomino extends Piece{
    private int [] valeur; // Tableau de 2 valeurs contenant des valeurs entre 0 et 5 inclus.

    public PieceDomino(int i, int j) {
        this.valeur = new int[2];
        valeur[0] = i;
        valeur[1] = j;
    }
    public int getValeur(int i) { // Getter de valeur qui renvoie la valeur contenu à i de 0 ou 1.
        return this.valeur[i];
    }

    @Override
    public String toString() { // Renvoie un String donnant les valeurs du domino.
        return "[" + valeur[0] + "," + valeur[1] + "]";
    }
}
