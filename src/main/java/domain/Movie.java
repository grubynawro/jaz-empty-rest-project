package domain;

import java.util.List;

public class Movie {
	private int id;
	private String title;
	private String info;
	private List<Comment> comments;
	private List<Score> scores;
	private double mainScore = setMainScore();
	
	public double getMainScore(){
		return mainScore;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public List<Score> getScores() {
		return scores;
	}
	public void setScores(List<Score> scores) {
		this.scores = scores;
	}
	private double setMainScore(){
		double sum = 0;
		for (Score d:scores) sum += (double)d.getValue();
		double average = sum / scores.size();
		return average;
	}
	
}
