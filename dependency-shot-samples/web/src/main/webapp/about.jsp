<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>About VDM</title>
    </head>
    <body>
        <div id="about">
            <table align="center" border="0px">
                <tr>
                    <td width="400px" height="200px">
                        <p align="justify">
                            <c:out value="${about}"/>
                        </p>
                    </td>
                </tr>
                <tr>
                    <td align="center">
                        <a href="randomvdm"><c:out value="${vdm}"/></a>
                    </td>
                </tr>
            </table>
        </div>
    </body>
</html>
