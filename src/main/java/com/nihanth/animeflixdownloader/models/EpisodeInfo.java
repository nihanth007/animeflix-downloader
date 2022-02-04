package com.nihanth.animeflixdownloader.models;

import java.io.Serializable;

public class EpisodeInfo implements Serializable {

	private static final long serialVersionUID = -889913460044473931L;

	private int episodeNumber;
	private String episodeName;
	private int season;
	private String episodeLink;
	private String downloadLink;

	public EpisodeInfo(int episodeNumber, String episodeLink) {
		super();
		this.episodeNumber = episodeNumber;
		this.episodeLink = episodeLink;
	}

	public EpisodeInfo(int episodeNumber, String episodeName, String episodeLink) {
		super();
		this.episodeNumber = episodeNumber;
		this.episodeName = episodeName;
		this.episodeLink = episodeLink;
	}

	public int getEpisodeNumber() {
		return episodeNumber;
	}

	public void setEpisodeNumber(int episodeNumber) {
		this.episodeNumber = episodeNumber;
	}

	public String getEpisodeName() {
		return episodeName;
	}

	public void setEpisodeName(String episodeName) {
		this.episodeName = episodeName;
	}

	public int getSeason() {
		return season;
	}

	public void setSeason(int season) {
		this.season = season;
	}

	public String getEpisodeLink() {
		return episodeLink;
	}

	public void setEpisodeLink(String episodeLink) {
		this.episodeLink = episodeLink;
	}

	public String getDownloadLink() {
		return downloadLink;
	}

	public void setDownloadLink(String downloadLink) {
		this.downloadLink = downloadLink;
	}

}
