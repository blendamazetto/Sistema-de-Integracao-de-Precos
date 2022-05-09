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
                        action="${pageContext.servletContext.contextPath}/PesquisaProduto"
                        method="POST"
                >
                    <div class="form-group">
                        <label for="inputDescricao">Descrição do notebook</label>
                        <input name="descricao" type="text" class="form-control" id="inputDescricao" placeholder="Insira a descrição do notebook">
                    </div>
                    <div class="form-row">
                        <div class="col">
                            <label for="inputData">Data mínima de coleta</label>
                            <input name="data" type="date" class="form-control" id="inputData">

                        </div>
                        <div class="col">
                            <label for="inputClassificacao">Classificação</label>
                            <input name="classificacao" type="number" min="0.0" max="5.0" step="0.5" class="form-control" id="inputClassificacao" placeholder="Insira a classificação minima do produto">

                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col">
                            <label for="inputMinimumPrice">Preço Mínimo</label>
                            <input name="precoMinimo" type="number" min="0.00" max="10000.00" step="0.01" class="form-control" id="inputMinimumPrice" placeholder="Insira o preço mínimo do jogo em reais.">
                        </div>
                        <div class="col">
                            <label for="inputMaximumPrice">Preço Máximo</label>
                            <input name="precoMaximo" type="number" min="0.00" max="10000.00" step="0.01" class="form-control" id="inputMaximumPrice" placeholder="Insira o preço máximo do jogo em reais.">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col">
                            <label for="inputLoja">Loja</label>
                            <select name="loja" id="inputLoja" class="form-control">
                                <option value="">Todas</option>
                                <option value="Amazon">Amazon</option>
                                <option value="Kabum">Kabum</option>
                                <option value="MagazineLuiza">Magazine Luiza</option>
                            </select>
                        </div>
                        <div class="col">
                            <label for="inputDisponibilidade">Disponibilidade</label>
                            <select name="disponibilidade" id="inputDisponibilidade" class="form-control">
                                <option value="2">Todas</option>
                                <option value="1">Disponível</option>
                                <option value="0">Indisponível</option>
                            </select>
                        </div>
                    </div>
                    <br>
                    <div class="form-row">
                        <div class="col">
                            <h5>Ordenação </h5>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col">
                            <label for="inputSentido">Crescente/decrescente</label>
                            <select name="sentido" id="inputSentido" class="form-control">
                                <option value="ASC">Crescente</option>
                                <option value="DESC">Decrescente</option>
                            </select>
                        </div>
                        <div class="col">
                            <label for="inputOrdenação">Ordenar por</label>
                            <select name="ordenarPor" id="inputOrdenação" class="form-control">
                                <option value="loja_vende_notebook.data_crawling">Data de coleta</option>
                                <option value="loja_vende_notebook.preco">Preço</option>
                                <option value="loja_vende_notebook.classificacao">Classificação</option>
                            </select>
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
        <table class="table" align="center" border="0" cellpadding="0">
            <thead class="#87CEFA" align="center">
                <tr>
                    <th scope="col" align="left">CLASSIFICAÇÃO</th>
                    <th scope="col" align="center">DESCRIÇÃO</th>
                    <th scope="col" align="center">VALOR</th>
                    <th scope="col" align="center">LOJA</th>
                    <th scope="col" align="center">DATA</th>
                </tr>
            </thead>
            <tbody align="center">
                <c:forEach var="produto" items="${requestScope.lista_produtos}">
                    <tr>
                        <td align="center">
                            <span><c:out value="${produto.getClassificacaoAsString()}"/></span>
                        </td>                        
                        <td align="left">
                            <span><c:out value="${produto.descricao}"/></span>
                        </td>
                        <td align="center">
                            <span><c:out value="${produto.getPrecoAsString()}"/></span>
                        </td>
                        <td align="center">
                            <span><c:out value="${produto.nomeLoja}"/></span>
                        </td>                     
                        <td align="center">
                            <span><c:out value="${produto.dataCrawling}"/></span>
                        </td>
                        <td align="center">                          
                            <form action="/graficos" method="post">
                                <input type="hidden" name="classificacao" value="${produto.getClassificacaoAsString()}">
                                <input type="hidden" name="descricao" value="${produto.descricao}">
                                <input type="hidden" name="valor" value="${produto.getPrecoAsString()}">
                                <input type="hidden" name="loja" value="${produto.nomeLoja}">
                                <input type="hidden" name="data" value="${produto.dataCrawling}">
                                <input type="hidden" name="url" value="${produto.urlProduto}">
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
