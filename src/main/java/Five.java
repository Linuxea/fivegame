import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 主游戏程序
 * Created by Linuxea on 11/6/17.
 */

public class Five extends JFrame {

    private JToolBar jToolBar;
    private JButton startButton, backButton, exitButton;
    private ChessBoard chessBoard;

    private Five() {
        super("five game");
        jToolBar = new JToolBar();
        startButton = new JButton("start");
        backButton = new JButton("back");
        exitButton = new JButton("exit");
        chessBoard = new ChessBoard();
        jToolBar.add(startButton);
        jToolBar.add(backButton);
        jToolBar.add(exitButton);

        ActionMonitor monitor = new ActionMonitor();
        startButton.addActionListener(monitor);
        backButton.addActionListener(monitor);
        exitButton.addActionListener(monitor);

        this.add(jToolBar, BorderLayout.NORTH);
        this.add(chessBoard, BorderLayout.CENTER);
        super.setLocation(200, 200);
        super.pack();
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.setResizable(false);
        super.setVisible(true);

    }

    public static void main(String[] args) {
        new Five();
    }

    class ActionMonitor implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == startButton) {
                chessBoard.restart();
            } else if (e.getSource() == backButton) {
                chessBoard.goback();
            } else if (e.getSource() == exitButton) {
                System.exit(0);
            }
        }
    }

}
