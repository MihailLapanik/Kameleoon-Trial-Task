package com.lapanik.kameleoontrialtask.repository;

import com.lapanik.kameleoontrialtask.model.entity.Vote;
import com.lapanik.kameleoontrialtask.model.enums.VoteStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Integer countVoteByCreateDateBetweenAndQuoteIdAndVoteStatus(Date startDate, Date endDate, Long id, VoteStatus voteStatus);
}
