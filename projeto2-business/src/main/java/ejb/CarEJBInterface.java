package ejb;

import javax.ejb.Local;
import javax.ejb.Remote;

@Remote
public interface CarEJBInterface {
    String getAllCars(int order);
    String getCarsByBrand(String brand, int order);
    String getCarsByBrandAndModel(String brand, String model, int order);
    String getCarsByPriceRange(long low_value, long up_value, int order);
}
