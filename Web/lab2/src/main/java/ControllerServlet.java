import org.json.JSONArray;
import table.Table;
import table.TableRow;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ControllerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        if (checkGetTableRequest(request)) {
            Table table = (Table) request.getSession().getAttribute("table");
            List<TableRow> rows = table.getTableRows();
            out.print(new JSONArray(rows));
            out.close();
            response.setStatus(HttpServletResponse.SC_OK);
        } else if (checkArgumentExists(request)) {
            getServletContext().getRequestDispatcher("/AreaCheckServlet").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (checkCleanRequest(request)) {
            cleanTable(request);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    private boolean checkCleanRequest(HttpServletRequest req) {
        return "true".equals(req.getParameter("clean"));
    }

    private void cleanTable(HttpServletRequest request) {
        HttpSession currentSession = request.getSession();
        currentSession.setAttribute("table", new Table());
    }

    private boolean checkArgumentExists(HttpServletRequest request) {
        return request.getParameter("x") != null &&
                request.getParameter("y") != null &&
                request.getParameter("r") != null &&
                request.getParameter("offset") != null;
    }

    private boolean checkGetTableRequest(HttpServletRequest request) {
        return "true".equals(request.getParameter("getTable"));
    }
}
