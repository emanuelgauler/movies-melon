package com.opyguatec.movies_melon.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opyguatec.movies_melon.api.utils.ErrorResponseModel;
import com.opyguatec.movies_melon.api.utils.MovieResponseModel;
import com.opyguatec.movies_melon.core.Movie;
import com.opyguatec.movies_melon.core.MovieExistingError;
import com.opyguatec.movies_melon.core.MoviesMelon;
import com.opyguatec.movies_melon.data.MysqlMoviesStore;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "Movies", urlPatterns = "/api/movies")
public class MoviesServlet extends HttpServlet {

   private DateFormat release_parser = new SimpleDateFormat("dd/MM/yyyy");
   private MoviesMelon melones;

   @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      PrintWriter out = resp.getWriter();
      ObjectMapper mapper = new ObjectMapper();
      mapper.setDateFormat(release_parser);
      try {
         resp.setContentType("application/json");

         Movie new_movie = Movie.with(
               mapper.readValue(req.getReader(), Movie.class));
         
         melones.add(new_movie);

         resp.setStatus(HttpServletResponse.SC_CREATED);

         out.println(mapper.writeValueAsString(
               MovieResponseModel.with(new_movie)));

      } catch (MovieExistingError e) {
         resp.setStatus(HttpServletResponse.SC_CONFLICT);
         out.println(mapper.writeValueAsString(ErrorResponseModel.with(e)));
      } catch (Exception e) {
         String message = String.format(">> [%s] %s\n%s", e.getClass().getName(), e.getMessage(),
               e.getStackTrace().toString());
         resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
      }
   }


   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      resp.setContentType("application/json");
      ObjectMapper mapper = new ObjectMapper();
      mapper.setDateFormat(release_parser);
      PrintWriter out = resp.getWriter();

      try {
         String response = mapper.writeValueAsString( movies_from(melones) );
         out.printf("{\"movies\":%s, \"count\":%d }", response, melones.listing().size());
      } catch (JsonProcessingException e ) {
         resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
      } catch (Exception e) {
         resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
      }
   }


   private List<MovieResponseModel> movies_from(MoviesMelon melones) throws Exception {
      List<MovieResponseModel> movies = melones.listing()
            .stream().map(e -> MovieResponseModel.with(e))
            .collect(Collectors.toList());
      return movies;
   }


   @Override
   public void init() throws ServletException {
      super.init();
      melones = new MoviesMelon(new MysqlMoviesStore());
   }

}