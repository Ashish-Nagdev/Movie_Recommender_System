# Movie_Recommender_System Sample using Similarity Algorithms by calculating classifier & Vector factorization methods

Recommendations are usually represented as ranked lists of items. The ranking of these items are based on user’s preferences and other constraints. In computing this ranking, the system captures the user’s preferences either explicitly by ratings for products, or implicitly by user’s actions, navigation to a product page as a sign of preference.

Collaborative-filtering is a strategy used in recommender systems that depends on the suggested item from a group of people with similar tastes as the current active user and if the user accepts this suggestion, any future suggestion from these group of people is relevant. Recommender Systems mimic this behavior by applying algorithms that leverage recommendations from a community of users to an active user. 

Recommender systems use various criteria in making suggestions like the users context, need, some data can be captured by the implicit user feedback. This data is stored in the recommender systems database and used for further the recommendation engine and suggest items.

Implementation
Here, we implement an item-based recommender system using Spark on a dataset of movies to suggest movies based on similar ratings.
Dataset We choose the MovieLens Dataset, The dataset contains two files:

• movies.base: The data is randomly ordered. This is a tab separated list of user id | item id | rating | timestamp.The time stamps are unix seconds since 1/1/1970 UTC

• movies.item: This file contains entries separted by ”|”. The entries are: movie id |
movie title | release date | video release date | IMDb URL | unknown | Action |
Adventure | Animation | Children’s | Comedy | Crime | Documentary | Drama |
Fantasy | Film-Noir | Horror | Musical | Mystery | Romance | Sci-Fi | Thriller |
War | Western. The last 19 fields are the genres, a 1 indicates the movie is of that
genre, a 0 indicates it is not.

Concept: Imagine you are an owner of an online movie business and you want to generate movie recommendations based on the user preferences. You introduce a rating system (1 to 5 stars).We use these ratings as the user preferences and the user to movie mapping to recommend future movies.
Solution: Correlation
• For every pair of the movies A and B find all the people who rated A and B
• Use these ratings to form a Movie A vector and Movie B vector.
• Calculate the Correlation between the two vectors.
• Whenever someone watches a particular movie that has been rated you can recommed a new movie which has close correlation to it.

To Run application, Just run 'sbt run' command.

Sample output:

Star Wars (1977) Empire Strikes Back, The (1980) 0.7419 0.7168 0.9888 0.5306
Star Wars (1977) Return of the Jedi(1983)        0.6714 0.6539 0.9851 0.6708
Star Wars (1977) Raiders of the Lost Ark (1981)  0.5074 0.4917 0.9816 0.5607
Star Wars (1977) Meet John Doe (1941)            0.6396 0.4397 0.9840 0.0442
Star Wars (1977) Love in the Afternoon(1957)     0.9234 0.4374 0.9912 0.0181
Star Wars (1977) Man of the Year(1995)           1.0000 0.4118 0.9995 0.0141
