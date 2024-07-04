package com.opyguatec.movies_melon.api;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opyguatec.movies_melon.api.utils.MovieNotFoundResponse;
import com.opyguatec.movies_melon.api.utils.MovieResponseModel;
import com.opyguatec.movies_melon.core.Movie;
import com.opyguatec.movies_melon.core.MoviesMelon;
import com.opyguatec.movies_melon.data.StoreInMemory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "MovieServlet", urlPatterns = "/api/movies/*")
public class MovieServlet extends HttpServlet {

   private MoviesMelon melones;
   private ObjectMapper mapper;

   @Override
   protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      // TODO Auto-generated method stub
      super.doDelete(req, resp);
   }

   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      resp.setContentType("application/json");
      try {
         Movie movie = melones.find_movie_for_id(movie_id_from(req));
         /*
          * $$$ Recordatorio como lo hice al principio $$$
          * melones.listing().stream()
          * .filter(e -> e.its_id().equals(movie_id))
          * .findFirst().orElse(null);
          */
         
         if( movie != null ) {
            resp.getWriter().println(mapper.writeValueAsString(MovieResponseModel.with(movie)));
         } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().println(mapper.writeValueAsString(new MovieNotFoundResponse()));
         }
      } catch (Exception e) {
         resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
      }
   }

   private String movie_id_from(HttpServletRequest req) {
      String movie_id = req.getPathInfo().replaceAll("/", "");
      return movie_id;
   }

   @Override
   protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      // TODO Auto-generated method stub
      super.doPatch(req, resp);
   }

   @Override
   public void init() throws ServletException {
      super.init();
      try {
         melones = new MoviesMelon(new StoreInMemory());
         mapper = new ObjectMapper();
         mapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
      } catch (ParseException e) {
         e.printStackTrace();
      }
   }
   
}
