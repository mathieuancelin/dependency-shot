<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Random VDM</title>
    </head>
    <body>
        <div id="vdm">
            <table align="center" border="0px">
                <tr>
                    <td align="center">
                        <h2>Random VDM</h2>
                    </td>
                </tr>
                <tr>
                    <td width="400px" height="200px">
                        <p align="justify"
                            <b>author</b> : 
                            <c:out value="${vdmauthor}"/>
                            <br/><br/>
                            <c:out value="${vdmtext}" default="Click on the button below"/>
                        </p>
                    </td>
                </tr>
                <tr>
                    <td align="center">
                        <form METHOD="POST" ACTION="randomvdm">
                            <input type="submit" value="Get new random VDM">
                        </form>
                    </td>
                </tr>
            </table>
        </div>
    </body>
</html>
