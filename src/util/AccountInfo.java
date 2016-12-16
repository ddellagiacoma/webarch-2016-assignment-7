package util;

import java.io.Serializable;

/**
 *
 * @author Daniele
 */
public class AccountInfo implements Serializable {
    
  
    String username,pw;

    public AccountInfo(String username, String pw) {
       this.username=username;
       this.pw=pw;
    }

    public String getUsername() {
        return username;
    }

    public String getPw() {
        return pw;
    }

}
