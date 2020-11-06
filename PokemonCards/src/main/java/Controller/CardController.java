/**
 * 
 */
package Controller;

import DAO.CardPersistenceException;
import DTO.Card;
import DTO.Card.Element;
import Service.CardDataValidationException;
import Service.CardDuplicateIdException;
import Service.CardServiceLayer;
import UI.CardView;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;



/**
 * @author 
 *
 */
public class CardController {
	
	private CardView view;
	private CardServiceLayer service;
	
	public CardController(CardServiceLayer service, CardView view) {
		this.service = service;
		this.view = view;
	}
        //Keeps running the program and displaying the menu by try-catching any errors in any of the 6 functions within a while loop. 
	public void run() throws IOException, CardDuplicateIdException, CardDataValidationException {
		boolean keepGoing = true;
		int menuSelection = 0;
		while(keepGoing) {
                try {
			
				
				menuSelection = getMenuSelection();
				
				switch(menuSelection) {
				case 1:
					listCards();
					break;
				case 2:
					createCard();
					break;
				case 3:
					viewCard();
					break;
				case 4:
					removeCard();
					break;
                                case 5:
                                        displayTypeCards();
                                        break;
                                case 6:
                                        updateCard();
                                        break;
				case 7:
					keepGoing = false;
					break;
				default:
					unknownCommand();
				}
			
			exitMessage();
		}catch(CardPersistenceException | IllegalArgumentException e) {
			view.displayErrorMessage(e.getMessage());
		}
                }
	}
	
	private int getMenuSelection() {
		// UserIO myIo = new UserIOConsoleImpl();
		// CardView view = new CardView(myIo);
		return view.printMenuAndGetSelection();
	}
	
	private void createCard() throws CardPersistenceException, IOException, CardDuplicateIdException, CardDataValidationException{
		view.displayCreateCardBanner();
                boolean hasErrors= false;
                do{
                    try {
                        Card currentCard=view.getNewCardInfo();
                        service.createCard(currentCard);
                        view.displayCreateCardBanner();
                        hasErrors= false;
                    } catch (CardDuplicateIdException | CardDataValidationException | InputMismatchException | IllegalArgumentException | NullPointerException e){
                        hasErrors=true;
                        view.displayErrorMessage(e.getMessage());
                    }
                } while (hasErrors);
	}
	
	private void listCards() throws CardPersistenceException{
		view.displayDisplayAllBanner();
		List<Card> cardList = service.getAllCards();
		view.displayCardList(cardList);
	}
	
	private void viewCard() throws CardPersistenceException{
		view.displayDisplayCardBanner();
		String name = view.getNameChoice();
		Card card = service.getCard(name);
		view.displayCard(card);
	}
	
	private void removeCard() throws CardPersistenceException, IOException{
		view.displayRemoveCardBanner();
		String name = view.getNameChoice();
		Card removedCard = service.removeCard(name);
		view.displayRemoveResult(removedCard);
	}
	
	private void unknownCommand() {
		view.displayUnknownCommandBanner();
	}
	
	private void exitMessage() {
		view.displayExitBanner();
	}
        
        private void displayTypeCards() throws CardPersistenceException{
            Element element= view.getType();
            List<Card> cardOfType= service.getCardsOfType(element);
            view.displayCardList(cardOfType);
        }
        
        private void updateCard() throws CardPersistenceException{
            String cardName= view.getNameChoice();
            Card existingCard= service.getCard(cardName);
            while (existingCard==null){
                view.displayNoSuchCard();
                view.displayCardList(service.getAllCards());
                cardName=view.getNameChoice();
                existingCard= service.getCard(cardName);
            }
            String[] params= view.getUpdates();
            Card updatedCard= service.updateCard(cardName, params);
            view.displayUpdateSuccessfulBanner();
            view.displayCard(updatedCard);
            
        }
	
}
