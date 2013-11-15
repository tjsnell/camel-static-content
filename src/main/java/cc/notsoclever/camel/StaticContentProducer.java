package cc.notsoclever.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.MimetypesFileTypeMap;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * The static producer.
 */
public class StaticContentProducer extends DefaultProducer {
   private static final Logger LOG = LoggerFactory.getLogger(StaticContentProducer.class);
   private StaticContentEndpoint endpoint;

   public StaticContentProducer(StaticContentEndpoint endpoint) {
      super(endpoint);
      this.endpoint = endpoint;
   }

   public void process(Exchange exchange) throws Exception {
      Message in = exchange.getIn();

      String relativepath = in.getHeader(Exchange.HTTP_PATH, String.class);

      if (relativepath.isEmpty()) {
         relativepath = "index.html";
      }

      String pathStr = endpoint.getPath() + "/" + relativepath;

      System.out.println("---- - pathStr = " + pathStr);
      Path path = FileSystems.getDefault().getPath(pathStr);

      //String mimeType = Files.probeContentType(path);

      MimetypesFileTypeMap map = new MimetypesFileTypeMap();

      System.out.println("----=== " + map.getContentType(pathStr));
      String mimeType = map.getContentType(pathStr);

      Message msg = getMessageForResponse(exchange);

      try {
         msg.setBody(Files.readAllBytes(path));
         msg.setHeader(Exchange.CONTENT_TYPE, mimeType);
         msg.setHeader(Exchange.HTTP_RESPONSE_CODE, "200");
      } catch (IOException e) {
         msg.setBody(pathStr + " not found.");
         msg.setHeader(Exchange.HTTP_RESPONSE_CODE, "404");
      }
   }

   private Message getMessageForResponse(Exchange exchange) {
      if (exchange.getPattern().isOutCapable()) {
         Message out = exchange.getOut();
         out.copyFrom(exchange.getIn());
         return out;
      }

      return exchange.getIn();
   }


}
