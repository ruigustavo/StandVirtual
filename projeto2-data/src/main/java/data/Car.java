package data;

import javax.persistence.*;
import java.util.List;

@Entity
public class Car {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private String brand;
    private String model;
    private String price;
    @ManyToOne
    private User owner;
    @ManyToMany
    private List<User> followers;
}
