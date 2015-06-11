package alceausu.nlp.sense;

import javax.annotation.Resource;

import org.apache.uima.resource.ResourceInitializationException;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.http.outbound.HttpRequestExecutingMessageHandler;
import org.springframework.integration.json.ObjectToJsonTransformer;
import org.springframework.stereotype.Component;

import alceausu.nlp.sense.model.CommStore;
import alceausu.nlp.sense.model.CommStoreRepl;

@Configuration
@EnableAutoConfiguration
@EnableIntegration
@PropertySource({"classpath:app.properties"})
@ComponentScan(basePackages = "alceausu.nlp.sense")
@EnableElasticsearchRepositories(basePackages = "alceausu.nlp.sense.repository")
public class NlpConfig {
	
	@Value("${mysql.user}") 
    String user;
	
	@Value("${mysql.pass}") 
    String pass;
	
	@Value("${elasticsearch.host}")
	String elasticsearchHost;
	
	@Value("${elasticsearch.port}")
	int elasticsearchPort;
	
	public NlpConfig() {
    }

    @Bean(name="annPipeline")
    public LinguisticAnnotationPipeline annPipeline() throws ResourceInitializationException {
        return new LinguisticAnnotationPipeline(user, pass);
    }
    
    @Transformer(inputChannel="requestChannel", outputChannel="requestJsonChannel")
    public ObjectToJsonTransformer objToJsonTransformer() {
		return new ObjectToJsonTransformer();
    }
    
    @ServiceActivator(inputChannel="requestJsonChannel", outputChannel="replyChannel")
    public HttpRequestExecutingMessageHandler outGateway() {
    	HttpRequestExecutingMessageHandler httpRequestHandler = new HttpRequestExecutingMessageHandler(elasticsearchHost);
    	httpRequestHandler.setHttpMethod(HttpMethod.POST);
    	return httpRequestHandler;
    }
    
    @MessagingGateway(name="elasticGateway")
    public interface ElasticStoreGateway {	
    	@Gateway(requestChannel="requestChannel", replyChannel="replyChannel")
    	public CommStoreRepl storeCommand(CommStore commStore);
    }
    
    @Bean
    public Client client() {
        TransportClient client = new TransportClient();
        TransportAddress address = new InetSocketTransportAddress(elasticsearchHost, elasticsearchPort);
        client.addTransportAddress(address);
        return client;
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchTemplate(client());
    }
}
