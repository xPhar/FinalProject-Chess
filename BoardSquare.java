import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class BoardSquare implements MouseListener{
    private ChessPiece piece = null;
    private JPanel panel = new JPanel();
    private final Dimension position;
    private Color baseColor;
    private Color highlightedColor = new Color(0xF5D16E);
    private BoardSquare[][] board;
    private boolean isHighlighted = false;

    public BoardSquare(JPanel parentPanel, int xPos, int yPos, Color color, BoardSquare[][] board) {
        this.position = new Dimension(xPos, yPos);
        this.baseColor = color;
        this.board = board;
        panel.setBackground(color);
        panel.addMouseListener(this);
        parentPanel.add(panel, position);
    }

    public ChessPiece getPiece() {
        return this.piece;
    }

    public ChessPiece setPiece(ChessPiece piece) {
        this.piece = piece;
        panel.add(piece.getLabel());
        return this.piece;
    }

    public void removePiece() {
        this.piece = null;
    }

    public boolean hasPiece() {
        return piece != null;
    }

    public Dimension getPos() {
        return position;
    }

    public BoardSquare[][] getBoard() {
        return this.board;
    }

    public void setTargeted() {
        panel.setBackground(Color.MAGENTA);
    }

    public void setHighlighed() {
        panel.setBackground(highlightedColor);
    }

    public void removeHighlight() {
        panel.setBackground(baseColor);
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
        if (this.isHighlighted) {
            Main.getSelectedPiece().getBoardSquare().removePiece();
            this.setPiece(Main.getSelectedPiece());
            Main.resetTargetted(board);
        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
