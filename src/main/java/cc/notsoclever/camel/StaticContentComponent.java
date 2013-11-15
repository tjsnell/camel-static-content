package cc.notsoclever.camel;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;

import java.util.Map;

/**
 * Represents the component that manages {@link StaticContentEndpoint}.
 */
public class StaticContentComponent extends DefaultComponent {

   private String path;
   private String defaultFile;

   protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {

      defaultFile = getAndRemoveParameter(parameters, "defaultFile", String.class);

      path = remaining;
      if (path == null) {
         throw new IllegalArgumentException("Resource path must be specified.");
      }

      System.out.println("------------- path = " + path);


      Endpoint endpoint = new StaticContentEndpoint(uri, this);
      setProperties(endpoint, parameters);
      return endpoint;
   }

   public String getPath() {
      return path;
   }
}
