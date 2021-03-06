package kr.co.blog.entitiy;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"user","comments"})
@Table(name="BOARD_TBL")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @Column(name="NAME")
    private String name;

    @Column(name="CONTENT")
    private String content;


    @CreationTimestamp
    @Column(name="CREATE_TIME")
    private LocalDateTime create_time;

    @UpdateTimestamp
    @Column(name="UPDATE_TIME")
    private LocalDateTime update_time;


    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();

    @Column(name="DELETED")
    private boolean deleted;

}
