package core.backend.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewControllerTest {
    @Autowired ReviewController reviewController;
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }
}