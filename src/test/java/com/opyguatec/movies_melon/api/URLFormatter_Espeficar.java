package com.opyguatec.movies_melon.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.opyguatec.movies_melon.api.utils.URLFormatter;
import com.opyguatec.movies_melon.core.Movie;

public class URLFormatter_Espeficar {
   @Test void 
   que_dada_una_pelicula__calcule_su_url() throws ParseException {
      DateFormat release_parse = new SimpleDateFormat("dd-MM-yyyy");
      String title      = "Furiosa: La saga de mad max";
      String synopsys   ="Sinopsis de la película";
      Date release      = release_parse.parse("23-05-2024");
      String post_path  = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/tGHUlykWn9V2IIQ4ZaATIAq9VLB.jpg";
      Movie movie       = Movie.with(title, synopsys, release, post_path, "", List.of(), List.of());
      String host       = "localhost";
      Integer port       = 8080;
      String context    = "movies-melon";
      String expected_url = String
         .format("http://%s:%d/%s/api/movies/%s"
         , host, port, context, movie.its_id());

      String url  = URLFormatter
         .with(host, port, context)
         .format( movie );

      assertThat(url , is(equalTo(expected_url)));
   }
   
}
