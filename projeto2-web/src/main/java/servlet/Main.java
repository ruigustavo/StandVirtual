package servlet;

import DTOs.CarDTO;
import DTOs.UserDTO;
import Helper.Hasher;
import ejb.CarEJBInterface;
import ejb.UserEJBInterface;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.registry.infomodel.User;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Enumeration;
import java.util.List;

@WebServlet("/Main")
@MultipartConfig
public class Main extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @EJB
    UserEJBInterface userejb;
    @EJB
    CarEJBInterface carejb;

    public Main() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if(request.getSession().getAttribute("user")==null){
            if(action==null){
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
            else if(action.compareToIgnoreCase("register")==0){
                request.getRequestDispatcher("register.jsp").forward(request,response);
            }else if(action.compareToIgnoreCase("login")==0){
                request.getRequestDispatcher("login.jsp").forward(request,response);
            }else{
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        }else{

            if(action==null){
                request.getRequestDispatcher("menu.jsp").forward(request,response);
            }
            else if(action.compareToIgnoreCase("edit-profile")==0){
                request.getRequestDispatcher("edit-profile.jsp").forward(request,response);
            }else if(action.compareToIgnoreCase("new-car")==0){
                request.getRequestDispatcher("new-car.jsp").forward(request,response);
            }else if(action.compareToIgnoreCase("edit-car")==0){
                int id = Integer.parseInt(request.getParameter("id"));
                CarDTO car = (CarDTO) carejb.getCarById(id);
                request.getSession().setAttribute("car",car);
                request.getRequestDispatcher("edit-car.jsp").forward(request,response);
            }else if(action.compareToIgnoreCase("my-cars")==0){
                UserDTO user = (UserDTO) request.getSession().getAttribute("user");
                List<CarDTO> cars = carejb.getCarsOfUser(user.getId());
                request.getSession().setAttribute("carslist",cars);
                request.getRequestDispatcher("my-cars.jsp").forward(request,response);
            }else if(action.compareToIgnoreCase("detail-car")==0){
                int id = Integer.parseInt(request.getParameter("id"));
                CarDTO car = (CarDTO) carejb.getCarById(id);
                request.getSession().setAttribute("car",car);
                request.getRequestDispatcher("detail-car.jsp").forward(request,response);
            }else if(action.compareToIgnoreCase("search-car")==0){
                request.getRequestDispatcher("menu.jsp").forward(request,response);
            }else if(action.compareToIgnoreCase("list-all")==0){
                int order = Integer.parseInt(request.getParameter("order"));
                List<CarDTO> cars = carejb.getAllCars(order);
                List<String> brands = carejb.getDistinctBrands();
                List<String> models = carejb.getDistinctModels();
                request.getSession().setAttribute("carslist",cars);
                request.getSession().setAttribute("brandslist",brands);
                request.getSession().setAttribute("modelslist",models);
                request.getRequestDispatcher("list-all.jsp").forward(request,response);
            }else if(action.compareToIgnoreCase("list-brand-model")==0){
                int order = Integer.parseInt(request.getParameter("order"));
                List<CarDTO> cars = carejb.getCarsByBrandAndModel(request.getParameter("brand"),request.getParameter("model"),order);
                List<String> brands = carejb.getDistinctBrands();
                List<String> models = carejb.getDistinctModels();
                request.getSession().setAttribute("carslist",cars);
                request.getSession().setAttribute("brandslist",brands);
                request.getSession().setAttribute("modelslist",models);
                request.getRequestDispatcher("list-all.jsp").forward(request,response);
            }else if(action.compareToIgnoreCase("list-brand")==0){
                int order = Integer.parseInt(request.getParameter("order"));
                List<CarDTO> cars = carejb.getCarsByBrand(request.getParameter("brand"),order);
                List<String> brands = carejb.getDistinctBrands();
                List<String> models = carejb.getDistinctModels();
                request.getSession().setAttribute("carslist",cars);
                request.getSession().setAttribute("brandslist",brands);
                request.getSession().setAttribute("modelslist",models);
                request.getRequestDispatcher("list-all.jsp").forward(request,response);
            }else if(action.compareToIgnoreCase("list-price")==0){
                int order = Integer.parseInt(request.getParameter("order"));
                List<CarDTO> cars = carejb.getCarsByPriceRange(Long.parseLong(request.getParameter("low_value")),Long.parseLong(request.getParameter("up_value")),order);
                List<String> brands = carejb.getDistinctBrands();
                List<String> models = carejb.getDistinctModels();
                request.getSession().setAttribute("carslist",cars);
                request.getSession().setAttribute("brandslist",brands);
                request.getSession().setAttribute("modelslist",models);
                request.getRequestDispatcher("list-all.jsp").forward(request,response);
            }else if(action.compareToIgnoreCase("list-km")==0){
                int order = Integer.parseInt(request.getParameter("order"));
                List<CarDTO> cars = carejb.getCarsByKmRange(Long.parseLong(request.getParameter("low_value")),Long.parseLong(request.getParameter("up_value")),order);
                List<String> brands = carejb.getDistinctBrands();
                List<String> models = carejb.getDistinctModels();
                request.getSession().setAttribute("carslist",cars);
                request.getSession().setAttribute("brandslist",brands);
                request.getSession().setAttribute("modelslist",models);
                request.getRequestDispatcher("list-all.jsp").forward(request,response);
            }else if(action.compareToIgnoreCase("list-year")==0){
                int order = Integer.parseInt(request.getParameter("order"));
                List<CarDTO> cars = carejb.getCarsNewerThan(Integer.parseInt(request.getParameter("year")),order);
                List<String> brands = carejb.getDistinctBrands();
                List<String> models = carejb.getDistinctModels();
                request.getSession().setAttribute("carslist",cars);
                request.getSession().setAttribute("brandslist",brands);
                request.getSession().setAttribute("modelslist",models);
                request.getRequestDispatcher("list-all.jsp").forward(request,response);
            }else{
                request.getRequestDispatcher("menu.jsp").forward(request,response);
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
                response.sendRedirect("index.jsp");
            }else if(action.compareToIgnoreCase("login")==0){
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                int result = userejb.login(email,Hasher.hashPassword(password));
                if(result>0){
                    UserDTO user = (UserDTO) userejb.getUserById(result);
                    request.getSession().setAttribute("user",user);
                    request.getRequestDispatcher("menu.jsp").forward(request,response);
                }else{
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
            }else{
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        }else{
            UserDTO user = (UserDTO) request.getSession().getAttribute("user");
            String finalDestination = "menu.jsp";
            if(action.compareToIgnoreCase("logout")==0){
                request.getSession().removeAttribute("user");
                finalDestination = "index.jsp";
            }else if(action.compareToIgnoreCase("edit-profile")==0){
                if(request.getParameter("password")!=null){
                    user.setPassword(Hasher.hashPassword(request.getParameter("password")));
                }
                user.setEmail(request.getParameter("email"));
                user.setName(request.getParameter("name"));
                user.setAddress(request.getParameter("address"));
                user.setPhone(request.getParameter("phone"));

                userejb.editUserInfo(user);

            }else if(action.compareToIgnoreCase("new-car")==0){

                CarDTO car = new CarDTO(request.getParameter("brand"),
                        request.getParameter("model"),
                        Long.parseLong(request.getParameter("price")),
                        Long.parseLong(request.getParameter("km")),
                        request.getParameter("registration_month"),
                        Integer.parseInt(request.getParameter("registration_year")),
                        (UserDTO) request.getSession().getAttribute("user"),
                        Base64.getEncoder().encode(
                                encodePicture(request.getPart("picture"))
                                        .toByteArray())
                );
                carejb.addCar(car);

            }else if(action.compareToIgnoreCase("follow-car")==0){
                String id = request.getParameter("id");
                carejb.followCar(Integer.parseInt(id),user.getId());
            }else if(action.compareToIgnoreCase("delete-car")==0){
                String id = request.getParameter("id");
                carejb.deleteCarById(Integer.parseInt(id));
            }else if(action.compareToIgnoreCase("unfollow-car")==0){
                String id = request.getParameter("id");
                carejb.unfollowCar(Integer.parseInt(id),user.getId());
            }else if(action.compareToIgnoreCase("edit-car")==0){
                CarDTO car;
                if(request.getPart("picture").getSize()>0){
                    car = new CarDTO(Integer.parseInt(request.getParameter("id")),
                            request.getParameter("brand"),
                            request.getParameter("model"),
                            Long.parseLong(request.getParameter("price")),
                            Long.parseLong(request.getParameter("km")),
                            request.getParameter("registration_month"),
                            Integer.parseInt(request.getParameter("registration_year")),
                            user,
                            Base64.getEncoder().encode(
                                    encodePicture(request.getPart("picture"))
                                            .toByteArray())
                    );
                }else{
                    car = new CarDTO(Integer.parseInt(request.getParameter("id")),
                            request.getParameter("brand"),
                            request.getParameter("model"),
                            Long.parseLong(request.getParameter("price")),
                            Long.parseLong(request.getParameter("km")),
                            request.getParameter("registration_month"),
                            Integer.parseInt(request.getParameter("registration_year")),
                            user,
                            null
                    );
                }
                carejb.editCarInfo(car);
            }
            if(action.compareToIgnoreCase("logout")!=0){
                UserDTO refreshed = userejb.getUserById(user.getId());
                request.getSession().setAttribute("user",refreshed);
                System.out.println(refreshed.getFollowingCars().toString());
            }
            response.sendRedirect(finalDestination);
        }
    }

    public ByteArrayOutputStream encodePicture(Part filePart){
        //https://stackoverflow.com/questions/19138706/how-to-convert-part-to-blob-so-i-can-store-it-in-mysql/19139125#19139125

        InputStream fileContent;
        try {
            fileContent = filePart.getInputStream();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[10240];
            for (int length = 0; (length = fileContent.read(buffer)) > 0;) output.write(buffer, 0, length);
            return output;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
