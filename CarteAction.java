public class CarteAction extends CarteSaboteur {
	boolean sabotage; // Si la carte est dédiée au sabotage ou à l'aide.
	char type; // c, l, o (correspond à Chariot, lampe, outil).

	public CarteAction(boolean s, char t) {
		this.sabotage = s;
		this.type = t;
	}
}
