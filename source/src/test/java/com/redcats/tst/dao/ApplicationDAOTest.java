package com.redcats.tst.dao;

import com.redcats.tst.dao.impl.ApplicationDAO;
import com.redcats.tst.database.DatabaseSpring;
import com.redcats.tst.entity.Application;
import com.redcats.tst.exception.CerberusException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Class that test the ApplicationDAO
 *
 * @author Tiago Bernardes
 * @version 1.0, 02/07/2013
 * @see com.redcats.tst.dao.impl.ApplicationDAO
 * @since 2.0.0
 */
@RunWith(MockitoJUnitRunner.class)
public class ApplicationDAOTest {

    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement statement;
    @Mock
    private ResultSet resultSet;
    @Mock
    private DatabaseSpring databaseSpring;
    @InjectMocks
    private ApplicationDAO applicationDAO;

    @Test
    public void testFindAllApplicationReturnOneApplication() throws SQLException, CerberusException {
        String application = "Cerberus";
        String description = "Testing Interface new";
        String internal = "N";
        //sort is converted to int
        String sort = "4500";
        String type = "GUI";
        String system = "DEFAULT";
        String subsystem = "DEVTOOLS";
        String svnurl = "http://";
        //deploytype is converted to ""
        String deploytype = null;
        String mavengroupid = "";

        when(databaseSpring.connect()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("application")).thenReturn(application);
        when(resultSet.getString("description")).thenReturn(description);
        when(resultSet.getString("internal")).thenReturn(internal);
        when(resultSet.getString("sort")).thenReturn(sort);
        when(resultSet.getString("type")).thenReturn(type);
        when(resultSet.getString("system")).thenReturn(system);
        when(resultSet.getString("subsystem")).thenReturn(subsystem);
        when(resultSet.getString("svnurl")).thenReturn(svnurl);
        when(resultSet.getString("deploytype")).thenReturn(deploytype);
        when(resultSet.getString("mavengroupid")).thenReturn(mavengroupid);

        List<Application> list = applicationDAO.findAllApplication();
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());

        Application app = list.get(0);
        Assert.assertEquals(application, app.getApplication());
        Assert.assertEquals(description, app.getDescription());
        Assert.assertEquals(internal, app.getInternal());
        Assert.assertEquals(4500, app.getSort());
        Assert.assertEquals(type, app.getType());
        Assert.assertEquals(system, app.getSystem());
        Assert.assertEquals(subsystem, app.getSubsystem());
        Assert.assertEquals(svnurl, app.getSvnurl());
        Assert.assertEquals("", app.getDeploytype());
        Assert.assertEquals(mavengroupid, app.getMavengroupid());
    }
}
