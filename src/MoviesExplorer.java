import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class MoviesExplorer 
{
	public static void main(String[] args) throws IOException 
	{
		// 1) Load the movies
        List<Movie> movies = new ArrayList<>();
        File file = new File("F:/Java/workspace/Zad6/src/movies.txt");
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		StringBuffer stringBuffer = new StringBuffer();
		String line;
		while ((line = bufferedReader.readLine()) != null) 
		{
			stringBuffer.append(line);
			stringBuffer.append("\n");
			addMovie(movies, line);
		}
		fileReader.close();
		for(int i = 0; i < movies.size(); i++)
		{
			System.out.println(movies.get(i).toString());
		}
        
         //2) Find the number of movies released in 2003
		
		Stream<Movie> movies2003 = movies.stream()
				.filter(m -> m.getYear() == 2003);
		movies2003.forEach(m -> System.out.println(m.toString()));

        // 3) Find the first movie that contains Lord of the Rings
		
		Stream<Movie> lotrmovies = movies.stream()
				.filter(m -> m.getTitle().toString().contains("Lord of the Rings"));
		Optional<Movie> lotr = lotrmovies.findFirst();
		lotr.ifPresent(m -> System.out.println(m.toString()));
		lotr.orElseThrow(IllegalArgumentException::new);

        // 4) Display the films sorted by the release year
		
		List<Integer> sortedMovies = movies.stream()
				.map(m -> m.getYear())
				.sorted()
				.collect(Collectors.toList());

    }
	 private static void addMovie(List<Movie> movies, String movieInfo) 
	 {
	        String elements[] = movieInfo.split("/");
	        String title = parseMovieTitle(elements);
	        String releaseYear = parseMovieReleaseYear(elements);

	        Movie movie = new Movie(title, Integer.valueOf(releaseYear));

	        for (int i = 1; i < elements.length; i++) 
	        {
	            String[] name = elements[i].split(", ");
	            String lastName = name[0].trim();
	            String firstName = "";
	            if (name.length > 1) 
	            {
	                firstName = name[1].trim();
	            }

	            Actor actor = new Actor(firstName, lastName);
	            movie.addActor(actor);
	        }

	        movies.add(movie);
	 }

	 private static String parseMovieTitle(String[] elements) 
	 {
	     return elements[0].substring(0, elements[0].toString().lastIndexOf("(")).trim();
	 }

	 private static String parseMovieReleaseYear(String[] elements) 
	 {
	     String releaseYear = elements[0]
	        .substring(elements[0].toString().lastIndexOf("(") + 1,
	         elements[0].toString().lastIndexOf(")"));
	     if (releaseYear.contains(",")) 
	     {
	         releaseYear = releaseYear.substring(0, releaseYear.indexOf(","));
	     }
	     return releaseYear;
	 }
}
