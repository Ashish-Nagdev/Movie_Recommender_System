package com.sherpa.ds.logic

import org.apache.spark.SparkContext._
import org.apache.spark.SparkContext
import com.sherpa.ds.algorithms.VectorCorelations._
import com.sherpa.ds.algorithms.VectorSimilarities._
import com.sherpa.ds.logic.SimilarityRecommendation._
import com.sherpa.ds.logic.TopKRecommendations._
import org.apache.spark.rdd.RDD

import scala.collection.Map

/**
  * Created by Ashish Nagdev on 11/7/16.
  */
class SimilarityRecommendation(ratings: RDD[(Int, Int, Int)], movieNames: Map[Int, String]) {

  // get num raters per movie, keyed on movie id
  val numRatersPerMovie = ratings
    .groupBy(tup => tup._2)
    .map(grouped => (grouped._1, grouped._2.size))

  // join ratings with num raters on movie id
  val ratingsWithSize = ratings
    .groupBy(tup => tup._2)
    .join(numRatersPerMovie)
    .flatMap(joined => {
      joined._2._1.map(f => (f._1, f._2, f._3, joined._2._2))
    })

  // ratingsWithSize now contains the following fields: (user, movie, rating, numRaters).

  // dummy copy of ratings for self join
  val ratings2 = ratingsWithSize.keyBy(tup => tup._1)

  // join on userid and filter movie pairs such that we don't double-count and exclude self-pairs
  val ratingPairs =
  ratingsWithSize
    .keyBy(tup => tup._1)
    .join(ratings2)
    .filter(f => f._2._1._2 < f._2._2._2)

  // compute raw inputs to similarity metrics for each movie pair
  val vectorCalcs =
  ratingPairs
    .map(data => {
      val key = (data._2._1._2, data._2._2._2)
      val stats =
        (data._2._1._3 * data._2._2._3, // rating 1 * rating 2
          data._2._1._3, // rating movie 1
          data._2._2._3, // rating movie 2
          math.pow(data._2._1._3, 2), // square of rating movie 1
          math.pow(data._2._2._3, 2), // square of rating movie 2
          data._2._1._4, // number of raters movie 1
          data._2._2._4) // number of raters movie 2
      (key, stats)
    })
    .groupByKey()
    .map(data => {
      val key = data._1
      val vals = data._2
      val size = vals.size
      val dotProduct = vals.map(f => f._1).sum
      val ratingSum = vals.map(f => f._2).sum
      val rating2Sum = vals.map(f => f._3).sum
      val ratingSq = vals.map(f => f._4).sum
      val rating2Sq = vals.map(f => f._5).sum
      val numRaters = vals.map(f => f._6).max
      val numRaters2 = vals.map(f => f._7).max
      (key, (size, dotProduct, ratingSum, rating2Sum, ratingSq, rating2Sq, numRaters, numRaters2))
    })

  // compute similarity metrics for each movie pair
  val similarities =
  vectorCalcs
    .map(fields => {
      val key = fields._1
      val (size, dotProduct, ratingSum, rating2Sum, ratingNormSq, rating2NormSq, numRaters, numRaters2) = fields._2
      val corr = correlation(size, dotProduct, ratingSum, rating2Sum, ratingNormSq, rating2NormSq)
      val regCorr = regularizedCorrelation(size, dotProduct, ratingSum, rating2Sum,
        ratingNormSq, rating2NormSq, PRIOR_COUNT, PRIOR_CORRELATION)
      val cosSim = cosineSimilarity(dotProduct, scala.math.sqrt(ratingNormSq), scala.math.sqrt(rating2NormSq))
      val jaccard = jaccardSimilarity(size, numRaters, numRaters2)

      (key, (corr, regCorr, cosSim, jaccard))
    })

  samplingRecommendation(similarities, movieNames)

}

object SimilarityRecommendation {

  val PRIOR_COUNT = 10
  val PRIOR_CORRELATION = 0

}
