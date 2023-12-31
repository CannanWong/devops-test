package ic.doc.web;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class IndexPage implements Page {

    public void writeTo(HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();

        // Header
        writer.println("<html>");
        writer.println("<head><title>Welcome</title></head>");
        writer.println("<body>");

        // Content
        writer.println(
                "<h1>Welcome Mark!!</h1>" +
                        "<p>Enter your query in the box below: " +
                        "<form>" +
                        "<input type=\"text\" name=\"q\" />" +
                        "<br><input type=\"radio\" id=\"html\" name=\"type\" value=\"html\">" +
                        "<label for=\"html\">HTML</label><br>" +
                        "<input type=\"radio\" id=\"md\" name=\"type\" value=\"md\">" +
                        "<label for=\"md\">MarkDown</label><br>" +
                        "<input type=\"radio\" id=\"pdf\" name=\"type\" value=\"pdf\">" +
                        "<label for=\"pdf\">PDF</label><br>" +
                        "<br><br><input type=\"submit\">" +
                        "</form>" +
                        "</p>");

        // Footer
        writer.println("</body>");
        writer.println("</html>");
    }
    
}
