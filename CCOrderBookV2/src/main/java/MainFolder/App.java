package MainFolder;





import MainFolder.Controller.OrderController;
import MainFolder.DAO.OrderDaoImpl;
import MainFolder.DAO.OrderDaoInterface;
import MainFolder.Service.OrderServiceLayer;
import MainFolder.Service.OrderServiceLayerImpl;
import MainFolder.UI.OrderBookView;
import MainFolder.UI.UserIO;
import MainFolder.UI.UserIOConsoleImpl;
import java.io.IOException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;



/**
 * @author 
 *
 */
public class App {

	public static void main(String[] args) throws IOException, Exception {
                
		UserIO myIo = new UserIOConsoleImpl();
		OrderBookView myView = new OrderBookView(myIo);
                OrderDaoInterface myDao=new OrderDaoImpl();
		OrderServiceLayer myService = new OrderServiceLayerImpl(myDao);
		OrderController controller = new OrderController(myService, myView);
		
                controller.run();
                
            /*AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
            
            appContext.scan("Order");
            appContext.refresh();
            
            OrderController controller = appContext.getBean("dtoController", OrderController.class);
            controller.run();
*/
            
	}
}
