public class CarteChemin extends CarteSaboteur {
    private boolean[] direction; // Tableau qui montre si l'accès vers la droite, gauche, haut, bas est autorisé.

    public CarteChemin(boolean[] d){
        this.direction = d;
    }
}
