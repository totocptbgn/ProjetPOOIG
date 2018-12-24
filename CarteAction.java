public class CarteAction extends CarteSaboteur {
    boolean sabotage; //si la carte est dédiée au sabotage ou à l'aide
    char type; // chariot,lampe,outil (c,l,o)

    public CarteAction(boolean s, char t) {
        this.sabotage = s;
        this.type = t;
    }
}
