package com.minas.market.webapi.contract;

import com.minas.market.webapi.dto.Announcement;
import com.minas.market.webapi.dto.Message;
import com.minas.market.webapi.dto.request.MessageRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v1/message")
@Tag(name = "Messages")
public interface MessageAPI {
    @Operation(summary = "Create Message", description = "Create message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Message created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UUID.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content)
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UUID> create(
            @RequestBody @Valid MessageRequest messageRequest
    );

    @Operation(summary = "Update message", description = "Update message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UUID.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content)
    })
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UUID> update(
            @PathVariable("id") UUID id,
            @RequestBody @Valid MessageRequest messageRequest
    );

    @Operation(summary = "Get message", description = "Get message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message localized",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Announcement.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
            @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content)
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Message> getOne(@PathVariable("id") UUID messageId);

    @Operation(summary = "Delete message", description = "Delete message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message deleted"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content)
    })
    @DeleteMapping("/{id}")
    void delete(@PathVariable("id") UUID messageId);
}
