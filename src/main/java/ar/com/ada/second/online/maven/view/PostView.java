package ar.com.ada.second.online.maven.view;

import ar.com.ada.second.online.maven.model.dao.PostDAO;
import ar.com.ada.second.online.maven.model.dto.PostDTO;
import ar.com.ada.second.online.maven.model.dto.UserDTO;
import ar.com.ada.second.online.maven.utils.CommandLineTable;
import ar.com.ada.second.online.maven.utils.Keyboard;
import ar.com.ada.second.online.maven.utils.Paginator;

import java.util.List;

public class PostView {
    private static PostView postView;

    private PostView() {
    }

    public static PostView getInstance() {
        if (postView == null) postView = new PostView();
        return postView;
    }

    public void showTitlePostModule() {
        System.out.println("\n####################################");
        System.out.println("#   Ada Social Network: Post   #");
        System.out.println("####################################\n");
    }

    public Integer postMenuSelectOption() {
        System.out.println("Que desea realizar: ");
        System.out.println("| 1 | Crear un post");
        System.out.println("| 2 | Lista de post");
        System.out.println("| 3 | Editar post");
        System.out.println("| 4 | Eliminar post");
        System.out.println("| 5 | Regresar el menu principal");
        return Keyboard.getInputInteger();
    }

    public String getNicknameToFind() {
        System.out.println("Ingrese el seudonimo del usuario autor del post [ presione Q|q para cancelar ]: ");

        return Keyboard.getInputAlphanumeric();
    }

    public void userNotFound(String nickname) {
        System.out.printf("\nOops! Usuario no encontrado con el seudonimo %s\n\n", nickname);
    }

    public void cancelledProcess() {
        System.out.println("Proceso cancelado");
        Keyboard.pressEnterKeyToContinue();
    }

    public String getDataPost() {
        System.out.println("Ingrese en contenido del post [ presione Q|q para cancelar ]: ");
        return Keyboard.getInputAlphanumeric();
    }

    public void showNewPost(PostDTO dto) {
        System.out.println("\nPost creado con exito:\n");
        System.out.printf("Post id: %d", dto.getId());
        System.out.printf("\nEmail: %s", dto.getUser().getEmail());
        System.out.printf("\nNickname: %s\n", dto.getUser().getNickName());
        System.out.printf("\nPost: %s\n\n", dto.getBody());
    }

    public void showTitleListPost() {
        System.out.println("\n########################################");
        System.out.println("#   Ada Social Network: Lista de Posts   #");
        System.out.println("#########################################\n");
    }

    public String printPostsPerPage(List<PostDAO> posts, List<String> paginator, Boolean isHeaderShown) {
        if (isHeaderShown) {
            System.out.println("\n###########################################");
            System.out.println("#   Ada Social Network: Lista de Post   #");
            System.out.println("###########################################\n");
        }

        CommandLineTable st = new CommandLineTable();
        st.setShowVerticalLines(true);

        st.setHeaders("ID", "Contenido", "autor");
        posts.forEach(postDAO -> {
            st.addRow(
                    postDAO.getId().toString(),
                    postDAO.getBody(),
                    postDAO.getUser().getNickName()
            );
        });
        st.print();

        System.out.println("\n+-----------------------------------------------------------------------+");
        paginator.forEach(page -> System.out.print(page + " "));
        System.out.println("\n+-----------------------------------------------------------------------+");

        return Keyboard.getInputAlphanumeric();
    }

    public void postsListNotFound() {
        System.out.println("No hay post registrados en la base de datos");
        Keyboard.pressEnterKeyToContinue();
    }

    public void showTitleNewPost() {
        System.out.println("\n######################################");
        System.out.println("#   Ada Social Network: Nuevo Post   #");
        System.out.println("######################################\n");
    }

    public void showTitleEditOrDeletePost(String action) {
        String title = (action.equals(Paginator.EDIT))
                ? "Editar"
                : "Eliminar";

        System.out.println("\n######################################");
        System.out.println("# Ada Social Network: " + title + " Posts #");
        System.out.println("######################################\n");
    }

    public void selectPostIdToEditOrDelete(String action) {
        System.out.println("Seleccione el id para " + action + " de la siguiente lista de posteos: ");
        Keyboard.pressEnterKeyToContinue();
    }

    public void postNotExist(Integer id, UserDTO authorUser) {
        String info = (authorUser != null)
                ? "No existe un post con el id " + id + " para el usuario " + authorUser.getNickName()
                : "No existe un post con el id " + id + " asociado";

        System.out.println(info);
    }

    public Integer postIdSelection(String action) {
        System.out.println("Ingrese el numero de ID del post para " + action + " ó 0 para cancelar: \n");
        return Keyboard.getInputInteger();
    }

    public void editOrDeletehPostCanceled(String action) {
        action = Paginator.EDIT.equals(action) ? "edicion" : "eliminacion";
        System.out.println("Ha cancelado la " + action + " del post\n");
        Keyboard.pressEnterKeyToContinue();
    }

    public String getDataEditPost(PostDAO postToEdit) {
        System.out.printf("Ingrese el nuevo contenido del post (%s): \n", postToEdit.getBody());
        return Keyboard.getInputAlphanumeric();
    }

    public void showPost(PostDTO dto) {
        System.out.println("\nDatos del Post:");
        System.out.printf("id: %d", dto.getId());
        System.out.printf("\nContenido: %s", dto.getBody());
        System.out.printf("\nNickname: %s\n\n", dto.getUser().getNickName());

        Keyboard.pressEnterKeyToContinue();
    }

    public Boolean areYouSureToRemoveIt(PostDAO postToDelete) {
        System.out.println("\n¿Seguro que desea eleminar el siguiente registro?");
        System.out.printf("ID: %d", postToDelete.getId());
        System.out.printf("\nContenido: %s", postToDelete.getBody());
        System.out.printf("\nAutor: %s\n", postToDelete.getUser().getNickName());

        System.out.println("| 1 | Si\n| 2 | No");

        return Keyboard.getInputInteger() == 1;
    }

    public void postHasBeenSuccessfullyRemoved() {
        System.out.println("Se ha eliminado el registro exitosamente!");
    }

    public void errorWhenDeletingRecord() {
        System.out.println("Oops!! hubo un error al eliminar el registro, intentelo de nuevo.");
    }
}
