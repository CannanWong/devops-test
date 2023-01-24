package ic.doc;

import ic.doc.web.HTMLResultPage;
import ic.doc.web.IndexPage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class WebServer {

  public WebServer() throws Exception {
    Server server = new Server(Integer.valueOf(System.getenv("PORT")));

    ServletHandler handler = new ServletHandler();
    handler.addServletWithMapping(new ServletHolder(new Website()), "/*");
    server.setHandler(handler);

    server.start();
  }

  static class Website extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
      /*query*/
      String query = req.getParameter("q");
      if (query == null) {
        new IndexPage().writeTo(resp);
      } else {
        /*download type*/
        HTMLResultPage resultPage = new HTMLResultPage(query, new QueryProcessor().process(query));
        String type = req.getParameter("type");
        if (type == "md") {
          resultPage.downloadResults();
          resp.setContentType("text/markdown");
          download(resp);
          new IndexPage().writeTo(resp);
        }
        else if (type == "pdf"){
          resultPage.generatePdf();
          resp.setContentType("application/pdf");
          download(resp);
          new IndexPage().writeTo(resp);
        } else {
          resultPage.writeTo(resp);
        }
      }
    }
    
    private void download(HttpServletResponse resp) throws IOException {
      PrintWriter output = resp.getWriter();
      // resp.setHeader("Content-disposition", "attachment; filename=results.pdf");

      FileInputStream inputStream = new FileInputStream("results.pdf");
      int input = inputStream.read();
      while (input != -1) {
        output.write(input);
        input = inputStream.read();
      }

      inputStream.close();
      output.close();
    }
  }

  

  public static void main(String[] args) throws Exception {
    new WebServer();
  }
}

