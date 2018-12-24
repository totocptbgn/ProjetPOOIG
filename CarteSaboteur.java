public class CarteSaboteur extends Piece {
    private int ID;

    public CarteSaboteur(){
        super();
        // Mettre l'ID en AutoIncrement ?
    }

    public class CarteChemin {
        private boolean[] direction; // Tableau qui montre si l'accès vers la droite,gauche,haut,bas est autorisé

        public CarteChemin(boolean[] d){
            this.direction = d;
        }

    }

    public class CarteAction {
        boolean sabotage; // Si la carte est dédiée au sabotage ou à l'aide.
        char type; // c, l, o (correspond à Chariot, lampe, outil).

        public CarteAction(boolean s, char t) {
            this.sabotage = s;
            this.type = t;
        }
    }
}
