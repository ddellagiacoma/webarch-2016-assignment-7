package bean;

import javax.ejb.Remote;
import util.AccountInfo;

/**
 *
 * @author Daniele
 */
@Remote
public interface AccountRemote {
    AccountInfo getAccountInfo(int accountId);

    int register(String username,String pw);
    int login(String username,String pw); 
}
