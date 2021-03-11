package ar.com.ada.second.online.maven.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PostDTO {
    private Integer id;
    private String body;
    private UserDTO user;

    public PostDTO(String body, UserDTO user) {
        this.body = body;
        this.user = user;
    }
}
