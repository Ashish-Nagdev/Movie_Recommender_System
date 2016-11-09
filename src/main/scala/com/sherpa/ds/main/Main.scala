package com.sherpa.ds.main

import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import com.sherpa.ds.logic.SimilarityRecommendation

/**
  * Created by Ashish Nagdev on 11/7/16.
  */
object Main extends App {

  /**
    * Spark programs require a SparkContext to be initialized
    */
  val conf = new SparkConf()
    .setAppName("Similar_Movie_Recommendation")
    .setMaster("local")
  val sc = new SparkContext(conf)
  new SimilarityRecommendation(sc)

}
