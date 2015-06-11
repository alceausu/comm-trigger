package alceausu.nlp.sense.model;

import java.util.UUID;

public class QueryComm {
	
	private UUID apiKey;
	private String device;
	private String text;
	
	public QueryComm() {
		
	}
	
	public QueryComm(UUID apiKey, String device, String text) {
		this.apiKey = apiKey;
		this.device = device;
		this.text = text;
	}
	
	public UUID getApiKey() {
		return apiKey;
	}
	public String getDevice() {
		return device;
	}
	public String getText() {
		return text;
	}
	public void setApiKey(UUID apiKey) {
		this.apiKey = apiKey;
	}

	public void setDevice(String device) {
		this.device = device;
	}
	public void setText(String text) {
		this.text = text;
	}
}
