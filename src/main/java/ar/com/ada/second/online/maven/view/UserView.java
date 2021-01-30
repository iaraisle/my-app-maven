package ar.com.ada.second.online.maven.view;

import ar.com.ada.second.online.maven.controller.UserController;
import ar.com.ada.second.online.maven.model.dto.UserDTO;
import ar.com.ada.second.online.maven.utils.Keyboard;

import java.security.Key;
import java.util.HashMap;

public class UserView {

    private static UserView userView;
    private UserController userController = UserController.getInstance();


    private UserView() {
    }

    public static UserView getInstance() {
        if (userView == null) userView = new UserView();
        return userView;
    }

    //Ada Social Network: Usuarios => showTitleUserModule()

    //userMenuSelectOption(): opcion1 crear usuario, opción 5 regresar al menu principal.

    //HashMap <String(key), String(valor)> getDataNewUser() captura info del usuario y lo guarda en una key
    public void showTitleUserModule() {
        System.out.println("#####################################");
        System.out.println("#   Ada Social Network: Usuarios   #");
        System.out.println("#####################################\n");
    }


    public Integer userMenuSelectOption() {
        System.out.println("Qué desea realizar: ");
        System.out.println("| 1 | Crear usuario");
        System.out.println("| 5 | Regresar al menú principal");
        return Keyboard.getInputInteger();
    }

    public HashMap<String, String> getDataNewUser() {
        System.out.println("#####################################");
        System.out.println("#   Ada Social Network: Nuevo usuario   #");
        System.out.println("#####################################\n");

        HashMap<String, String> data = new HashMap<>();

        System.out.println("Ingrese un nombre de usuario: ");
        String nickname = Keyboard.getInputString();
        data.put("nickname", nickname);
        //Mismo proceso en 1 paso:
        // data.put("nickname", Keyboard.getInputString());


        System.out.println("Ingrese un email: ");
        String email = Keyboard.getInputString();
        data.put("email", email);
        //data.put("email", Keyboard.getInputString());

        return null;
    }

    public void existingUser() {
        System.out.println("Oops!! El usuario ya existe en la base de datos.");
        Keyboard.pressEnterKeyToContinue();
    }

    public void showNewUser(UserDTO dto) {

        System.out.println("\nUsuario creado con éxito:\n");
        System.out.printf("id: %d\n", dto.getId());
        System.out.printf("email: %s\n", dto.getEmail());
        System.out.printf("Nickname: %s\n", dto.getNickName());

        Keyboard.pressEnterKeyToContinue();
    }
}
