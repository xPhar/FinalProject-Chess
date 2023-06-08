import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class BoardSquare implements MouseListener{
    private ChessPiece piece = null;
    private JPanel panel = new JPanel();
    private final int xPos;
    private final int yPos;
    private Color baseColor;
    private Color highlightedColor = new Color(0xF5D16E);
    private boolean isHighlighted = false;
    private boolean isTargeted = false;

    public BoardSquare(JPanel parentPanel, int xPos, int yPos, Color color) {
        this.baseColor = color;
        this.xPos = xPos;
        this.yPos = yPos;
        panel.setBackground(color);
        panel.addMouseListener(this);
        parentPanel.add(panel, new Dimension(xPos, yPos));
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
        if (this.piece == null) { 
            return;
        }
        this.panel.remove(this.piece.getLabel());
        this.piece = null;
    }

    public boolean hasPiece() {
        return piece != null;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setTargeted() {
        isTargeted = true;
        panel.setBackground(Color.MAGENTA);
    }

    public void setHighlighed() {
        isHighlighted = true;
        panel.setBackground(highlightedColor);
    }

    public void removeHighlight() {
        isHighlighted = false;
        isTargeted = false;
        panel.setBackground(baseColor);
    }

    public void mousePressed(MouseEvent e) {
        if (this.isTargeted) {
            return;
        }
        if (hasPiece() && getPiece().getColor() == Main.getTurn()) {
            Main.resetTargetted();
            Main.updateHighLight(this.getPiece());
            switch(getPiece().getIdentifier()) {
                case ChessPiece.PAWN:
                    if (getPiece().getColor() == ChessPiece.WHITE) {
                        if (yPos - 1 >= 0 && Main.getBoard()[xPos - 1][yPos - 1].hasPiece() && Main.getBoard()[xPos - 1][yPos - 1].getPiece().getColor() != getPiece().getColor()) {
                            Main.getBoard()[xPos - 1][yPos - 1].setTargeted();
                        }
                        if (yPos + 1 < 8 && Main.getBoard()[xPos - 1][yPos + 1].hasPiece() && Main.getBoard()[xPos - 1][yPos + 1].getPiece().getColor() != getPiece().getColor()) {
                            Main.getBoard()[xPos - 1][yPos + 1].setTargeted();
                        }
                        if (Main.getBoard()[xPos - 1][yPos].hasPiece()) {
                            break;
                        }
                        if (!Main.getBoard()[xPos - 1][yPos].hasPiece()) {
                            Main.getBoard()[xPos - 1][yPos].setTargeted();
                        }
                        if (!getPiece().pieceHasMoved() && !Main.getBoard()[xPos - 2][yPos].hasPiece()) {
                            Main.getBoard()[xPos - 2][yPos].setTargeted();
                        }
                        break;
                    }
                    if (yPos - 1 >= 0 && Main.getBoard()[xPos + 1][yPos - 1].hasPiece() && Main.getBoard()[xPos + 1][yPos - 1].getPiece().getColor() != getPiece().getColor()) {
                        Main.getBoard()[xPos + 1][yPos - 1].setTargeted();
                    }
                    if (yPos + 1 < 8 && Main.getBoard()[xPos + 1][yPos + 1].hasPiece() && Main.getBoard()[xPos + 1][yPos + 1].getPiece().getColor() != getPiece().getColor()) {
                        Main.getBoard()[xPos + 1][yPos + 1].setTargeted();
                    }
                    if (!Main.getBoard()[xPos + 1][yPos].hasPiece()) {
                        Main.getBoard()[xPos + 1][yPos].setTargeted();
                    }
                    if (!getPiece().pieceHasMoved() && !Main.getBoard()[xPos + 2][yPos].hasPiece()) {
                        Main.getBoard()[xPos + 2][yPos].setTargeted();
                    }
                    break;
                case ChessPiece.KNIGHT:
                    for (int i = yPos - 2; i <= yPos + 2; i++) {
                        if (i < 0 || i > 7) {
                            continue;
                        }
                        if (i == yPos) {
                            continue;
                        }
                        if (i == yPos + 2 || i == yPos - 2) {
                            if (xPos + 1 < 8 && (!Main.getBoard()[xPos + 1][i].hasPiece() || Main.getBoard()[xPos + 1][i].getPiece().getColor() != getPiece().getColor())) {
                                Main.getBoard()[xPos + 1][i].setTargeted();
                            }
                            if (xPos - 1 >= 0 && (!Main.getBoard()[xPos - 1][i].hasPiece() ||Main.getBoard()[xPos - 1][i].getPiece().getColor() != getPiece().getColor())) {
                                Main.getBoard()[xPos - 1][i].setTargeted();
                            }
                        }
                        else {
                            if (xPos + 2 < 8 &&(!Main.getBoard()[xPos + 2][i].hasPiece() || Main.getBoard()[xPos + 2][i].getPiece().getColor() != getPiece().getColor())) {
                            Main.getBoard()[xPos + 2][i].setTargeted();
                            }
                            if (xPos - 2 >= 0 &&(!Main.getBoard()[xPos - 2][i].hasPiece() || Main.getBoard()[xPos - 2][i].getPiece().getColor() != getPiece().getColor())) {
                                Main.getBoard()[xPos - 2][i].setTargeted();
                            }
                        }
                    }
                    break;
                case ChessPiece.BISHOP:
                    for (int i = 1; i < 8; i++) {
                        if (xPos + i == 8 || yPos + i == 8) {
                            break;
                        }
                        if (Main.getBoard()[xPos + i][yPos + i].hasPiece()) {
                            if (Main.getBoard()[xPos + i][yPos + i].getPiece().getColor()!= getPiece().getColor()) {
                                Main.getBoard()[xPos + i][yPos + i].setTargeted();
                            }
                            break;
                        }
                        Main.getBoard()[xPos + i][yPos + i].setTargeted();
                    }
                    for (int i = 1; i < 8; i++) {
                        if (xPos + i == 8 || yPos - i < 0) {
                            break;
                        }
                        if (Main.getBoard()[xPos + i][yPos - i].hasPiece()) {
                            if (Main.getBoard()[xPos + i][yPos - i].getPiece().getColor() != getPiece().getColor()) {
                                Main.getBoard()[xPos + i][yPos - i].setTargeted();
                            }
                            break;
                        }
                        Main.getBoard()[xPos + i][yPos - i].setTargeted();
                    }
                    for (int i = 1; i < 8; i++) {
                        if (xPos - i < 0 || yPos + i == 8) {
                            break;
                        }
                        if (Main.getBoard()[xPos - i][yPos + i].hasPiece()) {
                            if (Main.getBoard()[xPos - i][yPos + i].getPiece().getColor() != getPiece().getColor()) {
                                Main.getBoard()[xPos - i][yPos + i].setTargeted();
                            }
                            break;
                        }
                        Main.getBoard()[xPos - i][yPos + i].setTargeted();
                    }
                    for (int i = 1; i < 8; i++) {
                        if (xPos - i < 0|| yPos - i < 0) {
                            break;
                        }
                        if (Main.getBoard()[xPos - i][yPos - i].hasPiece()) {
                            if (Main.getBoard()[xPos - i][yPos - i].getPiece().getColor() != getPiece().getColor()) {
                                Main.getBoard()[xPos - i][yPos - i].setTargeted();
                            }
                            break;
                        }
                        Main.getBoard()[xPos - i][yPos - i].setTargeted();
                    }
                    break;
                case ChessPiece.ROOK:
                    for (int i = xPos + 1; i < 8; i++) {
                        if (Main.getBoard()[i][yPos].hasPiece()) {
                            if (Main.getBoard()[i][yPos].getPiece().getColor() != getPiece().getColor()) {
                                Main.getBoard()[i][yPos].setTargeted();
                            }
                            break;
                        }
                        Main.getBoard()[i][yPos].setTargeted();
                    }
                    for (int i = xPos - 1; i >= 0; i--) {
                        if (Main.getBoard()[i][yPos].hasPiece()) {
                            if (Main.getBoard()[i][yPos].getPiece().getColor() != getPiece().getColor()) {
                                Main.getBoard()[i][yPos].setTargeted();
                            }
                            break;
                        }
                        Main.getBoard()[i][yPos].setTargeted();
                    }
                    for (int i = yPos + 1; i < 8; i++) {
                        if (Main.getBoard()[xPos][i].hasPiece()) {
                            if (Main.getBoard()[xPos][i].getPiece().getColor() != getPiece().getColor()) {
                                Main.getBoard()[xPos][i].setTargeted();
                            }
                            break;
                        }
                        Main.getBoard()[xPos][i].setTargeted();
                    }
                    for (int i = yPos - 1; i >= 0; i--) {
                        if (Main.getBoard()[xPos][i].hasPiece()) {
                            if (Main.getBoard()[xPos][i].getPiece().getColor() != getPiece().getColor()) {
                                Main.getBoard()[xPos][i].setTargeted();
                            }
                            break;
                        }
                        Main.getBoard()[xPos][i].setTargeted();
                    }
                    break;
                case ChessPiece.QUEEN:
                    for (int i = 1; i < 8; i++) {
                        if (xPos + i == 8 || yPos + i == 8) {
                            break;
                        }
                        if (Main.getBoard()[xPos + i][yPos + i].hasPiece()) {
                            if (Main.getBoard()[xPos + i][yPos + i].getPiece().getColor() != getPiece().getColor()) {
                                Main.getBoard()[xPos + i][yPos + i].setTargeted();
                            }
                            break;
                        }
                        Main.getBoard()[xPos + i][yPos + i].setTargeted();
                    }
                    for (int i = 1; i < 8; i++) {
                        if (xPos + i == 8 || yPos - i < 0) {
                            break;
                        }
                        if (Main.getBoard()[xPos + i][yPos - i].hasPiece()) {
                            if (Main.getBoard()[xPos + i][yPos - i].getPiece().getColor() != getPiece().getColor()) {
                                Main.getBoard()[xPos + i][yPos - i].setTargeted();
                            }
                            break;
                        }
                        Main.getBoard()[xPos + i][yPos - i].setTargeted();
                    }
                    for (int i = 1; i < 8; i++) {
                        if (xPos - i < 0 || yPos + i == 8) {
                            break;
                        }
                        if (Main.getBoard()[xPos - i][yPos + i].hasPiece()) {
                            if (Main.getBoard()[xPos - i][yPos + i].getPiece().getColor() != getPiece().getColor()) {
                                Main.getBoard()[xPos - i][yPos + i].setTargeted();
                            }
                            break;
                        }
                        Main.getBoard()[xPos - i][yPos + i].setTargeted();
                    }
                    for (int i = 1; i < 8; i++) {
                        if (xPos - i < 0 || yPos - i < 0) {
                            break;
                        }
                        if (Main.getBoard()[xPos - i][yPos - i].hasPiece()) {
                            if (Main.getBoard()[xPos - i][yPos - i].getPiece().getColor() != getPiece().getColor()) {
                                Main.getBoard()[xPos - i][yPos - i].setTargeted();
                            }
                            break;
                        }
                        Main.getBoard()[xPos - i][yPos - i].setTargeted();
                    }
                    for (int i = xPos + 1; i < 8; i++) {
                        if (Main.getBoard()[i][yPos].hasPiece()) {
                            if (Main.getBoard()[i][yPos].getPiece().getColor() != getPiece().getColor()) {
                                Main.getBoard()[i][yPos].setTargeted();
                            }
                            break;
                        }
                        Main.getBoard()[i][yPos].setTargeted();
                    }
                    for (int i = xPos - 1; i >= 0; i--) {
                        if (Main.getBoard()[i][yPos].hasPiece()) {
                            if (Main.getBoard()[i][yPos].getPiece().getColor() != getPiece().getColor()) {
                                Main.getBoard()[i][yPos].setTargeted();
                            }
                            break;
                        }
                        Main.getBoard()[i][yPos].setTargeted();
                    }
                    for (int i = yPos + 1; i < 8; i++) {
                        if (Main.getBoard()[xPos][i].hasPiece()) {
                            if (Main.getBoard()[xPos][i].getPiece().getColor() != getPiece().getColor()) {
                                Main.getBoard()[xPos][i].setTargeted();
                            }
                            break;
                        }
                        Main.getBoard()[xPos][i].setTargeted();
                    }
                    for (int i = yPos - 1; i >= 0; i--) {
                        if (Main.getBoard()[xPos][i].hasPiece()) {
                            if (Main.getBoard()[xPos][i].getPiece().getColor() != getPiece().getColor()) {
                                Main.getBoard()[xPos][i].setTargeted();
                            }
                            break;
                        }
                        Main.getBoard()[xPos][i].setTargeted();
                    }
                    break;
                case ChessPiece.KING:
                    if (!getPiece().pieceHasMoved() && Main.getBoard()[xPos][7].hasPiece() && !Main.getBoard()[xPos][7].getPiece().pieceHasMoved()) {
                        if (!Main.getBoard()[xPos][5].hasPiece() && !Main.getBoard()[xPos][6].hasPiece()) {
                            Main.getBoard()[xPos][7].setTargeted();
                        }
                    }
                    if (xPos - 1 >= 0) {
                        if (Main.getBoard()[xPos - 1][yPos].hasPiece()) {
                            if (Main.getBoard()[xPos - 1][yPos].getPiece().getColor() != getPiece().getColor()) {
                                Main.getBoard()[xPos - 1][yPos].setTargeted();
                            }
                        }
                        else {
                            Main.getBoard()[xPos - 1][yPos].setTargeted();
                        }
                    }
                    if (xPos - 1 >= 0 && yPos - 1 >= 0) {
                        if (Main.getBoard()[xPos - 1][yPos - 1].hasPiece()) {
                            if (Main.getBoard()[xPos - 1][yPos - 1].getPiece().getColor() != getPiece().getColor()) {
                                Main.getBoard()[xPos - 1][yPos - 1].setTargeted();
                            }
                        }
                        else {
                            Main.getBoard()[xPos - 1][yPos - 1].setTargeted();
                        }
                    }
                    if (yPos - 1 >= 0) {
                        if (Main.getBoard()[xPos][yPos - 1].hasPiece()) {
                            if (Main.getBoard()[xPos][yPos - 1].getPiece().getColor() != getPiece().getColor()) {
                                Main.getBoard()[xPos][yPos - 1].setTargeted();
                            }
                        }
                        else {
                            Main.getBoard()[xPos][yPos - 1].setTargeted();
                        }
                    }
                    if (xPos + 1 < 8 && yPos - 1 >= 0) {
                        if (Main.getBoard()[xPos + 1][yPos - 1].hasPiece()) {
                            if (Main.getBoard()[xPos + 1][yPos - 1].getPiece().getColor() != getPiece().getColor()) {
                                Main.getBoard()[xPos + 1][yPos - 1].setTargeted();
                            }
                        }
                        else {
                            Main.getBoard()[xPos + 1][yPos - 1].setTargeted();
                        }
                    }
                    if (xPos + 1 < 8) {
                        if (Main.getBoard()[xPos + 1][yPos].hasPiece()) {
                            if (Main.getBoard()[xPos + 1][yPos].getPiece().getColor() != getPiece().getColor()) {
                                Main.getBoard()[xPos + 1][yPos].setTargeted();
                            }
                        }
                        else {
                            Main.getBoard()[xPos + 1][yPos].setTargeted();
                        }
                    }
                    if (xPos + 1 < 8 && yPos + 1 < 8) {
                        if (Main.getBoard()[xPos + 1][yPos + 1].hasPiece()) {
                            if (Main.getBoard()[xPos + 1][yPos + 1].getPiece().getColor() != getPiece().getColor()) {
                                Main.getBoard()[xPos + 1][yPos + 1].setTargeted();
                            }
                        }
                        else {
                            Main.getBoard()[xPos + 1][yPos + 1].setTargeted();
                        }
                    }
                    if (yPos + 1 < 8) {
                        if (Main.getBoard()[xPos][yPos + 1].hasPiece()) {
                            if (Main.getBoard()[xPos][yPos + 1].getPiece().getColor() != getPiece().getColor()) {
                                Main.getBoard()[xPos][yPos + 1].setTargeted();
                            }
                        }
                        else {
                            Main.getBoard()[xPos][yPos + 1].setTargeted();
                        }
                    }
                    if (xPos - 1 >= 0 && yPos + 1 < 8) {
                        if (Main.getBoard()[xPos - 1][yPos + 1].hasPiece()) {
                            if (Main.getBoard()[xPos - 1][yPos + 1].getPiece().getColor() != getPiece().getColor()) {
                                Main.getBoard()[xPos - 1][yPos + 1].setTargeted();
                            }
                        }
                        else {
                            Main.getBoard()[xPos - 1][yPos + 1].setTargeted();
                        }
                    }
                    break;
                }
        }
    }

    public boolean isTargeted() {
        return isTargeted;
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
        if (this.isHighlighted) {
            return;
        }
        if (this.isTargeted) {
            Main.getSelectedPiece().getBoardSquare().removePiece();
            removePiece();
            this.setPiece(Main.getSelectedPiece());
            getPiece().setBoardSquare(this);
            getPiece().pieceMoved();
            Main.resetTargetted();
            Main.toggleTurn();

            if (this.getPiece().getColor() == ChessPiece.BLACK) {
                if (Main.inCheck(Main.getBlackKing())) {
                    System.out.println("BLACK'S IN CHECK!");
                }
            }
            else {
                if (Main.inCheck(Main.getWhiteKing())) {
                    System.out.println("WHITE'S IN CHECK!");
                }
            }
        }
        else {
            Main.resetTargetted();
        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
