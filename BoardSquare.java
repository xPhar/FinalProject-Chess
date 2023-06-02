import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

public class BoardSquare {
    private ChessPiece piece = null;
    private JPanel panel = new JPanel();
    private final Dimension position;
    private Color baseColor;
    private Color highlightedColor = new Color(0xF5D16E);
    private BoardSquare[][] board;

    public BoardSquare(JPanel parentPanel, int xPos, int yPos, Color color, BoardSquare[][] board) {
        this.position = new Dimension(xPos, yPos);
        this.baseColor = color;
        this.board = board;
        panel.setBackground(color);
        parentPanel.add(panel, position);
    }

    public ChessPiece getPiece() {
        return this.piece;
    }

    public void setPiece(ChessPiece piece) {
        this.piece = piece;
        panel.add(piece.getLabel());
    }

    public void removePiece() {
        this.piece = null;
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
}
