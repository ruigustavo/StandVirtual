package DTOs;

import data.Car;

import java.io.Serializable;
import java.util.List;

public class UserDTO implements Serializable{

    private int id;
    private String email;
    private String password;
    private String name;
    private String address;
    private String phone;
    private List<CarDTO> sellingCars;
    private List<CarDTO> followingCars;

    public UserDTO(int id, String email, String name, String address, String phone, List<CarDTO> sellingCars, List<CarDTO> followingCars) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.sellingCars = sellingCars;
        this.followingCars = followingCars;
    }


    public List<CarDTO> getFollowingCars() {
        return followingCars;
    }

    public boolean isFollowing(int i){
        for(CarDTO c: this.followingCars){
            if(c.getId()==i)
                return true;
        }
        return false;
    }

    public void setFollowingCars(List<CarDTO> followingCars) {
        this.followingCars = followingCars;
    }



    public List<CarDTO> getSellingCars() {
        return sellingCars;
    }

    public void setSellingCars(List<CarDTO> sellingCars) {
        this.sellingCars = sellingCars;
    }



    public UserDTO() {
    }

    public UserDTO(String email, String name, String address, String phone) {
        this.email = email;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public UserDTO(String email, String password, String name, String address, String phone) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public UserDTO(int id, String email, String name, String address, String phone) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public int getId() {
        return id;
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

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", sellingCars=" + sellingCars +
                ", followingCars=" + followingCars +
                '}';
    }
}
