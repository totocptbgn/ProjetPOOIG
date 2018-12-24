public class CarteChemin extends CarteSaboteur {
    private boolean[] direction; //tableau qui montre si l'accès vers la droite,gauche,haut,bas est autorisé

    public CarteChemin(boolean[] d){
        this.direction = d;
    }

    public boolean[] getDirection(){
        return direction;
    }
}
