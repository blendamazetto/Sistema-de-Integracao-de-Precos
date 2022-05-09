<%-- 
    Document   : graficos
    Created on : 8 de mai. de 2022, 00:26:18
    Author     : blend
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="/view/include/navbar.jsp" %>
    <br>
    <div class="container">
        <table class="table" align="center" border="0" cellpadding="0">
            <thead class="#87CEFA" align="center">
                <tr>
                    <th scope="col" align="left">CLASSIFICAÇÃO</th>
                    <th scope="col" align="center">DESCRIÇÃO</th>
                    <th scope="col" align="center">VALOR</th>
                    <th scope="col" align="center">LOJA</th>
                    <th scope="col" align="center">DATA</th>
                    <th scope="col" align="center">URL</th>
                </tr>
            </thead>
            <tbody align="center">
                    <tr>
                        <td align="center">
                            <%=request.getParameter("classificacao")%>
                        </td>                        
                        <td align="left">
                            <%=request.getParameter("descricao")%>
                        </td>
                        <td align="center">
                            <%=request.getParameter("valor")%>
                        </td>
                        <td align="center">
                            <%=request.getParameter("loja")%>
                        </td>                     
                        <td align="center">
                            <%=request.getParameter("data")%>
                        </td>
                        <td align="center">
                            <%=request.getParameter("url")%>
                        </td>
                    </tr>
            </tbody>
        </table>
    </div>
    <%@include file="/view/include/scripts.jsp"%>
    </body>
</html>
