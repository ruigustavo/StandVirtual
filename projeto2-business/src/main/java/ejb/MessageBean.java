package ejb;


import Crawler.Cars;
import Crawler.XMLUnmarshal;
import DTOs.CarDTO;
import DTOs.UserDTO;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

@MessageDriven(
        name = "CarMessageHandler",
        activationConfig = {
                @ActivationConfigProperty( propertyName = "destinationType",
                        propertyValue = "javax.jms.Topic"),
                @ActivationConfigProperty( propertyName = "destination",
                        propertyValue ="/topic/TopicExample")
        }
)
public class MessageBean implements MessageListener {

    @Resource
    private MessageDrivenContext mdctx;

    @EJB
    CarEJBInterface carBean;

    @EJB
    UserEJBInterface userBean;

    public MessageBean() {
    }

    @Override
    public void onMessage(Message message) {
        System.out.println("MESSAGE BEANMESSAGE BEANMESSAGE BEANMESSAGE BEANMESSAGE BEANMESSAGE BEANMESSAGE BEANMESSAGE BEANMESSAGE BEANMESSAGE BEANMESSAGE BEANMESSAGE BEANMESSAGE BEANMESSAGE BEANMESSAGE BEANMESSAGE BEANMESSAGE BEANMESSAGE BEANMESSAGE BEANMESSAGE BEANMESSAGE BEAN");
        try {
            System.out.println(message.getStringProperty("text"));
        } catch (JMSException e) {
            e.printStackTrace();
        }
        try {
            Cars cars = (Cars) XMLUnmarshal.doUnmarshal((String) message.getStringProperty("text"));
            for(Cars.Car c : cars.getCar()){
                if(c.getKm()!=null){
                    carBean.addCar(new CarDTO(
                            c.getBrand(),
                            c.getModel(),
                            Long.parseLong(c.getPrice().getValue()),
                            Long.parseLong(c.getKm()),
                            c.getMonth(),
                            c.getYear(),
                            userBean.getUserById(4),
                            null
                    ));
                }else{
                    carBean.addCar(new CarDTO(
                            c.getBrand(),
                            c.getModel(),
                            Long.parseLong(c.getPrice().getValue()),
                            Long.parseLong("0"),
                            c.getMonth(),
                            c.getYear(),
                            userBean.getUserById(4),
                            null
                    ));
                }
            }
        } catch (JMSException e) {
            mdctx.setRollbackOnly();
            e.printStackTrace();
        }
    }
}

