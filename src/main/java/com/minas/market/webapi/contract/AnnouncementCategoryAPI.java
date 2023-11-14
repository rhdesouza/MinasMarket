package com.minas.market.webapi.contract;

import com.minas.market.infrastructure.persistence.entity.security.ConstRoles;
import com.minas.market.webapi.dto.Announcement;
import com.minas.market.webapi.dto.AnnouncementCategory;
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
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/announcement-category")
@Tag(name = "Announcement Category")
public interface AnnouncementCategoryAPI {

    @Secured({ConstRoles.ROLE_ADMIN_ADMIN})
    @Operation(summary = "Create announcement category", description = "Create announcement category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category announcement created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UUID.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content)
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UUID> create(@RequestBody @Valid String category);

    @Secured({ConstRoles.ROLE_ADMIN_ADMIN})
    @Operation(summary = "Get category announcement", description = "Get category announcement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category announcement localized",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Announcement.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
            @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content)
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<AnnouncementCategory> getOne(@PathVariable("id") UUID categoryId);

    @Secured({ConstRoles.ROLE_ADMIN_ADMIN})
    @Operation(summary = "Get all categories", description = "Get all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Announcement localized",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = AnnouncementCategory.class), minItems = 2)
                    )),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<AnnouncementCategory>> getAllCategories();

    @Secured({ConstRoles.ROLE_ADMIN_ADMIN})
    @Operation(summary = "Delete category announcement", description = "Delete category announcement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Announcement deleted"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content)
    })
    @DeleteMapping("/{id}")
    void delete(@PathVariable("id") UUID categoryId);
}
