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

    /**
	 * GET PIECE
     * This method returns this square's piece (null if no piece)
	 */
    public ChessPiece getPiece() {
        return this.piece;
    }

    /**
	 * SET PIECE
     * This method removes any previous piece from this square, then adds the passed piece
     * and displays it on the GUI
	 */
    public ChessPiece setPiece(ChessPiece piece) {
        removePiece();
        this.piece = piece;
        panel.add(piece.getLabel());
        return this.piece;
    }

    /**
	 * REMOVE PIECE
     * This method removes the piece from this square, and removes it from the GUI.
     * If there is no piece on this square, returns immediately.
	 */
    public void removePiece() {
        if (this.piece == null) { 
            return;
        }
        this.panel.remove(this.piece.getLabel());
        this.piece = null;
    }

    /**
	 * GET COLOR
     * This method returns the square's color
	 */
    public Color getColor() {
        return this.baseColor;
    }

    /**
	 * HAS PIECE
     * Returns true if this square has a piece, otherwise returns false
	 */
    public boolean hasPiece() {
        return piece != null;
    }

    /**
	 * GET XPOS
     * Returns this square's xPos
	 */
    public int getxPos() {
        return xPos;
    }

    /**
	 * GET YPOS
     * Returns this square's yPos
	 */
    public int getyPos() {
        return yPos;
    }

    /**
	 * SET TARGETED
     * This method sets the square to being targeted, and reflects as such on the GUI
	 */
    public void setTargeted() {
        isTargeted = true;
        panel.setBackground(Color.MAGENTA);
    }

    /**
	 * SET HIGHLIGHTED
     * When called, this method sets the square to be highlighted, and reflects as such on the GUI
	 */
    public void setHighlighed() {
        isHighlighted = true;
        panel.setBackground(highlightedColor);
    }

    /**
	 * REMOVE HIGHLIGHT
     * This method returns the square to it's original colour, and sets it to
     * no longer be targeted or highlighted
	 */
    public void removeHighlight() {
        isHighlighted = false;
        isTargeted = false;
        panel.setBackground(baseColor);
    }

    /**
	 * GET LEGAL MOVES
     * This method returns an ArrayList of boardsquares which contains all legals moves
     * for this square's piece to make. In the case that there is no legal move to be made,
     * this method returns an empty ArrayList.
	 */
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
                        if (!Main.getBoard()[xPos][6].hasPiece() && !Main.getBoard()[xPos][5].hasPiece()) {
                            legalMoves.add(Main.getBoard()[xPos][7]);
                        }
                    }
                    if (!getPiece().pieceHasMoved() && Main.getBoard()[xPos][0].hasPiece() && !Main.getBoard()[xPos][0].getPiece().pieceHasMoved()) {
                        if (!Main.getBoard()[xPos][1].hasPiece() && !Main.getBoard()[xPos][2].hasPiece() && !Main.getBoard()[xPos][3].hasPiece()) {
                            legalMoves.add(Main.getBoard()[xPos][0]);
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

    /**
	 * GET PANEL
     * This method returns the panel assosiated with this square
	 */
    public JPanel getPanel() {
        return this.panel;
    }

    /**
	 * IS SELECTING PROMOTION
     * This method returns true if the square is currently being occupied for displaying
     * an option for pawn promotion. 
     */
    public boolean isSelectingPromotion() {
        return selectingPromotion;
    }

    /**
	 * PROMPT FOR PROMOTION
     * This method hides this square's piece from the GUI, and instead displays the requested option
     * for pawn promotion. The background of this square is set to white for increased distinction.
     * This method also calls on Main's set waiting for promotion.
	 */
    // TODO: why does main.setwaitingforpromotion() get called here? change?
    public void promptForPromotion() {
        if (hasPiece()) {
            getPiece().getLabel().setVisible(false);
        }
        getPanel().setBackground(new Color(0xF0F0F0));
        replacementLabel = null;
        selectingPromotion = true;
        switch (xPos) {
            case 0:
                replacementLabel = new JLabel(new ImageIcon("Pieces/WhiteQueen.png"));
                break;
            case 1:
                replacementLabel = new JLabel(new ImageIcon("Pieces/WhiteRook.png"));
                break;
            case 2:
                replacementLabel = new JLabel(new ImageIcon("Pieces/WhiteKnight.png"));
                break;
            case 3:
                replacementLabel = new JLabel(new ImageIcon("Pieces/WhiteBishop.png"));
                break;
            case 4:
                replacementLabel = new JLabel(new ImageIcon("Pieces/BlackBishop.png"));
                break;
            case 5:
                replacementLabel = new JLabel(new ImageIcon("Pieces/BlackKnight.png"));
                break;
            case 6:
                replacementLabel = new JLabel(new ImageIcon("Pieces/BlackRook.png"));
                break;
            case 7:
                replacementLabel = new JLabel(new ImageIcon("Pieces/BlackQueen.png"));
                break;
        }
        getPanel().add(replacementLabel);
        Main.setWaitingForPromotion();
    }

    /**
	 * MOUSE PRESSED
     * When the user left-clicks down on this square, it is set as the newly highlighted square
     * if it is this colour's turn and this square has a piece. This also sets all squares it's 
     * piece targets to be targetted.
	 */
    public void mousePressed(MouseEvent e) {
        // If the button pressed is not a left-click; return
        if (e.getButton() != MouseEvent.BUTTON1) {
            return;
        }
        // Returns if this is targeted (will be handled upon release)
        if (this.isTargeted) {
            return;
        }
        // If not tartgeted and selectable, swaps the highlighted piece to this piece
        if (hasPiece() && getPiece().getColor() == Main.getTurn()) {
            Main.resetTargetted();
            Main.updateHighLight(this.getPiece());

            for (BoardSquare move : getLegalMoves()) {
                move.setTargeted();
            }
        }
    }

    /**
	 * IS TARGETED
     * Returns true if this square is targeted
	 */
    public boolean isTargeted() {
        return isTargeted;
    }

    /**
	 * MOVE CAUSES CHECK
     * This method takes a piece and a square it wishes to move to as parameters.
     * By effectively completing the move, then checking if the king is in check, 
     * the method can determine whether the move causes check. This method will return
     * whether or not that move causes check, before moving pieces back to their origin
	 */
    public static boolean moveCausesCheck(ChessPiece movingPiece, BoardSquare squareToCheck) {
        boolean moveCausesCheck = false;
        // Stores previous square & position to reset afterwards
        BoardSquare prevBoardSquare = movingPiece.getBoardSquare();
        ChessPiece removedPiece = null;
        if (squareToCheck.hasPiece()) {
            removedPiece = squareToCheck.getPiece();
        }
        // "Makes" the move
        squareToCheck.setPiece(movingPiece);
        movingPiece.setBoardSquare(squareToCheck);
        prevBoardSquare.removePiece();
        // Checks whether the move causes the moving colour's king to be placed in check
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
        // Resets board to previous state
        if (removedPiece != null) {
            squareToCheck.setPiece(removedPiece);
        }
        else {
            squareToCheck.removePiece();
        }
        prevBoardSquare.setPiece(movingPiece);
        movingPiece.setBoardSquare(prevBoardSquare);
        // Returns whether or not the move is legal
        return moveCausesCheck;
    }

    /**
	 * MOUSE CLICKED
     * Unutilized (Required to fulfill implementation of MouseListener)
	 */
    public void mouseClicked(MouseEvent e) {
        return;
    }

    /**
	 * MOUSE RELEASED
     * Handles movement of pieces. Allows for castling, en passant, and other unique moves.
     * Updates the move log to reflect the played move.
	 */
    public void mouseReleased(MouseEvent e) {
        // If this is the highlighted piece, return
        if (this.isHighlighted) {
            return;
        }
        if (e.getButton() == MouseEvent.BUTTON1) {
            // If it is white's turn; add turn number to log
            String turnLog = " ";
            if (Main.getTurnNumber() == 1 && Main.getTurn() == ChessPiece.WHITE) {
                // On first turn, remove white space from start of log
                turnLog = "";
            }
            if (Main.getTurn() == ChessPiece.WHITE) {
                turnLog += String.valueOf(Main.getTurnNumber() + ".");
            }
            // If currently prompted for promotion
            if (Main.isWaitingForPromotion()) {
                if (!isSelectingPromotion()) {
                    // If this square is not related to the promotion, return
                    return;
                }
                else {
                    // Remove the pawn
                    Main.getSelectedPiece().getBoardSquare().removePiece();
                    Main.getSelectedPiece().removePiece();
                    Main.revalidate();
                    
                    // If this is a white pawn
                    if (xPos < 4) {
                        // For each square utilized for promotion options, hide promotion label and show original piece if it exists
                        for (int i = 0; i < 4; i++) {
                            Main.getBoard()[i][yPos].getPanel().remove(Main.getBoard()[i][yPos].getReplacementLabel());;
                            if (Main.getBoard()[i][yPos].hasPiece()) {
                                Main.getBoard()[i][yPos].getPiece().getLabel().setVisible(true);
                            }
                            Main.getBoard()[i][yPos].getPanel().setBackground(Main.getBoard()[i][yPos].getColor());
                            // Used to ensure GUI removes previous selections
                            Main.getBoard()[i][yPos].setHighlighed();
                            Main.getBoard()[i][yPos].removeHighlight();
                            Main.revalidate();
                        }
                        if (Main.getBoard()[0][yPos].hasPiece()) {
                            // Removes piece being taken
                            Main.getBoard()[0][yPos].getPiece().removePiece();
                            Main.getBoard()[0][yPos].removePiece();
                        }
                        // Creates the piece selected by the player
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
                        // For each square utilized for promotion options, hide promotion label and show original piece if it exists
                        for (int i = 7; i > 3; i--) {
                            Main.getBoard()[i][yPos].getPanel().remove(Main.getBoard()[i][yPos].getReplacementLabel());;
                            if (Main.getBoard()[i][yPos].hasPiece()) {
                                Main.getBoard()[i][yPos].getPiece().getLabel().setVisible(true);
                            }
                            Main.getBoard()[i][yPos].getPanel().setBackground(Main.getBoard()[i][yPos].getColor());
                            // Used to ensure GUI removes previous selections
                            Main.getBoard()[i][yPos].setHighlighed();
                            Main.getBoard()[i][yPos].removeHighlight();
                            Main.revalidate();
                        }
                        if (Main.getBoard()[7][yPos].hasPiece()) {
                            // Removes piece being taken
                            Main.getBoard()[7][yPos].getPiece().removePiece();
                            Main.getBoard()[7][yPos].removePiece();
                        }
                        // Creates the piece selected by the player
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
                // Updates move log to reflect currently played move
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
                    turnLog += Main.identifierToLetter(Main.getBoard()[7][yPos].getPiece().getIdentifier());
                }

                // Calls upon necessary methods in Main to update/reset required variables
                Main.revalidate();
                Main.removeWaitingForPromotion();
                Main.resetTargetted();
                Main.toggleTurn();

                // Updates game log to reflect current move
                Main.updateMoveLog(turnLog);

                return;
            }
            // If this is targetted (user wants to move a piece to this square)
            if (this.isTargeted) {
                // If a rook is being targetted by a king (castling is a permitted move)
                if (Main.getSelectedPiece().getIdentifier() == ChessPiece.KING && this.hasPiece() && this.getPiece().getIdentifier() == ChessPiece.ROOK
                        && Main.getSelectedPiece().getColor() == this.getPiece().getColor()) {
                    // Set kingxPos and kingyPos to variables for increased readability
                    int kingxPos = Main.getSelectedPiece().getBoardSquare().getxPos();
                    int kingyPos = Main.getSelectedPiece().getBoardSquare().getyPos();
                    BoardSquare kingNewSquare;
                    BoardSquare rookNewSquare;
                    // If the rook is to the right of the king (Short Castle)
                    if (kingyPos < this.getyPos()) {
                        // Move king right 2 squares, move rook to the left of the king's new position (right of it's old pos)
                        kingNewSquare = Main.getBoard()[kingxPos][kingyPos + 2];
                        rookNewSquare = Main.getBoard()[kingxPos][kingyPos + 1];
                        // Update turn log
                        turnLog += "O-O";
                    }
                    // Long castle (rook to the left of the king)
                    else {
                        // Move king 2 squares to the left, move rook to the right of the king's new position (left of it's old pos)
                        kingNewSquare = Main.getBoard()[kingxPos][kingyPos - 2];
                        rookNewSquare = Main.getBoard()[kingxPos][kingyPos - 1];
                        // Update turn log
                        turnLog += "O-O-O";
                    }

                    // Update board squares to reflect their new pieces (remove pieces from old squares, add to new squares)
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
                        // If there is a pawn that has moved 2 squares on its last move, allow en passant to occur
                        if (Main.getTurnNumber() > 1 && Main.getLastMoved().getIdentifier() == ChessPiece.PAWN && (Main.getLastMoved().getBoardSquare().getxPos() == getxPos() - 1
                            || Main.getLastMoved().getBoardSquare().getxPos() == getxPos() + 1)) {
                            if (xPos - 1 >= 0 && yPos - 1 >= 0 && Main.getBoard()[xPos - 1][yPos - 1].getPiece() == Main.getSelectedPiece()
                                    || xPos - 1 >= 0 && yPos + 1 < 8 && Main.getBoard()[xPos - 1][yPos + 1].getPiece() == Main.getSelectedPiece()
                                    || xPos + 1 < 8 && yPos - 1 >= 0 && Main.getBoard()[xPos + 1][yPos - 1].getPiece() == Main.getSelectedPiece()
                                    || xPos + 1 < 8 && yPos + 1 < 8 && Main.getBoard()[xPos + 1][yPos + 1].getPiece() == Main.getSelectedPiece()) {
                                Main.getLastMoved().getBoardSquare().setHighlighed();
                                Main.getLastMoved().getBoardSquare().removeHighlight();
                                Main.getLastMoved().getBoardSquare().removePiece();
                                // Ensures GUI updates to reflect piece no longer existing
                                Main.revalidate();
                                Main.getLastMoved().removePiece();
                            }
                        }
                        // If this pawn is ready to promote, display options to user
                        if (Main.getSelectedPiece().getColor() == ChessPiece.WHITE && this.xPos == 0) {
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

                    // Update turn log
                    turnLog += Main.identifierToLetter(Main.getSelectedPiece().getIdentifier());
                    if (this.hasPiece()) {
                        if (Main.getSelectedPiece().getIdentifier() == 1) {
                            turnLog += Main.columnNumberToLetter(Main.getSelectedPiece().getBoardSquare().getyPos());
                        }
                        // If a piece was taken
                        turnLog += "x";
                    }
                    turnLog += Main.columnNumberToLetter(yPos);
                    turnLog += String.valueOf(Math.abs(xPos - 8));

                    // (Re)Move pieces as necessary
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

                // Check if there is checkmate or a stalemate has been reached
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
                            Main.resetTargetted();
                            Main.toggleTurn();

                            Main.endGame(ChessPiece.WHITE);
                            return;
                        }
                    }
                    else {
                        if (!hasLegalMove) {
                            turnLog += "1/2-1/2";

                            Main.updateMoveLog(turnLog);
                            Main.resetTargetted();
                            Main.toggleTurn();

                            Main.endGame("Stalemate");
                            return;
                        }
                    }
                }
                // Check if there is checkmate or a stalemate has been reached
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
                            Main.resetTargetted();
                            Main.toggleTurn();

                            Main.endGame(ChessPiece.BLACK);
                            return;
                        }
                    }
                    else {
                        if (!hasLegalMove) {
                            turnLog += "1/2-1/2";
                            
                            Main.updateMoveLog(turnLog);
                            Main.resetTargetted();
                            Main.toggleTurn();

                            Main.endGame("Stalemate");
                            return;
                        }
                    }
                }

                Main.updateMoveLog(turnLog);

                Main.resetTargetted();
                Main.toggleTurn();

                // If the first move of the game, start timer
                if (Main.getTurnNumber() == 1 && this.getPiece().getColor() == ChessPiece.WHITE) {
                    Main.startTimer();
                }
            }
            else {
                Main.resetTargetted();
            }
        }
    }

    /**
	 * GET REPLACEMENT LABEL
     * This method returns the promotion label of this square, which holds the JLabel displaying the
     * promotion option
	 */
    private Component getReplacementLabel() {
        Component toReturn = replacementLabel;
        replacementLabel = null;
        return toReturn;
    }

    /**
	 * MOUSE ENTERED
     * Unutilized (Required to fulfill implementation of MouseListener)
	 */
    public void mouseEntered(MouseEvent e) {
    }

    /**
	 * MOUSE EXITED
     * Unutilized (Required to fulfill implementation of MouseListener)
	 */
    public void mouseExited(MouseEvent e) {
    }
}
