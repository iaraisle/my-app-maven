package ar.com.ada.second.online.maven.view;

import ar.com.ada.second.online.maven.controller.UserController;
import ar.com.ada.second.online.maven.model.dao.UserDAO;
import ar.com.ada.second.online.maven.model.dto.UserDTO;
import ar.com.ada.second.online.maven.utils.CommandLineTable;
import ar.com.ada.second.online.maven.utils.Keyboard;
import ar.com.ada.second.online.maven.utils.Paginator;

import java.security.Key;
import java.util.HashMap;
import java.util.List;

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
        System.out.println("| 2 | Lista de usuarios");
        System.out.println("| 3 | Editar usuario");
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

        return data;
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

    public String printUserPerPage(List<UserDAO> users, List<String> paginator, String optionSelectEditOrDelete, boolean isHeaderShown) {
        if (isHeaderShown) {
            System.out.println("#########################################");
            System.out.println("#  Ada Social Network: Lista de Usuarios  #");
            System.out.println("#########################################\n");

        }

        CommandLineTable st = new CommandLineTable();
        st.setShowVerticalLines(true);//if false (default) then no vertical lines are shown

        st.setHeaders("ID", "Nickname", "Email");
        users.forEach(userDAO -> {
            st.addRow(
                    userDAO.getId().toString(), //commandLineTable siempre va a mostrar string
                    userDAO.getNickName(),
                    userDAO.getEmail()
            );
        });

        st.print();

        if (optionSelectEditOrDelete != null && !optionSelectEditOrDelete.isEmpty())
            paginator.set(paginator.size() - 2, optionSelectEditOrDelete);

        System.out.println("\n+----------------------------------------+");
        paginator.forEach(page -> System.out.print(page + " "));
        System.out.println("\n+----------------------------------------+");

        return Keyboard.getInputString();
    }

    public void usersListNotFound() {
        System.out.println("No hay usuarios registrados en la base de datos");
        Keyboard.pressEnterKeyToContinue();
    }

    public void selectUserIdToEditorDeleteInfo(String action) {
        System.out.println("Seleccione el id para " + action + "de la siguiente lista de usuarios: ");
        Keyboard.pressEnterKeyToContinue();
    }

    public void userNotExist(Integer id) {
        System.out.println("No existe el usuario con el id" + id + " asociado");
        System.out.println("Seleccione un id valido o 0 para cancelar");
    }

    public Integer userIdSelection(String action) {
        switch (action){
            case Paginator.EDIT:
                action = "Editar";
                break;
        }
        System.out.println("Ingrese el numero de id del cliente para " + action + "o 0 para cancelar\n ");

        return Keyboard.getInputInteger();
    }

    public HashMap<String, String> getDataEditUser(UserDAO dao) {
        System.out.println("#####################################");
        System.out.println("#   Ada Social Network: Editar usuario   #");
        System.out.println("#####################################\n");

        HashMap<String, String> data = new HashMap<>();

        System.out.printf("Ingrese el nuevo Nickname (%s): ", dao.getNickName());
        data.put("nickname", Keyboard.getInputString());


        System.out.printf("Ingrese el email (%s): ", dao.getEmail());
        data.put("email", Keyboard.getInputString());

        return data;
    }

    public void showUser(UserDTO dto) {
        System.out.println("\nDatos del usuario:\n");
        System.out.printf("id: %d\n", dto.getId());
        System.out.printf("email: %s\n", dto.getEmail());
        System.out.printf("Nickname: %s\n", dto.getNickName());

        Keyboard.pressEnterKeyToContinue();

    }
}
