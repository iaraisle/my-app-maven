package ar.com.ada.second.online.maven.view;

import ar.com.ada.second.online.maven.utils.Keyboard;

public class MainView {

    private static MainView mainView;

    private MainView() {
    }

    public static MainView getInstance() {
        if (mainView == null) mainView = new MainView();
        return mainView;
    }

    public void invalidOption() {
        System.out.println("ERROR :: debe ingresar una opcion valida\n");
    }

    public void showTitleApp() {
        System.out.println("#####################################");
        System.out.println("#   Bienvenido a la red social Ada  #");
        System.out.println("#####################################\n");
    }

    public void showTitleReturnMenu() {
        System.out.println("\n###################");
        System.out.println("#   Red social Ada  #");
        System.out.println("###################\n");
    }

    public Integer mainMenuSelectOption() {
        System.out.println("Seleccione un modulo con el que desea trabajar: ");
        System.out.println("| 1 | Usuarios");
        System.out.println("| 2 | Posteos");
        System.out.println("| 5 | Finalizar app");
        return Keyboard.getInputInteger();
    }
}
