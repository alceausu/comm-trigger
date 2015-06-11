package alceausu.nlp.sense.model;

import java.util.UUID;

public class CommStore {

	private static final String DEFAULT_UUID = "eb4c76ba-0aff-11e5-a6c0-1697f925ec7b";
	private static final String DEFAULT_DEVICE = "generic";
	
	private UUID apiKey = UUID.fromString(DEFAULT_UUID);
	private String device = DEFAULT_DEVICE;
	private String text;
	private String command;
	
	public UUID getApiKey() {
		return apiKey;
	}
	public void setApiKey(UUID apiKey) {
		this.apiKey = apiKey;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	

}
