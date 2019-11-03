package com.astrazeneca

import org.apache.log4j.{Level, Logger}
import org.apache.logging.log4j.scala.Logging
import org.apache.spark.sql.SparkSession

object MainEntry extends App with Logging {

  logger.info("Application Started ..")

  logger.info("Initialize Spark Session ..")
  val spark: SparkSession = SparkSession
    .builder()
    .master("local[*]")
    //It could be replaced from Job submission or config file UI
    .config("spark.driver.memory", "7g")
    .config("spark.executor.memory", "2g")
    .config("spark.dynamicAllocation.enabled", "true")
    .config("spark.executor.cores", 1)
    .config("spark.executor.instances", 4)
    .config("spark.dynamicAllocation.minExecutors", "1")
    .config("spark.dynamicAllocation.maxExecutors", "4")
    .appName("AstraZeneca_Drug_Analysis")
    .getOrCreate()

  logger.debug("Turn off spark logging ..")
  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)

  logger.info("Start Transformation Pipeline ..")
  ETLPipelineLogic(spark).prepareData()

}
