package ar.com.ada.second.online.maven.controller;

import ar.com.ada.second.online.maven.model.dto.UserDTO;
import ar.com.ada.second.online.maven.view.MainView;
import ar.com.ada.second.online.maven.view.UserView;

import java.util.HashMap;

public class UserController {
    private static UserController userController;
    private UserView userView = UserView.getInstance();
    private MainView mainView = MainView.getInstance();


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

        /* 2da versión
        String nickname = dataNewUser.get("nickname");
        String email = dataNewUser.get("email");

        UserDTO userDTO = new UserDTO();
        userDTO.setNickname(nickname);
        userDTO.setEmail(email);

        3ra versión
        */

    }

    // init: showTitleUserModule => while =>
    //  userMenuSelectionOption
    //      1 createNewUser,
    //      5 showTitleReturnMenu

    // createNewUser: getDataNewUser => new UserDTO

}
