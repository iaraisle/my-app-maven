package ar.com.ada.second.online.maven.model.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter//se estila a que las anotaciones queden pegadas a la clase donde se van a aplicar
public class UserDAO {
    private Integer id;
    private String nickName;
    private  String name;
}
