package com.ArsenyVekshin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import com.ArsenyVekshin.table.Table;
import com.ArsenyVekshin.table.TableRow;
import org.json.JSONObject;

@WebServlet(name="AreaCheckServlet", value="/areaCheck/*")
public class AreaCheckServlet extends HttpServlet {
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getDispatcherType().name().equals("FORWARD")) {
            resp.sendError(403, "You don't have access rights to this resource");
            return;
        }
        super.service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        long currentTime = System.currentTimeMillis();

        try {
            float x = Float.parseFloat(request.getParameter("x"));
            float y = Float.parseFloat(request.getParameter("y"));
            float r = Float.parseFloat(request.getParameter("r"));
            long offset = -Long.parseLong(request.getParameter("offset"));
            Instant clientTime = Instant.now().plus(offset, ChronoUnit.MINUTES);
            boolean result = checkArea(x,y,r);

            double scriptWorkingTime = System.currentTimeMillis() - currentTime;

            TableRow newRow = new TableRow(x, y, r, result, clientTime.toString(), scriptWorkingTime);
            Table sessionTable = (Table) request.getSession().getAttribute("table");
            sessionTable.getTableRows().add(newRow);

            PrintWriter out = response.getWriter();
            out.print(new JSONObject(newRow));
            out.close();
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (NumberFormatException e) {
            response.sendError(400, "Transmitted values are not numeric");
        } catch (IOException e) {
            response.sendError(418, "Unidentified error");
        }
    }

    private boolean checkArea(float x, float y, float r){
        if (x>=0 && y>=0){
            if (x<=r && y<=r/2) return true;
        }
        else if (x<=0 && y>=0){
            if (y < (x/2 + 0.5)) return true;
        }
        else if (x<=0 && y<=0){
            if ((x*x + y*y) < r) return true;
        }
        return false;
    }
}