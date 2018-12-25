public class CarteAction extends CarteSaboteur {
	boolean sabotage; // Si la carte est dédiée au sabotage ou à l'aide.
	char type; // c, l, o (correspond à Chariot, lampe, outil).

	public CarteAction(boolean s, char t) {
		this.sabotage = s;
		this.type = t;
	}

	public boolean getSabotage(){
	    return sabotage;
    }

    public char getType(){
	    return type;
    }

    public void setSabotage(boolean b){
	    sabotage = b;
    }

    @Override
    public String toString() {
        String s = "--Carte Action--";

        if(type == 'c'){
            s += "\nchariot";
        }

        if(type == 'o'){
            s += "\noutils";
        }

        if(type == 'l'){
            s += "\nlampe";
        }

        return s;
    }
}
