import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

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
    private ArrayList<ChessPiece> chessPieceList;
    private int row;
    private int column;
    static final boolean BLACK = false;
    static final boolean WHITE = true;
    static final int PAWN = 1;
    static final int KNIGHT = 2;
    static final int BISHOP = 3;
    static final int ROOK = 4;
    static final int QUEEN = 5;
    static final int KING = 6;

    public ChessPiece(int identifier, boolean color, BoardSquare startingSquare, ArrayList<ChessPiece> pieceList, int row, int column) {
        this.identifier = identifier;
        this.color = color;
        this.currentSquare = startingSquare;
        this.row = row;
        this.column = column;
        this.chessPieceList = pieceList;

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

    public boolean getColor() {
        return color;
    }
    
    public BoardSquare getBoardSquare() {
        return currentSquare;
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
        Main.resetTargetted(this.getBoardSquare().getBoard());
        Main.updateHighLight(chessPieceList, this);
        switch(identifier) {
            case PAWN:
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
                break;
            case KNIGHT:
                for (int i = column - 2; i <= column + 2; i++) {
                    if (i < 0 || i > 7) {
                        continue;
                    }
                    if (i == column) {
                        continue;
                    }
                    if (i == column + 2 || i == column - 2) {
                        if (row + 1 < 7 && (!currentSquare.getBoard()[row + 1][i].hasPiece() || currentSquare.getBoard()[row + 1][i].getPiece().getColor() != this.color)) {
                            currentSquare.getBoard()[row + 1][i].setTargeted();
                        }
                        if (row - 1 >= 0 && (!currentSquare.getBoard()[row - 1][i].hasPiece() ||currentSquare.getBoard()[row - 1][i].getPiece().getColor() != this.color)) {
                            currentSquare.getBoard()[row - 1][i].setTargeted();
                        }
                    }
                    else {
                        if (row + 2 < 7 &&(!currentSquare.getBoard()[row + 2][i].hasPiece() || currentSquare.getBoard()[row + 2][i].getPiece().getColor() != this.color)) {
                        currentSquare.getBoard()[row + 2][i].setTargeted();
                        }
                        if (row - 2 >= 0 &&(!currentSquare.getBoard()[row - 2][i].hasPiece() || currentSquare.getBoard()[row - 2][i].getPiece().getColor() != this.color)) {
                            currentSquare.getBoard()[row - 2][i].setTargeted();
                        }
                    }
                }
                break;
            case BISHOP:
                for (int i = 1; i < 8; i++) {
                    if (row + i == 8 || column + i == 8) {
                        break;
                    }
                    if (currentSquare.getBoard()[row + i][column + i].hasPiece()) {
                        if (currentSquare.getBoard()[row + i][column + i].getPiece().getColor() == !this.color) {
                            currentSquare.getBoard()[row + i][column + i].setTargeted();
                        }
                        break;
                    }
                    currentSquare.getBoard()[row + i][column + i].setTargeted();
                }
                for (int i = 1; i < 8; i++) {
                    if (row + i == 8 || column - i < 0) {
                        break;
                    }
                    if (currentSquare.getBoard()[row + i][column - i].hasPiece()) {
                        if (currentSquare.getBoard()[row + i][column - i].getPiece().getColor() == !this.color) {
                            currentSquare.getBoard()[row + i][column - i].setTargeted();
                        }
                        break;
                    }
                    currentSquare.getBoard()[row + i][column - i].setTargeted();
                }
                for (int i = 1; i < 8; i++) {
                    if (row - i < 0 || column + i == 8) {
                        break;
                    }
                    if (currentSquare.getBoard()[row - i][column + i].hasPiece()) {
                        if (currentSquare.getBoard()[row - i][column + i].getPiece().getColor() == !this.color) {
                            currentSquare.getBoard()[row - i][column + i].setTargeted();
                        }
                        break;
                    }
                    currentSquare.getBoard()[row - i][column + i].setTargeted();
                }
                for (int i = 1; i < 8; i++) {
                    if (row - i < 0|| column - i < 0) {
                        break;
                    }
                    if (currentSquare.getBoard()[row - i][column - i].hasPiece()) {
                        if (currentSquare.getBoard()[row - i][column - i].getPiece().getColor() == !this.color) {
                            currentSquare.getBoard()[row - i][column - i].setTargeted();
                        }
                        break;
                    }
                    currentSquare.getBoard()[row - i][column - i].setTargeted();
                }
                break;
            case ROOK:
                for (int i = row + 1; i < 8; i++) {
                    if (currentSquare.getBoard()[i][column].hasPiece()) {
                        if (currentSquare.getBoard()[i][column].getPiece().getColor() == !this.color) {
                            currentSquare.getBoard()[i][column].setTargeted();
                        }
                        break;
                    }
                    currentSquare.getBoard()[i][column].setTargeted();
                }
                for (int i = row - 1; i >= 0; i--) {
                    if (currentSquare.getBoard()[i][column].hasPiece()) {
                        if (currentSquare.getBoard()[i][column].getPiece().getColor() == !this.color) {
                            currentSquare.getBoard()[i][column].setTargeted();
                        }
                        break;
                    }
                    currentSquare.getBoard()[i][column].setTargeted();
                }
                for (int i = column + 1; i < 8; i++) {
                    if (currentSquare.getBoard()[row][i].hasPiece()) {
                        if (currentSquare.getBoard()[row][i].getPiece().getColor() == !this.color) {
                            currentSquare.getBoard()[row][i].setTargeted();
                        }
                        break;
                    }
                    currentSquare.getBoard()[row][i].setTargeted();
                }
                for (int i = column - 1; i >= 0; i--) {
                    if (currentSquare.getBoard()[row][i].hasPiece()) {
                        if (currentSquare.getBoard()[row][i].getPiece().getColor() == !this.color) {
                            currentSquare.getBoard()[row][i].setTargeted();
                        }
                        break;
                    }
                    currentSquare.getBoard()[row][i].setTargeted();
                }
                break;
            case QUEEN:
                for (int i = 1; i < 8; i++) {
                    if (row + i == 8 || column + i == 8) {
                        break;
                    }
                    if (currentSquare.getBoard()[row + i][column + i].hasPiece()) {
                        if (currentSquare.getBoard()[row + i][column + i].getPiece().getColor() == !this.color) {
                            currentSquare.getBoard()[row + i][column + i].setTargeted();
                        }
                        break;
                    }
                    currentSquare.getBoard()[row + i][column + i].setTargeted();
                }
                for (int i = 1; i < 8; i++) {
                    if (row + i == 8 || column - i < 0) {
                        break;
                    }
                    if (currentSquare.getBoard()[row + i][column - i].hasPiece()) {
                        if (currentSquare.getBoard()[row + i][column - i].getPiece().getColor() == !this.color) {
                            currentSquare.getBoard()[row + i][column - i].setTargeted();
                        }
                        break;
                    }
                    currentSquare.getBoard()[row + i][column - i].setTargeted();
                }
                for (int i = 1; i < 8; i++) {
                    if (row - i < 0 || column + i == 8) {
                        break;
                    }
                    if (currentSquare.getBoard()[row - i][column + i].hasPiece()) {
                        if (currentSquare.getBoard()[row - i][column + i].getPiece().getColor() == !this.color) {
                            currentSquare.getBoard()[row - i][column + i].setTargeted();
                        }
                        break;
                    }
                    currentSquare.getBoard()[row - i][column + i].setTargeted();
                }
                for (int i = 1; i < 8; i++) {
                    if (row - i < 0|| column - i < 0) {
                        break;
                    }
                    if (currentSquare.getBoard()[row - i][column - i].hasPiece()) {
                        if (currentSquare.getBoard()[row - i][column - i].getPiece().getColor() == !this.color) {
                            currentSquare.getBoard()[row - i][column - i].setTargeted();
                        }
                        break;
                    }
                    currentSquare.getBoard()[row - i][column - i].setTargeted();
                }
                for (int i = row + 1; i < 8; i++) {
                    if (currentSquare.getBoard()[i][column].hasPiece()) {
                        if (currentSquare.getBoard()[i][column].getPiece().getColor() == !this.color) {
                            currentSquare.getBoard()[i][column].setTargeted();
                        }
                        break;
                    }
                    currentSquare.getBoard()[i][column].setTargeted();
                }
                for (int i = row - 1; i >= 0; i--) {
                    if (currentSquare.getBoard()[i][column].hasPiece()) {
                        if (currentSquare.getBoard()[i][column].getPiece().getColor() == !this.color) {
                            currentSquare.getBoard()[i][column].setTargeted();
                        }
                        break;
                    }
                    currentSquare.getBoard()[i][column].setTargeted();
                }
                for (int i = column + 1; i < 8; i++) {
                    if (currentSquare.getBoard()[row][i].hasPiece()) {
                        if (currentSquare.getBoard()[row][i].getPiece().getColor() == !this.color) {
                            currentSquare.getBoard()[row][i].setTargeted();
                        }
                        break;
                    }
                    currentSquare.getBoard()[row][i].setTargeted();
                }
                for (int i = column - 1; i >= 0; i--) {
                    if (currentSquare.getBoard()[row][i].hasPiece()) {
                        if (currentSquare.getBoard()[row][i].getPiece().getColor() == !this.color) {
                            currentSquare.getBoard()[row][i].setTargeted();
                        }
                        break;
                    }
                    currentSquare.getBoard()[row][i].setTargeted();
                }
                break;
        }
    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }
}
