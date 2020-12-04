/**
 * 
 */
package MainFolder.UI;



import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author
 *
 */
/*
@Component
public class DTOView {
	
	private UserIO io;
	@Autowired
	public DTOView(UserIO io) {
		this.io = io;
	}
	
	public int printMenuAndGetSelection() {
		io.print("Main Menu");
		io.print("1. List All DTOs");
		io.print("2. Create New DTO");
		io.print("3. View a DTO");
		io.print("4. Remove a DTO");
                io.print("5. List all DTOs of a Type");
                io.print("6. Update a DTO");
		io.print("7. Exit");
		
		return io.readInt("Please select from the above choice.", 1, 7);
	}
	
	public DTO getNewDTOInfo() {
		Type1 var1 = io.readString("Please enter a DTO Var1");
		Type2 var2 = io.readInt("Please enter Var2");
		Type3 var3 = io.readString("Please enter Var3");
		Type4 var4 = io.readString("Please enter Var4");
                Var5 var5= io.readString("Please enter Var5");
		
		DTO currentDTO = new DTO(var1, var2, var3, var4, var5);
		return currentDTO;
	}
	
	public void displayCreateDTOBanner() {
		io.print("=== Create DTO ===");
	}
	
	public void displayCreateSuccessBanner() {
		io.readString("DTO successfully created. Please hit enter to continue");
	}
	
	public void displayDTOList(List<DTO> dtoList) {
		for(DTO currentDTO : dtoList) {
			io.print("\n"+currentDTO.getVar1());
			io.print(currentDTO.getVar2()+"Var2");
                        io.print(currentDTO.getVar3());
			io.print("Var4: " +currentDTO.getVar4());
                        io.print("Var5: "+currentDTO.getVar5()+"\n");
		}
		io.readString("Please hit enter to continue");
	}
	
	public void displayDisplayAllBanner() {
		io.print("=== Display All DTOs ===");
	}
	
	public void displayDisplayDTOBanner() {
		io.print("=== Display DTO ===");
	}
	
	public String getVar1Choice() {
		return io.readString("Please enter the dto var1");
	}
	
	public void displayDTO(DTO currentDTO) {
		if(currentDTO != null) {
			io.print("\n"+currentDTO.getVar1());
			io.print(currentDTO.getVar2()+"Var2");
                        io.print(currentDTO.getVar3());
			io.print("Var4: " +currentDTO.getVar4());
                        io.print("Var5: "+currentDTO.getVar5()+"\n");
		}else {
			io.print("No such dto.");
		}
		io.readString("Please hit enter to continue.");
	}
	
	public void displayRemoveDTOBanner() {
		io.print("=== Remove DTO ===");
	}
	
	public void displayRemoveResult(DTO dtoRecord) {
		if(dtoRecord != null) {
			io.print("DTO successfully removed.");
		}else {
			io.print("No such dto.");
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
        
        public Var5 getType(){
            Var5 type= Var5.valueOf(io.readString("What is the var5?"));
            return type;
        }
        //Takes in the new values for the parameters of an existing dto that the user wants to update. 
        //Will return a String array full of the new values. Verifies if the string input from the user is valid in the cases of Var2 and Var5
        //by checking if they can be converted to an Int and Var5, respectively. 
        //If not valid, it keeps asking the user for an input until it is valid. 
        //Gives the user the option to hit enter if there is no change. This gives a "" String input. 
        //The servicelayer/controller will interpret this as not changing the original parameter. 
        public String[] getUpdates(){
            
            String newVar1= io.readString("What is the new var1? Or press enter if no change.");
            
            String newVar2;
            String intString= "";
            while (true){
                try{
                intString= io.readString("What is the new Var2? Or press enter if no change.");
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
            newVar2= intString;
            
            String newVar3= io.readString("What is the new Var3? Or press enter if no change.");
            String newVar4= io.readString("What is the new Var4? Or press enter if no change.");
            
            String newVar5;
            String var5String= "";
            while (true){
                try{
                    var5String= io.readString("What is the new Var5? Or press enter if no change.");
                    if (!var5String.equals("")){
                        Var5.valueOf(var5String);
                        break;
                    } else {
                        break;
                    }

                } catch (IllegalArgumentException e){
                    io.print("Invalid input. Please enter the correct Var5");
            }
                
            }
            newVar5= var5String;
            String[] updatedParam= {newVar1, newVar2, newVar3, newVar4, newVar5};
            
            return updatedParam;
        }
        
        public void displayUpdateSuccessfulBanner(){
            io.print("=== DTO Successfully Updated ===");
        }
        
        public void displayNoSuchDTO(){
            io.print("No such dto exists. Please select from the list of available dtos");
        }

}
*/