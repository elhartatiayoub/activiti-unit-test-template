import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UndoService implements JavaDelegate {
    private static final Logger log= LoggerFactory.getLogger(UndoService.class);

    public void execute(DelegateExecution execution) throws Exception {

            log.info("undo service here now ...");
    }

}