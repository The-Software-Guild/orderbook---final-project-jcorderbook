




import DTO.Controller.DTOController;
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
		DTOView myView = new DTOView(myIo);
                DTODaoInterface myDao=new DTODaoImpl();
		DTOServiceLayer myService = new DTOServiceLayerImpl(myDao);
		DTOController controller = new DTOController(myService, myView);
		
                controller.run();
                */
            AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
            
            appContext.scan("DTO");
            appContext.refresh();
            
            DTOController controller = appContext.getBean("dtoController", DTOController.class);
            controller.run();
            
	}
}
