import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * 棋子类
 * Created by Linuxea on 11/6/17.
 */

public class Chess {

    public static final int DIAMETER = ChessBoard.SPAN - 2;
    ChessBoard chessBoard;
    private Color color;
    private int col;
    private int row;

    public Chess(ChessBoard chessBoard, int col, int row, Color color) {
        this.chessBoard = chessBoard;
        this.col = col;
        this.color = color;
        this.row = row;
    }

    public static int getDIAMETER() {
        return DIAMETER;
    }

    public void draw(Graphics graphics) {
        int xPos = col * chessBoard.SPAN + chessBoard.MARGIN;
        int yPos = row * chessBoard.SPAN + chessBoard.MARGIN;
        Graphics2D graphics2D = (Graphics2D) graphics;
        RadialGradientPaint gradientPaint = null;
        int x = xPos + DIAMETER / 4;
        int y = yPos + DIAMETER / 4;
        float[] floats = {0f, 1f};
        Color[] colors = {Color.WHITE, Color.black};
        if (color == Color.black) {
            gradientPaint = new RadialGradientPaint(x, y, DIAMETER, floats, colors);
        } else if (color == Color.white) {
            gradientPaint = new RadialGradientPaint(x, y, DIAMETER * 2, floats, colors);
        }

        graphics2D.setPaint(gradientPaint);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
                RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);
        Ellipse2D ellipse2D = new Ellipse2D.Float(xPos - DIAMETER / 2, yPos - DIAMETER / 2,
                DIAMETER, DIAMETER);

        graphics2D.fill(ellipse2D);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    public void setChessBoard(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }
}
