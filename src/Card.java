/**
* This creates the Card class.
*
* @author Jan Derrick Guarin
* @version 12/9/2018
*/

/*
  I have not discussed the Java language code 
  in my program with anyone other than my instructor 
  or the teaching assistants assigned to this course.

  I have not used Java language code obtained 
  from another student, or any other unauthorized 
  source, either modified or unmodified.

  If any Java language code or documentation 
  used in my program was obtained from another source, 
  such as a text book or webpage, those have been 
  clearly noted with a proper citation in the comments 
  of my code.
*/

public class Card {
    private String front;
    private String back;
    
    public Card(String front, String back){
        this.front=front;
        this.back=back;
    }
    public String getBack(){
        return back;
    }
    public String getFront(){
        return front;
    }
}
