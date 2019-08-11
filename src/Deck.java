import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private String title;
    private ArrayList<Card> flashCards;
    
    public Deck(String title){
        this.title = title;
        flashCards = new ArrayList<>();
    }
    public void addCard(String f,String b){
        flashCards.add(new Card(f,b));
    }
    public Card getCard(int i){
        return flashCards.get(i);
    }
    public int getSize() {
        return flashCards.size();
    }
    public String getTitle() {
        return title;
    }
    public void shuffle() {
        Collections.shuffle(flashCards);
    }
}
