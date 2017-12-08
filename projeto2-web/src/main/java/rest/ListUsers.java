package rest;

import DTOs.UserDTO;
import ejb.UserEJBInterface;

import javax.ejb.EJB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name="ListUsers")
public class ListUsers {
    private List<UserDTO> users;

    public ListUsers(){
        this.users = new ArrayList<>();
    }

    public List<UserDTO> getUsers() {
        return this.users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    public void addUser(UserDTO c) {
        this.users.add(c);
    }
}
