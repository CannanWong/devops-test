package ic.doc.web;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

public class HTMLResultPage implements Page {

  private final String query;
  private final String answer;

  public HTMLResultPage(String query, String answer) {
    this.query = query;
    this.answer = answer;
  }

  public void writeTo(HttpServletResponse resp) throws IOException {
    resp.setContentType("text/html");
    PrintWriter writer = resp.getWriter();

    // Header
    writer.println("<html>");
    writer.println("<head><title>" + query + "</title></head>");
    writer.println("<body>");

    // Content
    if (answer == null || answer.isEmpty()) {
      writer.println("<h1>Sorry</h1>");
      writer.print("<p>Sorry, we didn't understand <em>" + query + "</em></p>");
    } else {
      writer.println("<h1>" + query + "</h1>");
      writer.println("<p>" + answer.replace("\n", "<br>") + "</p>");
    }

    writer.println("<p><a href=\"/\">Back to Search Page</a></p>");

    // Footer
    writer.println("</body>");
    writer.println("</html>");
  }

  public void downloadResults(FileWriter writer) throws IOException {
    writer.write("<html>" +
        "<head><title>\"" + query + "\"</title></head>" +
        "<body>");

    if (answer == null || answer.isEmpty()) {
      writer.write("<p>Sorry\nSorry, we didn't understand " + query + "</em></p>");
    } else {
      writer.write("<h1>" + query + "</h1>");
      writer.write("<p>" + answer.replace("\n", "<br>") + "</p>");
    }
    // Footer
    writer.write("</body>");
    writer.write("</html>");
  }
  
}

