package com.astrazeneca

import com.astrazeneca.logic.LogicUtils
import com.astrazeneca.metadata.DrugsLabelMetadata.{DrugDetails, DrugLabelConfig}
import com.astrazeneca.metadata.JsonRepoMetadata.AppConfigRules
import com.astrazeneca.utils.Constants.{appConfig, projectCurrentPath}
import com.astrazeneca.utils.DataDownloader.getDataOnlineData
import com.astrazeneca.utils.JsonExtractor.getJsonParsed
import org.apache.logging.log4j.scala.Logging
import org.apache.spark.sql.{Dataset, SparkSession}

/** ETLPipelineLogic case class handle the ETL pipeline logic.
  *
  * @param spark :SparkSession object for spark functionality.
  */
case class ETLPipelineLogic(spark: SparkSession) extends Logging {

  def prepareData(): Unit = {
    logger.info("Preparing the data")

    logger.info("Checking job parameters")
    val appConfigRules: AppConfigRules = getJsonParsed[AppConfigRules](appConfig)
    logger.info("%s".format(appConfigRules))

    // check the readFromLocal flag
    logger.info("check read from local flag " + appConfigRules.readFromLocal)
    if (!appConfigRules.readFromLocal) getDataOnlineData(appConfigRules)

    val logic = LogicUtils(spark)

    //else no read it from local config folder
    logger.info("parse drug label details")
    val drugsLabelsData: Dataset[DrugLabelConfig] = logic.readDrugsLabelJson(
      projectCurrentPath + appConfigRules.fdaUnZippedPath + appConfigRules.fdaDrugLabelDataExtension)
    logger.debug("%s".format(drugsLabelsData.show(10, truncate = false)))

    logger.info("map and transform drug label details")
    val drugSplIngredients: Dataset[DrugDetails] = logic.parseDrugCol(drugsLabelsData, appConfigRules)
    logger.debug("%s".format(drugSplIngredients.show(10, truncate = false)))

    logger.info("average number of ingredients (spl_product_data_elements) per year")
    val avgDrugsIngredientsPerYearDS = logic.getAvgDrugsIngredientsPerYear(drugSplIngredients)
    logger.info("%s".format(avgDrugsIngredientsPerYearDS.show(10, truncate = false)))

    logger.info("average number of ingredients per year and per delivery route for all manufacturers")
    val avgDrugsIngredientsPerYearPerDeliveryDS = logic.getAvgDrugsIngredientsPerYearPerDelivery(drugSplIngredients)
    logger.info("%s".format(avgDrugsIngredientsPerYearPerDeliveryDS.show(10, truncate = false)))
  }
}
