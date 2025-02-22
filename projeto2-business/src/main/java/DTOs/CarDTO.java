package DTOs;

import java.io.Serializable;
import java.util.List;

public class CarDTO implements Serializable {
    private int id;
    private String brand;
    private String model;
    private long price;
    private long km;
    private UserDTO owner;
    private String registration_month;
    private int registration_year;
    private byte[] picture;
    private List<UserDTO> followers;


    public CarDTO(String brand, String model, long price,long km, String registration_month, int registration_year, UserDTO owner, byte[] picture) {
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.owner = owner;
        this.km=km;
        this.registration_month = registration_month;
        this.registration_year = registration_year;
        this.picture = picture;
    }

    public CarDTO(int id, String brand, String model, long price, long km, String registration_month, int registration_year, UserDTO owner, byte[] picture) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.km=km;
        this.owner = owner;
        this.registration_month = registration_month;
        this.registration_year = registration_year;
        this.picture = picture;
    }


    @Override
    public String toString() {
        return "CarDTO{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", km=" + km +
                ", owner=" + owner.getId() +
                ", registration_month='" + registration_month + '\'' +
                ", registration_year=" + registration_year +
                '}';
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getKm() {
        return km;
    }

    public void setKm(long km) {
        this.km = km;
    }

    public CarDTO(int id, String brand, String model, long price, long km, String registration_month, int registration_year, UserDTO owner, byte[] picture, List<UserDTO> followers) {
        this.id=id;
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.km=km;
        this.owner = owner;
        this.registration_month = registration_month;
        this.registration_year=registration_year;
        this.picture = picture;
        this.followers = followers;
    }

    public UserDTO getOwner() {
        return owner;
    }

    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }


    public String getBrand() {
        return brand;
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

    public byte[] getPicture(){
        return picture;
    }

    public String getPictureEncoded() {
        return new String(this.picture);
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public List<UserDTO> getFollowers() {
        return followers;
    }

    public void setFollowers(List<UserDTO> followers) {
        this.followers = followers;
    }
}
