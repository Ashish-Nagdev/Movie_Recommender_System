package com.sherpa.ds.logic

import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD
import scala.collection.Map

/**
  * Created by Ashish Nagdev on 11/7/16.
  */
object TopKRecommendations {

  def samplingRecommendation(similarities: RDD[((Int, Int), (Double, Double, Double, Double))], movieNames:Map[Int,String]): Unit = {
    val sample = similarities.filter(m => {
      val movies = m._1
      (movieNames(movies._1).contains("Star Wars (1977)"))
    })

    // collect results, excluding NaNs if applicable
    val result = sample.map(v => {
      val m1 = v._1._1
      val m2 = v._1._2
      val corr = v._2._1
      val rcorr = v._2._2
      val cos = v._2._3
      val j = v._2._4
      (movieNames(m1), movieNames(m2), corr, rcorr, cos, j)
    }).collect().filter(e => !(e._4 equals Double.NaN)) // test for NaNs must use equals rather than ==
      .sortBy(elem => elem._4).take(10)

    // print the top 10 out
    result.foreach(r => println(r._1 + " | " + r._2 + " | " + r._3.formatted("%2.4f") + " | " + r._4.formatted("%2.4f")
      + " | " + r._5.formatted("%2.4f") + " | " + r._6.formatted("%2.4f")))
  }
}
