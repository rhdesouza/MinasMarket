package com.minas.market.webapi.contract;

import com.minas.market.webapi.dto.Announcement;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/api/v1/public")
@Tag(name = "Public")
public interface PublicAPI {

    @Operation(summary = "Get all announcements", description = "Get all announcements")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Announcement localized",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = Announcement.class), minItems = 2)
                    )),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    @GetMapping(value = "/announcements", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Announcement>> getAnnouncements(
            @RequestParam(value = "title", required = false) String title
    );


}
