package alceausu.nlp.sense.model;

public class CommStoreRepl {

	private String _index;
	private String _type;
	private int _version;
	private boolean created;
	private String annotation;

	public String getAnnotation() {
		return annotation;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	public String get_index() {
		return _index;
	}

	public void set_index(String _index) {
		this._index = _index;
	}

	public String get_type() {
		return _type;
	}

	public void set_type(String _type) {
		this._type = _type;
	}

	public int get_version() {
		return _version;
	}

	public void set_version(int _version) {
		this._version = _version;
	}

	public boolean isCreated() {
		return created;
	}

	public void setCreated(boolean created) {
		this.created = created;
	}

}
