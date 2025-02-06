package core.backend.domain;

import java.time.LocalDateTime;
import java.util.*;

public class Member {
    private Integer id;

    private String email;

    private String name;

    private String password;

//    private String role;

    private String nationality;

    private LocalDateTime createDate;

    private String badge;

    private String photoUrl;

    private List<Review> reviews;
}
