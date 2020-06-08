package paket1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class TextEditor extends JFrame {
    static JTextArea textArea = new JTextArea();
    static JTextField textField = new JTextField(10);
    static String text = "";

    public TextEditor() {
        super("Text editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        Controller.fileChooser.setName("FileChooser");
        add(Controller.fileChooser);

        initComponents();

        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
//-------------- MENU BAR --------------------------------------
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu searchMenu = new JMenu("Search");
        JCheckBox regexBox = new JCheckBox("Use regex");

        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem loadMenuItem = new JMenuItem("Load");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        JMenuItem searchMenuItem = new JMenuItem("Search");
        JMenuItem upMenuItem = new JMenuItem("Previous search");
        JMenuItem downMenuItem = new JMenuItem("Next search");
        JMenuItem regexMenuItem = new JMenuItem("Use regular expression");

        loadMenuItem.addActionListener(Controller.loadFile);
        saveMenuItem.addActionListener(Controller.saveFile);
        exitMenuItem.addActionListener(event -> {
            System.exit(0);
        });

        searchMenuItem.addActionListener(Controller.searchButton);
        upMenuItem.addActionListener(Controller.upButton);
        downMenuItem.addActionListener(Controller.downButton);
        regexMenuItem.addActionListener(Controller.searchRegexMenu);

        fileMenu.add(loadMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        searchMenu.add(searchMenuItem);
        searchMenu.add(upMenuItem);
        searchMenu.add(downMenuItem);
        searchMenu.add(regexMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(searchMenu);
        setJMenuBar(menuBar);

 //------------- PANEL 1 -----------------------------

        JPanel panel_1 = new JPanel();
        panel_1.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton buttonLoad = new JButton(Controller.newImageIcon("./src/images/load.png"));
        buttonLoad.setPreferredSize(new Dimension(25, 25));
        JButton buttonSave = new JButton(Controller.newImageIcon("./src/images/save.jpg"));
        buttonSave.setPreferredSize(new Dimension(25, 25));
        JButton buttonSearch = new JButton(Controller.newImageIcon("./src/images/search.jpg"));
        buttonSearch.setPreferredSize(new Dimension(25, 25));
        JButton buttonUp = new JButton(Controller.newImageIcon("./src/images/up.png"));
        buttonUp.setPreferredSize(new Dimension(25, 25));
        JButton buttonDown = new JButton(Controller.newImageIcon("./src/images/down.png"));
        buttonDown.setPreferredSize(new Dimension(25, 25));

        buttonSave.addActionListener(Controller.saveFile);
        buttonLoad.addActionListener(Controller.loadFile);
        buttonSearch.addActionListener(Controller.searchButton);
        buttonUp.addActionListener(Controller.upButton);
        buttonDown.addActionListener(Controller.downButton);
        regexBox.addItemListener(event -> {
            Controller.regexFlag = !Controller.regexFlag;
        });


        panel_1.add(buttonLoad);
        panel_1.add(buttonSave);
        panel_1.add(textField);
        panel_1.add(buttonSearch);
        panel_1.add(buttonUp);
        panel_1.add(buttonDown);
        panel_1.add(regexBox);
        add(panel_1, BorderLayout.NORTH);

//------------- PANEL 2 ----------------------------------------

        JPanel panel_2 = new JPanel();
        panel_2.setLayout(new BorderLayout());

        JScrollPane scrollableTextArea = new JScrollPane(textArea);
        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        panel_2.add(scrollableTextArea);
        add(panel_2, BorderLayout.CENTER);

 //----------------TEST BLOXK ---------------------------------

        textArea.setName("TextArea");
        textField.setName("SearchField");
        buttonSave.setName("SaveButton");
        buttonLoad.setName("OpenButton");
        buttonSearch.setName("StartSearchButton");
        buttonUp.setName("PreviousMatchButton");
        buttonDown.setName("NextMatchButton");
        regexBox.setName("UseRegExCheckbox");
        Controller.fileChooser.setName("FileChooser");
        scrollableTextArea.setName("ScrollPane");
        fileMenu.setName("MenuFile");
        searchMenu.setName("MenuSearch");
        loadMenuItem.setName("MenuOpen");
        saveMenuItem.setName("MenuSave");
        exitMenuItem.setName("MenuExit");
        searchMenuItem.setName("MenuStartSearch");
        upMenuItem.setName("MenuPreviousMatch");
        downMenuItem.setName("MenuNextMatch");
        regexMenuItem.setName("MenuUseRegExp");
    }

    public static void main(String[] args) {
        new TextEditor();
    }
}
