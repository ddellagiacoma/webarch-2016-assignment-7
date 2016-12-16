package bean;

import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Daniele
 */
@Remote
public interface ShoppingCartRemote {
  
  void getCart(int id);
  void addToCart(int bookID);
  List<String> list();
  void buy();
  void leave();
  List<String> getContents();


}