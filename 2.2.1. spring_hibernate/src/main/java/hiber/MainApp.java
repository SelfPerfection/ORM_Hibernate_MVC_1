package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.CarService;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context =
              new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);
      CarService carService = context.getBean(CarService.class);

      // Add some Users
      userService.add(new User("User1", "Lastname1", "user1@mail.ru"));
      userService.add(new User("User2", "Lastname2", "user2@mail.ru"));
      userService.add(new User("User3", "Lastname3", "user3@mail.ru"));
      userService.add(new User("User4", "Lastname4", "user4@mail.ru"));

      // Add some Cars
      carService.add(new Car("BMW", 1));
      carService.add(new Car("BMW", 3));
      carService.add(new Car("BMW", 5));

      // Add some User with Car [use Setter]
      User userJ = new User("James", "Bond", "jb007@gmail.com");
      userJ.setCar(new Car("Aston Martin", 7));
      userService.add(userJ);

      // Add some Users with Car [use Constructor]
      userService.add(new User("Ferdinand", "Porsche", "fp991@gmail.com",
              new Car("Porsche", 991)));
      userService.add(new User("Enzo", "Ferrari", "enzo1898@gmail.com",
              new Car("Ferrari", 488)));
      userService.add(new User("Albert", "Mukhametzianov", "albert.mukhametzianov@gmail.com",
              new Car("Porsche", 991)));

      // Select all users from db
      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = " + user.getId());
         System.out.println("First Name = " + user.getFirstName());
         System.out.println("Last Name = " + user.getLastName());
         System.out.println("Email = " + user.getEmail());
         System.out.println("Car_id = " + ((user.getCar() != null) ? user.getCar() : "-" ));
         System.out.println();
      }

      // Select Users with car by model and series
      List<User> userListByCar = carService.getUsersByCar("Porsche", 991);
      userListByCar.forEach(System.out::println);

      context.close();
   }
}