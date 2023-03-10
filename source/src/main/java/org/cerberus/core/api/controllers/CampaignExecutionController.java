/*
 * Cerberus Copyright (C) 2013 - 2017 cerberustesting
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This file is part of Cerberus.
 *
 * Cerberus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Cerberus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Cerberus.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.cerberus.core.api.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.cerberus.core.api.controllers.wrappers.ResponseWrapper;
import org.cerberus.core.api.dto.v001.CICampaignResultDTOV001;
import org.cerberus.core.api.dto.v001.CampaignExecutionDTOV001;
import org.cerberus.core.api.dto.views.View;
import org.cerberus.core.api.entity.CICampaignResult;
import org.cerberus.core.api.exceptions.EntityNotFoundException;
import org.cerberus.core.api.exceptions.FailedReadOperationException;
import org.cerberus.core.api.mappers.v001.CICampaignResultMapperV001;
import org.cerberus.core.api.mappers.v001.CampaignExecutionMapperV001;
import org.cerberus.core.api.services.CampaignExecutionService;
import org.cerberus.core.api.services.PublicApiAuthenticationService;
import org.cerberus.core.crud.service.ILogEventService;
import org.cerberus.core.service.ciresult.ICIService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @author lucashimpens
 */
@AllArgsConstructor
@Api(tags = "Campaign Execution")
@Validated
@RestController
@RequestMapping(path = "/public/campaignexecutions/")
public class CampaignExecutionController {

    private static final String API_VERSION_1 = "X-API-VERSION=1";
    private static final String API_KEY = "X-API-KEY";

    private final CampaignExecutionMapperV001 campaignExecutionMapper;
    private final CICampaignResultMapperV001 ciCampaignResultMapper;
    private final ILogEventService logEventService;
    private final CampaignExecutionService campaignExecutionService;
    private final PublicApiAuthenticationService apiAuthenticationService;
    private final ICIService ciService;

    private static final String EXECUTIONS_CAMPAIGN_CI_PATH = "/campaignexecutions/ci";
    private static final String EXECUTIONS_CAMPAIGN_CI_SVG_PATH = "/campaignexecutions/ci/svg";

    @ApiOperation(value = "Get a campaign execution by campaign execution id (tag)", response = CampaignExecutionDTOV001.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Campaign execution successfully returned.", response = CampaignExecutionDTOV001.class),
            @ApiResponse(code = 404, message = "Campaign execution was not found."),
            @ApiResponse(code = 500, message = "An error occurred when retrieving the campaign execution.")
    })
    @JsonView(View.Public.GET.class)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/{campaignExecutionId}", headers = {API_VERSION_1}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseWrapper<CampaignExecutionDTOV001> findCampaignExecutionById(
            @PathVariable("campaignExecutionId") String campaignExecutionId,
            @RequestHeader(name = API_KEY, required = false) String apiKey,
            HttpServletRequest request,
            Principal principal) {
        logEventService.createForPublicCalls("/campaignexecutions", "CALL", String.format("API /campaignexecutions called with URL: %s", request.getRequestURL()), request);
        this.apiAuthenticationService.authenticate(principal, apiKey);
        return ResponseWrapper.wrap(
                this.campaignExecutionMapper
                        .toDto(
                                this.campaignExecutionService.findByExecutionIdWithExecutions(campaignExecutionId, "")
                        )
        );
    }

    @ApiOperation(value = "Get the last execution of a campaign with its name", response = CampaignExecutionDTOV001.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Campaign execution successfully returned.", response = CampaignExecutionDTOV001.class),
            @ApiResponse(code = 404, message = "Campaign execution was not found."),
            @ApiResponse(code = 500, message = "An error occurred when retrieving the campaign execution.")
    })
    @JsonView(View.Public.GET.class)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/{campaignId}/last", headers = {API_VERSION_1}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseWrapper<CampaignExecutionDTOV001> findLastCampaignExecutionByCampaignId(
            @PathVariable("campaignId") String campaignId,
            @RequestHeader(name = API_KEY, required = false) String apiKey,
            HttpServletRequest request,
            Principal principal) {
        logEventService.createForPublicCalls("/campaignexecutions", "CALL", String.format("API /campaignexecutions called with URL: %s", request.getRequestURL()), request);
        this.apiAuthenticationService.authenticate(principal, apiKey);
        return ResponseWrapper.wrap(
                this.campaignExecutionMapper
                        .toDto(
                                this.campaignExecutionService.findByExecutionIdWithExecutions("", campaignId)
                        )
        );
    }

    @ApiOperation(value = "Get a campaign execution (CI Results) by campaign execution id (tag)", response = CICampaignResultDTOV001.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Campaign execution successfully returned.", response = CICampaignResultDTOV001.class),
            @ApiResponse(code = 404, message = "Campaign execution was not found."),
            @ApiResponse(code = 500, message = "An error occurred when retrieving the campaign execution.")
    })
    @JsonView(View.Public.GET.class)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/ci/{campaignExecutionId}", headers = {API_VERSION_1}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseWrapper<CICampaignResultDTOV001> findCiCampaignExecutionById(
            @PathVariable("campaignExecutionId") String campaignExecutionId,
            @RequestHeader(name = API_KEY, required = false) String apiKey,
            HttpServletRequest request,
            Principal principal) {
        logEventService.createForPublicCalls(EXECUTIONS_CAMPAIGN_CI_PATH, "CALL", String.format("API /campaignexecutions/ci called with URL: %s", request.getRequestURL()), request);
        this.apiAuthenticationService.authenticate(principal, apiKey);
        CICampaignResult ciCampaignResult = this.ciService.getCIResultApi(campaignExecutionId, "");
        logEventService.createForPublicCalls(EXECUTIONS_CAMPAIGN_CI_PATH, "CALLRESULT", String.format("CI Results calculated for tag '%s' result [%s]", ciCampaignResult.getCampaignExecutionId(), ciCampaignResult.getGlobalResult()), request);
        return ResponseWrapper.wrap(
                this.ciCampaignResultMapper.toDTO(
                        ciCampaignResult
                )
        );
    }

    @ApiOperation(value = "Get the last execution (CI Results) of a campaign with its name", response = CICampaignResultDTOV001.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Campaign execution successfully returned.", response = CICampaignResultDTOV001.class),
            @ApiResponse(code = 404, message = "Campaign execution was not found."),
            @ApiResponse(code = 500, message = "An error occurred when retrieving the campaign execution.")
    })
    @JsonView(View.Public.GET.class)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/ci/{campaignId}/last", headers = {API_VERSION_1}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseWrapper<CICampaignResultDTOV001> findCiCampaignExecutionByCampaignId(
            @PathVariable("campaignId") String campaignId,
            @RequestHeader(name = API_KEY, required = false) String apiKey,
            HttpServletRequest request,
            Principal principal) {
        logEventService.createForPublicCalls(EXECUTIONS_CAMPAIGN_CI_PATH, "CALL", String.format("API /campaignexecutions/ci called with URL: %s", request.getRequestURL()), request);
        this.apiAuthenticationService.authenticate(principal, apiKey);
        CICampaignResult ciCampaignResult = this.ciService.getCIResultApi("", campaignId);
        logEventService.createForPublicCalls(EXECUTIONS_CAMPAIGN_CI_PATH, "CALLRESULT", String.format("CI Results calculated for campaign '%s' result [%s]", campaignId, ciCampaignResult.getGlobalResult()), request);
        return ResponseWrapper.wrap(
                this.ciCampaignResultMapper.toDTO(
                        ciCampaignResult
                )
        );
    }

    @ApiOperation(value = "Get execution (CI Results) of a campaign in SVG output with the campaign execution id (tag)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Campaign execution successfully returned."),
            @ApiResponse(code = 404, message = "Campaign execution was not found."),
            @ApiResponse(code = 500, message = "An error occurred when retrieving the campaign execution.")
    })
    @JsonView(View.Public.GET.class)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "ci/svg/{campaignExecutionId}", produces = "image/svg+xml")
    public ResponseEntity<String> findCiSvgCampaignExecutionById(
            @PathVariable("campaignExecutionId") String campaignExecutionId,
            HttpServletRequest request) {
        logEventService.createForPublicCalls(EXECUTIONS_CAMPAIGN_CI_SVG_PATH, "CALL", String.format("API /campaignexecutions/ci/svg called with URL: %s", request.getRequestURL()), request);
        try {
            CICampaignResult ciCampaignResult = this.ciService.getCIResultApi(campaignExecutionId, "");
            logEventService.createForPublicCalls(EXECUTIONS_CAMPAIGN_CI_SVG_PATH, "CALLRESULT", String.format("CI Results calculated for campaign '%s' result [%s]", campaignExecutionId, ciCampaignResult.getGlobalResult()), request);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(this.ciService.generateSvg(ciCampaignResult.getCampaignExecutionId(), ciCampaignResult.getGlobalResult()));
        } catch (EntityNotFoundException exception) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(this.ciService.generateSvg("NOT FOUND", "ERR"));

        } catch (FailedReadOperationException exception) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(this.ciService.generateSvg("Error when retrieving the campaign execution", "ERR"));
        }
    }

    @ApiOperation(value = "Get the last execution (CI Results) of a campaign with its name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Campaign execution successfully returned."),
            @ApiResponse(code = 404, message = "Campaign execution was not found."),
            @ApiResponse(code = 500, message = "An error occurred when retrieving the campaign execution.")
    })
    @JsonView(View.Public.GET.class)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "ci/svg/{campaignId}/last", produces = "image/svg+xml")
    public ResponseEntity<String> findLastCiSvgCampaignExecutionByCampaignId(
            @PathVariable("campaignId") String campaignId,
            HttpServletRequest request) {
        logEventService.createForPublicCalls(EXECUTIONS_CAMPAIGN_CI_SVG_PATH, "CALL", String.format("API /campaignexecutions/ci/svg called with URL: %s", request.getRequestURL()), request);
        try {
            CICampaignResult ciCampaignResult = this.ciService.getCIResultApi("", campaignId);
            logEventService.createForPublicCalls(EXECUTIONS_CAMPAIGN_CI_SVG_PATH, "CALLRESULT", String.format("CI Results calculated for campaign '%s' result [%s]", campaignId, ciCampaignResult.getGlobalResult()), request);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(this.ciService.generateSvg(ciCampaignResult.getCampaignExecutionId(), ciCampaignResult.getGlobalResult()));
        } catch (EntityNotFoundException exception) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(this.ciService.generateSvg("NOT FOUND", "ERR"));
        } catch (FailedReadOperationException exception) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(this.ciService.generateSvg("Error when retrieving the campaign execution", "ERR"));
        }
    }
}
