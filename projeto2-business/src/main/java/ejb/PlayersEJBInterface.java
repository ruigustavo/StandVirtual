package ejb;

import data.Player;

import javax.ejb.Local;
import java.util.List;

@Local
public interface PlayersEJBInterface {
    public void populate();
    public List<Player> playersTallerThan(float threshold);
}