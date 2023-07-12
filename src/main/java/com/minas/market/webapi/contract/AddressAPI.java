package com.minas.market.webapi.contract;

import com.minas.market.webapi.dto.Address;
import com.minas.market.webapi.dto.request.AddressRequest;
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

@RequestMapping("/api/v1/address")
@Tag(name = "Address")
public interface AddressAPI {

    @Operation(summary = "Create address", description = "Create address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Address created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UUID.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content)
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UUID> create(
            @RequestParam("userId") UUID userId,
            @RequestBody @Valid AddressRequest addressCreate
    );

    @Operation(summary = "Update address", description = "Update address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UUID.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content)
    })
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UUID> update(
            @RequestParam("userId") UUID userId,
            @PathVariable("id") UUID id,
            @RequestBody @Valid AddressRequest addressCreate
    );

    @Operation(summary = "Get address", description = "Get address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address localized",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Address.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
            @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content)
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Address> getOne(@PathVariable("id") UUID id);

    @Operation(summary = "Get all address by user", description = "Get all address by user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address localized",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = Address.class), minItems = 2)
                    )),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Address>> getAll(@RequestParam("userId") UUID userId);

    @Operation(summary = "Delete address", description = "Delete address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address deleted"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content)
    })
    @DeleteMapping("/{id}")
    void delete(@PathVariable("id") UUID id);
}
