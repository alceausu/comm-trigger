package alceausu.nlp.sense.entities;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "nlp", type = "command", shards = 1, replicas = 0)
public class NlpCommand {
	
	private static final String DEFAULT_UUID = "eb4c76ba-0aff-11e5-a6c0-1697f925ec7b";
	private static final String DEFAULT_DEVICE = "generic";
	
    @Id
    private String id;
    private String text;
    private String command;
	private UUID apiKey = UUID.fromString(DEFAULT_UUID);
	private String device = DEFAULT_DEVICE;
	@Field(type = FieldType.Date, format = DateFormat.custom, pattern ="dd-MM-yyyy HH:mm:ss")
	private Date date;
   // @Field(type= FieldType.Nested)
    private List<Tag> tags;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
