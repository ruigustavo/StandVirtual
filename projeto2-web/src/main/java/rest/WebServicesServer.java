package rest;

import DTOs.CarDTO;
import ejb.CarEJBInterface;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
public class WebServicesServer {

    @EJB
    CarEJBInterface carejb;

    public WebServicesServer() {
        System.out.println("WebServicesServer created");
    }

    @GET
    @Path("gettext")
    @Produces({MediaType.TEXT_PLAIN})
    public String getText() {
        return "Hello World!";
    }

    @GET
    @Path("/cars")
    @Produces({MediaType.APPLICATION_JSON})
    public List<CarDTO> getAllCars() {
        System.out.println("getting all cars");
        List<CarDTO> toSend = carejb.getAllCars(1);

        System.out.println(toSend);
        if(toSend!=null)
            return toSend;
        return null;
    }
}
