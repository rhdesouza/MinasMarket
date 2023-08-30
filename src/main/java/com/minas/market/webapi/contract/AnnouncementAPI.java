package com.minas.market.webapi.contract;

import com.minas.market.webapi.dto.Announcement;
import com.minas.market.webapi.dto.AnnouncementMessage;
import com.minas.market.webapi.dto.request.AnnouncementRequest;
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

@RequestMapping("/api/v1/announcement")
@Tag(name = "Announcement")
public interface AnnouncementAPI {

    @Operation(summary = "Create announcement", description = "Create announcement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Announcement created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UUID.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content)
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UUID> create(
            @RequestBody @Valid AnnouncementRequest announcementRequest
    );

    @Operation(summary = "Update announcement", description = "Update announcement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Announcement updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UUID.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content)
    })
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UUID> update(
            @PathVariable("id") UUID id,
            @RequestBody @Valid AnnouncementRequest announcementRequest
    );

    @Operation(summary = "Get announcement", description = "Get announcement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Announcement localized",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Announcement.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
            @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content)
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<AnnouncementMessage> getOne(@PathVariable("id") UUID announcementId);

    @Operation(summary = "Get all announcements by user", description = "Get all announcements by user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Announcement localized",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = Announcement.class), minItems = 2)
                    )),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Announcement>> getAllByUserId(@RequestParam("userId") UUID userId);

    @Operation(summary = "Delete announcement", description = "Delete announcement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Announcement deleted"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content)
    })
    @DeleteMapping("/{id}")
    void delete(@PathVariable("id") UUID announcementId);

}
