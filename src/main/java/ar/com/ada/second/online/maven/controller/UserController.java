package ar.com.ada.second.online.maven.controller;

import ar.com.ada.second.online.maven.model.dao.JpaUserDAO;
import ar.com.ada.second.online.maven.model.dao.UserDAO;
import ar.com.ada.second.online.maven.model.dto.UserDTO;
import ar.com.ada.second.online.maven.utils.Keyboard;
import ar.com.ada.second.online.maven.utils.Paginator;
import ar.com.ada.second.online.maven.view.MainView;
import ar.com.ada.second.online.maven.view.UserView;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class UserController {

    private static UserController userController;
    private UserView userView = UserView.getInstance();
    private MainView mainView = MainView.getInstance();
    private JpaUserDAO jpaUserDAO = JpaUserDAO.getInstance();



    private UserController() {
    }

    public static UserController getInstance() {
        if (userController == null) userController = new UserController();
        return userController;
    }

    public void init() {
        boolean shouldItStay = true;
        userView.showTitleUserModule();

        while (shouldItStay) {
            Integer option = userView.userMenuSelectOption();
            switch (option) {
                case 1:
                    createNewUser();
                    break;
                case 2:
                    showAllUsers();
                    break;
                case 3:
                    editUser();
                    break;
                case 5:
                    shouldItStay = false;
                    mainView.showTitleReturnMenu();
                    break;
                default:
                    mainView.invalidOption();
            }
        }
    }


    private void createNewUser() {
        HashMap<String, String> dataNewUser = userView.getDataNewUser();
        //para crear la variable: Escribo userView.getDataNewUser()
        // pongo el cursor al principio, pongo Ctrl + Alt + V y elijo la variable
        // de qué parte del método quiero poner

        String nickname = dataNewUser.get("nickname"); //para extraer la info del hashmap
        String email = dataNewUser.get("email");
        UserDTO userDTO = new UserDTO(nickname, email);

        /*2da versión
        String nickname = dataNewUser.get("nickname");
        String email = dataNewUser.get("email");

        UserDTO userDTO = new UserDTO();
        userDTO.setNickName(nickname);
        userDTO.setEmail(email);

        3ra versión
        UserDTO userDTO = new UserDTO();
        userDTO.setNickName(dataNewUser.get("nickname"));
        userDTO.setEmail(dataNewUser.get("email"));
        */

        //Validación de registro en la base de datos
        try {
            jpaUserDAO.findByEmailOrNickname(userDTO.getEmail(), userDTO.getNickName());
        } catch (Exception e) {
            System.out.println(e.getMessage()); //para que aparezca por pantalla
            userView.existingUser();
            return;
        }


        UserDAO userDAO = UserDAO.toDAO(userDTO);

        jpaUserDAO.save(userDAO);

        userDTO.setId(userDAO.getId());

        userView.showNewUser(userDTO);
    }

    // init: showTitleUserModule => while =>
    //  userMenuSelectionOption
    //      1 createNewUser,
    //      5 showTitleReturnMenu

    // createNewUser: getDataNewUser => new UserDTO


    private void showAllUsers() {

        printRecordsPerPage(null, true);
    }


    private void editUser() {
       UserDAO userToEdit = getUsertoEditOrDelete(Paginator.EDIT);
       if (userToEdit != null) {
           HashMap<String, String> dataEditUser = userView.getDataEditUser(userToEdit);

           if (!dataEditUser.get("nickname").isEmpty())
               userToEdit.setNickName(dataEditUser.get("nickname"));

           if (!dataEditUser.get("email").isEmpty())
               userToEdit.setEmail(dataEditUser.get("email"));

           jpaUserDAO.save(userToEdit);

           UserDTO userDTO = UserDAO.toDTO(userToEdit);

           userView.showUser(userDTO);
       }

    }

    private UserDAO getUsertoEditOrDelete(String optionEditOrDelete) {
        boolean shouldGetOut = false;
        Optional<UserDAO> userToEditOptional = Optional.empty();
        String actionInfo = Paginator.EDIT.equals(optionEditOrDelete) ? "Editar" : "Eliminar";
        userView.selectUserIdToEditorDeleteInfo(actionInfo);

        Integer userIdToEdit = printRecordsPerPage(optionEditOrDelete, false);

        if (userIdToEdit !=0){
            while (!shouldGetOut) {
                userToEditOptional = jpaUserDAO.findById(userIdToEdit);

                if (!userToEditOptional.isPresent()) {
                    userView.userNotExist(userIdToEdit);
                    userIdToEdit = userView.userIdSelection(optionEditOrDelete);
                    shouldGetOut = (userIdToEdit == 0);

                } else shouldGetOut = true;
            }
        }

        return userToEditOptional.isPresent() ? userToEditOptional.get() : null;

    }


    private Integer printRecordsPerPage(String optionSelectEditOrDelete, boolean isHeaderShown) {
        int limit = 4,
                currentPage = 0,
                totalUsers,
                totalPages,
                userIdSelected = 0;

        List<UserDAO> users; // el contenido que quiero que tenga el paginado viene de esta lista
        List<String> paginator; //cómo mostrar los resultados al usuario

        boolean shouldGetOut = false;

        while (!shouldGetOut){
            totalUsers = jpaUserDAO.getTotalRecords();
            totalPages = (int) Math.ceil((double)totalUsers / limit); //ceil redondea para arriba y así me aseguro de siempre tener un entero así que puedo castear (int)
            paginator = Paginator.buildPaginator(currentPage, totalPages);
            users = jpaUserDAO.findAll(currentPage*limit, limit);

            if (!users.isEmpty()) {
                String choice = userView.printUserPerPage(
                        users, paginator, optionSelectEditOrDelete, isHeaderShown
                );

                switch (choice) {
                    case "i":
                    case "I":
                        currentPage = 0;
                        break;
                    case "a":
                    case "A":
                        if (currentPage > 0) currentPage--;
                        break;
                    case "s":
                    case "S":
                        if (currentPage + 1 < totalPages) currentPage++;
                        break;
                    case "u":
                    case "U":
                        currentPage = totalPages - 1;
                        break;
                    case "e":
                    case "E":
                        if (optionSelectEditOrDelete != null) {
                            userIdSelected = userView.userIdSelection(optionSelectEditOrDelete);
                            shouldGetOut = true;
                        }
                        break;
                    case "q":
                    case "Q":
                        shouldGetOut = true;
                        break;
                    default:
                        if (choice.matches("^-?\\d+$")){
                            int page = Integer.parseInt(choice);
                            if (page > 0 && page <= totalPages) currentPage = page - 1;
                        } else Keyboard.invalidData();


                }
            } else {
                shouldGetOut = true;
                userView.usersListNotFound();
            }
        }

        return userIdSelected;
    }

}
