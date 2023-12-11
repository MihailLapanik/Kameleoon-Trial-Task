package com.lapanik.kameleoontrialtask.service;

import com.lapanik.kameleoontrialtask.model.dto.VoteDto;
import com.lapanik.kameleoontrialtask.model.entity.Quote;
import com.lapanik.kameleoontrialtask.model.entity.User;
import com.lapanik.kameleoontrialtask.model.entity.Vote;
import com.lapanik.kameleoontrialtask.model.enums.VoteStatus;
import com.lapanik.kameleoontrialtask.repository.QuoteRepository;
import com.lapanik.kameleoontrialtask.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final QuoteRepository quoteRepository;
    private final UserService userService;
    private final QuoteService quoteService;

    public void votingOnQuotes(VoteDto voteDto, VoteStatus voteStatus) {
        User user = userService.findUserByUsername(voteDto.getUsername());
        Quote quote = quoteService.getQuoteById(voteDto.getQuoteId());
        Optional<Vote> vote = getVoteByUsername(voteDto, quote.getVotes());

        if (vote.isPresent()) {
            changeStatus(voteStatus, vote.get());
        } else {
            createVote(voteStatus, quote, user);
        }
    }

    private void createVote(VoteStatus voteStatus, Quote quote, User user) {
        Vote newVote = new Vote(quote, user, voteStatus);
        quote.getVotes().add(newVote);
        voteRepository.save(newVote);
        quoteRepository.save(quote);
    }

    private void changeStatus(VoteStatus voteStatus, Vote vote) {
        vote.setVoteStatus(voteStatus);
        voteRepository.save(vote);
    }

    private static Optional<Vote> getVoteByUsername(VoteDto voteDto, Set<Vote> votes) {
        return votes.stream()
                .filter(v -> v.getUser().getUsername().equals(voteDto.getUsername()))
                .findFirst();
    }
}
