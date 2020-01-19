package kr.co.blog.entitiy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString(exclude = {"board","user"})
@Table(name="COMMENT_TBL")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @Column(name="COMMENT")
    private String comment;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="BOARD_ID")
    private Board board;


    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="USER_ID")
    private User user;


    @Column(name="LIKES")
    private Long like;

    @Column(name="CREATE_DATE")
    @CreationTimestamp
    private LocalDateTime createDate;

    @Column(name="DELETED")
    private Boolean deleted;

    @Column(name="PARENT_ID")
    private Long parentId;


    public void AddBoard(Board board){
        this.board =board;
        board.getComments().add(this);
    }
    public void AddUser(User user){
        this.user = user;
        user.getComments().add(this);
    }
}
