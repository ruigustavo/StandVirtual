package data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String email;
    private String password;
    private String name;
    private String address;
    private String phone;

    //In this case Unidirectional helps to see the cars the user is selling
    //and the owner of the car that its being sold.
    @OneToMany
    private List<Car> sellingCars;

    //Bidirectional Many2Many because i just need to see who's following the car
    //not the other way, so car wins the owning side.
    @ManyToMany(mappedBy = "followers")
    private List<Car> followingCars;


    public User(String email, String password, String name, String address, String phone) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.sellingCars = new ArrayList<>();
        this.followingCars = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Car> getSellingCars() {
        return sellingCars;
    }


    public List<Car> getFollowingCars() {
        return followingCars;
    }


    public int getId() {
        return id;
    }
}
