package com.opyguatec.movies_melon.api.utils;

import com.opyguatec.movies_melon.core.Movie;

public class URLFormatter {

   private String host;
   private Integer port;
   private String context;

   public URLFormatter(String host, Integer port, String context) {
      this.host      = host;
      this.port      = port;
      this.context   = context;
   }

	public String format(Movie movie) {
      return String.format("http://%s:%d/%s/api/movies/%s"
      , host
      , port
      , context
      , movie.its_id());
	}

   public static URLFormatter with(String host, Integer port, String context) {
      URLFormatter formatter  = new URLFormatter(host, port, context);
      return formatter;
   }

}
