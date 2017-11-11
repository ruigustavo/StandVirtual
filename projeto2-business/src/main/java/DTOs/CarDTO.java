package DTOs;

import data.User;

import java.io.Serializable;

public class CarDTO implements Serializable {
    private String brand;
    private String model;
    private long price;
    private UserDTO owner;

    @Override
    public String toString() {
        return "CarDTO{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", owner=" + owner +
                '}';
    }

    public CarDTO(String brand, String model, long price, UserDTO owner) {
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.owner = owner;
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
