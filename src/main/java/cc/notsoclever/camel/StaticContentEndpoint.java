package cc.notsoclever.camel;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.impl.DefaultEndpoint;

/**
 * Represents a static endpoint.
 */
public class StaticContentEndpoint extends DefaultEndpoint {

    public StaticContentEndpoint() {
    }

    public StaticContentEndpoint(String uri, StaticContentComponent component) {
        super(uri, component);
    }

    public StaticContentEndpoint(String endpointUri) {
        super(endpointUri);
    }

    public Producer createProducer() throws Exception {
        return new StaticContentProducer(this);
    }

    public Consumer createConsumer(Processor processor) throws Exception {
       throw new RuntimeCamelException("Cannot consume from a StaticContent endpoint: " + getEndpointUri());
    }

    public boolean isSingleton() {
        return true;
    }

   public String getPath() {
      StaticContentComponent comp = (StaticContentComponent) getComponent();
      return comp.getPath();
   }
}
