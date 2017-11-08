import DTOs.UserDTO;
import ejb.UserEJBInterface;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Tester {

    private int userId;
    private boolean logged;
    private InitialContext ctx;
    private UserEJBInterface userEJB;

    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public Tester() {
        this.logged=false;
        usersBeginEJB();
        while(1<2){
            menuTUI();
            while (logged){
                loggedMenu();
            }
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



    private void loggedMenu() {

        try {
            System.out.println("TODO");
            br.readLine();
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