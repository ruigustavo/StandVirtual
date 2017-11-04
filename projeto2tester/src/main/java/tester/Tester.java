package tester;


import ejb.UserEJBInterface;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.naming.Context;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Tester {
    @Inject
    UserEJBInterface userEJBlocal;

    private int userId;
    private boolean logged;

    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public Tester() {
        this.logged=false;
        while(!logged){
            int result = loginTUI();
            if(result>0){
                System.out.println("******************************");
                System.out.println("Success");
                System.out.println("******************************");
                this.logged=true;
                this.userId=result;
            }
            while(logged){
                menuTUI();
            }
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

            return userEJBlocal.login(log[0],hashPassword(log[1]));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;
    }

    private void menuTUI() {
        System.out.println("******************************");
        System.out.println("do something;");
        System.out.println("******************************");

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
