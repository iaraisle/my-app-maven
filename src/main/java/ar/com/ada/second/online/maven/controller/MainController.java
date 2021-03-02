package ar.com.ada.second.online.maven.controller;

import ar.com.ada.second.online.maven.view.MainView;

public class MainController {

    private static MainController mainController;
    private MainView mainView = MainView.getInstance();
    private UserController userController = UserController.getInstance();


    private MainController() {
    }

    public static MainController getInstance() {
        if (mainController == null) mainController = new MainController();
        return mainController;
    }

    public void init() {
        boolean shouldItStay = true;
        mainView.showTitleApp();

        while (shouldItStay) {
            Integer option = mainView.mainMenuSelectOption();
            switch (option) {
                case 1:
                    // llamaria al controllador de usuario
                    userController.init();
                    break;
                case 5:
                    shouldItStay = false;
                    break;
                default:
                    mainView.invalidOption();
            }
        }

    }
}
