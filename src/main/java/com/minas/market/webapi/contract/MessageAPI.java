package com.minas.market.webapi.contract;

import com.minas.market.webapi.dto.Announcement;
import com.minas.market.webapi.dto.Message;
import com.minas.market.webapi.dto.request.MessageRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/message")
@Tag(name = "Message")
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
    ResponseEntity<Message> getOne(@PathVariable("id") UUID id);

    @Operation(summary = "Delete message", description = "Delete message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message deleted"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content)
    })
    @DeleteMapping("/{id}")
    void delete(@PathVariable("id") UUID id);

    @Operation(summary = "Get all messages by user or announcement", description = "Get all messages by user or announcement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Messages localized",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = Message.class), minItems = 2)
                    )),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Message>> getAllMessagesByUserOrAnnouncements(
            @RequestParam(name = "userId", required = false) UUID userId,
            @RequestParam(name = "announcementId", required = false) UUID announcementId
    );

    @Operation(summary = "Read Message", description = "Read message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Message read",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UUID.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content)
    })
    @PostMapping("/read/{id}")
    void readMessage(@PathVariable("id") UUID id);

}
