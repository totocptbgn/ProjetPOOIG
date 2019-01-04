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

    @Override
    public String toString() {
        String s = "";

        if(type == 'c'){
            s += "chariot";
        }

        if(type == 'o'){
            s += "outils";
        }

        if(type == 'l'){
            s += "lampe";
        }
        if(sabotage) {
            s += " saboté(e)(s)";
        }
        if(!sabotage){
            s += " réparé(e)(s)";
        }

        return s;
    }
}
