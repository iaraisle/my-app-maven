package ar.com.ada.second.online.maven.model.dao;

import lombok.*;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

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

    @Column(name= "name", length = 50, nullable = false, unique = true)
    private  String name;
}
