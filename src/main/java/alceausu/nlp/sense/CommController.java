package alceausu.nlp.sense;

import java.io.IOException;
import java.util.UUID;

import org.apache.uima.UIMAException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import alceausu.nlp.sense.NlpConfig.ElasticStoreGateway;
import alceausu.nlp.sense.entities.NlpCommand;
import alceausu.nlp.sense.model.CommStore;
import alceausu.nlp.sense.model.CommStoreRepl;
import alceausu.nlp.sense.model.QueryComm;
import alceausu.nlp.sense.model.QueryCommRepl;
import alceausu.nlp.sense.service.NlpCommandService;


@RestController
public class CommController {
	
	private static final Logger logger = LoggerFactory.getLogger(CommController.class);
	
	@Autowired
	private LinguisticAnnotationPipeline annPipeline;
	
	@Autowired
    private NlpCommandService nlpCommService;
	
	@Autowired 
	private ApplicationContext applicationContext;
//	@Autowired @Qualifier("elasticGateway.handler")
//	private MessagingGateway elasticGateway;
	
	/*
	@RequestParam(value="apiKey", defaultValue=DEFAULT_UUID, required = false) String apiKey, 
	@RequestParam(value="device", required = false) String device,
	*/
	
	@RequestMapping(value="/test")
	public QueryCommRepl queryTest() {
//		if (annPipeline == null)
//			
//				annPipeline = new LinguisticAnnotationPipeline();
		
		try {
			System.out.println( annPipeline.annotate("Mozart was born in Salzburg") );
		} catch (UIMAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new QueryCommRepl("test");
	}
	
	/**
	 * Stores the commands to the DB.
	 * @param queryStore
	 * @return
	 */
	@RequestMapping(value="/store", method=RequestMethod.POST, 
			consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public CommStoreRepl query(@RequestParam(value="apiKey", required = false) String apiKey, 
    		@RequestParam(value="device", required = false) String device, @RequestBody CommStore commStore) {
		if (apiKey != null)
			commStore.setApiKey(UUID.fromString(apiKey));
		if (device != null)
			commStore.setDevice(device);
		String annText = commStore.getText();
		
		try {
			annText	= annPipeline.annotate(commStore.getText());
		} catch (UIMAException | IOException e) {
			logger.info(e.getMessage());
		}
		
		NlpCommand comm = new NlpCommand();
		comm.setApiKey(commStore.getApiKey());
		comm.setDevice(commStore.getDevice());
		comm.setText(annText);
		comm.setCommand(commStore.getCommand());
		comm.setDate(new java.util.Date());
		
        NlpCommand resp = nlpCommService.save(comm);
		
		return new CommStoreRepl();
    }
	
	/**
	 * Queries the commands from DB given the spoken text and returns the matching command.
	 * @param queryComm
	 * @return
	 */
	@RequestMapping(value="/query", method=RequestMethod.POST, consumes="application/json")
    public QueryCommRepl query(@RequestBody QueryComm queryComm) {
		
		return new QueryCommRepl("test");
    }
}
