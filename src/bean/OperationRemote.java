package bean;

import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Daniele
 */
@Remote
public interface OperationRemote {
    void addBook(String title,String price);
    List<String> listOperation();
}