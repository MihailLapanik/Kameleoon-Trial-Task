package com.lapanik.kameleoontrialtask.repository;

import com.lapanik.kameleoontrialtask.model.entity.Quote;
import com.lapanik.kameleoontrialtask.model.enums.VoteStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
    @Query("SELECT q.id from Quote as q")
    List<Long> findAllIds();

    @Query("SELECT q FROM Quote q" +
            " LEFT JOIN q.votes v " +
            "GROUP BY q.id " +
            "ORDER BY SUM(CASE WHEN v.voteStatus = :voteStatus THEN 1 ELSE -1 END) DESC")
    List<Quote> getListQuote(VoteStatus voteStatus);


}
