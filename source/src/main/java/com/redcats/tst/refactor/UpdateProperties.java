/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redcats.tst.refactor;


import com.redcats.tst.log.MyLogger;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Level;

/**
 *
 * @author bcivel
 */
public class UpdateProperties extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // response.setContentType("text/html;charset=UTF-8");
        this.processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // response.setContentType("text/html;charset=UTF-8");
        this.processRequest(request, response);
    }

    /*
     * public boolean formIsFill(String s) {
     *
     * if (s.isEmpty() || (s.trim().compareTo("") == 0) || (s.compareTo(" ") ==
     * 0) || (s.compareTo("Mandatory, KEY") == 0)) { return false; } return
     * true; }
     */

    /*
     * Return true if all fields contains in the testcase_info are not null at
     * the specified index
     */
    public boolean formIsFullFill(List<String[]> testcase_info, int index) {

        if (testcase_info.isEmpty()) {
            return false;
        }

        for (String[] t : testcase_info) {
            if (t[index].isEmpty() || t[index].trim().equals("")
                    || t[index].equals(" ") || (t[index] == null)) {
                return false;
            }
        }

        return true;
    }

    public String getStringParameter(String parameter,
            HttpServletRequest request) {

        if (request.getParameter(parameter) != null) {
            return request.getParameter(parameter);
        }
        return "";
    }

    /*
     * Return the String table if the request contain the parameter (in case
     * table is not filled)
     */
    public String[] getStringTable(String parameter, HttpServletRequest request) {

        if (request.getParameterValues(parameter) != null) {
//            System.out.println("Properties Values for : "  + request.getParameter(parameter));
            return request.getParameterValues(parameter);

        }
        return new String[0];
    }

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        /*
         * Database connexion
         */
        DbMysqlController db = new DbMysqlController();
        Connection conn = db.connect();

        try {



            Statement stmt = conn.createStatement();
            Statement stmt2 = conn.createStatement();

            /*
             * Get Test, TestCase and Country to update
             */
            String test_testcase_no_format = this.getStringParameter(
                    "testcase_hidden", request);
            this.getStringParameter("Test", request);
            this.getStringParameter("TestCase", request);
            String country_list_no_format = this.getStringParameter(
                    "CountryList", request);

            /*
             * Properties Insert/Update
             */

            String[] testcase_properties_delete = request.getParameterValues("properties_delete");
            // this.getStringTable("old_property_hidden", request);
            // String[] testcase_properties_countr_list = this.getStringTable(
            // "old_property_hidden", request);
            String[] testcase_properties_countr_list = request.getParameterValues("old_property_hidden");
            if (testcase_properties_countr_list == null) {
                testcase_properties_countr_list = new String[0];
            }
            String[] testcase_properties_propertyrow = request.getParameterValues("property_hidden");
            String[] testcase_properties_property = this.getStringTable(
                    "properties_property", request);
            String[] testcase_properties_country = this.getStringTable(
                    "properties_country", request);
            String[] testcase_properties_type = this.getStringTable(
                    "properties_type", request);
            String[] testcase_properties_value = this.getStringTable(
                    "properties_value", request);
            String[] testcase_properties_length = this.getStringTable(
                    "properties_length", request);
            String[] testcase_properties_rowlimit = this.getStringTable(
                    "properties_rowlimit", request);
            String[] testcase_properties_nature = this.getStringTable(
                    "properties_nature", request);

            /*
             * 0 : Test 1 : TestCase
             */
            String[] test_testcase_format = new String[]{"", ""};
            test_testcase_format = test_testcase_no_format.split(" - ");
            String[] country_list = country_list_no_format.split(" - ");

            /*
             * Properties
             */
            List<String[]> testcase_properties_propertycountry = new ArrayList<String[]>();
            testcase_properties_propertycountry.add(testcase_properties_country);

            List<String[]> testcase_properties_info = new ArrayList<String[]>();
            testcase_properties_info.add(testcase_properties_propertyrow);
            testcase_properties_info.add(testcase_properties_property);
            testcase_properties_info.add(testcase_properties_type);
            testcase_properties_info.add(testcase_properties_value);
            testcase_properties_info.add(testcase_properties_length);
            testcase_properties_info.add(testcase_properties_rowlimit);
            testcase_properties_info.add(testcase_properties_nature);

            List<String[]> countr_list = new ArrayList<String[]>();
            countr_list.add(testcase_properties_countr_list);


            // To Delete the country unselected
            Collection<String> listOne = new ArrayList<String>();
            Collection<String> listTwo = new ArrayList<String>();
            Collection<String> similar = new ArrayList<String>();
            Collection<String> different = new ArrayList<String>();

            listOne.addAll(Arrays.asList(testcase_properties_countr_list));
            listTwo.addAll(Arrays.asList(testcase_properties_country));

            similar.addAll(listOne);
            similar.retainAll(listTwo);
            different.addAll(listOne);
            different.removeAll(similar);
            List<String> differentlist = new ArrayList<String>(different);
            //

            ResultSet rs_Properties = stmt2.executeQuery("SELECT * "
                    + " FROM TestCaseCountryProperties " + " WHERE Test = '"
                    + test_testcase_format[0] + "'" + " AND TestCase = '"
                    + test_testcase_format[1] + "'");

            // the country property already exists????
            if (rs_Properties.first()) {

                for (int i = 0; i < testcase_properties_property.length; i++) {
                    for (int j = 0; j < differentlist.size(); j++) {
                        String[] tc_country = new String[]{"", ""};
                        tc_country = differentlist.get(j).split(" - ");

                        if (testcase_properties_propertyrow[i].equals(tc_country[0])) {
                            // if the number of the line is the same for the
                            // country and the property:
                            stmt.execute("DELETE FROM TestCaseCountryProperties "
                                    + " WHERE Test = '"
                                    + test_testcase_format[0]
                                    + "' "
                                    + " AND TestCase = '"
                                    + test_testcase_format[1]
                                    + "' "
                                    + " AND Country = '"
                                    + tc_country[1]
                                    + "' "
                                    + " AND Property = '"
                                    + testcase_properties_property[i] + "'");
                            // Is the country exist in the database??
                        }// end of the if loop
                    } // end of the loop for differnet list
                } // end of the property loop >>>>>The country unselected have
                // been removed

                //Delete the property which have been renamed    
                for (int i = 0; i < testcase_properties_property.length; i++) {
                    for (int p = 0; p < testcase_properties_countr_list.length; p++) {
                        String[] tc_prop = new String[]{"", "", ""};
                        tc_prop = testcase_properties_countr_list[p].split(" - ");
                        if (testcase_properties_propertyrow[i].equals(tc_prop[0])
                                && !testcase_properties_property[i].equals(tc_prop[2])) {
                            stmt.execute("DELETE FROM TestCaseCountryProperties "
                                    + " WHERE Test = '"
                                    + test_testcase_format[0]
                                    + "' "
                                    + " AND TestCase = '"
                                    + test_testcase_format[1]
                                    + "' "
                                    + " AND Country = '"
                                    + tc_prop[1]
                                    + "' "
                                    + " AND Property = '"
                                    + tc_prop[2] + "'");

                        }
                    }
                }
                // for each line, insert the property when not exist and update
                // when exist.
                for (int i = 0; i < testcase_properties_property.length; i++) {
                    // for each country flagged in the page
                    for (int j = 0; j < testcase_properties_country.length; j++) {
                        // separate the number of the line to the country:
                        // example: 1 - AT
                        String[] testcase_country = new String[]{"", ""};
                        testcase_country = testcase_properties_country[j].split(" - ");
                        // if the number of the line is the same for the country
                        // and the property:
                        if (testcase_properties_propertyrow[i].equals(testcase_country[0])) {
                            ResultSet rs_numberOfTestCasesCountryProperties = stmt.executeQuery("SELECT Test, TestCase, Country, Property "
                                    + " FROM TestCaseCountryProperties "
                                    + " WHERE Test = '"
                                    + test_testcase_format[0]
                                    + "'"
                                    + " AND TestCase = '"
                                    + test_testcase_format[1]
                                    + "'"
                                    + " AND Country = '"
                                    + testcase_country[1]
                                    + "'"
                                    + " AND Property = '"
                                    + testcase_properties_property[i]
                                    + "'");
                            // Is the country exist in the database??
                            // the country property already exists, make an
                            // update
                            if (rs_numberOfTestCasesCountryProperties.first()) {

                                TestCaseCountryProperties properties = new TestCaseCountryProperties();
                                properties.setTest(test_testcase_format[0]);
                                properties.setTestcase(test_testcase_format[1]);
                                properties.setCountry(testcase_country[1]);
                                properties.setProperty(testcase_properties_property[i]);

                                properties.setNature(testcase_properties_nature[i]);
                                properties.setRowlimit(Integer.parseInt(testcase_properties_rowlimit[i]));
                                properties.setLength(Integer.parseInt(testcase_properties_length[i]));
                                properties.setValue(testcase_properties_value[i]);
                                properties.setType(testcase_properties_type[i]);

                                properties.update();

                            } else // the country property does'nt extist, make an
                            // insert :
                            { /*
                                 * Insert new rows
                                 */

                                TestCaseCountryProperties properties = new TestCaseCountryProperties();
                                properties.setTest(test_testcase_format[0]);
                                properties.setTestcase(test_testcase_format[1]);
                                properties.setCountry(testcase_country[1]);
                                properties.setProperty(testcase_properties_property[i]);
                                properties.setNature(testcase_properties_nature[i]);
                                properties.setRowlimit(Integer.parseInt(testcase_properties_rowlimit[i]));
                                properties.setLength(Integer.parseInt(testcase_properties_length[i]));
                                properties.setValue(testcase_properties_value[i]);
                                properties.setType(testcase_properties_type[i]);
                                properties.insert();

                            }// end of the else loop
                            rs_numberOfTestCasesCountryProperties.close();
                        }// end of the if loop

                    }// Close the loop for country
                }// Close the loop for (property)
            } // end of the if loop (property already exists??
            else // The property is a first one
            {
                for (int i = 0; i < testcase_properties_property.length; i++) {
                    // for each country flagged in the page
                    for (int j = 0; j < testcase_properties_country.length; j++) {
                        // separate the number of the line to the country:
                        // example: 1 - AT
                        String[] testcase_country = new String[]{"", ""};
                        testcase_country = testcase_properties_country[j].split(" - ");
                        // if the number of the line is the same for the country
                        // and the property:
                        if (testcase_properties_propertyrow[i].equals(testcase_country[0])) {
                            TestCaseCountryProperties properties = new TestCaseCountryProperties();
                            properties.setTest(test_testcase_format[0]);
                            properties.setTestcase(test_testcase_format[1]);
                            properties.setCountry(testcase_country[1]);
                            properties.setProperty(testcase_properties_property[i]);
                            properties.setNature(testcase_properties_nature[i]);
                            properties.setRowlimit(Integer.parseInt(testcase_properties_rowlimit[i]));
                            properties.setLength(Integer.parseInt(testcase_properties_length[i]));
                            properties.setValue(testcase_properties_value[i]);
                            properties.setType(testcase_properties_type[i]);
                            properties.insert();
                        } // Close the condition on the row number
                    } // Close the loop for (country)
                } // Close the else condition
            }
            rs_Properties.close();

            /*
             * Delete Properties
             */

            if (testcase_properties_delete != null) { // If some properties
                // check for delete
                for (String element : testcase_properties_delete) {
                    String property_and_country[] = element.split(" - ");
                    for (int i = 1; i < property_and_country.length; i++) {
                        stmt.execute("DELETE FROM TestCaseCountryProperties "
                                + " WHERE Test = '" + test_testcase_format[0]
                                + "' " + " AND TestCase = '"
                                + test_testcase_format[1] + "' "
                                + " AND Country = '" + property_and_country[i]
                                + "' " + " AND Property = '"
                                + property_and_country[0] + "'");
                    }
                }
            }

            String sql = "UPDATE TestCase "
                    + "SET LastModifier = '" + request.getUserPrincipal().getName()
                    + "' WHERE Test = '" + test_testcase_format[0]
                    + "' AND TestCase = '" + test_testcase_format[1] + "' ";
            stmt.execute(sql);

            stmt.close();
            stmt2.close();

            /*
             * Redirect
             */
            response.sendRedirect("TestCase.jsp?Load=Load&Test="
                    + test_testcase_format[0] + "&TestCase="
                    + test_testcase_format[1]);
            return;

        } catch (SQLException ex) {
            MyLogger.log(UpdateTestCase1.class.getName(), Level.FATAL,
                    "" + ex);
            // out.println ( UpdateTestCase.class.getName ( ) + ex ) ;

        } catch (NullPointerException ex) {
            MyLogger.log(UpdateTestCase1.class.getName(), Level.FATAL,
                    "" + ex);

        } catch (ArrayIndexOutOfBoundsException ex) {
            MyLogger.log(UpdateTestCase1.class.getName(), Level.FATAL,
                    "" + ex);
        } finally {
            try {
                conn.close();
            } catch (Exception ex) {
                MyLogger.log(UpdateProperties.class.getName(), Level.INFO, "Exception closing Connection: " + ex.toString());
            }
        }
        response.sendRedirect("TestCase.jsp?Load=Load&Test="
                + request.getParameter("Test") + "&TestCase="
                + request.getParameter("TestCase"));
        out.close();
    }
}
