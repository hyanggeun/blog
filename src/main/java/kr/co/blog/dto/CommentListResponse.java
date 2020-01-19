package kr.co.blog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class CommentListResponse {

    String comment;
    String userName;
    Long like;
    LocalDateTime createDate;
    boolean deleted;
    Long parentId;

    public CommentListResponse(String comment, String userName, Long like, LocalDateTime createDate, boolean deleted, Long parentId) {
        this.comment = comment;
        this.userName = userName;
        this.like = like;
        this.createDate = createDate;
        this.deleted = deleted;
        this.parentId = parentId;
    }
}
