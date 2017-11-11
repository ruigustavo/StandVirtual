import DTOs.CarDTO;
import DTOs.UserDTO;
import ejb.CarEJBInterface;
import ejb.UserEJBInterface;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Tester {

    private int userId;
    private boolean logged;
    private InitialContext ctx;
    private UserEJBInterface userEJB;
    private CarEJBInterface carEJB;

    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public Tester() {
        this.logged=false;
        usersBeginEJB();
        carsBeginEJB();
        while(1<2){
            menuTUI();
            while (logged){
                loggedMenu();
            }
        }
    }

    private void carsBeginEJB(){
        try{
            ctx = new InitialContext();
            carEJB = (CarEJBInterface) ctx.lookup("firstear/projeto2-business-0.0.1-SNAPSHOT/CarEJB!ejb.CarEJBInterface");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    private void usersBeginEJB(){
        try{
            ctx = new InitialContext();

//            NamingEnumeration<NameClassPair> list = ctx.list("");
//            while (list.hasMore()) {
//                System.out.println(list.next().getName());
//            }
            userEJB = (UserEJBInterface) ctx.lookup("firstear/projeto2-business-0.0.1-SNAPSHOT/UserEJB!ejb.UserEJBInterface");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        new Tester();

    }



    //Text-based user interface (TUI)
    private int loginTUI() {
        try {
            String[] log = new String[2];
            System.out.println("******************************");
            System.out.println("Login");
            System.out.println("******************************");

            System.out.print("Email: ");
            log[0]=br.readLine();
            System.out.print("\nPassword: ");
            log[1]=br.readLine();

            return userEJB.login(log[0],hashPassword(log[1]));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;
    }

    private void menuTUI() {
        try {
            System.out.println("******************************");
            System.out.println("1-Register");
            System.out.println("2-Login");
            System.out.println("******************************");
            String input = br.readLine();
            switch (input){
                case "1":
                    register();
                    break;
                case "2":
                    int id = loginTUI();
                    if(id>0){
                        this.userId = id;
                        this.logged = true;
                    }
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void register(){
        try {
            UserDTO newUser = new UserDTO();
            System.out.println("******************************");
            System.out.println("Register");
            System.out.println("******************************");
            System.out.println("Email: ");
            newUser.setEmail(br.readLine());
            System.out.println("******************************");
            System.out.println("Password: ");
            newUser.setPassword(hashPassword(br.readLine()));
            System.out.println("******************************");
            System.out.println("Name: ");
            newUser.setName(br.readLine());
            System.out.println("******************************");
            System.out.println("Address: ");
            newUser.setAddress(br.readLine());
            System.out.println("******************************");
            System.out.println("Phone");
            newUser.setPhone(br.readLine());
            userEJB.register(newUser);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void editProfileGUI(){
        System.out.println("******************************");
        System.out.println("Select the paramter you want to edit");
        System.out.println("1-Name: ");
        System.out.println("2-Email: ");
        System.out.println("3-Adress: ");
        System.out.println("4-Phone: ");
        System.out.println("5-Password");
        System.out.println("0-Logout");
        try {
            String field = br.readLine();
            String value = null;
            System.out.println("******************************");
            switch (field) {
                case "1":
                    System.out.println("Insert your new name:");
                    value = br.readLine();
                    break;
                case "2":
                    System.out.println("Insert your new e-mail:");
                    value = br.readLine();
                    break;
                case "3":
                    System.out.println("Insert your new address:");
                    value = br.readLine();
                    break;
                case "4":
                    System.out.println("Insert your new phone:");
                    value = br.readLine();
                    break;
                case "5":
                    System.out.println("Insert your new password:");
                    value = br.readLine();
                    value = hashPassword(value);
                    break;
                default:
                    logged = false;
                    return;
            }
            userEJB.editUserInfo(this.userId,Integer.parseInt(field),value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteUserGUI(){
        int aux = this.userId;
        logged = false;
        userEJB.deleteUserById(aux);
        System.out.println("User Eliminated");
    }
private void showAllMyCarsGUI(){
    System.out.println("List of cars you own");
    List<CarDTO> myCars = userEJB.getCarsOfUser(this.userId);
    for (CarDTO p : myCars) {
        System.out.println(p.toString());
    }
}

    private void showAllCarsGUI(int order){
        List<CarDTO> myCars = carEJB.getAllCars(order);
        for (CarDTO p : myCars) {
            System.out.println(p.toString());
        }
        System.out.println("1-Price ascending : ");
        System.out.println("2-Price descending: ");
        System.out.println("3-Brand ascending: ");
        System.out.println("4-Brand descendin: ");
        System.out.println("5-Brand and model ascending");
        System.out.println("6-Brand and model descending");
        System.out.println("0-Go back to main menu");
        String input = null;

            try {
                input = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(Integer.parseInt(input)!=0)
                showAllCarsGUI(Integer.parseInt(input));
            else loggedMenu();

    }
    private void showCarsByBrandGUI(int order){
        System.out.println("Insert the brand you desire:");
        try {
            String brand = br.readLine();
            showCarsByBrandAuxGUI(brand, order);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private  void showCarsByBrandAuxGUI(String brand, int order){
        List<CarDTO> myCars = carEJB.getCarsByBrand(brand,order);
        for (CarDTO p : myCars) {
            System.out.println(p.toString());
        }
        System.out.println("1-Price ascending : ");
        System.out.println("2-Price descending: ");
        System.out.println("3-Model ascending: ");
        System.out.println("4-Model descendin: ");
        System.out.println("0-Back to main Menu");
        String new_order = null;
        try {
            new_order = br.readLine();
            if(Integer.parseInt(new_order)>0)
                showCarsByBrandAuxGUI(brand,Integer.parseInt(new_order));
            else loggedMenu();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void showCarsByBrandAndMode(int order){
        System.out.println("Insert the brand you desire:");

        try {
            String brand = br.readLine();
            System.out.println("Insert the model you desire:");
            String model = br.readLine();
            showCarsByBrandAndModelAuxGUI(brand,model, order);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   private void showCarsByBrandAndModelAuxGUI(String brand, String model, int order){

       List<CarDTO> myCars = carEJB.getCarsByBrandAndModel(brand,model,order);
       for (CarDTO p : myCars) {
           System.out.println(p.toString());
       }
       System.out.println("1-Price ascending : ");
       System.out.println("2-Price descending: ");
       System.out.println("0-Back to main Menu");
       String new_order = null;
       try {
           new_order = br.readLine();
           if(Integer.parseInt(new_order)>0)
               showCarsByBrandAndModelAuxGUI(brand,model,Integer.parseInt( new_order));
           else loggedMenu();
       } catch (IOException e) {
           e.printStackTrace();
       }
    }

    private void showCarsByPriceRangeGUI(){
        System.out.println("Insert lower value");
        try {
            String low= br.readLine();
            System.out.println("Insert upper value");
            String upper = br.readLine();
            showCarsByPriceRangeAuxGUI(low,upper, 2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private  void showCarsByPriceRangeAuxGUI(String low,String upper ,int order){
        try {
            List<CarDTO> Cars = carEJB.getCarsByPriceRange(Long.parseLong(low),Long.parseLong(upper),order);
            for (CarDTO p : Cars) {
                System.out.println(p.toString());
            }
            System.out.println("1-Search again");
            System.out.println("2-Price ascending : ");
            System.out.println("3-Price descending: ");
            System.out.println("4-Brand ascending: ");
            System.out.println("5-Brand descendin: ");
            System.out.println("6-Brand and model ascending");
            System.out.println("7-Brand and model descending");
            System.out.println("0-Back to main menu");
            String input = br.readLine();
            switch (input){
                case "1":
                    showCarsByPriceRangeGUI();
                    break;
                case "0":
                    loggedMenu();
                    break;
                default:
                    showCarsByPriceRangeAuxGUI(low, upper, Integer.parseInt(input));
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void addNewCarForSaleGUI(){
        CarDTO newCar = new CarDTO();
        UserDTO s = userEJB.getUserById(this.userId);
        try {
            System.out.println("Insert the brand:");
            newCar.setBrand(br.readLine());
            System.out.println("Insert the mode:");
            newCar.setModel(br.readLine());
            System.out.println("Insert the price:");
            newCar.setPrice(Long.parseLong(br.readLine()));
            newCar.setOwner(s);
            carEJB.addCar(newCar);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private void editCarGUI(){
        System.out.println("List of cars you own");
        List<CarDTO> myCars = userEJB.getCarsOfUser(this.userId);
        for (CarDTO p : myCars) {
            System.out.println(p.toString());
        }

        try {
            String value=null;
            System.out.println("Insert the id of the car you want to edit");
            String temp_id = br.readLine();
            System.out.println("Select the parameter you want to edit");
            System.out.println("1-Brand");
            System.out.println("2-Model");
            System.out.println("3-Price");
            System.out.println("4-Data(does not exist)");
            String field = br.readLine();
            switch (field) {
                case "1":
                    System.out.println("Insert your new Brand:");
                    value = br.readLine();
                    break;
                case "2":
                    System.out.println("Insert your new model:");
                    value = br.readLine();
                    break;
                case "3":
                    System.out.println("Insert your new price:");
                    value = br.readLine();
                    break;
            }
            carEJB.editCarInfo(Integer.parseInt(temp_id),Integer.parseInt(field),value);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loggedMenu() {

        try {
            System.out.println("1-Edit Profile");
            System.out.println("2-Delete account(not working)");
            System.out.println("3-Show all my cars for sale");
            System.out.println("4-Show all cars for sale");
            System.out.println("5-Search cars by brand");
            System.out.println("6-Search cars by brand and model");
            System.out.println("7-Search cars by range of price");
            System.out.println("8-Add new car for sale");
            System.out.println("9-Edit one my cars");
            System.out.println("0-Logout");
            String input = br.readLine();
            switch (input) {
                case "1":
                    editProfileGUI();
                    break;
                case "2":
                    deleteUserGUI();
                    break;
                case "3":
                    showAllMyCarsGUI();
                    break;
                case "4":
                    showAllCarsGUI(1);
                    break;
                case "5":
                    showCarsByBrandGUI(1);
                    break;
                case "6":
                    showCarsByBrandAndMode(1);
                    break;
                case "7":
                    showCarsByPriceRangeGUI();
                    break;
                case "8":
                    addNewCarForSaleGUI();
                    break;
                case "9":
                    editCarGUI();
                    break;
                default:
                    logged = false;
                    return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String hashPassword(String passwordToHash){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(passwordToHash.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}