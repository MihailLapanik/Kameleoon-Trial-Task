package com.lapanik.kameleoontrialtask;

import com.lapanik.kameleoontrialtask.repository.QuoteRepository;
import com.lapanik.kameleoontrialtask.repository.UserRepository;
import com.lapanik.kameleoontrialtask.repository.VoteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KameleoonTrialTaskApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private VoteRepository voteRepository;

    @AfterEach
    public void clearDB() {
        voteRepository.deleteAll();
        quoteRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void contextLoads() {
    }

}
