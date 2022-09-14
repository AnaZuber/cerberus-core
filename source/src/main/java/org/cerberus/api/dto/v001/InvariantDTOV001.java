/**
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
package org.cerberus.api.dto.v001;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.cerberus.api.dto.views.View;

import javax.validation.constraints.NotBlank;

/**
 * @author mlombard
 */
@Data
@Builder
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(name = "Invariant")
public class InvariantDTOV001 {

    @JsonView(value = {View.Public.GET.class})
    private String idName;

    @NotBlank(message = "Value is mandatory")
    @JsonView(value = {View.Public.GET.class, View.Public.POST.class})
    private String value;

    @JsonView(value = {View.Public.GET.class})
    private Integer sort;

    @JsonView(value = {View.Public.GET.class})
    private String description;

    @JsonView(value = {View.Public.GET.class})
    private String shortDescription;

    @JsonView(value = {View.Public.GET.class})
    private String attribute1;

    @JsonView(value = {View.Public.GET.class})
    private String attribute2;

    @JsonView(value = {View.Public.GET.class})
    private String attribute3;

    @JsonView(value = {View.Public.GET.class})
    private String attribute4;

    @JsonView(value = {View.Public.GET.class})
    private String attribute5;

    @JsonView(value = {View.Public.GET.class})
    private String attribute6;

    @JsonView(value = {View.Public.GET.class})
    private String attribute7;

    @JsonView(value = {View.Public.GET.class})
    private String attribute8;

    @JsonView(value = {View.Public.GET.class})
    private String attribute9;
}
 