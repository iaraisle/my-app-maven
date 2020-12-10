package ar.com.ada.second.online.maven.controller;

import ar.com.ada.second.online.maven.view.MainView;

public class UserController {
    private static UserController userController;
    private MainView mainView = MainView.getInstance();


    private UserController() {
    }

    public static UserController getInstance() {
        if (userController == null) userController = new UserController();
        return userController;
    }
}
