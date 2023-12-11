package com.lapanik.kameleoontrialtask.controller;

import com.lapanik.kameleoontrialtask.model.dto.VoteDto;
import com.lapanik.kameleoontrialtask.service.VoteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.lapanik.kameleoontrialtask.model.enums.VoteStatus.DISLIKE;
import static com.lapanik.kameleoontrialtask.model.enums.VoteStatus.LIKE;

@RestController
@RequestMapping(value = "/vote")
@Tag(name = "Vote Management", description = "Endpoints for managing Vote entities")
@RequiredArgsConstructor
public class VoteController {
    private final VoteService voteService;

    @PostMapping("/like")
    public ResponseEntity<Void> likeQuote(@RequestBody VoteDto voteDto) {
        voteService.votingOnQuotes(voteDto, LIKE);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/dislike")
    public ResponseEntity<Void> dislikeQuote(@RequestBody VoteDto voteDto) {
        voteService.votingOnQuotes(voteDto, DISLIKE);
        return ResponseEntity.ok().build();
    }
}
