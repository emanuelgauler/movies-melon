function movie_card_from( data ) {
   const { movie, movie_url } = data;
   const { poster, release, title } = movie;
   const card_image        = document.createElement('figure');
   const caption           = document.createElement('figcaption');
   const photo             = document.createElement('img');
   const release_date_text = document.createElement('p');
   const movie_link        = document.createElement('a');
   const title_text        = document.createElement('h2');
   
   photo.src                     = poster;
   photo.alt                     = `poster: ${title.replace(" ", "-")}`
   title_text.textContent        = title;
   release_date_text.textContent = release;
   caption.appendChild( movie_link );
   caption.appendChild(release_date_text);
   movie_link.appendChild(title_text);
   card_image.appendChild(photo);
   card_image.appendChild(caption);
   card_image.classList.add("movie-card");
   
   
   return card_image;
}


function load_movies_from( movies ) {
   const container = document.querySelector("#movies_container");
   while( container.firstChild ) {
      container.removeChild( container.firstChild );
   }
   movies.forEach(movie => {
      const movie_card = movie_card_from(movie)
      container.appendChild(movie_card);

   });
}


document.addEventListener('DOMContentLoaded', async function() {
   const response = await fetch("api/movies");
   const data     = await response.json();
   const { movies } = data;
   console.log(movies);
   load_movies_from( movies )
   /*
  alert("DOM Content Loaded");
   */
})