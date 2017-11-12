package DTOs;

import data.User;

import java.io.Serializable;

public class CarDTO implements Serializable {
    private int id;
    private String brand;
    private String model;
    private long price;
    private UserDTO owner;
    private String registration_month;
    private int registration_year;

    @Override
    public String toString() {
        return "CarDTO{" +
                "id=" + id + '\''+
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", owner=" + owner +
                ", registration month=" + registration_month +
                ", registration year=" + registration_year +
                '}';
    }

    public CarDTO(String brand, String model, long price, String registration_month, int registration_year, UserDTO owner) {
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.owner = owner;
        this.registration_month = registration_month;
        this.registration_year=registration_year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CarDTO(int id, String brand, String model, long price, String registration_month, int registration_year, UserDTO owner) {
        this.id=id;

        this.brand = brand;
        this.model = model;
        this.price = price;
        this.owner = owner;
        this.registration_month = registration_month;
        this.registration_year=registration_year;
    }

    public UserDTO getOwner() {
        return owner;
    }

    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }

    public CarDTO() {
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
}
