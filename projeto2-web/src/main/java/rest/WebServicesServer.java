package rest;

import DTOs.CarDTO;
import DTOs.UserDTO;
import data.User;
import ejb.CarEJBInterface;
import ejb.UserEJBInterface;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;


//https://stackoverflow.com/questions/3027834/inject-an-ejb-into-jax-rs-restful-service
@Stateless
@Path("/")
public class WebServicesServer {

    @EJB
    CarEJBInterface carejb;

    @EJB
    UserEJBInterface userejb;


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
        System.out.println("Getting all cars");
        List<CarDTO> toSend = carejb.getAllCars(7);

        if(toSend!=null)
            return toSend;
        return null;
    }


    @GET
    @Path("/users")
    @Produces({MediaType.APPLICATION_XML})
    public ListUsers getAllUsers() {
        System.out.println("Getting all users");
        ListUsers lu = new ListUsers();
        lu.setUsers(userejb.getAllUsers());
        return lu;
    }
}
