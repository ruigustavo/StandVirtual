package ejb;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface PlayersEJBInterface {
    public void populate();
}