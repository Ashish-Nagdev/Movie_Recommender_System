# Movie_Recommender_System using classifier & Vector factorization

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
