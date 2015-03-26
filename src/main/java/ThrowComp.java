import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Iob on 25/03/2015.
 */
public class ThrowComp implements JavaDelegate {

    private static final Logger log= LoggerFactory.getLogger(UndoService.class);

    public void execute(DelegateExecution execution) throws Exception {

        log.info("triggering the compensation, ...");
    }
}
