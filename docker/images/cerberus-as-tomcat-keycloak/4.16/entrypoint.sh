#!/bin/bash
# Cerberus Copyright (C) 2016 Cerberus Testing
# DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
#
# This file is part of Cerberus.
#
# Cerberus is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# Cerberus is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with Cerberus. If not, see <http://www.gnu.org/licenses/>.

# Cerberus Tomcat configuration (with Keycloack acticated)

export CATALINA_OPTS="$CATALINA_OPTS -DDATABASE_HOST=$DATABASE_HOST -DDATABASE_PORT=$DATABASE_PORT -DDATABASE_NAME=$DATABASE_NAME -DDATABASE_USER=$DATABASE_USER -DDATABASE_PASSWORD=$DATABASE_PASSWORD -Dorg.cerberus.authentification=keycloak -Dorg.cerberus.keycloak.realm=${KEYCLOAK_REALM} -Dorg.cerberus.keycloak.client=${KEYCLOAK_CLIENT} -Dorg.cerberus.keycloak.url=${KEYCLOAK_URL}"

/usr/local/tomcat/bin/catalina.sh start

tail -F /usr/local/tomcat/logs/catalina.out
