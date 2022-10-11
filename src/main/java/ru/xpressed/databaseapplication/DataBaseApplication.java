package ru.xpressed.databaseapplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.xpressed.databaseapplication.repository.*;

import java.sql.Date;
import java.util.*;


@SpringBootApplication
public class DataBaseApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DataBaseApplication.class, args);
    }

    private final Scanner scanner = new Scanner(System.in);
    private final Map<String, String> commands = new HashMap<>();

    private ClientRepository clientRepository;
    private DishRepository dishRepository;
    private DistributionRepository distributionRepository;
    private MenuRepository menuRepository;
    private OrderRepository orderRepository;
    private PositionsRepository positionsRepository;
    private ShiftRepository shiftRepository;
    private WaiterRepository waiterRepository;

    @Autowired
    public void setPositionsRepository(PositionsRepository positionsRepository) {
        this.positionsRepository = positionsRepository;
    }

    @Autowired
    public void setShiftRepository(ShiftRepository shiftRepository) {
        this.shiftRepository = shiftRepository;
    }

    @Autowired
    public void setWaiterRepository(WaiterRepository waiterRepository) {
        this.waiterRepository = waiterRepository;
    }

    @Autowired
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Autowired
    public void setDishRepository(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @Autowired
    public void setDistributionRepository(DistributionRepository distributionRepository) {
        this.distributionRepository = distributionRepository;
    }

    @Autowired
    public void setMenuRepository(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void run(String... args) {
        System.out.println("Welcome to the Restaurant's inner CLI\n" +
                "Web Interface is ready on port 8080\n" +
                "Please, consider reading HELP info! [ help ]");

        loop: while (true) {
            System.out.print("\ncommand > ");
            String input = scanner.nextLine();

            if (input.matches("^[a-z]+.*")) {
                String mainCommand = input.split(" ", 2)[0];
                if (input.split(" ", 2).length > 1) {
                    Arrays.stream(input.split(" ", 2)[1].split("\\s+(?=-)")).forEach(com -> commands.put(com.split(" ")[0], com.split(" ").length > 1 ? com.split(" ")[1] : null));
                }

                switch (mainCommand) {
                    default -> System.out.println("Unknown command");

                    case "help" -> System.out.println("""
                            List of available commands
                            help\t\tShow list of available commands.
                                                    
                            exit\t\tExit the application.
                                                    
                            get\t\t\tGet information from any repository.
                            \t[ -r ... ]\t\tKey for choosing repository
                            \t(Client, Dish, Distribution, Menu, Order, Positions, Shift, Waiter).
                            \t[ -a ]\t\t\tShow all records in repository.
                            \t[ -i ... ]\t\tSearch for record by ID.
                            \t[ -p ... ]\t\tParametrized search of records. Try any command for hint.
                            
                            insert\t\tInsert record into any table.
                            \t[ -r ... ]\t\tKey for choosing repository
                            \t(Client, Dish, Distribution, Menu, Order, Positions, Shift, Waiter).
                            \t[ -p ... ]\t\tParametrized values to insert. Try any command for hint.
                            
                            delete\t\tDelete record from any table.
                            \t[ -i ... ]\t\tSearch for record by ID or IDs.""");

                    case "exit" -> {
                        System.out.println("Application is shutting down!");
                        System.exit(0);
                    }

                    case "get" -> {
                        if (commands.get("-r") != null) {
                            switch (commands.get("-r")) {
                                default -> System.out.println("Wrong usage of [ get -r ]!");

                                case "client" -> {
                                    if (commands.containsKey("-a")) {
                                        System.out.println(clientRepository.findAll().toString());
                                    } else if (commands.get("-i") != null) {
                                        System.out.println(clientRepository.find(Integer.parseInt(commands.get("-i"))));
                                    } else if (commands.get("-p") != null) {
                                        String[] params = commands.get("-p").split(",");
                                        if (params.length == 4) {
                                            for (int iter = 0; iter < params.length; iter++) {
                                                if (Objects.equals(params[iter], "@")) {
                                                    params[iter] = null;
                                                }
                                            }
                                            System.out.println(clientRepository.find(params[0] != null ? Integer.parseInt(params[0]) : null, params[1], params[2], params[3]));
                                        } else {
                                            System.out.println("""
                                                    Wrong usage of [ get -r client -p ... ]!
                                                    Try: [ get -r client -p idOrder(@),surname(@),name(@),patronymic(@) ]
                                                    Use "@" if you don't know the value!""");
                                        }
                                    } else {
                                        System.out.println("Wrong usage of [ get -r client ]!");
                                    }
                                }

                                case "dish" -> {
                                    if (commands.containsKey("-a")) {
                                        System.out.println(dishRepository.findAll().toString());
                                    } else if (commands.get("-i") != null) {
                                        System.out.println(dishRepository.find(Integer.parseInt(commands.get("-i"))));
                                    } else if (commands.get("-p") != null) {
                                        String[] params = commands.get("-p").split(",");
                                        if (params.length == 4) {
                                            for (int iter = 0; iter < params.length; iter++) {
                                                if (Objects.equals(params[iter], "@")) {
                                                    params[iter] = null;
                                                }
                                            }
                                            System.out.println(dishRepository.find(params[0], params[1] != null ? Integer.parseInt(params[1]) : null, params[2], params[3] != null ? Integer.parseInt(params[3]) : null));
                                        } else {
                                            System.out.println("""
                                                    Wrong usage of [ get -r dish -p ... ]!
                                                    Try: [ get -r dish -p type(@),calories(@),compound(@),price(@) ]
                                                    Use "@" if you don't know the value!""");
                                        }
                                    } else {
                                        System.out.println("Wrong usage of [ get -r dish ]!");
                                    }
                                }

                                case "distribution" -> {
                                    if (commands.containsKey("-a")) {
                                        System.out.println(distributionRepository.findAll().toString());
                                    } else if (commands.get("-p") != null) {
                                        String[] params = commands.get("-p").split(",");
                                        if (params.length == 2) {
                                            for (int iter = 0; iter < params.length; iter++) {
                                                if (Objects.equals(params[iter], "@")) {
                                                    params[iter] = null;
                                                }
                                            }
                                            System.out.println(distributionRepository.find(params[0] != null ? Integer.parseInt(params[0]) : null, params[1] != null ? Integer.parseInt(params[1]) : null));
                                        } else {
                                            System.out.println("""
                                                    Wrong usage of [ get -r distribution -p ... ]!
                                                    Try: [ get -r dish -p idOrder(@),idWaiter(@) ]
                                                    Use "@" if you don't know the value!""");
                                        }
                                    } else {
                                        System.out.println("Wrong usage of [ get -r distribution ]!");
                                    }
                                }

                                case "menu" -> {
                                    if (commands.containsKey("-a")) {
                                        System.out.println(menuRepository.findAll().toString());
                                    } else if (commands.get("-i") != null) {
                                        System.out.println(menuRepository.find(Integer.parseInt(commands.get("-i"))));
                                    } else if (commands.get("-p") != null) {
                                        String[] params = commands.get("-p").split(",");
                                        if (params.length == 1) {
                                            for (int iter = 0; iter < params.length; iter++) {
                                                if (Objects.equals(params[iter], "@")) {
                                                    params[iter] = null;
                                                }
                                            }
                                            System.out.println(menuRepository.find(params[0]));
                                        } else {
                                            System.out.println("""
                                                    Wrong usage of [ get -r menu -p ... ]!
                                                    Try: [ get -r menu -p type(@) ]
                                                    Use "@" if you don't know the value!""");
                                        }
                                    } else {
                                        System.out.println("Wrong usage of [ get -r menu ]!");
                                    }
                                }

                                case "order" -> {
                                    if (commands.containsKey("-a")) {
                                        System.out.println(orderRepository.findAll().toString());
                                    } else if (commands.get("-i") != null) {
                                        System.out.println(orderRepository.find(Integer.parseInt(commands.get("-i"))));
                                    } else if (commands.get("-p") != null) {
                                        String[] params = commands.get("-p").split(",");
                                        if (params.length == 2) {
                                            for (int iter = 0; iter < params.length; iter++) {
                                                if (Objects.equals(params[iter], "@")) {
                                                    params[iter] = null;
                                                }
                                            }
                                            System.out.println(orderRepository.find(params[0] != null ? Integer.parseInt(params[0]) : null, params[1] != null ? Integer.parseInt(params[1]) : null));
                                        } else {
                                            System.out.println("""
                                                    Wrong usage of [ get -r order -p ... ]!
                                                    Try: [ get -r dish -p idOrder(@),idDish(@) ]
                                                    Use "@" if you don't know the value!""");
                                        }
                                    } else {
                                        System.out.println("Wrong usage of [ get -r order ]!");
                                    }
                                }

                                case "positions" -> {
                                    if (commands.containsKey("-a")) {
                                        System.out.println(positionsRepository.findAll().toString());
                                    } else if (commands.get("-p") != null) {
                                        String[] params = commands.get("-p").split(",");
                                        if (params.length == 2) {
                                            for (int iter = 0; iter < params.length; iter++) {
                                                if (Objects.equals(params[iter], "@")) {
                                                    params[iter] = null;
                                                }
                                            }
                                            System.out.println(positionsRepository.find(params[0] != null ? Integer.parseInt(params[0]) : null, params[1] != null ? Integer.parseInt(params[1]) : null));
                                        } else {
                                            System.out.println("""
                                                    Wrong usage of [ get -r positions -p ... ]!
                                                    Try: [ get -r positions -p idMenu(@),idDish(@) ]
                                                    Use "@" if you don't know the value!""");
                                        }
                                    } else {
                                        System.out.println("Wrong usage of [ get -r positions ]!");
                                    }
                                }

                                case "shift" -> {
                                    if (commands.containsKey("-a")) {
                                        System.out.println(shiftRepository.findAll().toString());
                                    } else if (commands.get("-i") != null) {
                                        System.out.println(shiftRepository.find(Integer.parseInt(commands.get("-i"))));
                                    } else if (commands.get("-p") != null) {
                                        String[] params = commands.get("-p").split(",");
                                        if (params.length == 2) {
                                            for (int iter = 0; iter < params.length; iter++) {
                                                if (Objects.equals(params[iter], "@")) {
                                                    params[iter] = null;
                                                }
                                            }
                                            System.out.println(shiftRepository.find(params[0], params[1]));
                                        } else {
                                            System.out.println("""
                                                    Wrong usage of [ get -r shift -p ... ]!
                                                    Try: [ get -r shift -p type(@),timetable(@) ]
                                                    Use "@" if you don't know the value!""");
                                        }
                                    } else {
                                        System.out.println("Wrong usage of [ get -r shift ]!");
                                    }
                                }

                                case "waiter" -> {
                                    if (commands.containsKey("-a")) {
                                        System.out.println(waiterRepository.findAll().toString());
                                    } else if (commands.get("-i") != null) {
                                        System.out.println(waiterRepository.find(Integer.parseInt(commands.get("-i"))));
                                    } else if (commands.get("-p") != null) {
                                        String[] params = commands.get("-p").split(",");
                                        if (params.length == 6) {
                                            for (int iter = 0; iter < params.length; iter++) {
                                                if (Objects.equals(params[iter], "@")) {
                                                    params[iter] = null;
                                                }
                                            }
                                            System.out.println(waiterRepository.find(params[0], params[1], params[2], params[3], params[4] != null ? Date.valueOf(params[4]) : null, params[5] != null ? Integer.parseInt(params[5]) : null));
                                        } else {
                                            System.out.println("""
                                                    Wrong usage of [ get -r waiter -p ... ]!
                                                    Try: [ get -r waiter -p surname(@),name(@),patronymic(@),gender(@),dateOfBirth[yyyy-mm-dd](@) ]
                                                    Use "@" if you don't know the value!""");
                                        }
                                    } else {
                                        System.out.println("Wrong usage of [ get -r waiter ]!");
                                    }
                                }
                            }
                        } else {
                            System.out.println("Wrong usage of [ get ]!");
                        }
                    }

                    case "insert" -> {
                        if (commands.get("-r") != null) {
                            switch (commands.get("-r")) {
                                default -> System.out.println("Wrong usage of [ insert -r ]!");

                                case "client" -> {
                                    if (commands.get("-p") != null) {
                                        String[] params = commands.get("-p").split(",");
                                        if (params.length == 4) {
                                            try {
                                                clientRepository.insert(Integer.parseInt(params[0]), params[1], params[2], params[3]);
                                                System.out.println("Successful insertion!");
                                            } catch (Exception e) {
                                                System.out.println("Something went wrong!\n" + e.getMessage());
                                            }
                                        } else {
                                            System.out.println("""
                                                    Wrong usage of [ insert -r client -p ... ]!
                                                    Try: [ insert -r client -p idOrder,surname,name,patronymic ]
                                                    All parameters must be entered!""");
                                        }
                                    } else {
                                        System.out.println("Wrong usage of [ insert -r client ]!");
                                    }
                                }

                                case "dish" -> {
                                    if (commands.get("-p") != null) {
                                        String[] params = commands.get("-p").split(",");
                                        if (params.length == 4) {
                                            try {
                                                dishRepository.insert(params[0], Integer.parseInt(params[1]), params[2], Integer.parseInt(params[3]));
                                                System.out.println("Successful insertion!");
                                            } catch (Exception e) {
                                                System.out.println("Something went wrong!\n" + e.getMessage());
                                            }
                                        } else {
                                            System.out.println("""
                                                    Wrong usage of [ insert -r dish -p ... ]!
                                                    Try: [ insert -r dish -p type,calories,compound,price ]
                                                    All parameters must be entered!""");
                                        }
                                    } else {
                                        System.out.println("Wrong usage of [ insert -r dish ]!");
                                    }
                                }

                                case "distribution" -> {
                                    if (commands.get("-p") != null) {
                                        String[] params = commands.get("-p").split(",");
                                        if (params.length == 2) {
                                            try {
                                                distributionRepository.insert(Integer.parseInt(params[0]), Integer.parseInt(params[1]));
                                                System.out.println("Successful insertion!");
                                            } catch (Exception e) {
                                                System.out.println("Something went wrong!\n" + e.getMessage());
                                            }
                                        } else {
                                            System.out.println("""
                                                    Wrong usage of [ insert -r distribution -p ... ]!
                                                    Try: [ insert -r distribution -p idOrder,idWaiter ]
                                                    All parameters must be entered!""");
                                        }
                                    } else {
                                        System.out.println("Wrong usage of [ insert -r distribution ]!");
                                    }
                                }

                                case "menu" -> {
                                    if (commands.get("-p") != null) {
                                        String[] params = commands.get("-p").split(",");
                                        if (params.length == 1) {
                                            try {
                                                menuRepository.insert(params[0]);
                                                System.out.println("Successful insertion!");
                                            } catch (Exception e) {
                                                System.out.println("Something went wrong!\n" + e.getMessage());
                                            }
                                        } else {
                                            System.out.println("""
                                                    Wrong usage of [ insert -r menu -p ... ]!
                                                    Try: [ insert -r menu -p type ]
                                                    All parameters must be entered!""");
                                        }
                                    } else {
                                        System.out.println("Wrong usage of [ insert -r menu ]!");
                                    }
                                }

                                case "order" -> {
                                    if (commands.get("-p") != null) {
                                        String[] params = commands.get("-p").split(",");
                                        if (params.length == 1) {
                                            try {
                                                orderRepository.insert(Integer.parseInt(params[0]));
                                                System.out.println("Successful insertion!");
                                            } catch (Exception e) {
                                                System.out.println("Something went wrong!\n" + e.getMessage());
                                            }
                                        } else {
                                            System.out.println("""
                                                    Wrong usage of [ insert -r order -p ... ]!
                                                    Try: [ insert -r order -p idDish ]
                                                    All parameters must be entered!""");
                                        }
                                    } else {
                                        System.out.println("Wrong usage of [ insert -r order ]!");
                                    }
                                }

                                case "positions" -> {
                                    if (commands.get("-p") != null) {
                                        String[] params = commands.get("-p").split(",");
                                        if (params.length == 2) {
                                            try {
                                                positionsRepository.insert(Integer.parseInt(params[0]), Integer.parseInt(params[1]));
                                                System.out.println("Successful insertion!");
                                            } catch (Exception e) {
                                                System.out.println("Something went wrong!\n" + e.getMessage());
                                            }
                                        } else {
                                            System.out.println("""
                                                    Wrong usage of [ insert -r positions -p ... ]!
                                                    Try: [ insert -r positions -p idMenu,idDish ]
                                                    All parameters must be entered!""");
                                        }
                                    } else {
                                        System.out.println("Wrong usage of [ insert -r positions ]!");
                                    }
                                }

                                case "shift" -> {
                                    if (commands.get("-p") != null) {
                                        String[] params = commands.get("-p").split(",");
                                        if (params.length == 2) {
                                            try {
                                                shiftRepository.insert(params[0], params[1]);
                                                System.out.println("Successful insertion!");
                                            } catch (Exception e) {
                                                System.out.println("Something went wrong!\n" + e.getMessage());
                                            }
                                        } else {
                                            System.out.println("""
                                                    Wrong usage of [ insert -r shift -p ... ]!
                                                    Try: [ insert -r shift -p type,timetable ]
                                                    All parameters must be entered!""");
                                        }
                                    } else {
                                        System.out.println("Wrong usage of [ insert -r shift ]!");
                                    }
                                }

                                case "waiter" -> {
                                    if (commands.get("-p") != null) {
                                        String[] params = commands.get("-p").split(",");
                                        if (params.length == 6) {
                                            try {
                                                waiterRepository.insert(params[0], params[1], params[2], params[3], Date.valueOf(params[4]), Integer.parseInt(params[5]));
                                                System.out.println("Successful insertion!");
                                            } catch (Exception e) {
                                                System.out.println("Something went wrong!\n" + e.getMessage());
                                            }
                                        } else {
                                            System.out.println("""
                                                    Wrong usage of [ insert -r waiter -p ... ]!
                                                    Try: [ insert -r waiter -p surname,name,patronymic,gender,dateOfBirth(yyyy-mm-dd),idShift ]
                                                    All parameters must be entered!""");
                                        }
                                    } else {
                                        System.out.println("Wrong usage of [ insert -r waiter ]!");
                                    }
                                }
                            }
                        } else {
                            System.out.println("Wrong usage of [ insert ]!");
                        }
                    }

                    case "delete" -> {
                        if (commands.get("-r") != null) {
                            switch (commands.get("-r")) {
                                default -> System.out.println("Wrong usage of [ delete -r ]!");

                                case "client" -> {
                                    if (commands.get("-i") != null) {
                                        try {
                                            clientRepository.delete(Integer.parseInt(commands.get("-i")));
                                            System.out.println("Successful deletion!");
                                        } catch (Exception e) {
                                            System.out.println("Something went wrong!\n" + e.getMessage());
                                        }
                                    } else {
                                        System.out.println("""
                                    Wrong usage of [ delete -r client -i ...]!
                                    Try: [ delete -r client -i id ]
                                    All parameters must be entered!""");
                                    }
                                }

                                case "dish" -> {
                                    if (commands.get("-i") != null) {
                                        try {
                                            dishRepository.delete(Integer.parseInt(commands.get("-i")));
                                            System.out.println("Successful deletion!");
                                        } catch (Exception e) {
                                            System.out.println("Something went wrong!\n" + e.getMessage());
                                        }
                                    } else {
                                        System.out.println("""
                                                Wrong usage of [ delete -r dish -i ...]!
                                                Try: [ delete -r dish -i id ]
                                                All parameters must be entered!""");
                                    }
                                }

                                case "distribution" -> {
                                    if (commands.get("-i") != null) {
                                        String[] params = commands.get("-i").split(",");
                                        if (params.length == 2) {
                                            try {
                                                distributionRepository.delete(Integer.parseInt(params[0]), Integer.parseInt(params[1]));
                                                System.out.println("Successful deletion!");
                                            } catch (Exception e) {
                                                System.out.println("Something went wrong!\n" + e.getMessage());
                                            }
                                        } else {
                                            System.out.println("""
                                                    Wrong usage of [ delete -r distribution -i ...]!
                                                    Try: [ delete -r distribution -i idOrder,idWaiter ]
                                                    All parameters must be entered!""");
                                        }
                                    }
                                }

                                case "menu" -> {
                                    if (commands.get("-i") != null) {
                                        try {
                                            menuRepository.delete(Integer.parseInt(commands.get("-i")));
                                            System.out.println("Successful deletion!");
                                        } catch (Exception e) {
                                            System.out.println("Something went wrong!\n" + e.getMessage());
                                        }
                                    } else {
                                        System.out.println("""
                                                Wrong usage of [ delete -r menu -i ...]!
                                                Try: [ delete -r menu -i id ]
                                                All parameters must be entered!""");
                                    }
                                }

                                case "order" -> {
                                    if (commands.get("-i") != null) {
                                        try {
                                            orderRepository.delete(Integer.parseInt(commands.get("-i")));
                                            System.out.println("Successful deletion!");
                                        } catch (Exception e) {
                                            System.out.println("Something went wrong!\n" + e.getMessage());
                                        }
                                    } else {
                                        System.out.println("""
                                                Wrong usage of [ delete -r order -i ...]!
                                                Try: [ delete -r order -i id ]
                                                All parameters must be entered!""");
                                    }
                                }

                                case "positions" -> {
                                    if (commands.get("-i") != null) {
                                        String[] params = commands.get("-i").split(",");
                                        if (params.length == 2) {
                                            try {
                                                positionsRepository.delete(Integer.parseInt(params[0]), Integer.parseInt(params[1]));
                                                System.out.println("Successful deletion!");
                                            } catch (Exception e) {
                                                System.out.println("Something went wrong!\n" + e.getMessage());
                                            }
                                        } else {
                                            System.out.println("""
                                                    Wrong usage of [ delete -r positions -i ...]!
                                                    Try: [ delete -r positions -i idMenu,idDish ]
                                                    All parameters must be entered!""");
                                        }
                                    }
                                }

                                case "shift" -> {
                                    if (commands.get("-i") != null) {
                                        try {
                                            shiftRepository.delete(Integer.parseInt(commands.get("-i")));
                                            System.out.println("Successful deletion!");
                                        } catch (Exception e) {
                                            System.out.println("Something went wrong!\n" + e.getMessage());
                                        }
                                    } else {
                                        System.out.println("""
                                                Wrong usage of [ delete -r shift -i ...]!
                                                Try: [ delete -r shift -i id ]
                                                All parameters must be entered!""");
                                    }
                                }

                                case "waiter" -> {
                                    if (commands.get("-i") != null) {
                                        try {
                                            waiterRepository.delete(Integer.parseInt(commands.get("-i")));
                                            System.out.println("Successful deletion!");
                                        } catch (Exception e) {
                                            System.out.println("Something went wrong!\n" + e.getMessage());
                                        }
                                    } else {
                                        System.out.println("""
                                                Wrong usage of [ delete -r waiter -i ...]!
                                                Try: [ delete -r waiter -i id ]
                                                All parameters must be entered!""");
                                    }
                                }
                            }
                        } else {
                            System.out.println("Wrong usage of [ delete ]!");
                        }
                    }
                }
            } else {
                System.out.println("Global syntax error!");
            }
            commands.clear();
        }
    }
}
