package ic.doc;

import ic.doc.web.HTMLResultPage;
import ic.doc.web.IndexPage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
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

  @WebServlet("/download")
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
        if (type.equals("md")) {
          resp.setContentType("text/markdown");
          try {
            download(resp, query, "md");
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        } else if (type.equals("pdf")) {
          resultPage.generatePdf();
          resp.setContentType("application/pdf");
          try {
            download(resp, query, "pdf");
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          //new IndexPage().writeTo(resp);
        } else {
          resultPage.writeTo(resp);
        }
      }
    }


    private void download(HttpServletResponse resp, String query, String type)
        throws IOException, InterruptedException {
      PrintWriter output = resp.getWriter();
      resp.setHeader("Content-disposition", "attachment; filename=\"" + query + "." + type +"\"");

      File temp = File.createTempFile(query, "." + type);
      FileWriter writer = new FileWriter(temp);

      if (type.equals("md")) {
        writer.write("#" + query + "\n");
        writer.write(new QueryProcessor().process(query));
      } else if (type.equals("pdf")) {
        Process pdfConverter = new ProcessBuilder("pandoc", "-f",
                                          "markdown", temp.getAbsolutePath(), "-o",
                                            temp.getAbsolutePath()).start();
        int exitCode = pdfConverter.waitFor();
        if (exitCode != 0) {
          System.err.println("cannot create pdf");
        }
      }

      FileInputStream inputStream = new FileInputStream(query);
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


