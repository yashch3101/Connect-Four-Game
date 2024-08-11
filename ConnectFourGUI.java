import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectFourGUI extends JFrame implements ActionListener {

    private static final int ROWS = 6;
    private static final int COLS = 7;
    private static final char PLAYER1 = 'X';
    private static final char PLAYER2 = 'O';
    private char currentPlayer = PLAYER1;

    private JButton[] buttons = new JButton[COLS];
    private JLabel[][] cells = new JLabel[ROWS][COLS];
    private char[][] board = new char[ROWS][COLS];

    public ConnectFourGUI() {
        setTitle("Connect Four");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(1, COLS));
        for (int i = 0; i < COLS; i++) {
            buttons[i] = new JButton("Drop");
            buttons[i].setActionCommand(String.valueOf(i));
            buttons[i].addActionListener(this);
            topPanel.add(buttons[i]);
        }
        add(topPanel, BorderLayout.NORTH);

        JPanel boardPanel = new JPanel(new GridLayout(ROWS, COLS));
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                cells[row][col] = new JLabel();
                cells[row][col].setOpaque(true);
                cells[row][col].setBackground(Color.WHITE);
                cells[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                cells[row][col].setHorizontalAlignment(SwingConstants.CENTER);
                cells[row][col].setFont(new Font("Arial", Font.BOLD, 40));
                boardPanel.add(cells[row][col]);
                board[row][col] = '.';
            }
        }
        add(boardPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int column = Integer.parseInt(e.getActionCommand());
        if (makeMove(column, currentPlayer)) {
            updateBoard();
            if (checkWin(currentPlayer)) {
                JOptionPane.showMessageDialog(this, "Player " + currentPlayer + " wins!");
                resetBoard();
            } else if (isBoardFull()) {
                JOptionPane.showMessageDialog(this, "It's a draw!");
                resetBoard();
            } else {
                currentPlayer = (currentPlayer == PLAYER1) ? PLAYER2 : PLAYER1;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Column full! Try another column.");
        }
    }

    private boolean makeMove(int column, char player) {
        for (int row = ROWS - 1; row >= 0; row--) {
            if (board[row][column] == '.') {
                board[row][column] = player;
                return true;
            }
        }
        return false;
    }

    private void updateBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                cells[row][col].setText(String.valueOf(board[row][col]));
                if (board[row][col] == PLAYER1) {
                    cells[row][col].setForeground(Color.RED);
                } else if (board[row][col] == PLAYER2) {
                    cells[row][col].setForeground(Color.YELLOW);
                }
            }
        }
    }

    private boolean checkWin(char player) {
        // Check horizontal
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS - 3; col++) {
                if (board[row][col] == player && board[row][col + 1] == player &&
                    board[row][col + 2] == player && board[row][col + 3] == player) {
                    return true;
                }
            }
        }

        // Check vertical
        for (int col = 0; col < COLS; col++) {
            for (int row = 0; row < ROWS - 3; row++) {
                if (board[row][col] == player && board[row + 1][col] == player &&
                    board[row + 2][col] == player && board[row + 3][col] == player) {
                    return true;
                }
            }
        }

        // Check diagonal (bottom-left to top-right)
        for (int row = 3; row < ROWS; row++) {
            for (int col = 0; col < COLS - 3; col++) {
                if (board[row][col] == player && board[row - 1][col + 1] == player &&
                    board[row - 2][col + 2] == player && board[row - 3][col + 3] == player) {
                    return true;
                }
            }
        }

        // Check diagonal (top-left to bottom-right)
        for (int row = 0; row < ROWS - 3; row++) {
            for (int col = 0; col < COLS - 3; col++) {
                if (board[row][col] == player && board[row + 1][col + 1] == player &&
                    board[row + 2][col + 2] == player && board[row + 3][col + 3] == player) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isBoardFull() {
        for (int col = 0; col < COLS; col++) {
            if (board[0][col] == '.') {
                return false;
            }
        }
        return true;
    }

    private void resetBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                board[row][col] = '.';
                cells[row][col].setText("");
            }
        }
        currentPlayer = PLAYER1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ConnectFourGUI game = new ConnectFourGUI();
            game.setVisible(true);
        });
    }
}
