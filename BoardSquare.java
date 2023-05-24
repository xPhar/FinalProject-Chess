import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

public class BoardSquare {
    private ChessPiece piece = null;
    private JPanel panel = new JPanel();
    private final Dimension position;
    private Color color;

    public BoardSquare(JPanel parentPanel, int xPos, int yPos, Color color) {
        this.position = new Dimension(xPos, yPos);
        this.color = color;
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
}
