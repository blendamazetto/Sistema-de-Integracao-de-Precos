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
        <div class="card mt-5 mb-5">
            <div class="card-body">
                <h3 class="card-title">Pesquisa </h3>
                <br>
                <form
                        class="form"
                        action="${pageContext.servletContext.contextPath}/PesquisaNotebook"
                        method="POST"
                >
                    <div class="form-group">
                        <label for="inputDescricao">Descrição do notebook</label>
                        <input name="descricao" type="text" class="form-control" id="inputDescricao" placeholder="Insira a descrição do notebook">
                    </div>
                    <div class="form-row">
                        <div class="col">
                            <label for="inputMarca">Nome da marca</label>
                            <input name="marca" type="text" class="form-control" id="inputMarca" placeholder="Insira o nome da marca">

                        </div>
                        <div class="col">
                            <label for="inputModelo">Código do modelo</label>
                            <input name="modelo" type="text" class="form-control" id="inputModelo" placeholder="Insira o código do Modelo">

                        </div>
                    </div>
                    <br>
                    <button type="submit" class="btn btn-primary">Pesquisar</button>
                </form>
            </div>
        </div>
    </div>

    <br>
    <div class="container">
        <table class="table" align="center" border="0" cellpadding="20">
            <thead class="#87CEFA" align="center">
                <tr>
                    <th scope="col" align="center">ID</th>
                    <th scope="col" align="center">MODELO</th>
                    <th scope="col" align="left">DESCRIÇÃO</th>
                    <th scope="col" align="center">MARCA</th>
                    <th scope="col" align="center">GRÁFICOS</th>
                </tr>
            </thead>
            <tbody align="center">
                <c:forEach var="notebook" items="${requestScope.lista_notebook}">
                    <tr>
                        <td align="center">
                            <span><c:out value="${notebook.id_notebook}"/></span>
                        </td>
                        <td align="center">
                            <span><c:out value="${notebook.modelo}"/></span>
                        </td>
                        <td align="left">
                            <span><c:out value="${notebook.descricao}"/></span>
                        </td>
                        <td align="center">
                            <span><c:out value="${notebook.marca}"/></span>
                        </td>
                        <td align="center">                          
                            <form action="/graficosNotebooks" method="post">
                                <input type="hidden" name="id_notebook" value="${notebook.id_notebook}">
                                <input type="hidden" name="modelo" value="${notebook.modelo}">
                                <input type="hidden" name="descricao" value="${notebook.descricao}">
                                <input type="hidden" name="marca" value="${notebook.marca}">
                                <input type="submit" value="Submit">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <%@include file="/view/include/scripts.jsp"%>
</body>
</html>
