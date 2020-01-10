package kr.co.blog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Setter
public class BoardListResponse {

//    public String getName();
//    public String getContent();
//    public LocalDateTime getCreateTime();
//    public LocalDateTime getUpdateTime();

    private Long id;
    private String name;
    private String content;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
    private String user_name;

    public BoardListResponse(Long id, String name, String content, LocalDateTime create_time, LocalDateTime update_time, String user_name){
        this.id = id;
        this.name = name;
        this.content = content;
        this.create_time = create_time;
        this.update_time = update_time;
        this.user_name = user_name;
    }
}
