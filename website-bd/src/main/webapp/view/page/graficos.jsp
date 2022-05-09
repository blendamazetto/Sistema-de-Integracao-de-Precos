<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="model.Produto" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.google.gson.Gson"%>
<%@ page import="com.google.gson.JsonObject"%>
 
<%
Gson gsonObj = new Gson();
Map<Object,Object> map = null;
List<Map<Object,Object>> listP = new ArrayList<Map<Object,Object>>();
List<Map<Object,Object>> listC = new ArrayList<Map<Object,Object>>();

List<Produto> l_prod = (List<Produto>)request.getAttribute("listaGrafico");

for(Produto p : l_prod){
    map = new HashMap<Object,Object>(); map.put("label", p.getDataCrawling()); map.put("y", p.getPreco()); listP.add(map);
    map = new HashMap<Object,Object>(); map.put("label", p.getDataCrawling()); map.put("y", p.getClassificacao()); listC.add(map);
}
 
String dataPointsP = gsonObj.toJson(listP);
String dataPointsC = gsonObj.toJson(listC);

System.out.println(dataPointsP);
%>

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
                    <th scope="col" align="center">DESCRIÇÃO</th>
                    <th scope="col" align="center">VALOR</th>
                    <th scope="col" align="left">CLASSIFICAÇÃO</th>
                    <th scope="col" align="center">LOJA</th>
                    <th scope="col" align="center">DATA</th>
                    <th scope="col" align="center">URL</th>
                </tr>
            </thead>
            <tbody align="center">
                    <tr>                      
                        <td align="left">
                            <%=request.getAttribute("descricao")%>
                        </td>
                        <td align="center">
                            <%=request.getAttribute("valor")%>
                        </td>
                        <td align="center">
                            <%=request.getAttribute("classificacao")%>
                        </td>  
                        <td align="center">
                            <%=request.getAttribute("loja")%>
                        </td>                     
                        <td align="center">
                            <%=request.getAttribute("data")%>
                        </td>
                        <td align="center">
                            <a href="<%=request.getAttribute("url")%>">Link</a>
                        </td>
                    </tr>
            </tbody>
        </table>
        <script type="text/javascript">
            window.onload = function() { 

                var chartP = new CanvasJS.Chart("chartContainer1", {
                        theme: "light2",
                        title: {
                                text: "Evolução dos Preços"
                        },
                        axisX: {
                                title: "Data Crawling"
                        },
                        axisY: {
                                title: "Preço",
                                includeZero: true
                        },
                        data: [{
                                type: "line",
                                yValueFormatString: "R$####.##",
                                dataPoints : <%out.print(dataPointsP);%>
                        }]
                });

                var chartC = new CanvasJS.Chart("chartContainer2", {
                        theme: "light2",
                        title: {
                                text: "Evolução da Classificação"
                        },
                        axisX: {
                                title: "Data Crawling"
                        },
                        axisY: {
                                title: "Classificação",
                                maximum: 5.5,
                                includeZero: true
                        },
                        data: [{
                                type: "line",
                                yValueFormatString: "#,#",
                                dataPoints : <%out.print(dataPointsC);%>
                        }]
                });
                chartP.render();
                chartC.render();
            }
        </script>
    </div>
    <br>
    <div id="chartContainer1" style="height: 370px; width: 50%; margin:0 auto;"></div>
    <br>
    <br>
    <div id="chartContainer2" style="height: 370px; width: 50%; margin:0 auto;"></div>
    <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
    <br>
    <%@include file="/view/include/scripts.jsp"%>
</body>
</html>