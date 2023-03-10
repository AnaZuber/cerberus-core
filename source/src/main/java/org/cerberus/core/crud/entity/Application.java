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
package org.cerberus.core.crud.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @author vertigo
 */
@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Application {

    @Id
    private String application;
    private int sort;
    private String type;
    private String system;
    private String subsystem;
    private String svnurl;
    private String bugTrackerUrl;
    private String bugTrackerNewUrl;
    private int poolSize;
    private String deploytype;
    private String mavengroupid;
    private String description;
    private String UsrCreated;
    private Timestamp DateCreated;
    private String UsrModif;
    private Timestamp DateModif;

    public Application(String application) {
        this.application = application;
    }

    /**
     * Invariant PROPERTY TYPE String.
     */
    public static final String TYPE_GUI = "GUI";
    public static final String TYPE_BAT = "BAT";
    public static final String TYPE_SRV = "SRV";
    public static final String TYPE_APK = "APK";
    public static final String TYPE_IPA = "IPA";
    public static final String TYPE_FAT = "FAT";
    public static final String TYPE_NONE = "NONE";

    public boolean hasSameKey(Application obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final Application other = obj;
        if ((this.application == null) ? (other.application != null) : !this.application.equals(other.application)) {
            return false;
        }
        return true;
    }

}
