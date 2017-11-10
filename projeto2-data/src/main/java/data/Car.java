package data;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Expose
    private int id;
    @Expose
    private String brand;
    @Expose
    private String model;
    @Expose
    private long price;

    @ManyToOne
    private User owner;

    @ManyToMany
    private List<User> followers;


    public Car(){
        super();
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", price='" + price + '\'' +
                ", owner=" + owner +
                '}';
    }

    public Car(String brand, String model, long price, User owner) {
        super();
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.owner = owner;
        this.followers = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<User> getFollowers() {
        return followers;
    }

}
