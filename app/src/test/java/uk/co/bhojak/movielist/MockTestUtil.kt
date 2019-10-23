package uk.co.bhojak.movielist


import uk.co.bhojak.movielist.data.model.Keyword
import uk.co.bhojak.movielist.data.model.entity.Movie


class MockTestUtil {
    companion object {
        fun mockMovie(keywords: List<Keyword> = emptyList())
                = Movie(1, keywords,
                        "",
                         false,
                         "",
                            "",
                            ArrayList(),
                            123, "", "",
                        "", "", 0f,
                    0, false, 0f)

        fun mockMovieList(): List<Movie> {
            val movies = ArrayList<Movie>()
            movies.add(mockMovie())
            movies.add(mockMovie())
            movies.add(mockMovie())
            return movies
        }


        fun mockKeywordList(): List<Keyword> {
            val keywords = ArrayList<Keyword>()
            keywords.add(Keyword(100, "keyword0"))
            keywords.add(Keyword(101, "keyword1"))
            keywords.add(Keyword(102, "keyword2"))
            return keywords
        }
    }
}
