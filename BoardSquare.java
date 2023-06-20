import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
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
    private boolean selectingPromotion = false;
    private JLabel replacementLabel = null;

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
        removePiece();
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

    public ArrayList<BoardSquare> getLegalMoves() {
        ArrayList<BoardSquare> legalMoves = new ArrayList<BoardSquare>();
        switch(getPiece().getIdentifier()) {
                case ChessPiece.PAWN:
                    if (getPiece().getColor() == ChessPiece.WHITE) {
                        if (yPos - 1 >= 0 && Main.getBoard()[xPos - 1][yPos - 1].hasPiece() && Main.getBoard()[xPos - 1][yPos - 1].getPiece().getColor() != getPiece().getColor()) {
                            if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos - 1][yPos - 1])) {
                                legalMoves.add(Main.getBoard()[xPos - 1][yPos - 1]);
                            }
                        }
                        else if (yPos - 1 >= 0 && Main.getBoard()[xPos][yPos - 1].hasPiece() && Main.getBoard()[xPos][yPos - 1].getPiece().getColor() != getPiece().getColor()){
                            if (Main.getLastMoved() == Main.getBoard()[xPos][yPos - 1].getPiece() && !moveCausesCheck(getPiece(), Main.getBoard()[xPos - 1][yPos - 1])
                                    && Main.getLastMoved().getBoardSquare().getxPos() == 3) {
                                legalMoves.add(Main.getBoard()[xPos - 1][yPos - 1]);
                            }
                        }
                        if (yPos + 1 < 8 && Main.getBoard()[xPos - 1][yPos + 1].hasPiece() && Main.getBoard()[xPos - 1][yPos + 1].getPiece().getColor() != getPiece().getColor()) {
                            if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos - 1][yPos + 1])) {
                                legalMoves.add(Main.getBoard()[xPos - 1][yPos + 1]);
                            }
                        }
                        else if (yPos + 1 < 8 && Main.getBoard()[xPos][yPos + 1].hasPiece() && Main.getBoard()[xPos][yPos + 1].getPiece().getColor() != getPiece().getColor()
                                    && Main.getLastMoved().getBoardSquare().getxPos() == 3) {
                            if (Main.getLastMoved() == Main.getBoard()[xPos][yPos + 1].getPiece() && !moveCausesCheck(getPiece(), Main.getBoard()[xPos - 1][yPos + 1])) {
                                legalMoves.add(Main.getBoard()[xPos - 1][yPos + 1]);
                            }
                        }
                        if (Main.getBoard()[xPos - 1][yPos].hasPiece()) {
                            break;
                        }
                        else {
                            if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos - 1][yPos])) {
                                legalMoves.add(Main.getBoard()[xPos - 1][yPos]);
                            }
                        }
                        if (!getPiece().pieceHasMoved() && !Main.getBoard()[xPos - 2][yPos].hasPiece()) {
                            if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos - 2][yPos])) {
                                legalMoves.add(Main.getBoard()[xPos - 2][yPos]);
                            }
                        }
                    }
                    else {
                        if (yPos - 1 >= 0 && Main.getBoard()[xPos + 1][yPos - 1].hasPiece() && Main.getBoard()[xPos + 1][yPos - 1].getPiece().getColor() != getPiece().getColor()) {
                            if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos + 1][yPos - 1])) {
                                legalMoves.add(Main.getBoard()[xPos + 1][yPos - 1]);
                            }
                        }
                        else if (yPos - 1 >= 0 && Main.getBoard()[xPos][yPos - 1].hasPiece() && Main.getBoard()[xPos][yPos - 1].getPiece().getColor() != getPiece().getColor()
                                    && Main.getLastMoved().getBoardSquare().getxPos() == 4) {
                            if (Main.getLastMoved() == Main.getBoard()[xPos][yPos - 1].getPiece() && !moveCausesCheck(getPiece(), Main.getBoard()[xPos + 1][yPos - 1])) {
                                legalMoves.add(Main.getBoard()[xPos + 1][yPos - 1]);
                            }
                        }
                        if (yPos + 1 < 8 && Main.getBoard()[xPos + 1][yPos + 1].hasPiece() && Main.getBoard()[xPos + 1][yPos + 1].getPiece().getColor() != getPiece().getColor()) {
                            if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos + 1][yPos + 1])) {
                                legalMoves.add(Main.getBoard()[xPos + 1][yPos + 1]);
                            }
                        }
                        else if (yPos + 1 < 8 && Main.getBoard()[xPos][yPos + 1].hasPiece() && Main.getBoard()[xPos][yPos + 1].getPiece().getColor() != getPiece().getColor()
                                    && Main.getLastMoved().getBoardSquare().getxPos() == 4) {
                            if (Main.getLastMoved() == Main.getBoard()[xPos][yPos + 1].getPiece() && !moveCausesCheck(getPiece(), Main.getBoard()[xPos + 1][yPos + 1])) {
                                legalMoves.add(Main.getBoard()[xPos + 1][yPos + 1]);
                            }
                        }
                        if (Main.getBoard()[xPos + 1][yPos].hasPiece()) {
                            break;
                        }
                        else {
                            if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos + 1][yPos])) {
                                legalMoves.add(Main.getBoard()[xPos + 1][yPos]);
                            }
                        }
                        if (!getPiece().pieceHasMoved() && !Main.getBoard()[xPos + 2][yPos].hasPiece()) {
                            if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos + 2][yPos])) {
                                legalMoves.add(Main.getBoard()[xPos + 2][yPos]);
                            }
                        }
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
                                if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos + 1][i])) {
                                    legalMoves.add(Main.getBoard()[xPos + 1][i]);
                                }
                            }
                            if (xPos - 1 >= 0 && (!Main.getBoard()[xPos - 1][i].hasPiece() ||Main.getBoard()[xPos - 1][i].getPiece().getColor() != getPiece().getColor())) {
                                if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos - 1][i])) {
                                    legalMoves.add(Main.getBoard()[xPos - 1][i]);
                                }
                            }
                        }
                        else {
                            if (xPos + 2 < 8 &&(!Main.getBoard()[xPos + 2][i].hasPiece() || Main.getBoard()[xPos + 2][i].getPiece().getColor() != getPiece().getColor())) {
                                if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos + 2][i])) {
                                    legalMoves.add(Main.getBoard()[xPos + 2][i]);
                                }
                            }
                            if (xPos - 2 >= 0 &&(!Main.getBoard()[xPos - 2][i].hasPiece() || Main.getBoard()[xPos - 2][i].getPiece().getColor() != getPiece().getColor())) {
                                if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos - 2][i])) {
                                    legalMoves.add(Main.getBoard()[xPos - 2][i]);
                                }
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
                                if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos + i][yPos + i])) {
                                    legalMoves.add(Main.getBoard()[xPos + i][yPos + i]);
                                }
                            }
                            break;
                        }
                        if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos + i][yPos + i])) {
                            legalMoves.add(Main.getBoard()[xPos + i][yPos + i]);
                        }
                    }
                    for (int i = 1; i < 8; i++) {
                        if (xPos + i == 8 || yPos - i < 0) {
                            break;
                        }
                        if (Main.getBoard()[xPos + i][yPos - i].hasPiece()) {
                            if (Main.getBoard()[xPos + i][yPos - i].getPiece().getColor() != getPiece().getColor()) {
                                if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos + i][yPos - i])) {
                                    legalMoves.add(Main.getBoard()[xPos + i][yPos - i]);
                                }
                            }
                            break;
                        }
                        if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos + i][yPos - i])) {
                            legalMoves.add(Main.getBoard()[xPos + i][yPos - i]);
                        }
                    }
                    for (int i = 1; i < 8; i++) {
                        if (xPos - i < 0 || yPos + i == 8) {
                            break;
                        }
                        if (Main.getBoard()[xPos - i][yPos + i].hasPiece()) {
                            if (Main.getBoard()[xPos - i][yPos + i].getPiece().getColor() != getPiece().getColor()) {
                                if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos - i][yPos + i])) {
                                    legalMoves.add(Main.getBoard()[xPos - i][yPos + i]);
                                }
                            }
                            break;
                        }
                        if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos - i][yPos + i])) {
                            legalMoves.add(Main.getBoard()[xPos - i][yPos + i]);
                        }
                    }
                    for (int i = 1; i < 8; i++) {
                        if (xPos - i < 0|| yPos - i < 0) {
                            break;
                        }
                        if (Main.getBoard()[xPos - i][yPos - i].hasPiece()) {
                            if (Main.getBoard()[xPos - i][yPos - i].getPiece().getColor() != getPiece().getColor()) {
                                if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos - i][yPos - i])) {
                                    legalMoves.add(Main.getBoard()[xPos - i][yPos - i]);
                                }
                            }
                            break;
                        }
                        if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos - i][yPos - i])) {
                            legalMoves.add(Main.getBoard()[xPos - i][yPos - i]);
                        }
                    }
                    break;
                case ChessPiece.ROOK:
                    for (int i = xPos + 1; i < 8; i++) {
                        if (Main.getBoard()[i][yPos].hasPiece()) {
                            if (Main.getBoard()[i][yPos].getPiece().getColor() != getPiece().getColor()) {
                                if (!moveCausesCheck(getPiece(), Main.getBoard()[i][yPos])) {
                                    legalMoves.add(Main.getBoard()[i][yPos]);
                                }
                            }
                            break;
                        }
                        if (!moveCausesCheck(getPiece(), Main.getBoard()[i][yPos])) {
                            legalMoves.add(Main.getBoard()[i][yPos]);
                        }
                    }
                    for (int i = xPos - 1; i >= 0; i--) {
                        if (Main.getBoard()[i][yPos].hasPiece()) {
                            if (Main.getBoard()[i][yPos].getPiece().getColor() != getPiece().getColor()) {
                                if (!moveCausesCheck(getPiece(), Main.getBoard()[i][yPos])) {
                                    legalMoves.add(Main.getBoard()[i][yPos]);
                                }
                            }
                            break;
                        }
                        if (!moveCausesCheck(getPiece(), Main.getBoard()[i][yPos])) {
                            legalMoves.add(Main.getBoard()[i][yPos]);
                        }
                    }
                    for (int i = yPos + 1; i < 8; i++) {
                        if (Main.getBoard()[xPos][i].hasPiece()) {
                            if (Main.getBoard()[xPos][i].getPiece().getColor() != getPiece().getColor()) {
                                if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos][i])) {
                                    legalMoves.add(Main.getBoard()[xPos][i]);
                                }
                            }
                            break;
                        }
                        if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos][i])) {
                            legalMoves.add(Main.getBoard()[xPos][i]);
                        }
                    }
                    for (int i = yPos - 1; i >= 0; i--) {
                        if (Main.getBoard()[xPos][i].hasPiece()) {
                            if (Main.getBoard()[xPos][i].getPiece().getColor() != getPiece().getColor()) {
                                if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos][i])) {
                                    legalMoves.add(Main.getBoard()[xPos][i]);
                                }
                            }
                            break;
                        }
                        if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos][i])) {
                            legalMoves.add(Main.getBoard()[xPos][i]);
                        }
                    }
                    break;
                case ChessPiece.QUEEN:
                    for (int i = 1; i < 8; i++) {
                        if (xPos + i == 8 || yPos + i == 8) {
                            break;
                        }
                        if (Main.getBoard()[xPos + i][yPos + i].hasPiece()) {
                            if (Main.getBoard()[xPos + i][yPos + i].getPiece().getColor() != getPiece().getColor()) {
                                if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos + i][yPos + i])) {
                                    legalMoves.add(Main.getBoard()[xPos + i][yPos + i]);
                                }
                            }
                            break;
                        }
                        if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos + i][yPos + i])) {
                            legalMoves.add(Main.getBoard()[xPos + i][yPos + i]);
                        }
                    }
                    for (int i = 1; i < 8; i++) {
                        if (xPos + i == 8 || yPos - i < 0) {
                            break;
                        }
                        if (Main.getBoard()[xPos + i][yPos - i].hasPiece()) {
                            if (Main.getBoard()[xPos + i][yPos - i].getPiece().getColor() != getPiece().getColor()) {
                                if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos + i][yPos - i])) {
                                    legalMoves.add(Main.getBoard()[xPos + i][yPos - i]);
                                }
                            }
                            break;
                        }
                        if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos + i][yPos - i])) {
                            legalMoves.add(Main.getBoard()[xPos + i][yPos - i]);
                        }
                    }
                    for (int i = 1; i < 8; i++) {
                        if (xPos - i < 0 || yPos + i == 8) {
                            break;
                        }
                        if (Main.getBoard()[xPos - i][yPos + i].hasPiece()) {
                            if (Main.getBoard()[xPos - i][yPos + i].getPiece().getColor() != getPiece().getColor()) {
                                if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos - i][yPos + i])) {
                                    legalMoves.add(Main.getBoard()[xPos - i][yPos + i]);
                                }
                            }
                            break;
                        }
                        if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos - i][yPos + i])) {
                            legalMoves.add(Main.getBoard()[xPos - i][yPos + i]);
                        }
                    }
                    for (int i = 1; i < 8; i++) {
                        if (xPos - i < 0 || yPos - i < 0) {
                            break;
                        }
                        if (Main.getBoard()[xPos - i][yPos - i].hasPiece()) {
                            if (Main.getBoard()[xPos - i][yPos - i].getPiece().getColor() != getPiece().getColor()) {
                                if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos - i][yPos - i])) {
                                    legalMoves.add(Main.getBoard()[xPos - i][yPos - i]);
                                }
                            }
                            break;
                        }
                        if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos - i][yPos - i])) {
                            legalMoves.add(Main.getBoard()[xPos - i][yPos - i]);
                        }
                    }
                    for (int i = xPos + 1; i < 8; i++) {
                        if (Main.getBoard()[i][yPos].hasPiece()) {
                            if (Main.getBoard()[i][yPos].getPiece().getColor() != getPiece().getColor()) {
                                if (!moveCausesCheck(getPiece(), Main.getBoard()[i][yPos])) {
                                    legalMoves.add(Main.getBoard()[i][yPos]);
                                }
                            }
                            break;
                        }
                        if (!moveCausesCheck(getPiece(), Main.getBoard()[i][yPos])) {
                            legalMoves.add(Main.getBoard()[i][yPos]);
                        }
                    }
                    for (int i = xPos - 1; i >= 0; i--) {
                        if (Main.getBoard()[i][yPos].hasPiece()) {
                            if (Main.getBoard()[i][yPos].getPiece().getColor() != getPiece().getColor()) {
                                if (!moveCausesCheck(getPiece(), Main.getBoard()[i][yPos])) {
                                    legalMoves.add(Main.getBoard()[i][yPos]);
                                }
                            }
                            break;
                        }
                        if (!moveCausesCheck(getPiece(), Main.getBoard()[i][yPos])) {
                            legalMoves.add(Main.getBoard()[i][yPos]);
                        }
                    }
                    for (int i = yPos + 1; i < 8; i++) {
                        if (Main.getBoard()[xPos][i].hasPiece()) {
                            if (Main.getBoard()[xPos][i].getPiece().getColor() != getPiece().getColor()) {
                                if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos][i])) {
                                    legalMoves.add(Main.getBoard()[xPos][i]);
                                }
                            }
                            break;
                        }
                        if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos][i])) {
                            legalMoves.add(Main.getBoard()[xPos][i]);
                        }
                    }
                    for (int i = yPos - 1; i >= 0; i--) {
                        if (Main.getBoard()[xPos][i].hasPiece()) {
                            if (Main.getBoard()[xPos][i].getPiece().getColor() != getPiece().getColor()) {
                                if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos][i])) {
                                    legalMoves.add(Main.getBoard()[xPos][i]);
                                }
                            }
                            break;
                        }
                        if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos][i])) {
                            legalMoves.add(Main.getBoard()[xPos][i]);
                        }
                    }
                    break;
                case ChessPiece.KING:
                    if (!getPiece().pieceHasMoved() && Main.getBoard()[xPos][7].hasPiece() && !Main.getBoard()[xPos][7].getPiece().pieceHasMoved()) {
                        if (!Main.getBoard()[xPos][5].hasPiece() && !Main.getBoard()[xPos][6].hasPiece()) {
                            legalMoves.add(Main.getBoard()[xPos][7]);
                        }
                    }
                    if (xPos - 1 >= 0) {
                        if (Main.getBoard()[xPos - 1][yPos].hasPiece()) {
                            if (Main.getBoard()[xPos - 1][yPos].getPiece().getColor() != getPiece().getColor()) {
                                if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos - 1][yPos])) {
                                    legalMoves.add(Main.getBoard()[xPos - 1][yPos]);
                                }
                            }
                        }
                        else {
                            if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos - 1][yPos])) {
                                legalMoves.add(Main.getBoard()[xPos - 1][yPos]);
                            }
                        }
                    }
                    if (xPos - 1 >= 0 && yPos - 1 >= 0) {
                        if (Main.getBoard()[xPos - 1][yPos - 1].hasPiece()) {
                            if (Main.getBoard()[xPos - 1][yPos - 1].getPiece().getColor() != getPiece().getColor()) {
                                if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos - 1][yPos - 1])) {
                                    legalMoves.add(Main.getBoard()[xPos - 1][yPos - 1]);
                                }
                            }
                        }
                        else {
                            if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos - 1][yPos - 1])) {
                                legalMoves.add(Main.getBoard()[xPos - 1][yPos - 1]);
                            }
                        }
                    }
                    if (yPos - 1 >= 0) {
                        if (Main.getBoard()[xPos][yPos - 1].hasPiece()) {
                            if (Main.getBoard()[xPos][yPos - 1].getPiece().getColor() != getPiece().getColor()) {
                                if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos][yPos - 1])) {
                                    legalMoves.add(Main.getBoard()[xPos][yPos - 1]);
                                }
                            }
                        }
                        else {
                            if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos][yPos - 1])) {
                                legalMoves.add(Main.getBoard()[xPos][yPos - 1]);
                            }
                        }
                    }
                    if (xPos + 1 < 8 && yPos - 1 >= 0) {
                        if (Main.getBoard()[xPos + 1][yPos - 1].hasPiece()) {
                            if (Main.getBoard()[xPos + 1][yPos - 1].getPiece().getColor() != getPiece().getColor()) {
                                if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos + 1][yPos - 1])) {
                                    legalMoves.add(Main.getBoard()[xPos + 1][yPos - 1]);
                                }
                            }
                        }
                        else {
                            if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos + 1][yPos - 1])) {
                                legalMoves.add(Main.getBoard()[xPos + 1][yPos - 1]);
                            }
                        }
                    }
                    if (xPos + 1 < 8) {
                        if (Main.getBoard()[xPos + 1][yPos].hasPiece()) {
                            if (Main.getBoard()[xPos + 1][yPos].getPiece().getColor() != getPiece().getColor()) {
                                if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos + 1][yPos])) {
                                    legalMoves.add(Main.getBoard()[xPos + 1][yPos]);
                                }
                            }
                        }
                        else {
                            if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos + 1][yPos])) {
                                legalMoves.add(Main.getBoard()[xPos + 1][yPos]);
                            }
                        }
                    }
                    if (xPos + 1 < 8 && yPos + 1 < 8) {
                        if (Main.getBoard()[xPos + 1][yPos + 1].hasPiece()) {
                            if (Main.getBoard()[xPos + 1][yPos + 1].getPiece().getColor() != getPiece().getColor()) {
                                if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos + 1][yPos + 1])) {
                                    legalMoves.add(Main.getBoard()[xPos + 1][yPos + 1]);
                                }
                            }
                        }
                        else {
                            if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos + 1][yPos + 1])) {
                                legalMoves.add(Main.getBoard()[xPos + 1][yPos + 1]);
                            }
                        }
                    }
                    if (yPos + 1 < 8) {
                        if (Main.getBoard()[xPos][yPos + 1].hasPiece()) {
                            if (Main.getBoard()[xPos][yPos + 1].getPiece().getColor() != getPiece().getColor()) {
                                if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos][yPos + 1])) {
                                    legalMoves.add(Main.getBoard()[xPos][yPos + 1]);
                                }
                            }
                        }
                        else {
                            if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos][yPos + 1])) {
                                legalMoves.add(Main.getBoard()[xPos][yPos + 1]);
                            }
                        }
                    }
                    if (xPos - 1 >= 0 && yPos + 1 < 8) {
                        if (Main.getBoard()[xPos - 1][yPos + 1].hasPiece()) {
                            if (Main.getBoard()[xPos - 1][yPos + 1].getPiece().getColor() != getPiece().getColor()) {
                                if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos - 1][yPos + 1])) {
                                    legalMoves.add(Main.getBoard()[xPos - 1][yPos + 1]);
                                }
                            }
                        }
                        else {
                            if (!moveCausesCheck(getPiece(), Main.getBoard()[xPos - 1][yPos + 1])) {
                                legalMoves.add(Main.getBoard()[xPos - 1][yPos + 1]);
                            }
                        }
                    }
                    break;
                }
        return legalMoves;
    }

    public JPanel getPanel() {
        return this.panel;
    }

    public boolean isSelectingPromotion() {
        return selectingPromotion;
    }

    public void promptForPromotion() {
        if (hasPiece()) {
            getPiece().getLabel().setVisible(false);
        }
        replacementLabel = null;
        selectingPromotion = true;
        if (xPos == 0 || xPos == 7) {
            replacementLabel = new JLabel(new ImageIcon("Pieces/WhiteQueen.png"));
        }
        if (xPos == 1 || xPos == 6) {
            replacementLabel = new JLabel(new ImageIcon("Pieces/WhiteRook.png"));
        }
        if (xPos == 2 || xPos == 5) {
            replacementLabel = new JLabel(new ImageIcon("Pieces/WhiteKnight.png"));
        }
        if (xPos == 3 || xPos == 4) {
            replacementLabel = new JLabel(new ImageIcon("Pieces/WhiteBishop.png"));
        }
        getPanel().add(replacementLabel);
        Main.setWaitingForPromotion();
    }

    public void mousePressed(MouseEvent e) {
        if (this.isTargeted) {
            return;
        }
        if (hasPiece() && getPiece().getColor() == Main.getTurn()) {
            Main.resetTargetted();
            Main.updateHighLight(this.getPiece());

            for (BoardSquare move : getLegalMoves()) {
                move.setTargeted();
            }
        }
    }

    public boolean isTargeted() {
        return isTargeted;
    }

    public static boolean moveCausesCheck(ChessPiece movingPiece, BoardSquare squareToCheck) {
        boolean moveCausesCheck = false;
        BoardSquare prevBoardSquare = movingPiece.getBoardSquare();
        ChessPiece removedPiece = null;
        if (squareToCheck.hasPiece()) {
            removedPiece = squareToCheck.getPiece();
        }
        squareToCheck.setPiece(movingPiece);
        movingPiece.setBoardSquare(squareToCheck);
        prevBoardSquare.removePiece();
        if (movingPiece.getColor() == ChessPiece.WHITE) {
            if (Main.inCheck(Main.getWhiteKing())) {
                moveCausesCheck = true;
            }
        }
        else {
            if (Main.inCheck(Main.getBlackKing())) {
                moveCausesCheck = true;
            }
        }
        if (removedPiece != null) {
            squareToCheck.setPiece(removedPiece);
        }
        else {
            squareToCheck.removePiece();
        }
        prevBoardSquare.setPiece(movingPiece);
        movingPiece.setBoardSquare(prevBoardSquare);
        return moveCausesCheck;
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
        if (this.isHighlighted) {
            return;
        }
        if (e.getButton() == MouseEvent.BUTTON1) {
            String turnLog = " ";
            if (Main.getTurnNumber() == 1 && Main.getTurn() == ChessPiece.WHITE) {
                turnLog = "";
            }
            if (Main.getTurn() == ChessPiece.WHITE) {
                turnLog += String.valueOf(Main.getTurnNumber() + ".");
            }
            if (Main.isWaitingForPromotion()) {
                if (!isSelectingPromotion()) {
                    return;
                }
                else {
                    Main.getSelectedPiece().getBoardSquare().removePiece();
                    Main.getSelectedPiece().removePiece();
                    Main.revalidate();
                    
                    if (xPos < 4) {
                        for (int i = 0; i < 4; i++) {
                            Main.getBoard()[i][yPos].getPanel().remove(Main.getBoard()[i][yPos].getReplacementLabel());;
                            if (Main.getBoard()[i][yPos].hasPiece()) {
                                Main.getBoard()[i][yPos].getPiece().getLabel().setVisible(true);
                            }
                            // Used to ensure GUI removes previous selections
                            Main.getBoard()[i][yPos].setHighlighed();
                            Main.getBoard()[i][yPos].removeHighlight();
                            Main.revalidate();
                        }
                        if (Main.getBoard()[0][yPos].hasPiece()) {
                            Main.getBoard()[0][yPos].getPiece().removePiece();
                            Main.getBoard()[0][yPos].removePiece();
                        }
                        if (xPos == 0) {
                            Main.getBoard()[0][yPos].setPiece(new ChessPiece(5, ChessPiece.WHITE, Main.getBoard()[0][yPos], 0, getyPos()));
                        }
                        if (xPos == 1) {
                            Main.getBoard()[0][yPos].setPiece(new ChessPiece(4, ChessPiece.WHITE, Main.getBoard()[0][yPos], 0, getyPos()));
                        }
                        if (xPos == 2) {
                            Main.getBoard()[0][yPos].setPiece(new ChessPiece(2, ChessPiece.WHITE, Main.getBoard()[0][yPos], 0, getyPos()));
                        }
                        if (xPos == 3) {
                            Main.getBoard()[0][yPos].setPiece(new ChessPiece(3, ChessPiece.WHITE, Main.getBoard()[0][yPos], 0, getyPos()));
                        }
                    }
                    else {
                        for (int i = 7; i > 3; i--) {
                            Main.getBoard()[i][yPos].getPanel().remove(Main.getBoard()[i][yPos].getReplacementLabel());;
                            Main.getBoard()[i][yPos].setHighlighed();
                            Main.getBoard()[i][yPos].removeHighlight();
                        }
                        if (Main.getBoard()[7][yPos].hasPiece()) {
                            Main.getBoard()[7][yPos].getPiece().removePiece();
                            Main.getBoard()[7][yPos].removePiece();
                        }
                        if (xPos == 7) {
                            Main.getBoard()[7][yPos].setPiece(new ChessPiece(5, ChessPiece.BLACK, Main.getBoard()[7][yPos], 7, getyPos()));
                        }
                        if (xPos == 6) {
                            Main.getBoard()[7][yPos].setPiece(new ChessPiece(4, ChessPiece.BLACK, Main.getBoard()[7][yPos], 7, getyPos()));
                        }
                        if (xPos == 5) {
                            Main.getBoard()[7][yPos].setPiece(new ChessPiece(2, ChessPiece.BLACK, Main.getBoard()[7][yPos], 7, getyPos()));
                        }
                        if (xPos == 4) {
                            Main.getBoard()[7][yPos].setPiece(new ChessPiece(3, ChessPiece.BLACK, Main.getBoard()[7][yPos], 7, getyPos()));
                        }
                    }
                }
                if (Main.getTurn() == ChessPiece.WHITE) {
                    turnLog += Main.columnNumberToLetter(yPos);
                    turnLog += String.valueOf(yPos + 1);
                    turnLog += "=";
                    turnLog += Main.identifierToLetter(Main.getBoard()[0][yPos].getPiece().getIdentifier());
                }
                else {
                    turnLog += Main.columnNumberToLetter(yPos);
                    turnLog += String.valueOf(yPos);
                    turnLog += "=";
                    turnLog += Main.identifierToLetter(Main.getBoard()[8][yPos].getPiece().getIdentifier());
                }

                Main.revalidate();
                Main.removeWaitingForPromotion();
                Main.resetTargetted();
                Main.toggleTurn();

                Main.updateMoveLog(turnLog);

                return;
            }
            if (this.isTargeted) {
                if (Main.getSelectedPiece().getIdentifier() == ChessPiece.KING && this.hasPiece() && this.getPiece().getIdentifier() == ChessPiece.ROOK
                        && Main.getSelectedPiece().getColor() == this.getPiece().getColor()) {
                    int kingxPos = Main.getSelectedPiece().getBoardSquare().getxPos();
                    int kingyPos = Main.getSelectedPiece().getBoardSquare().getyPos();
                    BoardSquare kingNewSquare;
                    BoardSquare rookNewSquare;
                    if (kingyPos < this.getyPos()) {
                        kingNewSquare = Main.getBoard()[kingxPos][kingyPos + 2];
                        rookNewSquare = Main.getBoard()[kingxPos][kingyPos + 1];
                        turnLog += "O-O";
                    }
                    else {
                        kingNewSquare = Main.getBoard()[kingxPos][kingyPos - 2];
                        rookNewSquare = Main.getBoard()[kingxPos][kingyPos - 1];
                        turnLog += "O-O-O";
                    }

                    Main.getSelectedPiece().getBoardSquare().removePiece();
                    kingNewSquare.setPiece(Main.getSelectedPiece());
                    Main.getSelectedPiece().setBoardSquare(kingNewSquare);
                    Main.getSelectedPiece().pieceMoved();
                    rookNewSquare.setPiece(this.getPiece());
                    getPiece().setBoardSquare(rookNewSquare);
                    removePiece();

                    // Update board to ensure piece remains visible
                    Main.revalidate();

                    Main.setLastMoved(kingNewSquare.getPiece());
                }
                else {
                    if (Main.getSelectedPiece().getIdentifier() == ChessPiece.PAWN) {
                        if (Main.getLastMoved() != null && Main.getLastMoved().getIdentifier() == ChessPiece.PAWN && Main.getLastMoved().getBoardSquare().getxPos() == getxPos() - 1) {
                            if (xPos - 1 >= 0 && yPos - 1 >= 0 && Main.getBoard()[xPos - 1][yPos - 1].getPiece() == Main.getSelectedPiece()
                                    || xPos - 1 >= 0 && yPos + 1 < 8 && Main.getBoard()[xPos - 1][yPos + 1].getPiece() == Main.getSelectedPiece()
                                    || xPos + 1 < 8 && yPos - 1 >= 0 && Main.getBoard()[xPos + 1][yPos - 1].getPiece() == Main.getSelectedPiece()
                                    || xPos + 1 < 8 && yPos + 1 < 8 && Main.getBoard()[xPos + 1][yPos + 1].getPiece() == Main.getSelectedPiece()) {
                                Main.getLastMoved().getBoardSquare().removePiece();
                                // Ensures GUI updates to reflect piece no longer existing
                                Main.revalidate();
                                Main.getLastMoved().removePiece();
                                System.err.println("idk what this is");
                            }
                        }
                        else if (Main.getSelectedPiece().getColor() == ChessPiece.WHITE && this.xPos == 0) {
                            for (int i = 0; i < 4; i++) {
                                Main.getBoard()[i][yPos].promptForPromotion();
                            }
                            return;
                        }
                        else if (Main.getSelectedPiece().getColor() == ChessPiece.BLACK && this.xPos == 7) {
                            for (int i = 7; i > 3; i--) {
                                Main.getBoard()[i][yPos].promptForPromotion();
                            }
                            return;
                        }
                    }

                    turnLog += Main.identifierToLetter(Main.getSelectedPiece().getIdentifier());
                    if (this.hasPiece()) {
                        if (Main.getSelectedPiece().getIdentifier() == 1) {
                            turnLog += Main.columnNumberToLetter(Main.getSelectedPiece().getBoardSquare().getyPos());
                        }
                        turnLog += "x";
                    }
                    turnLog += Main.columnNumberToLetter(yPos);
                    turnLog += String.valueOf(Math.abs(xPos - 8));

                    if (this.hasPiece()) {
                        this.getPiece().removePiece();
                    }
                    Main.getSelectedPiece().getBoardSquare().removePiece();
                    removePiece();
                    this.setPiece(Main.getSelectedPiece());
                    getPiece().setBoardSquare(this);
                    getPiece().pieceMoved();

                    Main.setLastMoved(Main.getSelectedPiece());
                }

                boolean hasLegalMove = false;

                if (Main.getTurn() == ChessPiece.WHITE) {
                    for (ChessPiece piece : Main.getPieceList()) {
                        if (piece.getColor() == ChessPiece.WHITE) {
                            continue;
                        }
                        if (piece.getBoardSquare().getLegalMoves().size() != 0) {
                            hasLegalMove = true;
                            break;
                        }
                    }
                    if (Main.inCheck(Main.getBlackKing())) {
                        turnLog += "#";

                        
                        if (!hasLegalMove) {
                            turnLog += " 1-0";
                            Main.updateMoveLog(turnLog);
                            Main.endGame(ChessPiece.WHITE);
                        }
                    }
                    else {
                        if (!hasLegalMove) {
                            turnLog += "1/2-1/2";
                            Main.updateMoveLog(turnLog);
                            Main.endGame("Stalemate");
                        }
                    }
                }
                else {
                    for (ChessPiece piece : Main.getPieceList()) {
                        if (piece.getColor() == ChessPiece.BLACK) {
                            continue;
                        }
                        if (piece.getBoardSquare().getLegalMoves().size() != 0) {
                            hasLegalMove = true;
                            break;
                        }
                    }
                    if (Main.inCheck(Main.getWhiteKing())) {

                        turnLog += "#";

                        
                        if (!hasLegalMove) {
                            turnLog += "0-1";
                            Main.updateMoveLog(turnLog);
                            Main.endGame(ChessPiece.BLACK);
                            System.out.println("checkmate black");
                        }
                    }
                    else {
                        if (!hasLegalMove) {
                            Main.endGame("Stalemate");
                        }
                    }
                }

                Main.updateMoveLog(turnLog);

                Main.resetTargetted();
                Main.toggleTurn();

                if (this.getPiece().getColor() == ChessPiece.WHITE && Main.getTurnNumber() == 1) {
                    Main.startTimer();
                }

            }
            else {
                Main.resetTargetted();
            }
        }
    }

    private Component getReplacementLabel() {
        Component toReturn = replacementLabel;
        replacementLabel = null;
        return toReturn;
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
