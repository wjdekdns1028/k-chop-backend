package core.backend.domain;

import java.util.*;

public class Food {
//    @Id
    private Integer id;

    private String name;

    private String description;

    private Integer scoville;

    private String sort;

    private String imgUrl;

    private List<Review> reviews;
}
