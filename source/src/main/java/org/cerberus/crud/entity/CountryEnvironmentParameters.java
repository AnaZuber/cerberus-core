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
package org.cerberus.crud.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author bcivel
 */
@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CountryEnvironmentParameters {

    @Id
    private String system;
    @Id
    private String country;
    @Id
    private String environment;
    @Id
    private String application;
    private String ip;
    private String domain;
    private String url;
    private String urlLogin;
    private String var1;
    private String var2;
    private String var3;
    private String var4;
    private String mobileActivity;
    private String mobilePackage;
    private int poolSize;

    public CountryEnvironmentParameters(String system, String country, String environment, String application) {
        this.system=system;
        this.country=country;
        this.environment=environment;
        this.application = application;
    }
    /**
     * Invariant ACTION String.
     */
    public static final int DEFAULT_POOLSIZE = 10;

    public boolean hasSameKey(CountryEnvironmentParameters obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CountryEnvironmentParameters other = obj;
        if ((this.system == null) ? (other.system != null) : !this.system.equals(other.system)) {
            return false;
        }
        if ((this.country == null) ? (other.country != null) : !this.country.equals(other.country)) {
            return false;
        }
        if ((this.environment == null) ? (other.environment != null) : !this.environment.equals(other.environment)) {
            return false;
        }
        if ((this.application == null) ? (other.application != null) : !this.application.equals(other.application)) {
            return false;
        }
        return true;
    }

}
