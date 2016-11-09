package com.sherpa.ds.main

import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import com.sherpa.ds.logic.SimilarityRecommendation
import org.apache.spark.rdd.RDD

import scala.collection.Map

/**
  * Created by Ashish Nagdev on 11/7/16.
  */
object Main extends App {

  val TRAIN_FILENAME = "movies.base"
  val MOVIES_FILENAME = "movies.item"

  /**
    * Spark programs require a SparkContext to be initialized
    */
  val conf = new SparkConf()
    .setAppName("Similar_Movie_Recommendation")
    .setMaster("local")
  val sc = new SparkContext(conf)

  try {

    // get movie names keyed on id
    val movies = sc.textFile(MOVIES_FILENAME)
      .map(line => {
        val fields = line.split("\\|")
        (fields(0).toInt, fields(1))
      })
    val movieNames: Map[Int, String] = movies.collectAsMap() // for local use to map id <-> movie name for pretty-printing

    // extract (userid, movieid, rating) from ratings data
    val ratings: RDD[(Int, Int, Int)] = sc.textFile(TRAIN_FILENAME)
      .map(line => {
        val fields = line.split("\t")
        (fields(0).toInt, fields(1).toInt, fields(2).toInt)
      })

    new SimilarityRecommendation(ratings, movieNames)
  } catch {
    case e: Exception => Seq[String]()
      println(e)
      sc.stop
      sys.exit(1)
  }

}
