package ar.com.ada.second.online.maven.view;

import ar.com.ada.second.online.maven.controller.UserController;

public class UserView {

    private static UserView userView;
    private UserController userController = UserController.getInstance();


    private UserView() {
    }

    public static UserView getInstance() {
        if (userView == null) userView = new UserView();
        return userView;
    }
}
