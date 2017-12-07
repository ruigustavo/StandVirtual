package ejb;

import javax.ejb.Local;
import javax.ejb.Remote;
import java.util.List;

@Local
public interface PlayersEJBInterface {
    public void populate();
}