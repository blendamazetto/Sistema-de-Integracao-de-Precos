<%-- 
    Document   : index
    Created on : 8 de fev. de 2022, 23:04:54
    Author     : blend
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User App Usuarios</title>
    </head>
    <body>
        <div class = container>
            <form class = "form_excluir_usuarios" action= "${PageContext.servletContext.contextPath}/user/delete">
                <table class = table table: striped>
                    <thead>
                        <tr>
                            <th class=col-lg-2 h4>Id</th>
                            <th class=col-lg-5 h4>Login</th>
                            <th class=col-lg-2 h4 text-center>Ação</th>
                            <th class=col-lg-1 h4 text-center>Excluir</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="usuario" items="${requestScope.userList}">
                        <tr>
                            <td>
                                <span class=h4><c:out value= "${usuario.id}"></span>
                            </td>
                            <td>
                                <a class="link_vizualizar_usuario" href="#" data-href ="${pageContext.servletContext.contextPath}/user/view">
                                    <span class="h4"><c:out value= "${usuario.login}"></span>
                                </a>
                            </td>
                            <td class="text-center">
                                <a class="btn btn-default"
                                   href="${pageContext.servletContext.contextPath}/user/editar"
                                   data-original-title="Editar">
                                    <i class="fa fa-pencil"></i>
                                </a>
                                <a class="btn btn-default link_excluir_usuario"
                                   data-href="${pageContext.servletContext.contextPath}/user/excluir"
                                   data-original-title="Excluir">
                                    <i class="fa fa-trash"></i>
                                </a>                                
                            </td>
                        </tr>
                    </tbody>
                </table>
            </form>
        </div>
    </body>
</html>
