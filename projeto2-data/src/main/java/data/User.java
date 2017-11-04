package data;

import javax.persistence.*;
import java.util.Date;
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
    @OneToMany(mappedBy = "owner")
    private List<Car> sellingCars;
    @OneToMany()
    private List<Car> followingCars;


}
