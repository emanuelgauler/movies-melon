package com.opyguatec.movies_melon.core;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

public class CopyingMovieSpec {

   @Test void 
   that_movie_can_be_copied() throws ParseException {
      DateFormat release_date_parse = new SimpleDateFormat("dd/MM/yyyy");
      Date release_date = release_date_parse.parse("26/05/2024");
      Movie expected = Movie.with("title", "synopsys", release_date, "some_path", "some director", List.of());

      Movie actual = expected;

      assertThat(actual.getTitle(), is(equalTo(expected.getTitle())));
      assertThat(actual.its_id(), is(equalTo(expected.its_id())));
      assertThat(actual.getPoster(), is(equalTo(expected.getPoster())));
   }
}
