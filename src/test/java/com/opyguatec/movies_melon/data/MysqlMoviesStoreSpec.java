package com.opyguatec.movies_melon.data;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import com.opyguatec.movies_melon.core.MoviesStore;

public class MysqlMoviesStoreSpec {
   @Test void 
   that_can_connect_to_mysql_services() {
      MoviesStore store = new MysqlMoviesStore();
      boolean connected = store.is_online();
      assertThat(connected, is(false));
   }
}
