import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Stack;

/**
 * 棋盘类
 * Created by Linuxea on 11/6/17.
 */

public class ChessBoard extends JPanel {

    public static final int MARGIN = 15;
    public static final int SPAN = 20;
    public static final int ROWS = 18;
    public static final int COLS = 18;
    Image image;

    Stack<Chess> chessList = new Stack<>(); // 栈来存在下棋步骤
    boolean isBlack = true;
    boolean onGamming = true;

    public ChessBoard() {
        image = Toolkit.getDefaultToolkit().getImage("");
        this.addMouseListener(new MouseMonitor());
        this.addMouseMotionListener(new MouseMotionMonitor());
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(image, 0, 0, this);
        for (int i = 0; i <= ROWS; i++) {
            graphics.drawLine(MARGIN, MARGIN + i * SPAN,
                    MARGIN + COLS * SPAN, MARGIN + i * SPAN);
        }

        for (int i = 0; i <= COLS; i++) {
            graphics.drawLine(MARGIN + i * SPAN, MARGIN,
                    MARGIN + i * SPAN, MARGIN + ROWS * SPAN);
        }

        graphics.fillRect(MARGIN + 3 * SPAN - 2, MARGIN + 3 * SPAN - 2, 5, 5);
        graphics.fillRect(MARGIN + (COLS / 2) * SPAN - 2, MARGIN + 3 * SPAN - 2, 5, 5);
        graphics.fillRect(MARGIN + (COLS - 3) * SPAN - 2, MARGIN + 3 * SPAN - 2, 5, 5);
        graphics.fillRect(MARGIN + 3 * SPAN - 2, MARGIN + (ROWS / 2) * SPAN - 2, 5, 5);
        graphics.fillRect(MARGIN + (COLS / 2) * SPAN - 2, MARGIN + (ROWS / 2) * SPAN - 2, 5, 5);

        graphics.fillRect(MARGIN + (COLS - 3) * SPAN - 2, MARGIN + (ROWS / 2) * SPAN - 2, 5, 5);
        graphics.fillRect(MARGIN + 3 * SPAN - 2, MARGIN + (ROWS - 3) * SPAN - 2, 5, 5);
        graphics.fillRect(MARGIN + (COLS / 2) * SPAN - 2, MARGIN + (ROWS - 3) * SPAN - 2, 5, 5);
        graphics.fillRect(MARGIN + (COLS - 3) * SPAN - 2, MARGIN + (ROWS - 3) * SPAN - 2, 5, 5);

        drawChessList(graphics);

    }

    /**
     * 画出所有下过的棋子
     *
     * @param graphics
     */
    private void drawChessList(Graphics graphics) {
        int totalCheesCount = chessList.size();
        int i = 0;
        for (Chess temp : chessList) {
            temp.draw(graphics);
            if (i++ == totalCheesCount - 1) {
                int xPos = temp.getCol() * SPAN + MARGIN;
                int yPos = temp.getRow() * SPAN + MARGIN;
                graphics.setColor(Color.red);
                graphics.drawRect(xPos - Chess.DIAMETER / 2, yPos - Chess.DIAMETER / 2,
                        Chess.DIAMETER, Chess.DIAMETER);
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(MARGIN * 2 + SPAN * COLS, MARGIN * 2 + SPAN * ROWS);
    }

    /**
     * 判断当前位置是否有棋
     *
     * @param col
     * @param row
     * @return
     */
    public boolean hasChess(int col, int row) {
        for (int i = 0; i < chessList.size() ; i++) {
            Chess chess = chessList.get(i);
            if (null != chess && chess.getCol() == col && chess.getRow() == row)
                return true;
        }
        return false;
    }


    /**
     * @param col
     * @param row
     * @param color
     * @return
     */
    public boolean hasChess(int col, int row, Color color) {
        for (int i = 0; i < chessList.size(); i++) {
            Chess chess = chessList.get(i);
            if (null != chess && chess.getCol() == col && chess.getRow() == row && chess.getColor() == color)
                return true;
        }
        return false;
    }


    /**
     * 通过判断四个方向是否有连续有棋子
     *
     * @param col
     * @param row
     * @return
     */
    public boolean isWin(int col, int row) {
        int continueCount = 1;
        Color color = isBlack ? Color.black : Color.white;

        // 向左列开始找起
        for (int x = col - 1; x >= 0; x--) {
            if (hasChess(x, row, color)) {
                continueCount++;
            } else {
                break;
            }
        }

        for (int x = col + 1; x <= COLS; x++) {
            if (hasChess(x, row, color)) {
                continueCount++;
            } else {
                break;
            }
        }

        // 水平方向已经有五个连续的或以上了
        if (continueCount >= 5) {
            return true;
        } else {
            continueCount = 1;
        }

        // 开始在纵向查找
        for (int y = row - 1; y >= 0; y--) {
            if (hasChess(col, y, color)) {
                continueCount++;
            } else {
                break;
            }
        }

        for (int y = row + 1; y <= ROWS; y++) {
            if (hasChess(col, y, color)) {
                continueCount++;
            } else {
                break;
            }
        }

        // 竖直方向已经有连续五个或者以上了.
        if (continueCount >= 5) {
            return true;
        } else {
            continueCount = 1;
        }


        for (int x = col + 1, y = row - 1; y >= 0 && x <= COLS; x++, y--) {
            if (hasChess(x, y, color)) {
                continueCount++;
            } else {
                break;
            }
        }

        for (int x = col - 1, y = row + 1; x >= 0 && y <= ROWS; x--, y++) {
            if (hasChess(x, y, color)) {
                continueCount++;
            } else {
                break;
            }
        }

        // 竖直方向已经有连续五个或者以上了.
        if (continueCount >= 5) {
            return true;
        } else {
            continueCount = 1;
        }


        for (int x = col - 1, y = row - 1; x >= 0 && y >= 0; x--, y--) {
            if (hasChess(x, y, color)) {
                continueCount++;
            } else {
                break;
            }
        }

        for (int x = col + 1, y = row + 1; x <= COLS && y <= ROWS; x++, y++) {
            if (hasChess(x, y, color)) {
                continueCount++;
            } else {
                break;
            }
        }

        if (continueCount >= 5) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 重新开始
     */
    public void restart() {
        chessList.clear();
        isBlack = true;
        onGamming = true;
        repaint();
    }

    /**
     * 悔棋
     */
    public void goback() {
        if (chessList.size() == 0) return;
        chessList.pop();
        isBlack = !isBlack;
        repaint();

    }

    class MouseMonitor extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {

            if (onGamming == false) return;

            int col = (e.getX() - MARGIN + SPAN / 2) / SPAN;
            int row = (e.getY() - MARGIN + SPAN / 2) / SPAN;
            if (col < 0 || col > COLS || row < 0 || row > ROWS) return;
            if (hasChess(col, row)) {
                return;
            }
            Chess chess = new Chess(ChessBoard.this, col, row, isBlack ? Color.black : Color.white);
            chessList.push(chess);
            repaint();

            if (isWin(col, row)) {
                String colName = isBlack ? "黑棋" : "白棋";
                String msg = String.format("恭喜,%s䊨了!", colName);
                JOptionPane.showMessageDialog(ChessBoard.this, msg);
                onGamming = false;
            }
            isBlack = !isBlack;

        }
    }

    class MouseMotionMonitor extends MouseMotionAdapter{
        @Override
        public void mouseMoved(MouseEvent e) {
            int col = (e.getX() - MARGIN + SPAN/2) / SPAN;
            int row = (e.getY() - MARGIN + SPAN/2) / SPAN;
            if(col < 0 || col > COLS || row < 0 || row > ROWS || !onGamming || hasChess(col, row)){
                ChessBoard.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            } else {
                ChessBoard.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        }
    }


}
