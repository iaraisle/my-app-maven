package ar.com.ada.second.online.maven.model.dao;

import ar.com.ada.second.online.maven.model.dto.UserDTO;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter//se estila a que las anotaciones queden pegadas a la clase donde se van a aplicar
@ToString
@Entity
@Table(name = "user")
public class UserDAO {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name= "nickname", length = 30, nullable = false, unique = true)
    private String nickName;

    @Column(name= "email", length = 50, nullable = false, unique = true)
    private  String email;

    public UserDAO(String nickName, String email) {
        this.nickName = nickName;
        this.email = email;
    }

    /*De manera manual sería:

    List<recordSet> list = rs.query ("Select * from user");
    for (list){
    int id = list.get(i).id();
    String nickname = list.get(i).nickname();
    String email = list.get(i).email();

    UserDAO userDAO = new UserDAO (id, nickname, email);
    }

    hibernate al @Entity(clase de mapeo con la info de la base de datos) me hace esto automáticamente
     */



    public static UserDAO toDAO (UserDTO dto) {
        UserDAO userDAO = new UserDAO(dto.getNickName(), dto.getEmail());
        if (dto.getId() != null)
            userDAO.setId(dto.getId());
        return userDAO;
    }

    public static UserDTO toDTO(UserDAO dao) {
        UserDTO dto = new UserDTO(dao.getNickName(), dao.getEmail());
        if (dao.getId() != null)
            dto.setId(dao.getId());
        return dto;
    }
}
