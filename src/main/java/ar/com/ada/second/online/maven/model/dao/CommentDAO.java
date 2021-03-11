package ar.com.ada.second.online.maven.model.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Comment")
public class CommentDAO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "Post_id", nullable = false, foreignKey = @ForeignKey(name = "fk_Comment_Post"))
    private PostDAO post;

    @ManyToOne
    @JoinColumn(name = "User_id", nullable = false, foreignKey = @ForeignKey(name = "fk_Comment_User"))
    private UserDAO user;
}
