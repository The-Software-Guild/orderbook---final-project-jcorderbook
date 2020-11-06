/**
 * 
 */
package App;

import Controller.OrderController;
import DAO.OrderAuditDao;
import DAO.OrderAuditDaoFileImpl;
import DAO.OrderDaoImpl;
import DAO.OrderDaoInterface;
import Service.OrderServiceLayer;
import Service.OrderServiceLayerImpl;
import UI.OrderView;
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
		OrderView myView = new OrderView(myIo);
                OrderDaoInterface myDao=new OrderDaoImpl();
                OrderAuditDao myAuditDao= new OrderAuditDaoFileImpl();
		OrderServiceLayer myService = new OrderServiceLayerImpl(myDao, myAuditDao);
		OrderController controller = new OrderController(myService, myView);
		
                controller.run();
                
            
	}
}
