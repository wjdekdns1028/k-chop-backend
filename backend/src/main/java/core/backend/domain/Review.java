package core.backend.domain;

import java.time.LocalDateTime;

public class Review {
    private Integer id;

    private Food food;

    private Member member;

    private String content;

    private Integer rating;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    private Integer upvote;

    private Integer downvote;
}
