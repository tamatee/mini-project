
import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MemoryGame extends JFrame implements ActionListener {

    private JButton[] buttons;
    private ImageIcon[] images;
    private int[] cardIndices;
    private int firstCardIndex;
    private int secondCardIndex;
    private boolean firstCardSelected;

    public MemoryGame() {
        setTitle("Memory Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 4));

        buttons = new JButton[16];
        images = new ImageIcon[8];
        cardIndices = new int[16];
        firstCardIndex = -1;
        secondCardIndex = -1;
        firstCardSelected = false;

        for (int i = 0; i < images.length; i++) {
            images[i] = new ImageIcon("image" + i + ".jpg");
        }

        for (int i = 0; i < cardIndices.length; i++) {
            cardIndices[i] = i % 8;
        }

        shuffle(cardIndices);

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton();
            buttons[i].addActionListener(this);
            add(buttons[i]);
        }

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        JButton buttonClicked = (JButton) e.getSource();

        int buttonIndex = -1;
        for (int i = 0; i < buttons.length; i++) {
            if (buttonClicked == buttons[i]) {
                buttonIndex = i;
            }
        }

        if (buttonIndex == -1) {
            System.out.println("Error: button not found.");
            return;
        }

        if (!firstCardSelected) {
            firstCardIndex = buttonIndex;
            buttons[firstCardIndex].setIcon(images[cardIndices[firstCardIndex]]);
            firstCardSelected = true;
        } else {
            secondCardIndex = buttonIndex;
            buttons[secondCardIndex].setIcon(images[cardIndices[secondCardIndex]]);
            firstCardSelected = false;
            if (cardIndices[firstCardIndex] != cardIndices[secondCardIndex]) {
                Timer timer = new Timer(250, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        buttons[firstCardIndex].setIcon(null);
                        buttons[secondCardIndex].setIcon(null);
                    }
                });
                timer.setRepeats(false);
                timer.start();
            } else {
                buttons[firstCardIndex].setEnabled(false);
                buttons[secondCardIndex].setEnabled(false);
            }
        }
    }

    private void shuffle(int[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    public static void main(String[] args) {
        MemoryGame game = new MemoryGame();
    }
}
