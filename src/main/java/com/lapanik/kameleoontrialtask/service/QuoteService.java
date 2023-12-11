package com.lapanik.kameleoontrialtask.service;

import com.lapanik.kameleoontrialtask.exception.NotFoundException;
import com.lapanik.kameleoontrialtask.model.dto.QuoteDto;
import com.lapanik.kameleoontrialtask.model.entity.Quote;
import com.lapanik.kameleoontrialtask.model.entity.User;
import com.lapanik.kameleoontrialtask.model.enums.VoteStatus;
import com.lapanik.kameleoontrialtask.repository.QuoteRepository;
import com.lapanik.kameleoontrialtask.repository.VoteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.lapanik.kameleoontrialtask.model.enums.VoteStatus.DISLIKE;
import static com.lapanik.kameleoontrialtask.model.enums.VoteStatus.LIKE;
import static org.apache.commons.lang3.time.DateUtils.addHours;

@Service
@RequiredArgsConstructor
public class QuoteService {

    private final QuoteRepository quoteRepository;
    private final VoteRepository voteRepository;
    private final UserService userService;

    public List<Quote> getAllQuotes() {
        return quoteRepository.findAll();
    }

    public Quote getQuoteById(Long id) {
        return quoteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found quote with id: " + id));
    }

    public Quote getRandomQuote() {
        List<Long> listIds = quoteRepository.findAllIds();
        Long randomId = listIds.get(new Random().nextInt(listIds.size()));
        return getQuoteById(randomId);
    }

    @Transactional
    public Quote createQuote(QuoteDto quoteDto) {
        User user = userService.findUserByUsername(quoteDto.getUsername());
        return quoteRepository.save(
                new Quote(quoteDto.getContent(), user)
        );
    }

    public Quote updateQuote(Long id, QuoteDto quoteDto) {
        Quote quote = getQuoteById(id);
        quote.setContent(quoteDto.getContent());
        return quoteRepository.save(quote);
    }

    public void deleteQuote(Long id) {
        quoteRepository.deleteById(id);
    }

    public List<Quote> getListQuote(VoteStatus voteStatus) {
        return quoteRepository.getListQuote(voteStatus);
    }

    public Map<Date, Integer> getGraph(int periodInHours, Long id) {
        if (id == null) throw new IllegalArgumentException("Quote id cannot be null");
        Date date = new Date();
        int progressVotes = 0;
        Map<Date, Integer> result = new HashMap<>();

        for (int i = periodInHours; i > 0; i--) {
            Date startGraph = addHours(date, i * -1);
            Date endGraph = addHours(date, (i - 1) * -1);

            Integer likeVotes = voteRepository
                    .countVoteByCreateDateBetweenAndQuoteIdAndVoteStatus(startGraph, endGraph, id, LIKE);
            Integer dislikeVotes = voteRepository
                    .countVoteByCreateDateBetweenAndQuoteIdAndVoteStatus(startGraph, endGraph, id, DISLIKE);

            progressVotes = calculateVotes(progressVotes, likeVotes, dislikeVotes);

            result.put(endGraph, progressVotes);

        }

        return result;
    }

    private Integer calculateVotes(int progressVotes, Integer likeVotes, Integer dislikeVotes) {
        if (likeVotes == null) likeVotes = 0;
        if (dislikeVotes == null) dislikeVotes = 0;
        return progressVotes + likeVotes - dislikeVotes;
    }
}
