package rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import domain.Comment;
import domain.Movie;
import domain.Score;
import domain.services.MovieService;

@Path("/movies")
public class MovieResources {
	private MovieService db = new MovieService();
	
	// pobranie listy filmow
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Movie> getAll(){
		return db.getAll();
	}
	
	// dodanie filmu
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response Add(Movie m){
		db.add(m);
		return Response.ok(m.getId()).build();
	}
	
	// pobranie filmu o danym id
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") int id){
		Movie result = db.get(id);
		if(result==null){
			return Response.status(404).build();
		}
		return Response.ok(result).build();
	}
	
	// update danych filmu o danym id
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") int id, Movie m){
		Movie result = db.get(id);
		if(result==null){
			Response.status(404).build();
		}
		m.setId(id);
		db.update(m);
		return Response.ok().build();
	}
	
	// usuniecie filmu o danym id
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") int id){
		Movie result = db.get(id);
		if(result==null)
			Response.status(404).build();
		db.update(result);
		return Response.ok().build();
	}
	
	// pobranie komentarzy filmu o danym id
	@GET
	@Path("/{movieId}/comment")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comment> getComments(@PathParam("movieId") int movieId){
		Movie result = db.get(movieId);
		if(result==null)
			return null;
		if(result.getComments()==null)
			result.setComments(new ArrayList<Comment>());
		return result.getComments();
	}
	
	// dodanie komentarza dla filmu
	@POST
	@Path("/{id}/comment")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addComment(@PathParam("id") int movieId, Comment comment){
		Movie result = db.get(movieId);
		if(result==null)
			return Response.status(404).build();
		if(result.getComments()==null)
			result.setComments(new ArrayList<Comment>());
		result.getComments().add(comment);
		return Response.ok().build();
	}
	
	//usuniecie komentarza dla filmu
	@DELETE
	@Path("/{movieId}/{commentId}")
	public Response deleteComment(@PathParam("movieId") int movieId, 
			@PathParam("commentId") int commentId){
		Movie result = db.get(movieId);
		if(result==null)
			Response.status(404).build();
		Comment comment = result.getComments().get(commentId);
		if(result.getComments()==null)
			Response.status(404).build();
		if(comment==null)
			Response.status(404).build();
		comment = null;
		return Response.ok().build();
	}
	
	// dodanie oceny dla filmu
	@POST
	@Path("/{id}/score")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addScore(@PathParam("id") int movieId, Score score){
		Movie result = db.get(movieId);
		if(result==null)
			return Response.status(404).build();
		if(result.getScores()==null)
			result.setScores(new ArrayList<Score>());
		result.getScores().add(score);
		return Response.ok().build();
	}
	
	// pobranie głownej oceny filmu
	@GET
	@Path("/{id}/mainscore")
	@Produces(MediaType.APPLICATION_JSON)
	public double getMainScore(@PathParam("id") int id){
		Movie movie = db.get(id);
		if(movie==null)
			Response.status(404).build();
		double result = movie.getMainScore();
		if(result < 0)
			Response.status(404).build();
		return movie.getMainScore();
	}
	
}
