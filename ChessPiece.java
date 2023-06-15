import javax.swing.JLabel;
import java.awt.Dimension;

import javax.swing.ImageIcon;

public class ChessPiece{
    private JLabel pieceLabel;
    private String name;
    private int value;
    private int identifier;
    private boolean hasMoved = false;
    private boolean color;
    private int xPos;
    private int yPos;
    private BoardSquare currentSquare;
    static final boolean BLACK = false;
    static final boolean WHITE = true;
    static final int PAWN = 1;
    static final int KNIGHT = 2;
    static final int BISHOP = 3;
    static final int ROOK = 4;
    static final int QUEEN = 5;
    static final int KING = 6;

    public ChessPiece(int identifier, boolean color, BoardSquare startingSquare, int xPos, int yPos) {
        this.identifier = identifier;
        this.color = color;
        this.currentSquare = startingSquare;
        this.xPos = xPos;
        this.yPos = yPos;

        if (identifier == PAWN) {
            this.name = "Pawn";
            this.value = 1;
            if (color == WHITE) {
                this.pieceLabel = new JLabel(new ImageIcon("Pieces/WhitePawn.png"));
            }
            else {
                this.pieceLabel = new JLabel(new ImageIcon("Pieces/BlackPawn.png"));
            }
        }
        else if (identifier == KNIGHT) {
            this.name = "Knight";
            this.value = 3;
            if (color == WHITE) {
                this.pieceLabel = new JLabel(new ImageIcon("Pieces/WhiteKnight.png"));
            }
            else {
                this.pieceLabel = new JLabel(new ImageIcon("Pieces/BlackKnight.png"));
            }
        }
        else if (identifier == BISHOP) {
            this.name = "Bishop";
            this.value = 3;
            if (color == WHITE) {
                this.pieceLabel = new JLabel(new ImageIcon("Pieces/WhiteBishop.png"));
            }
            else {
                this.pieceLabel = new JLabel(new ImageIcon("Pieces/BlackBishop.png"));
            }
        }
        else if (identifier == ROOK) {
            this.name = "Rook";
            this.value = 5;
            if (color == WHITE) {
                this.pieceLabel = new JLabel(new ImageIcon("Pieces/WhiteRook.png"));
            }
            else {
                this.pieceLabel = new JLabel(new ImageIcon("Pieces/BlackRook.png"));
            }
        }
        else if (identifier == QUEEN) {
            this.name = "Queen";
            this.value = 9;
            if (color == WHITE) {
                this.pieceLabel = new JLabel(new ImageIcon("Pieces/WhiteQueen.png"));
            }
            else {
                this.pieceLabel = new JLabel(new ImageIcon("Pieces/BlackQueen.png"));
            }
        }
        else if (identifier == KING) {
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
        Main.getPieceList().remove(this);
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public int getIdentifier() {
        return identifier;
    }

    public JLabel getLabel() {
        return pieceLabel;
    }

    public boolean getColor() {
        return color;
    }
    
    public BoardSquare getBoardSquare() {
        return currentSquare;
    }

    public void setBoardSquare(BoardSquare square) {
        this.currentSquare = square;
    }

    public void setRow(int row) {
        this.xPos = row;
    }

    public void setColumn(int column) {
        this.yPos = column;
    }

    public void pieceMoved() {
        this.hasMoved = true;
    }

    public boolean pieceHasMoved() {
        return hasMoved;
    }
}
