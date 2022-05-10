<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="model.Produto" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.google.gson.Gson"%>
<%@ page import="com.google.gson.JsonObject"%>
 
<%
Gson gsonObj = new Gson();
Map<Object,Object> map = null;
List<Map<Object,Object>> listAmazonP = new ArrayList<Map<Object,Object>>();
List<Map<Object,Object>> listAmazonC = new ArrayList<Map<Object,Object>>();

List<Produto> l_prodAmazon = (List<Produto>)request.getAttribute("listaAmazon");

for(Produto p : l_prodAmazon){
    map = new HashMap<Object,Object>(); 
    map.put("label", p.getDataCrawling()); 
    map.put("y", p.getPreco()); 
    listAmazonP.add(map);
    if (p.getClassificacao() > 0.0){
        map = new HashMap<Object,Object>(); 
        map.put("label", p.getDataCrawling()); 
        map.put("y", p.getClassificacao()); 
        listAmazonC.add(map);
    }
}
 
String dataAmazonP = gsonObj.toJson(listAmazonP);
String dataAmazonC = gsonObj.toJson(listAmazonC);

System.out.println("Amazon: " + dataAmazonP);

List<Map<Object,Object>> listKabumP = new ArrayList<Map<Object,Object>>();
List<Map<Object,Object>> listKabumC = new ArrayList<Map<Object,Object>>();

List<Produto> l_prodKabum = (List<Produto>)request.getAttribute("listaKabum");

for(Produto p : l_prodKabum){
    map = new HashMap<Object,Object>(); 
    map.put("label", p.getDataCrawling()); 
    map.put("y", p.getPreco()); 
    listKabumP.add(map);
    if (p.getClassificacao() > 0.0){
        map = new HashMap<Object,Object>(); 
        map.put("label", p.getDataCrawling()); 
        map.put("y", p.getClassificacao()); 
        listKabumC.add(map);
    }
}
 
String dataKabumP = gsonObj.toJson(listKabumP);
String dataKabumC = gsonObj.toJson(listKabumC);

System.out.println("Kabum: " + dataKabumP);

List<Map<Object,Object>> listMLP = new ArrayList<Map<Object,Object>>();
List<Map<Object,Object>> listMLC = new ArrayList<Map<Object,Object>>();

List<Produto> l_prodML = (List<Produto>)request.getAttribute("listaMagazineLuiza");

for(Produto p : l_prodML){
    map = new HashMap<Object,Object>(); 
    map.put("label", p.getDataCrawling()); 
    map.put("y", p.getPreco()); 
    listMLP.add(map);
    if (p.getClassificacao() > 0.0){
        map = new HashMap<Object,Object>(); 
        map.put("label", p.getDataCrawling()); 
        map.put("y", p.getClassificacao()); 
        listMLC.add(map);
    }
}
 
String dataMLP = gsonObj.toJson(listMLP);
String dataMLC = gsonObj.toJson(listMLC);

System.out.println("MagazineLuiza: " + dataMLP);
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
                    <th scope="col" align="center">ID</th>
                    <th scope="col" align="center">DESCRIÇÃO</th>
                    <th scope="col" align="left">MARCA</th>
                    <th scope="col" align="center">MODELO</th>
                </tr>
            </thead>
            <tbody align="center">
                    <tr>                      
                        <td align="left">
                            <%=request.getAttribute("id_notebook")%>
                        </td>
                        <td align="center">
                            <%=request.getAttribute("descricao")%>
                        </td>
                        <td align="center">
                            <%=request.getAttribute("marca")%>
                        </td>  
                        <td align="center">
                            <%=request.getAttribute("modelo")%>
                        </td>                     
                    </tr>
            </tbody>
        </table>
        <script type="text/javascript">
            window.onload = function() { 

            var chart1 = new CanvasJS.Chart("chartContainer1", {
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
                            showInLegend: true,
                            yValueFormatString: "R$####.##",
                            name: "Amazon",
                            dataPoints : <%out.print(dataAmazonP);%>
                        },
                        {
                            type: "line",
                            showInLegend: true,
                            yValueFormatString: "R$####.##",
                            name: "Kabum",
                            dataPoints : <%out.print(dataKabumP);%>
                        },
                        {
                            type: "line",
                            showInLegend: true,
                            yValueFormatString: "R$####.##",
                            name: "MagazineLuiza",
                            dataPoints : <%out.print(dataMLP);%>
                        }]
            });
            
            var chart2 = new CanvasJS.Chart("chartContainer2", {
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
                            showInLegend: true,
                            yValueFormatString: "#,#",
                            name: "Amazon",
                            dataPoints : <%out.print(dataAmazonC);%>
                        },
                        {
                            type: "line",
                            showInLegend: true,
                            yValueFormatString: "#,#",
                            name: "Kabum",
                            dataPoints : <%out.print(dataKabumC);%>
                        },
                        {
                            type: "line",
                            showInLegend: true,
                            yValueFormatString: "#,#",
                            name: "MagazineLuiza",
                            dataPoints : <%out.print(dataMLC);%>
                        }]
            });
            
            chart1.render();
            chart2.render();

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