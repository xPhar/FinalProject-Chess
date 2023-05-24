import javax.swing.JLabel;
import java.awt.Dimension;
import javax.swing.ImageIcon;

public class ChessPiece {
    private JLabel pieceLabel;
    private String name;
    private int value;
    private int identifier;
    private boolean alive = true;
    private boolean hasMoved = false;
    private boolean color;
    static final boolean BLACK = false;
    static final boolean WHITE = true; 

    public ChessPiece(int identifier, boolean color) {
        this.identifier = identifier;
        this.color = color;

        if (identifier == 1) {
            this.name = "Pawn";
            this.value = 1;
            if (color == WHITE) {
                this.pieceLabel = new JLabel(new ImageIcon("Pieces/WhitePawn.png"));
            }
            else {
                this.pieceLabel = new JLabel(new ImageIcon("Pieces/BlackPawn.png"));
            }
        }
        else if (identifier == 2) {
            this.name = "Knight";
            this.value = 3;
            if (color == WHITE) {
                this.pieceLabel = new JLabel(new ImageIcon("Pieces/WhiteKnight.png"));
            }
            else {
                this.pieceLabel = new JLabel(new ImageIcon("Pieces/BlackKnight.png"));
            }
        }
        else if (identifier == 3) {
            this.name = "Bishop";
            this.value = 3;
            if (color == WHITE) {
                this.pieceLabel = new JLabel(new ImageIcon("Pieces/WhiteBishop.png"));
            }
            else {
                this.pieceLabel = new JLabel(new ImageIcon("Pieces/BlackBishop.png"));
            }
        }
        else if (identifier == 4) {
            this.name = "Rook";
            this.value = 5;
            if (color == WHITE) {
                this.pieceLabel = new JLabel(new ImageIcon("Pieces/WhiteRook.png"));
            }
            else {
                this.pieceLabel = new JLabel(new ImageIcon("Pieces/BlackRook.png"));
            }
        }
        else if (identifier == 5) {
            this.name = "Queen";
            this.value = 9;
            if (color == WHITE) {
                this.pieceLabel = new JLabel(new ImageIcon("Pieces/WhiteQueen.png"));
            }
            else {
                this.pieceLabel = new JLabel(new ImageIcon("Pieces/BlackQueen.png"));
            }
        }
        else if (identifier == 6) {
            this.name = "King";
            this.value = 99;
            if (color == WHITE) {
                this.pieceLabel = new JLabel(new ImageIcon("Pieces/WhiteKing.png"));
            }
            else {
                this.pieceLabel = new JLabel(new ImageIcon("Pieces/BlackKing.png"));
            }
        }
        this.pieceLabel.setPreferredSize(new Dimension(100, 100));
    }

    public void removePiece() {
        this.alive = false;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public int getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    public int getIdentifier() {
        return this.identifier;
    }

    public JLabel getLabel() {
        return this.pieceLabel;
    }
}
