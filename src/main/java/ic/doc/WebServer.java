package ic.doc;

import ic.doc.web.HTMLResultPage;
import ic.doc.web.IndexPage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
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
        return;
      }
      String type = req.getParameter("type");
      if (Objects.isNull(type)) {
        new HTMLResultPage(query, new QueryProcessor().process(query)).writeTo(resp);
      } else {
        /*download type*/
        if (type.equals("html")) {
          resp.setContentType("text/html");
          try {
            download(resp, query, "html");
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        } else if (type.equals("md")) {
          resp.setContentType("text/markdown");
          try {
            download(resp, query, "md");
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        } else if (type.equals("pdf")) {
          resp.setContentType("application/pdf");
          try {
            download(resp, query, "pdf");
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        } else {
          new HTMLResultPage(query, new QueryProcessor().process(query)).writeTo(resp);
        }
      }
    }


    private void download(HttpServletResponse resp, String query, String type)
        throws IOException, InterruptedException {
      resp.setHeader("Content-disposition", "attachment; filename=\"" + query + "." + type + "\"");
      File temp;

      if (type.equals("html")) {
        temp = File.createTempFile(query, ".html");
        FileWriter writer = new FileWriter(temp);
        new HTMLResultPage(query, new QueryProcessor().process(query)).downloadResults(writer);
        writer.close();
      }else {
        temp = File.createTempFile(query, ".md");
        FileWriter writer = new FileWriter(temp);
        writer.write("#" + query + "\n");
        writer.write(new QueryProcessor().process(query));
        writer.close();

        if (type.equals("pdf")) {
          File tempInput = temp;
          temp = File.createTempFile( query, ".pdf\"");
          Process pdfConverter = new ProcessBuilder("pandoc", "-f",
              "markdown", tempInput.getAbsolutePath(), "-o",
              temp.getAbsolutePath()).start();

          int exitCode = pdfConverter.waitFor();
          if (exitCode != 0) {
            System.err.println("cannot create pdf");
            return;
          }
        }
      }

      FileInputStream inputStream = new FileInputStream(temp);
      byte[] bytes = inputStream.readAllBytes();
      inputStream.close();

      resp.getOutputStream().write(bytes);
      resp.getOutputStream().close();
    }
  }


  public static void main(String[] args) throws Exception {
    new WebServer();
  }
}


