package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;


public class Main {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Ivan", "Petrov", (byte)30);
        System.out.println("User с Ivan – name добавлен в базу данных");
        userService.saveUser("Maria", "Ivanova", (byte)50);
        System.out.println("User с Maria – name добавлен в базу данных");
        userService.saveUser("Petr", "Sidorov", (byte)20);
        System.out.println("User с Petr – name добавлен в базу данных");
        userService.saveUser("Svetlana", "Soboleva", (byte)40);
        System.out.println("User с Svetlana – name добавлен в базу данных");

        userService.getAllUsers().stream().forEach(System.out::println);

        userService.cleanUsersTable();

        userService.dropUsersTable();

        Util.disconnect();
    }
}
