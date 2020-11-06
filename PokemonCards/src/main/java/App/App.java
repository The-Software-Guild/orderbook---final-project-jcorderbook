/**
 * 
 */
package App;

import Controller.CardController;
import DAO.CardAuditDao;
import DAO.CardAuditDaoFileImpl;
import DAO.CardDaoImpl;
import DAO.CardDaoInterface;
import Service.CardServiceLayer;
import Service.CardServiceLayerImpl;
import UI.CardView;
import UI.UserIO;
import UI.UserIOConsoleImpl;
import java.io.IOException;



/**
 * @author 
 *
 */
public class App {

	public static void main(String[] args) throws IOException, Exception {
            
		UserIO myIo = new UserIOConsoleImpl();
		CardView myView = new CardView(myIo);
                CardDaoInterface myDao=new CardDaoImpl();
                CardAuditDao myAuditDao= new CardAuditDaoFileImpl();
		CardServiceLayer myService = new CardServiceLayerImpl(myDao, myAuditDao);
		CardController controller = new CardController(myService, myView);
		controller.run();
                
            
	}
}
