package Order;




import Order.Controller.OrderController;
import java.io.IOException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;



/**
 * @author 
 *
 */
public class App {

	public static void main(String[] args) throws IOException, Exception {
                /*
		UserIO myIo = new UserIOConsoleImpl();
		OrderView myView = new OrderView(myIo);
                OrderDaoInterface myDao=new OrderDaoImpl();
		OrderServiceLayer myService = new OrderServiceLayerImpl(myDao);
		OrderController controller = new OrderController(myService, myView);
		
                controller.run();
                */
            AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
            
            appContext.scan("Order");
            appContext.refresh();
            
            OrderController controller = appContext.getBean("orderController", OrderController.class);
            controller.run();
            
	}
}
