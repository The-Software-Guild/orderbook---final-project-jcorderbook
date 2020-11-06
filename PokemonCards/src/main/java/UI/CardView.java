/**
 * 
 */
package UI;


import DTO.Card;
import DTO.Card.Element;
import java.util.List;


/**
 * @author
 *
 */
public class CardView {
	
	private UserIO io;
	
	public CardView(UserIO io) {
		this.io = io;
	}
	
	public int printMenuAndGetSelection() {
		io.print("Main Menu");
		io.print("1. List All Cards");
		io.print("2. Create New Card");
		io.print("3. View a Card");
		io.print("4. Remove a Card");
                io.print("5. List all Cards of a Type");
                io.print("6. Update a Card");
		io.print("7. Exit");
		
		return io.readInt("Please select from the above choice.", 1, 7);
	}
	
	public Card getNewCardInfo() {
		String name = io.readString("Please enter a Card Name");
		int hp = io.readInt("Please enter HP");
		String stage = io.readString("Please enter Stage");
		String attack = io.readString("Please enter Attack");
                Element element= Element.valueOf(io.readString("Please enter Element"));
		
		Card currentCard = new Card(name, hp, stage, attack, element);
		return currentCard;
	}
	
	public void displayCreateCardBanner() {
		io.print("=== Create Card ===");
	}
	
	public void displayCreateSuccessBanner() {
		io.readString("Card successfully created. Please hit enter to continue");
	}
	
	public void displayCardList(List<Card> cardList) {
		for(Card currentCard : cardList) {
			io.print("\n"+currentCard.getName());
			io.print(currentCard.getHp()+"HP");
                        io.print(currentCard.getStage());
			io.print("Attack: " +currentCard.getAttack());
                        io.print("Element: "+currentCard.getElement()+"\n");
		}
		io.readString("Please hit enter to continue");
	}
	
	public void displayDisplayAllBanner() {
		io.print("=== Display All Cards ===");
	}
	
	public void displayDisplayCardBanner() {
		io.print("=== Display Card ===");
	}
	
	public String getNameChoice() {
		return io.readString("Please enter the card name");
	}
	
	public void displayCard(Card currentCard) {
		if(currentCard != null) {
			io.print("\n"+currentCard.getName());
			io.print(currentCard.getHp()+"HP");
                        io.print(currentCard.getStage());
			io.print("Attack: " +currentCard.getAttack());
                        io.print("Element: "+currentCard.getElement()+"\n");
		}else {
			io.print("No such card.");
		}
		io.readString("Please hit enter to continue.");
	}
	
	public void displayRemoveCardBanner() {
		io.print("=== Remove Card ===");
	}
	
	public void displayRemoveResult(Card cardRecord) {
		if(cardRecord != null) {
			io.print("Card successfully removed.");
		}else {
			io.print("No such card.");
		}
		io.readString("Please hit enter to continue");
	}
	
	public void displayExitBanner() {
		io.print("Good Bye!!!");
	}
	
	public void displayUnknownCommandBanner() {
		io.print("Unknown Command!!!");
	}
	
	public void displayErrorMessage(String errorMsg) {
		io.print("=== ERROR ===");
		io.print(errorMsg);
	}
        
        public Element getType(){
            Element type= Element.valueOf(io.readString("What is the element?"));
            return type;
        }
        //Takes in the new values for the parameters of an existing card that the user wants to update. 
        //Will return a String array full of the new values. Verifies if the string input from the user is valid in the cases of Hp and Element
        //by checking if they can be converted to an Int and Element, respectively. 
        //If not valid, it keeps asking the user for an input until it is valid. 
        //Gives the user the option to hit enter if there is no change. This gives a "" String input. 
        //The servicelayer/controller will interpret this as not changing the original parameter. 
        public String[] getUpdates(){
            
            String newName= io.readString("What is the new name? Or press enter if no change.");
            
            String newHp;
            String intString= "";
            while (true){
                try{
                intString= io.readString("What is the new HP? Or press enter if no change.");
                if (!intString.equals("")){
                    Integer.parseInt(intString);
                    break;
                } else {
                    break;
                }
            } catch (NumberFormatException e){
                io.print("Invalid input. Please enter an integer.");
            }
            }
            newHp= intString;
            
            String newStage= io.readString("What is the new Stage? Or press enter if no change.");
            String newAttack= io.readString("What is the new Attack? Or press enter if no change.");
            
            String newElement;
            String elementString= "";
            while (true){
                try{
                    elementString= io.readString("What is the new Element? Or press enter if no change.");
                    if (!elementString.equals("")){
                        Element.valueOf(elementString);
                        break;
                    } else {
                        break;
                    }

                } catch (IllegalArgumentException e){
                    io.print("Invalid input. Please enter the correct Element");
            }
                
            }
            newElement= elementString;
            String[] updatedParam= {newName, newHp, newStage, newAttack, newElement};
            
            return updatedParam;
        }
        
        public void displayUpdateSuccessfulBanner(){
            io.print("=== Card Successfully Updated ===");
        }
        
        public void displayNoSuchCard(){
            io.print("No such card exists. Please select from the list of available cards");
        }
}
