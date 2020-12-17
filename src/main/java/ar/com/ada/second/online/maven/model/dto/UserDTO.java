package ar.com.ada.second.online.maven.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter //se estila a que las anotaciones queden pegadas a la clase donde se van a aplicar
@ToString
public class UserDTO {
    private Integer id;
    private String nickName;
    private  String name;

    public UserDTO(String nickName, String name) {
        this.nickName = nickName;
        this.name = name;
    }
}
