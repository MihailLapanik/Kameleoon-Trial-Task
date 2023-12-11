package com.lapanik.kameleoontrialtask.controller;

import com.lapanik.kameleoontrialtask.model.dto.QuoteDto;
import com.lapanik.kameleoontrialtask.model.entity.Quote;
import com.lapanik.kameleoontrialtask.model.enums.VoteStatus;
import com.lapanik.kameleoontrialtask.service.QuoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.lapanik.kameleoontrialtask.exception.NotFoundException.MESSAGE;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.PATH;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

@RestController
@RequestMapping(value = "/quote")
@Tag(name = "Quote Management", description = "Endpoints for managing Quote entities")
@RequiredArgsConstructor
public class QuoteController {
    private final QuoteService quoteService;

    @Operation(summary = "Find all Quotes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
    }
    )
    @GetMapping
    public List<Quote> getAllQuotes() {
        return quoteService.getAllQuotes();
    }

    @Operation(summary = "Find Quote by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = MESSAGE)
    }
    )
    @GetMapping("/{id}")
    public Quote getQuoteById(@PathVariable Long id) {
        return quoteService.getQuoteById(id);
    }

    @Operation(summary = "Find random Quote")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = MESSAGE)
    }
    )
    @GetMapping("/random")
    public Quote getRandomQuote() {
        return quoteService.getRandomQuote();
    }

    @Operation(summary = "Create Quote")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = MESSAGE)
    }
    )
    @PostMapping("/create")
    public Quote createQuote(@RequestBody QuoteDto quoteDto) {
        return quoteService.createQuote(quoteDto);
    }

    @Operation(summary = "Update Quote")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = MESSAGE)
    }
    )
    @PutMapping("/{id}")
    public Quote updateQuote(
            @Parameter(required = true, in = PATH)
            @PathVariable Long id,
            @Parameter(required = true, in = QUERY)
            @RequestBody QuoteDto quoteDto) {
        return quoteService.updateQuote(id, quoteDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuote(@PathVariable Long id) {
        quoteService.deleteQuote(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public List<Quote> getListQuote(@RequestParam("status") VoteStatus voteStatus) {
        return quoteService.getListQuote(voteStatus);
    }

    @GetMapping("/graph")
    public Map<Date, Integer> getGraph(
            @RequestParam(value = "periodInHours", defaultValue = "24") int periodInHours,
            @RequestParam("quoteId") Long id) {
        return quoteService.getGraph(periodInHours, id);
    }

}
