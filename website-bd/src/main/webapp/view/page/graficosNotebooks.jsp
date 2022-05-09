<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@include file="/view/include/header.jsp"  %>
    <title>Notebooks</title>
</head>
<body>  
    <%@include file="/view/include/navbar.jsp" %>
    <br>
    <div class="container">
        <table class="table" align="center" border="0" cellpadding="0">
            <thead class="#87CEFA" align="center">
                <tr>
                    <th scope="col" align="center">ID NOTEBOOK</th>
                    <th scope="col" align="center">MODELO</th>
                    <th scope="col" align="left">DESCRICÃO</th>
                    <th scope="col" align="center">MARCA</th>

                </tr>
            </thead>
            <tbody align="center">
                    <tr>                      
                        <td align="center">
                            <%=request.getParameter("id_notebook")%>
                        </td>
                        <td align="center">
                            <%=request.getParameter("modelo")%>
                        </td>
                        <td align="center">
                            <%=request.getParameter("descricao")%>
                        </td>  
                        <td align="center">
                            <%=request.getParameter("marca")%>
                        </td>                     
                    </tr>
            </tbody>
        </table>
    </div>
    <br>
    <%@include file="/view/include/scripts.jsp"%>
</body>
</html>