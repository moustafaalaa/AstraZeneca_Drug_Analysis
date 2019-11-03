package com.astrazeneca.logic

import com.astrazeneca.metadata.DrugsLabelMetadata.{
  DrugDetails,
  DrugLabelConfig,
  DrugsIngredientsPerYear,
  DrugsIngredientsPerYearDelivery
}
import com.astrazeneca.metadata.JsonRepoMetadata.AppConfigRules
import org.apache.spark.sql.{Dataset, SparkSession}

case class LogicUtils(spark: SparkSession) {

  import spark.implicits._

  /** readDrugsLabelJson read input json file using spark json lib and cast it into DrugLabelConfig case class
    *
    * @param drugsLabelDataPath: String input path for the json file(s)
    * @return Dataset[DrugLabelConfig] parsed dataset for the input file
    */
  def readDrugsLabelJson(drugsLabelDataPath: String): Dataset[DrugLabelConfig] = {
    // parse the unzipped data
    spark.read
      .option("mode", "PERMISSIVE")
      .option("multiLine", true)
      .json(drugsLabelDataPath)
      .as[DrugLabelConfig]
  }

  /** parseDrugCol parse the input json for each drug apply mapping and filteration based on the required column
    * It filter the year and handle the null fields existence
    *
    * @param drugsLabelsData: Dataset[DrugLabelConfig] input parsed json object as Dataset[Case class]
    * @return Dataset[DrugDetails] parsed dataset for year, splProductElements, route.
    */
  def parseDrugCol(drugsLabelsData: Dataset[DrugLabelConfig], appConfigRules: AppConfigRules): Dataset[DrugDetails] = {
    drugsLabelsData
      .map(_.results)
      .flatMap(x => x)
      .filter(x =>
        (x.effective_time != null && x.effective_time.length >= 4 && x.effective_time
          .substring(0, 4) >= appConfigRules.startingYear))
      .map(x => {
        val transformedElements = x.spl_product_data_elements match {
          case x if (x == null || x.isEmpty) => List("")
          case h :: t => h :: t
        }
        val drugsList: List[String] = transformedElements
          .filter(x => !x.isEmpty && x != null)
          .flatMap(x => x.split(","))
        val drugsIngredientsList: List[(String, Int)] = drugsList.map(d => (d, d.split(" ").length))

        val transformedRoute = x.openfda.map(_.route) match {
          case Some(route :: rest) => route
          case _ => appConfigRules.unKnownRouteName
        }
        DrugDetails(x.effective_time.substring(0, 4), drugsIngredientsList, transformedRoute)
      })
  }

  /** getAvgDrugsIngredientsPerYear  get the average number of ingredients (spl_product_data_elements) per year.
    *
    * @param drugSplIngredients: Dataset[DrugDetails] contained the required column for filtration
    * @return Dataset[DrugsIngredientsPerYear] Dataset includes the average drug ingredients per year
    */
  def getAvgDrugsIngredientsPerYear(drugSplIngredients: Dataset[DrugDetails]): Dataset[DrugsIngredientsPerYear] = {
    //Analysis A enhanced
    drugSplIngredients
      .groupByKey(_.year)
      .reduceGroups(
        (x, y) => x.copy(splProductElements = x.splProductElements ++ y.splProductElements)
      )
      .map {
        case (_, row) ⇒ {
          val totalNumberOfIngredients: Int =
            row.splProductElements.map(_._2).sum
          val numberOfDrugs: Int = row.splProductElements.length
          val avgIngredientsPerDrug: Double = totalNumberOfIngredients / numberOfDrugs
          val allDrugsNames: String = row.splProductElements.map(_._1).mkString(",")
          DrugsIngredientsPerYear(
            row.year,
            allDrugsNames,
            avgIngredientsPerDrug
          )
        }
      }

    //Analysis A
    /* drugSplIngredients.groupByKey(_.year)
             .mapGroups((year, iter) => {
               val iterList: List[DrugMetadata] = iter.toList
               val drugsIngredientsList: List[(String, Int)] = iterList.flatten(x => x.splProductElements)
               val totalNumberOfIngredients: Int =drugsIngredientsList.map(_._2).sum
               val numberOfDrugs: Int =drugsIngredientsList.length
               val avgNumberOfIngredientsPerDrug: Double = totalNumberOfIngredients/numberOfDrugs
               val allDrugsNames: String = drugsIngredientsList.map(_._1).mkString(",")
               DrugsIngredientsPerYear(year,allDrugsNames,avgNumberOfIngredientsPerDrug)
             }).show(truncate = false)
   */

  }

  /** getAvgDrugsIngredientsPerYearPerDelivery calculate the average number of ingredients per year and per delivery route for all manufacturers
    *
    * @param drugSplIngredients: Dataset[DrugDetails] contained the required column for filtration
    * @return Dataset[DrugsIngredientsPerYear] Dataset includes the average drug ingredients per year per delivery method
    */
  def getAvgDrugsIngredientsPerYearPerDelivery(
    drugSplIngredients: Dataset[DrugDetails]): Dataset[DrugsIngredientsPerYearDelivery] = {
    //Analysis B enhanced
    drugSplIngredients
      .groupByKey(p => (p.year, p.route))
      .reduceGroups((x, y) => x.copy(splProductElements = x.splProductElements ++ y.splProductElements))
      .map {
        case (_, row) ⇒ {
          val totalNumberOfIngredients: Int = row.splProductElements.map(_._2).sum
          val numberOfDrugs: Int = row.splProductElements.length
          val avgNumberOfIngredientsPerDrug: Double = totalNumberOfIngredients / numberOfDrugs
          DrugsIngredientsPerYearDelivery(row.year, row.route, avgNumberOfIngredientsPerDrug)
        }
      }
  }
}
