package data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String brand;
    private String model;
    private long price;
    private String registration_month;
    private int registration_year;
    private long km;

    public long getKm() {
        return km;
    }

    public void setKm(long km) {
        this.km = km;
    }

    public Car(String brand, String model, long price, long km, String registration_month, int registration_year, byte[] picture, User owner) {
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.km=km;
        this.registration_month = registration_month;
        this.registration_year = registration_year;
        this.picture = picture;

        this.owner = owner;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    @Lob
    private byte[] picture;


    @ManyToOne(fetch=FetchType.LAZY,cascade={CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name="owner_id")
    private User owner;

    @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE})
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

    public String getRegistration_month() {
        return registration_month;
    }

    public void setRegistration_month(String registration_month) {
        this.registration_month = registration_month;
    }

    public int getRegistration_year() {
        return registration_year;
    }

    public void setRegistration_year(int registration_year) {
        this.registration_year = registration_year;
    }

    public Car(String brand, String model, long price,long km, String registration_month, int registration_year, User owner) {
        super();
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.km=km;
        this.owner = owner;
        this.registration_month = registration_month;
        this.registration_year = registration_year;
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
