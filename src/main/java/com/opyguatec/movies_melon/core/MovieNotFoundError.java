package com.opyguatec.movies_melon.core;

public class MovieNotFoundError extends Exception {
   public MovieNotFoundError() {
      super("La película solicitada no se encuentra almacenada");
   }
}
