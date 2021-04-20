package org.acme;

public class XrayEvent {

	private String eventName;
	private String name;
	private String key;
	private String comments;
	private Boolean secondOpinion;
	private String imageName;
	private String bucketName;
	private String url;

	public XrayEvent() {}

	public XrayEvent(String eventName, String name, String key, String comments, Boolean secondOpinion, String bucketName, String imageName, String url) {
		this.eventName = eventName;
		this.name = name;
		this.key = key;
		this.comments = comments;
		this.secondOpinion = secondOpinion;
		this.bucketName = bucketName;
		this.imageName = imageName;
		this.url = url;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Boolean getSecondOpinion() {
		return this.secondOpinion;
	}

	public void setSecondOpinion(Boolean secondOpinion) {
		this.secondOpinion = secondOpinion;
	}

	@Override
	public String toString() {
		return "{" +
			" eventName='" + eventName + "'" +
			", name='" + name + "'" +
			", key='" + key + "'" +
			", comments='" + comments + "'" +
			", secondOpinion='" + secondOpinion + "'" +
			", imageName='" + imageName + "'" +
			", bucketName='" + bucketName + "'" +
			", url='" + url + "'" +
			"}";
	}

    /**
     * @return Boolean return the secondOpinion
     */
    public Boolean isSecondOpinion() {
        return secondOpinion;
    }

    /**
     * @return String return the imageName
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * @param imageName the imageName to set
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    /**
     * @return String return the bucketName
     */
    public String getBucketName() {
        return bucketName;
    }

    /**
     * @param bucketName the bucketName to set
     */
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }


    /**
     * @return String return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

}
