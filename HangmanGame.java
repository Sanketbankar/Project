import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HangmanGame extends JFrame implements ActionListener {
    private String[] words = { "hangman", "coffee", "apple", "computer", "java", "canteen" };
    private String wordToGuess;
    private int score = 0;
    private int guessesLeft = 6;
    private StringBuilder hiddenWord;
    private boolean[] guessedLetters;

    private JLabel hiddenWordLabel;
    private JLabel scoreLabel;
    private JLabel guessesLeftLabel;
    private JTextField guessTextField;
    private JButton guessButton;

    public HangmanGame() {
        setTitle("Hangman Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        hiddenWordLabel = new JLabel();
        scoreLabel = new JLabel("Score: " + score);
        guessesLeftLabel = new JLabel("Guesses Left: " + guessesLeft);
        guessTextField = new JTextField(10);
        guessButton = new JButton("Guess");

        guessButton.addActionListener(this);
        guessTextField.addActionListener(this);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());
        mainPanel.add(hiddenWordLabel);
        mainPanel.add(scoreLabel);
        mainPanel.add(guessesLeftLabel);
        mainPanel.add(guessTextField);
        mainPanel.add(guessButton);

        getContentPane().add(mainPanel);

        initializeGame();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeGame() {
        wordToGuess = words[(int) (Math.random() * words.length)];
        hiddenWord = new StringBuilder();
        for (int i = 0; i < wordToGuess.length(); i++) {
            hiddenWord.append("_");
        }
        hiddenWordLabel.setText(hiddenWord.toString());
        score = 0;
        scoreLabel.setText("Score: " + score);
        guessesLeft = 6;
        guessesLeftLabel.setText("Guesses Left: " + guessesLeft);
        guessedLetters = new boolean[26];
    }

    private void updateHiddenWord(char guess) {
        if (guessedLetters[guess - 'a']) {
            return;
        }
        guessedLetters[guess - 'a'] = true;

        boolean found = false;
        for (int i = 0; i < wordToGuess.length(); i++) {
            if (wordToGuess.charAt(i) == guess) {
                hiddenWord.setCharAt(i, guess);
                found = true;
            }
        }
        hiddenWordLabel.setText(hiddenWord.toString());
        if (!found) {
            guessesLeft--;
            guessesLeftLabel.setText("Guesses Left: " + guessesLeft);
        } else {
            score += 5;
        }
        scoreLabel.setText("Score: " + score);
        if (guessesLeft == 0) {
            endGame("You lose! The word was: " + wordToGuess);
        } else if (hiddenWord.toString().equals(wordToGuess)) {
            endGame("Congratulations! You won!");
        }
    }

    private void endGame(String message) {
        guessTextField.setEnabled(false);
        guessButton.setEnabled(false);
        JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        initializeGame();
        guessTextField.setEnabled(true);
        guessButton.setEnabled(true);
        guessTextField.requestFocus();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == guessButton || e.getSource() == guessTextField) {
            String guessText = guessTextField.getText();
            if (guessText.length() > 0) {
                char guess = guessText.charAt(0);
                updateHiddenWord(guess);
                guessTextField.setText("");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HangmanGame::new);
    }
}
