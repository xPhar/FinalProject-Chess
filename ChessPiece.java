import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;

public class ChessPiece implements MouseListener{
    private JLabel pieceLabel;
    private String name;
    private int value;
    private int identifier;
    private boolean alive = true;
    private boolean hasMoved = false;
    private boolean color;
    private BoardSquare currentSquare;
    private int row;
    private int column;
    static final boolean BLACK = false;
    static final boolean WHITE = true;

    public ChessPiece(int identifier, boolean color, BoardSquare startingSquare, int row, int column) {
        this.identifier = identifier;
        this.color = color;
        this.currentSquare = startingSquare;
        this.row = row;
        this.column = column;

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
        this.pieceLabel.addMouseListener(this);
    }

    public void removePiece() {
        this.alive = false;
    }

    public boolean isAlive() {
        return alive;
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

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void mouseClicked(MouseEvent e) {
        
    }

    public void mousePressed(MouseEvent e) {
        switch(identifier) {
            case 1:
                if (color == WHITE) {
                    currentSquare.getBoard()[row - 1][column].setTargeted();
                    if (!hasMoved) {
                        currentSquare.getBoard()[row - 2][column].setTargeted();
                    }
                    break;
                }
                currentSquare.getBoard()[row + 1][column].setTargeted();
                if (!hasMoved) {
                    currentSquare.getBoard()[row + 2][column].setTargeted();
                }
            case 2:
                for (int i = column - 2; i <= column + 2; i++) {
                    // TODO: FIX THIS
                    if (i == column) {
                        continue;
                    }
                    if (i == column + 2 || i == column - 2) {
                        currentSquare.getBoard()[row + 1][i].setTargeted();
                        currentSquare.getBoard()[row - 1][i].setTargeted();
                    }
                    else {
                        currentSquare.getBoard()[row + 2][i].setTargeted();
                        currentSquare.getBoard()[row - 2][i].setTargeted();
                    }
                }
        }
    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }
}
