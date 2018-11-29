public class CaseDomino extends Case {
    int side;       // 0 ou 1

    public CaseDomino(){
        super();
        this.side = -1;
    }

    public void PoserPiece(PieceDomino p, int side){
        super.PoserPiece(p);
        this.side = side;
    }

    @Override
    public int getValeur() {
        PieceDomino p = (PieceDomino) getPiece();
        if (p == null) {
            return -1;
        }
        return ((PieceDomino) getPiece()).getValeur(side);
    }
}
