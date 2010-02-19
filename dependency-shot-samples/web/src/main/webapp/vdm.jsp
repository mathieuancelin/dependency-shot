<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.*" %>
<%@ page import="cx.ath.mancel01.dependencyshot.api.DSInjector" %>
<%@ page import="cx.ath.mancel01.dependencyshot.api.annotations.InjectLogger" %>
<%@ page import="cx.ath.mancel01.dependencyshot.samples.vdm.model.Vdm" %>
<%@ page import="cx.ath.mancel01.dependencyshot.samples.vdm.service.RandomService" %>
<%@ page import="cx.ath.mancel01.dependencyshot.samples.vdm.web.DependencyShotListener" %>
<%@ page import="java.util.logging.Logger" %>
<%@ page import="javax.inject.Inject" %>
<%!
    /**
     * The random VDM service.
     */
    @Inject
    private RandomService service;
    /**
     * The logger of the controller;
     */
    @Inject
    @InjectLogger
    private Logger logger;
    /**
     * Initialization of the injected object graph on this servlet.
     */
    public void jspInit() {
        try {
            super.init();
            DSInjector injector = (DSInjector) getServletContext()
                    .getAttribute(DependencyShotListener.INJECTOR_NAME);
            injector.injectInstance(this);
        } catch (ServletException se) {
            logger.severe("Error : [" + se.getMessage() + "]");
        }
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Random VDM</title>
    </head>
    <body>
        <div id="vdm">
            <table align="center" border="1px">
                <tr>
                    <td align="center">
                        <h2>Random VDM</h2>
                    </td>
                </tr>
                <tr>
                    <td width="400px" height="200px">
                        <p align="justify"
                            <%
                                Vdm vdm = service.getRandomVdm();
                                logger.info(vdm.toString());
                                out.println("<b>author</b> : " + vdm.getAuthor() + "<br/><br/>");
                                out.println(vdm.getText());
                            %>
                        </p>
                    </td>
                </tr>
                <tr>
                    <td align="center">
                        <form METHOD="GET" ACTION="vdm.jsp">
                            <input type="submit" value="Get new random VDM">
                        </form>
                    </td>
                </tr>
            </table>
        </div>
    </body>
</html>
