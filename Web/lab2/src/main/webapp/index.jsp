<%!
    String tableRowToHtml(TableRow tableRow) {
        if (tableRow == null) return "";
        return "<tr>" +
                "<td>" + tableRow.getX() + "</td>" +
                "<td>" + tableRow.getY() + "</td>" +
                "<td>" + tableRow.getR() + "</td>" +
                "<td>" + tableRow.getClientDate() + "</td>" +
                "<td>" + tableRow.getScriptWorkingTime() + " ms</td>" +
                "<td>" + tableRow.isHit() + "</td>" +
                "</tr>";
    }
%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.ArsenyVekshin.table.TableRow" %>
<jsp:useBean id="table" scope="session" beanName="com.ArsenyVekshin.table.Table" type="com.ArsenyVekshin.table.Table"/>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Л.Р. Веб ЛР2 Векшин А.И</title>
    <link rel="stylesheet" href="css/main.css">
    </head>
<body>
    <div id="container">
        <table>
            <thead height="200px">
            <tr>
                <th width="350px">
                    <img src="assets/kitten1.gif" height="175px" />
                </th>
                <th min-width="400px">
                    <h1 class="header">Arseny Vekshin</h1>
                    <h2 class="header">p3216 / 23152</h2>
                    <a href="https://github.com/ArsenyVekshin/ITMO/tree/master/Web/lab2" data-title="https://github.com/ArsenyVekshin/ITMO/tree/master/Web/lab2">
                        github
                    </a>
                </th>
                <th>
                    <img src="assets/kitten1.gif" height="175px" />
                </th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td id="col1">
                    <form id="form">
                        <br>
                        <div class="x-checkbox" >
                            <label id="x-label"> X: </label><br />
                            <input class="x" name="x" type="checkbox" id="x-2" value="-2" !checked /> <label>-2</label><br />
                            <input class="x" name="x" type="checkbox" id="x-1.5" value="-1.5" !checked /> <label>-1.5</label><br />
                            <input class="x" name="x" type="checkbox" id="x-1" value="-1" !checked /> <label>-1</label><br />
                            <input class="x" name="x" type="checkbox" id="x-0.5" value="-0.5" !checked /> <label>-0.5</label><br />
                            <input class="x" name="x" type="checkbox" id="x0" value="0" !checked /> <label>0</label><br />
                            <input class="x" name="x" type="checkbox" id="x0.5" value="0.5" !checked /> <label>0.5</label><br />
                            <input class="x" name="x" type="checkbox" id="x1" value="1" !checked /> <label>1</label><br />
                            <input class="x" name="x" type="checkbox" id="x1.5" value="1.5" !checked /> <label>1.5</label><br />
                            <input class="x" name="x" type="checkbox" id="x2" value="2" !checked /> <label>2</label>
                        </div>
                        <br />

                        <div class="y-input">
                            <label id="y-label">Y: </label>
                            <input id="y-value" name="y" type="text" placeholder="Y in range (-3.0, 5.0)">
                        </div>
                        <br/>

                        <div class="r-radio" >
                            <label id="r-label"> R: </label>
                            <input class="r" name="r" type="radio" value="1" !checked /> <label>1</label>
                            <input class="r" name="r" type="radio" value="2" !checked /> <label>2</label>
                            <input class="r" name="r" type="radio" value="3" !checked /> <label>3</label>
                            <input class="r" name="r" type="radio" value="4" !checked /> <label>4</label>
                            <input class="r" name="r" type="radio" value="5" !checked /> <label>5</label>
                        </div>
                        <br />

                        <button type="submit" id="submit-button">Submit</button>
                        <button type="clear" id="clear-button">Clear</button>
                    </form>
                </td>
                <td id="col2">
                    <center>
                        <canvas id="graph_canvas" width="400" height="400"></canvas>
                    </center>

                </td>
                <td id="col3">
                    <div class = "result">
                        <table id="results-table">
                            <thead>
                            <tr>
                                <th width="5%">X</th>
                                <th width="5%">Y</th>
                                <th width="5%">R</th>
                                <th width="45%">Current Time</th>
                                <th width="20%">Script Time(ms)</th>
                                <th width="20%">Result</th>
                            </tr>
                            </thead>
                            <tbody id="results-content">
                            <%
                                ArrayList<TableRow> tableRows = table.getTableRows();
                                for (TableRow tableRow: tableRows) {
                                    out.print(tableRowToHtml(tableRow));
                                }
                            %>
                            </tbody>
                        </table>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <script src="https://code.jquery.com/jquery-2.2.4.min.js"></script>
    <script src="js/main.js"></script>
    <script src="js/graph.js"></script>
    <script src="js/submit.js"></script>
    <script src="js/validator.js"></script>
    <script src="js/tableWorker.js"></script>

</body>
</html>