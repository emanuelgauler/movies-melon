package com.opyguatec.movies_melon.api.utils;

public class ErrorResponseModel {
   private String message;
   private int code;
   public int getCode() {
      return code;
   }
   public String getMessage() {
      return message;
   }
   public static ErrorResponseModel with( Exception e ) {
      ErrorResponseModel model = new ErrorResponseModel();
      model.message = e.getMessage();
      model.code = 1;
      return model;
   }
}
