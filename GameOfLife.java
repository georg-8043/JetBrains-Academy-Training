package paket1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOfLife extends JFrame {

    private int size = 20;
    private JPanel[][] cells;
    Controller controller;
    boolean isPaused;
    boolean isReset;

    public GameOfLife() throws InterruptedException {
        super("Game of life");
        controller = new Controller(20);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        while (true) {
            if (!isPaused) {
                initComponents();
                controller.newGens();
            } else {
                initComponents();
            }
            if (isReset) new GameOfLife();
        }
    }

    public void initComponents() {
     //--------------Panel 1-------------------
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        JToggleButton pauseButton = new JToggleButton( );
        pauseButton.setText(isPaused?"Play":"Pause");
        pauseButton.setSelected(isPaused);
        pauseButton.setName("PlayToggleButton");
        pauseButton.addItemListener(itemEvent -> {
            if (pauseButton.isSelected()) {
                pauseButton.setText("Pause");
                isPaused = true;
            } else {
                pauseButton.setText("Play");
                    isPaused = false;
            }
        });
        JButton resetButton = new JButton("Reset");
        resetButton.setName("ResetButton");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                isReset = true;
            }
        });


        buttonPanel.add(pauseButton);
        buttonPanel.add(resetButton);
     // ----------Panel 2----------------
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        JLabel generationLabel = new JLabel();
        JLabel aliveLabel = new JLabel();
        generationLabel.setName("GenerationLabel");
        aliveLabel.setName("AliveLabel");
        generationLabel.setText(controller.generation);
        aliveLabel.setText(controller.alive);
        generationLabel.setName("GenerationLabel");
        aliveLabel.setName("AliveLabel");
        textPanel.add(generationLabel, BorderLayout.NORTH);
        textPanel.add(aliveLabel);

    //--------Common 1-2 panel----------
        JPanel commonPanel = new JPanel();
        commonPanel.setLayout(new BorderLayout());

    //------------Panel 3-----------------
        JPanel universePanel = new JPanel();
        universePanel.setLayout(new GridLayout(size, size));
        cells = new JPanel[size][size];
        for (int i = 0; i < cells.length; i++){
            for (int j = 0; j < cells[i].length; j++) {
                JPanel cell = new JPanel();
                cell.setBorder(BorderFactory.createLineBorder(Color.black));
                if (controller.currentUniverse[i][j].isAlive) cell.setBackground(Color.BLACK);
                universePanel.add(cell);
                cells[i][j] = cell;
            }
        }
     //----------join panels-----------
        commonPanel.add(buttonPanel, BorderLayout.NORTH);
        commonPanel.add(textPanel);
        add(commonPanel, BorderLayout.WEST);
        add(universePanel, BorderLayout.CENTER);

        setVisible(true);
        setLocationRelativeTo(null);
    }

}
