package com.redcats.tst.servlet.publi;

import com.redcats.tst.entity.MessageGeneral;
import com.redcats.tst.entity.MessageGeneralEnum;
import com.redcats.tst.entity.TCExecution;
import com.redcats.tst.entity.TCase;
import com.redcats.tst.factory.IFactoryTCExecution;
import com.redcats.tst.factory.IFactoryTCase;
import com.redcats.tst.serviceEngine.IRunTestCaseService;
import com.redcats.tst.serviceEngine.impl.RunTestCaseService;
import com.redcats.tst.util.ParameterParserUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import version.Version;

/**
 * {Insert class description here}
 *
 * @author Tiago Bernardes
 * @version 1.0, 25/01/2013
 * @since 2.0.0
 */
@WebServlet(value = "/RunTestCase")
public class RunTestCase extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);

        //Tool
        String seleniumIP = ParameterParserUtil.parseStringParam(policy.sanitize(request.getParameter("ss_ip")), "");
        String seleniumPort = ParameterParserUtil.parseStringParam(policy.sanitize(request.getParameter("ss_p")), "");
        String browser = ParameterParserUtil.parseStringParam(policy.sanitize(request.getParameter("browser")), "firefox");

        //Test
        String test = ParameterParserUtil.parseStringParam(policy.sanitize(request.getParameter("Test")), "");
        String testCase = ParameterParserUtil.parseStringParam(policy.sanitize(request.getParameter("TestCase")), "");
        String country = ParameterParserUtil.parseStringParam(policy.sanitize(request.getParameter("Country")), "");
        String environment = ParameterParserUtil.parseStringParam(policy.sanitize(request.getParameter("Environment")), "");

        //Test Dev Environment
        boolean manualURL = ParameterParserUtil.parseBooleanParam(policy.sanitize(request.getParameter("manualURL")),false);
        String myHost = ParameterParserUtil.parseStringParam(policy.sanitize(request.getParameter("myhost")), "");
        String myContextRoot = ParameterParserUtil.parseStringParam(policy.sanitize(request.getParameter("mycontextroot")), "");
        String myLoginRelativeURL = ParameterParserUtil.parseStringParam(policy.sanitize(request.getParameter("myloginrelativeurl")), "");
        String myEnvData = ParameterParserUtil.parseStringParam(policy.sanitize(request.getParameter("myenvdata")), "");

        //Execution
        String tag = ParameterParserUtil.parseStringParam(policy.sanitize(request.getParameter("Tag")), "");
        String outputFormat = ParameterParserUtil.parseStringParam(policy.sanitize(request.getParameter("outputformat")), "compact");
        int screenshot = ParameterParserUtil.parseIntegerParam(policy.sanitize(request.getParameter("screenshot")), 1);
        int verbose = ParameterParserUtil.parseIntegerParam(policy.sanitize(request.getParameter("verbose")), 0);

        ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        IRunTestCaseService runTestCaseService = appContext.getBean(RunTestCaseService.class);
        IFactoryTCase factoryTCase = appContext.getBean(IFactoryTCase.class);
        IFactoryTCExecution factoryTCExecution = appContext.getBean(IFactoryTCExecution.class);

        TCase tCase = factoryTCase.create(test, testCase);

        TCExecution tCExecution = factoryTCExecution.create(0, test, testCase, null, null, environment, country, browser,
                0, 0, "", "", null, seleniumIP, null, seleniumPort, tag, "N", verbose, screenshot, outputFormat, null,
                Version.PROJECT_NAME_VERSION, tCase, null, null, manualURL, myHost, myContextRoot, myLoginRelativeURL, myEnvData, seleniumIP, seleniumPort, null, new MessageGeneral(MessageGeneralEnum.EXECUTION_PE_TESTSTARTED));

        tCExecution = runTestCaseService.runTestCase(tCExecution);

        long runID = tCExecution.getId();
        PrintWriter out = response.getWriter();
        if (outputFormat.equalsIgnoreCase("gui")) {
            if (runID > 0) {
                response.sendRedirect("./ExecutionDetail.jsp?id_tc=" + runID);
            } else {
                out.println("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><title>Test Execution Result</title></head>");
                out.println("<body>");
                out.println("<table>");
                out.println("<tr><td>RunID</td><td><span id='RunID'>" + runID + "</span></td></tr>");
                out.println("<tr><td>Test</td><td><span id='Test'>" + test + "</span></td></tr>");
                out.println("<tr><td>TestCase</td><td><span id='TestCase'>" + testCase + "</span></td></tr>");
                out.println("<tr><td>Country</td><td><span id='Country'>" + country + "</span></td></tr>");
                out.println("<tr><td>Environment</td><td><span id='Environment'>" + environment + "</span></td></tr>");
                out.println("<tr><td>TimestampStart</td><td><span id='TimestampStart'>" + new Timestamp(tCExecution.getStart()) + "</span></td></tr>");
                out.println("<tr><td>TimestampEnd</td><td><span id='TimestampEnd'>" + new Timestamp(tCExecution.getEnd()) + "</span></td></tr>");
                out.println("<tr><td>OutputFormat</td><td><span id='OutputFormat'>" + outputFormat + "</span></td></tr>");
                out.println("<tr><td>Verbose</td><td><span id='Verbose'>" + verbose + "</span></td></tr>");
                out.println("<tr><td>Screenshot</td><td><span id='Screenshot'>" + screenshot + "</span></td></tr>");
                out.println("<tr><td>Browser</td><td><span id='Browser'>" + browser + "</span></td></tr>");
                out.println("<tr><td>ManualURL</td><td><span id='ManualURL'>" + tCExecution.isManualURL() + "</span></td></tr>");
                out.println("<tr><td>MyHost</td><td><span id='MyHost'>" + tCExecution.getMyHost() + "</span></td></tr>");
                out.println("<tr><td>MyContextRoot</td><td><span id='MyContextRoot'>" + tCExecution.getMyContextRoot() + "</span></td></tr>");
                out.println("<tr><td>MyLoginRelativeURL</td><td><span id='MyLoginRelativeURL'>" + tCExecution.getMyLoginRelativeURL() + "</span></td></tr>");
                out.println("<tr><td>myEnvironmentData</td><td><span id='myEnvironmentData'>" + tCExecution.getEnvironmentData() + "</span></td></tr>");
                out.println("<tr><td>ReturnCode</td><td><b><span id='ReturnCodeDescription'>" + tCExecution.getResultMessage().getCode() + "</span></b></td></tr>");
                out.println("<tr><td>ReturnCodeDescription</td><td><b><span id='ReturnCodeDescription'>" + tCExecution.getResultMessage().getDescription() + "</span></b></td></tr>");
                out.println("<tr><td>ControlStatus</td><td><b><span id='ReturnCodeMessage'>" + tCExecution.getResultMessage().getCodeString() + "</span></b></td></tr>");
                out.println("<tr><td></td><td></td></tr>");
                out.println("</table><br><br>");
                out.println("<table border>");
                out.println("<tr>"
                        + "<td><input id=\"ButtonRetry\" type=\"button\" value=\"Retry\" onClick=\"window.location.reload()\"></td>"
                        + "<td><input id=\"ButtonBack\" type=\"button\" value=\"Go Back\" onClick=\"window.history.back()\"></td>"
                        + "<td><input id=\"ButtonOpenTC\" type=\"button\" value=\"Open Test Case\" onClick=\"window.open('TestCase.jsp?Test=" + test + "&TestCase=" + testCase + "&Load=Load')\"></td>"
                        + "</tr>");
                out.println("</table>");
                out.println("</body>");
                out.println("</html>");
            }
        } else if (outputFormat.equalsIgnoreCase("verbose-txt")) {
            String separator = " = ";
            out.println("RunID" + separator + runID);
            out.println("Test" + separator + test);
            out.println("TestCase" + separator + testCase);
            out.println("Country" + separator + country);
            out.println("Environment" + separator + environment);
            out.println("Time Start" + separator + new Timestamp(tCExecution.getStart()));
            out.println("Time End" + separator  + new Timestamp(tCExecution.getEnd()));
            out.println("OutputFormat" + separator + outputFormat);
            out.println("Verbose" + separator + verbose);
            out.println("Screenshot" + separator + screenshot);
            out.println("Browser" + separator + browser);
            out.println("ManualURL" + separator + tCExecution.isManualURL());
            out.println("MyHost" + separator + tCExecution.getMyHost());
            out.println("MyContextRoot" + separator + tCExecution.getMyContextRoot());
            out.println("MyLoginRelativeURL" + separator + tCExecution.getMyLoginRelativeURL());
            out.println("myEnvironmentData" + separator + tCExecution.getEnvironmentData());
            out.println("ReturnCode" + separator + tCExecution.getResultMessage().getCode());
            out.println("ReturnCodeDescription" + separator + tCExecution.getResultMessage().getDescription());
            out.println("ControlStatus" + separator + tCExecution.getResultMessage().getCodeString());
        } else if (outputFormat.equalsIgnoreCase("compact")) {
            out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tCExecution.getStart()) + " - " + runID
                    + " [" + test
                    + "|" + testCase
                    + "|" + country
                    + "|" + environment
                    + "] : '" + tCExecution.getResultMessage().getCodeString() + "' - "
                    + tCExecution.getResultMessage().getCode()
                    + " " + tCExecution.getResultMessage().getDescription());
        } else {
            out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tCExecution.getStart()) + " - " + runID
                    + " [" + test
                    + "|" + testCase
                    + "|" + country
                    + "|" + environment
                    + "] : '" + tCExecution.getResultMessage().getCodeString() + "' - "
                    + tCExecution.getResultMessage().getCode()
                    + " " + tCExecution.getResultMessage().getDescription());
        }
    }
}
