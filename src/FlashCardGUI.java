import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class FlashCardGUI extends JFrame {
    private int width, height;
    private Container contentPane;
    private ArrayList<Deck> deckList;
    //PRIMARY PANELS
    private JPanel deckPanel;    
    private JPanel addCardsPanel;    
    private JPanel viewCardsPanel;
    private JPanel studyPanel;
    //SUB PANELS
    private JPanel deckListPanel;
    //BUTTONS
    private JButton addDeckButton;
    private JButton returnToDeckButton;  
    
    public FlashCardGUI(int width, int height) {
        this.width = width;
        this.height = height;
        deckList = new ArrayList<>();
        viewCardsPanel = new JPanel();
        studyPanel = new JPanel();
        //Buttons
        returnToDeckButton = new JButton("Return to Decks");
                
    }
    public void setup() {
        setSize(width,height);
        setTitle("Flash Cards");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = getContentPane();      
        mainP();
        setResizable(false);
        setVisible(true);
    }
    //Creating the main panel (home page) featuring decks
    private void mainP() {
        deckPanel = new JPanel();
        deckListPanel = new JPanel();
        JPanel inputPanel = new JPanel();
        JTextField deckNameField = new JTextField("Enter deck name");
        addDeckButton = new JButton("Add a Deck");
        JScrollPane scrollPane = new JScrollPane(deckPanel);
        
        contentPane.add(scrollPane);
        deckPanel.setSize(width,height);
        deckPanel.setLayout(new BorderLayout());
        deckPanel.add(new JLabel("Decks", SwingConstants.CENTER), BorderLayout.NORTH);
        deckPanel.add(deckListPanel, BorderLayout.CENTER);
        deckListPanel.setLayout(new BoxLayout(deckListPanel, BoxLayout.PAGE_AXIS));
        for(int i = 0; i<deckList.size(); i++) {
            deckListPanel.add(deckItem(deckList.get(i)));
        }

        deckPanel.add(inputPanel, BorderLayout.SOUTH);
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        inputPanel.add(new JLabel("Deck Name: "));
        inputPanel.add(deckNameField);
        deckNameField.setColumns(12);
        deckNameField.addFocusListener(new emptyTextField(deckNameField)); 
        inputPanel.add(addDeckButton); //add output file reader "PrintWriter"
        class addDeckL implements ActionListener{
            public void actionPerformed(ActionEvent ae) {
                String deckName = deckNameField.getText();
                Deck newDeck = new Deck(deckName);
                deckList.add(newDeck);
                deckListPanel.add(deckItem(newDeck));
                deckNameField.setText("Enter deck name");
                contentPane.revalidate();
                contentPane.repaint();
                System.out.println("Deck list size update: "+deckList.size()+" decks."); //Debugging helper
            }
        }
        addDeckButton.addActionListener(new addDeckL());
    }
    private void addCardsP(Deck dToAdd) {
        addCardsPanel = new JPanel();        
        JPanel fbField = new JPanel();
        JTextField front = new JTextField();
        JTextField back = new JTextField();
        JPanel buttonsPane = new JPanel();
        JButton newCardButton = new JButton("Add Card");
        
        contentPane.removeAll();
        contentPane.add(addCardsPanel);
        addCardsPanel.setLayout(new BorderLayout());
        addCardsPanel.add(new JLabel(dToAdd.getTitle(), SwingConstants.CENTER), BorderLayout.NORTH);
        
        fbField.setLayout(new FlowLayout(FlowLayout.CENTER));
        fbField.add(new JLabel("Front: "));
        fbField.add(front);
        front.setColumns(10);
        fbField.add(new JLabel("Back: "));
        back.setColumns(10);
        fbField.add(back);
        addCardsPanel.add(fbField,BorderLayout.CENTER);
        
        buttonsPane.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonsPane.add(newCardButton);
        class newCardL implements ActionListener{
            public void actionPerformed(ActionEvent ae) {
                String f = front.getText();
                String b = back.getText();
                front.setText(null);
                back.setText(null);
                dToAdd.addCard(f,b);               
            }
        }
        newCardButton.addActionListener(new newCardL());
        buttonsPane.add(returnToDeckButton);
        returnToDeckButton.addActionListener(new ReturnToDecks());
        addCardsPanel.add(buttonsPane, BorderLayout.SOUTH);
    }
    private void viewCardsP(Deck dToAdd) {
        viewCardsPanel = new JPanel();
        JPanel fbList = new JPanel();
        JTextArea front = new JTextArea();
        JTextArea back = new JTextArea();
        JPanel buttonsPane = new JPanel();
//        JButton previousPageButton = new JButton("Previous Page"); // no more time
//        JButton nextPageButton = new JButton("Next Page");
        JScrollPane scrollPane;
        contentPane.removeAll();
        contentPane.add(viewCardsPanel);
        viewCardsPanel.setSize(width,height);
        viewCardsPanel.setLayout(new BorderLayout());
        viewCardsPanel.add(new JLabel(dToAdd.getTitle(), SwingConstants.CENTER), BorderLayout.NORTH);
        
        fbList.setLayout(new GridLayout(1,1));
        fbList.add(front);
        front.setEditable(false);
        for(int i=0; i<dToAdd.getSize(); i++) {
            front.append(dToAdd.getCard(i).getFront()+"\n");
        }
        fbList.add(back);
        back.setEditable(false);
        for(int i=0; i<dToAdd.getSize(); i++) {
            back.append(dToAdd.getCard(i).getBack()+"\n");
        }
        scrollPane = new JScrollPane(fbList);
        viewCardsPanel.add(scrollPane,BorderLayout.CENTER);
        
        buttonsPane.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonsPane.add(returnToDeckButton);
        returnToDeckButton.addActionListener(new ReturnToDecks());
        viewCardsPanel.add(buttonsPane, BorderLayout.SOUTH);
    }
    private void studyP(Deck dToAdd) {
        dToAdd.shuffle();
//        Font font = new Font("Comic Sans MS", Font.BOLD, 24);
        studyPanel = new JPanel();
        ArrayList<JPanel> frontCards = new ArrayList<>();
        ArrayList<String> frontArray = new ArrayList<>();
        ArrayList<JPanel> backCards = new ArrayList<>();
        ArrayList<String> backArray = new ArrayList<>();
        JPanel buttonsPanel = new JPanel();
        JButton showBackButton = new JButton("Show Back");
        JButton nextCardButton = new JButton("Next Card");
        
        for(int i=0; i<dToAdd.getSize(); i++) {
            frontArray.add(dToAdd.getCard(i).getFront());
            frontCards.add(new JPanel());
            frontCards.get(i).setLayout(new FlowLayout(FlowLayout.CENTER));
            frontCards.get(i).add(new JLabel(frontArray.get(i)), SwingConstants.CENTER); //increase font sizes and font type
            backArray.add(dToAdd.getCard(i).getBack());
            backCards.add(new JPanel());
            backCards.get(i).setLayout(new FlowLayout(FlowLayout.CENTER));
            backCards.get(i).add(new JLabel(backArray.get(i)), SwingConstants.CENTER);
        }
        
        contentPane.removeAll();
        contentPane.add(studyPanel);
        studyPanel.setSize(width,height);
        studyPanel.setLayout(new BorderLayout());
        studyPanel.add(new JLabel(dToAdd.getTitle(), SwingConstants.CENTER), BorderLayout.NORTH);

        class buttonClickedL implements ActionListener {
            int i = 0;
            public void actionPerformed(ActionEvent ae) {                
                if(i<dToAdd.getSize()) {
                    studyPanel.remove(frontCards.get(i));
                    if(ae.getSource().equals(showBackButton)) {
                        buttonsPanel.remove(showBackButton);
                        studyPanel.add(backCards.get(i), BorderLayout.CENTER);
                        buttonsPanel.add(nextCardButton);
                        if(i==dToAdd.getSize()-1) {
                            nextCardButton.setEnabled(false);
                        }
                        contentPane.revalidate();
                        contentPane.repaint();
                    }
                    else if(ae.getSource().equals(nextCardButton)) {
                        buttonsPanel.remove(nextCardButton);
                        studyPanel.remove(backCards.get(i));
                        buttonsPanel.add(showBackButton);
                        studyPanel.add(frontCards.get(i+1), BorderLayout.CENTER);
                        contentPane.revalidate();
                        contentPane.repaint();
                    }
                    i++;
                }
            }
        }
        studyPanel.add(frontCards.get(0), BorderLayout.CENTER);
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.add(returnToDeckButton);
        returnToDeckButton.addActionListener(new ReturnToDecks());
        buttonsPanel.add(showBackButton);
        showBackButton.addActionListener(new buttonClickedL());
        nextCardButton.addActionListener(new buttonClickedL());
        studyPanel.add(buttonsPanel, BorderLayout.SOUTH);
    }
    private JPanel deckItem(Deck d) { 
        JButton addCardsButton;
        JButton viewCardsButton;
        JButton studyButton;
        JButton deleteDeckButton;
        addCardsButton = new JButton("Add Cards");
        class addCardsL implements ActionListener { 
            public void actionPerformed(ActionEvent ae) {
                addCardsP(d); //Connects to addCardsP
                contentPane.revalidate();
                contentPane.repaint();
            }
        }
        viewCardsButton = new JButton("View Cards");
        class viewCardsL implements ActionListener { 
            public void actionPerformed(ActionEvent ae) {
                viewCardsP(d); //Connects to viewCardsP
                contentPane.revalidate();
                contentPane.repaint();
            }
        }
        studyButton = new JButton("Study");
        if(d.getSize()==0) {
            System.out.println("No items in array.");
            studyButton.setEnabled(false);
        }
        class studyL implements ActionListener { 
            public void actionPerformed(ActionEvent ae) {
                studyP(d);              
                contentPane.revalidate();
                contentPane.repaint();
            }
        }
        deleteDeckButton = new JButton("Delete deck");
        class deleteDeckL implements ActionListener {
            public void actionPerformed(ActionEvent ae) {
                deckListPanel.removeAll();
                deckList.remove(d);
                System.out.println("Deck list size update: "+deckList.size()+" decks.");
                for(int i = 0; i<deckList.size(); i++) {
                    deckListPanel.add(deckItem(deckList.get(i))); //called deckItem within the deckItem method.
                }
                contentPane.revalidate();
                contentPane.repaint();
            }
        }
        JPanel item = new JPanel();
        item.setLayout(new GridLayout(1,6));
        item.add(new JLabel(d.getTitle(),SwingConstants.CENTER));
        item.add(new JLabel(Integer.toString(d.getSize())+" cards",SwingConstants.CENTER));
        item.add(addCardsButton);
        addCardsButton.addActionListener(new addCardsL());
        item.add(viewCardsButton);
        viewCardsButton.addActionListener(new viewCardsL());
        item.add(studyButton);
        studyButton.addActionListener(new studyL());
        item.add(deleteDeckButton);
        deleteDeckButton.addActionListener(new deleteDeckL());
        return item;
    }
    private class ReturnToDecks implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            contentPane.removeAll();
            mainP();
            contentPane.revalidate();
            contentPane.repaint();
        }
    }
    private class emptyTextField implements FocusListener {
        JTextField txtInput;
        public emptyTextField(JTextField txtInput) {
            this.txtInput = txtInput;
        }
        public void focusGained(FocusEvent e) {
            txtInput.setText(null);
        }
        public void focusLost(FocusEvent e) {
            //no useful action when focus is not on the deck name textfield
            System.out.println("New focus is set.");
        }
    }

}