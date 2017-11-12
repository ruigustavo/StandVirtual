package servlet;

import DTOs.UserDTO;
import Helper.Hasher;
import ejb.UserEJBInterface;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Main")
public class Main extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @EJB
    UserEJBInterface userejb;

    public Main() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doGet");
        System.out.println(request.toString());
        String action = request.getParameter("action");
        if(request.getSession().getAttribute("user")==null){
            if(action==null) {
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }else if(action.compareToIgnoreCase("register")==0){
                request.getRequestDispatcher("register.jsp").forward(request,response);
            }else if(action.compareToIgnoreCase("login")==0){
                request.getRequestDispatcher("login.jsp").forward(request,response);
            }
        }else{
            if(action==null){
                request.getRequestDispatcher("menu.jsp").forward(request,response);
            }else if(action.compareToIgnoreCase("edit-profile")==0){
                request.getRequestDispatcher("edit-profile.jsp").forward(request,response);
            }
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(request.toString());
        String action = request.getParameter("action");
        if(request.getSession().getAttribute("user")==null){
            if(action==null) {
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }else if(action.compareToIgnoreCase("register")==0){
                UserDTO newUser = new UserDTO(request.getParameter("email"),
                        Hasher.hashPassword(request.getParameter("password")),
                        request.getParameter("name"),
                        request.getParameter("address"),
                        request.getParameter("phone")
                );
                userejb.register(newUser);
                request.getRequestDispatcher("index.jsp")
                        .forward(request,response);
            }else if(action.compareToIgnoreCase("login")==0){
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                int result = userejb.login(email,Hasher.hashPassword(password));
                if(result>0){
                    request.getSession().setAttribute("user",userejb.getUserById(result));
                    request.getRequestDispatcher("menu.jsp").forward(request,response);
                }
            }
        }else{
            if(action==null){
                request.getRequestDispatcher("menu.jsp").forward(request,response);
            }
            else if(action.compareToIgnoreCase("logout")==0){
                request.getSession().removeAttribute("user");
                request.getRequestDispatcher("index.jsp").forward(request,response);
            }else if(action.compareToIgnoreCase("edit-profile")==0){
                UserDTO user = (UserDTO) request.getSession().getAttribute("user");
                if(request.getParameter("password")!=null){
                    System.out.println("changing password");
                    System.out.println(user.getPassword());
                    user.setPassword(Hasher.hashPassword(request.getParameter("password")));
                }
                System.out.println(user.getPassword());
                user.setEmail(request.getParameter("email"));
                user.setName(request.getParameter("name"));
                user.setAddress(request.getParameter("address"));
                user.setPhone(request.getParameter("phone"));

                userejb.editUserInfo(user);
                request.getRequestDispatcher("menu.jsp").forward(request,response);
            }
        }
    }
}
