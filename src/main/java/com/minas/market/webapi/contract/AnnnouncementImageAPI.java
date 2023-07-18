package com.minas.market.webapi.contract;

import com.minas.market.webapi.dto.AnnouncementImage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/announcement-image")
@Tag(name = "Announcement image")
public interface AnnnouncementImageAPI {

    @Operation(summary = "Create announcement images", description = "Create announcement images")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Image Announcement created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UUID.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content)
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, headers = "content-type=multipart/form-data")
    ResponseEntity<UUID> create(
            @NotNull @RequestHeader(name = "announcement-id") UUID announcementId,
            @NotNull @RequestParam("file") MultipartFile file
    ) throws Exception;

    @Operation(summary = "Get announcement image", description = "Get announcement image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image Announcement localized",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AnnouncementImage.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
            @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content)
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<AnnouncementImage> getOne(@PathVariable("id") UUID id);

    @Operation(summary = "Get all images by announcements", description = "Get all images by announcements")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Announcement localized",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = AnnouncementImage.class), minItems = 2)
                    )),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<AnnouncementImage>> getAllByAnnouncementId(@RequestParam("announcementId") UUID announcementId);

    @Operation(summary = "Delete image announcement ", description = "Delete image announcement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image announcement deleted"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content)
    })
    @DeleteMapping("/{id}")
    void delete(@PathVariable("id") UUID id);

}
