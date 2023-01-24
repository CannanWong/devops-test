package ic.doc.web;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileWriter;


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

    public void downloadResults() throws IOException {
	 File results = new File("results.md");
      	 FileWriter writer = new FileWriter("results.md");
     	 if (answer == null || answer.isEmpty()) {
     		 writer.write("# Sorry\nSorry, we didn't understand " + query + "");
     	 }
   	 else {
        	writer.write("# " + query + "\n" + answer);
      	 }	
    }
}
