/**
 * 
 */
package MainFolder.Controller;


import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



/**
 * @author 
 *
 */
@Component
public class DTOController {
	
	private DTOView view;
	private DTOServiceLayer service;
	@Autowired
	public DTOController(DTOServiceLayer service, DTOView view) {
		this.service = service;
		this.view = view;
	}
        //Keeps running the program and displaying the menu by try-catching any errors in any of the 6 functions within a while loop. 
	public void run() throws IOException, DTODuplicateIdException, DTODataValidationException {
		boolean keepGoing = true;
		int menuSelection = 0;
		while(keepGoing) {
                try {
			
				
				menuSelection = getMenuSelection();
				
				switch(menuSelection) {
				case 1:
					listDTOs();
					break;
				case 2:
					createDTO();
					break;
				case 3:
					viewDTO();
					break;
				case 4:
					removeDTO();
					break;
                                case 5:
                                        displayTypeDTOs();
                                        break;
                                case 6:
                                        updateDTO();
                                        break;
				case 7:
					keepGoing = false;
					break;
				default:
					unknownCommand();
				}
			
			exitMessage();
		}catch(DTOPersistenceException | IllegalArgumentException e) {
			view.displayErrorMessage(e.getMessage());
		}
                }
	}
	
	private int getMenuSelection() {
		// UserIO myIo = new UserIOConsoleImpl();
		// DTOView view = new DTOView(myIo);
		return view.printMenuAndGetSelection();
	}
	
	private void createDTO() throws DTOPersistenceException, IOException, DTODuplicateIdException, DTODataValidationException{
		view.displayCreateDTOBanner();
                boolean hasErrors= false;
                do{
                    try {
                        DTO currentDTO=view.getNewDTOInfo();
                        service.createDTO(currentDTO);
                        view.displayCreateDTOBanner();
                        hasErrors= false;
                    } catch (DTODuplicateIdException | DTODataValidationException | InputMismatchException | IllegalArgumentException | NullPointerException e){
                        hasErrors=true;
                        view.displayErrorMessage(e.getMessage());
                    }
                } while (hasErrors);
	}
	
	private void listDTOs() throws DTOPersistenceException{
		view.displayDisplayAllBanner();
		List<DTO> dtoList = service.getAllDTOs();
		view.displayDTOList(dtoList);
	}
	
	private void viewDTO() throws DTOPersistenceException{
		view.displayDisplayDTOBanner();
		Type1 var1 = view.getNameChoice();
		DTO dto = service.getDTO(var1);
		view.displayDTO(dto);
	}
	
	private void removeDTO() throws DTOPersistenceException, IOException{
		view.displayRemoveDTOBanner();
		Type1 var1 = view.getNameChoice();
		DTO removedDTO = service.removeDTO(var1);
		view.displayRemoveResult(removedDTO);
	}
	
	private void unknownCommand() {
		view.displayUnknownCommandBanner();
	}
	
	private void exitMessage() {
		view.displayExitBanner();
	}
        
        private void displayTypeDTOs() throws DTOPersistenceException{
            Element element= view.getType();
            List<DTO> dtoOfType= service.getDTOsOfType(element);
            view.displayDTOList(dtoOfType);
        }
        
        private void updateDTO() throws DTOPersistenceException{
            Type1 dtoName= view.getNameChoice();
            DTO existingDTO= service.getDTO(dtoName);
            while (existingDTO==null){
                view.displayNoSuchDTO();
                view.displayDTOList(service.getAllDTOs());
                dtoName=view.getNameChoice();
                existingDTO= service.getDTO(dtoName);
            }
            String[] params= view.getUpdates();
            DTO updatedDTO= service.updateDTO(dtoName, params);
            view.displayUpdateSuccessfulBanner();
            view.displayDTO(updatedDTO);
            
        }
	
}
