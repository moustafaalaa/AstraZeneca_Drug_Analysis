import com.astrazeneca.logic.LogicUtils
import com.astrazeneca.metadata.DrugsLabelMetadata.{
  DrugDetails,
  DrugLabelConfig,
  DrugsIngredientsPerYear,
  DrugsIngredientsPerYearDelivery
}
import com.astrazeneca.utils.FilesHandler.readFileProjectResources
import com.astrazeneca.utils.JsonExtractor
import com.holdenkarau.spark.testing._
import org.apache.spark.sql.Dataset
import org.scalatest.{FunSuite, Matchers}

class DrugDetailsExtractionTest extends FunSuite with Matchers with DatasetSuiteBase {
  var logic: LogicUtils = _

  test("Testing Drugs Label json parsing") {

    val actual: DrugLabelConfig =
      JsonExtractor.getJsonObj[DrugLabelConfig](readFileProjectResources("/data/test.json").mkString)
    val expected: DrugLabelConfig = JsonParsedObjects.drugLabelConfig
    assert(actual, expected) // equal
  }

  test("Testing Drugs Label mapping logic") {
    val spark = SparkSessionProvider._sparkSession
    import spark.implicits._
    logic = LogicUtils(spark)

    val actual: Dataset[DrugDetails] =
      logic.parseDrugCol(Seq(JsonParsedObjects.drugLabelConfig).toDS(), TestingUtils.appConfigRules)
    val expected: Dataset[DrugDetails] = TestingUtils.expectedDrugDetails.toDS()
    //assert(actual.collect(), expected.collect) // equal

    assertDatasetEquals(actual, expected)
  }

  test("Testing getAvgDrugsIngredientsPerYear logic") {
    val spark = SparkSessionProvider._sparkSession
    import spark.implicits._
    logic = LogicUtils(spark)
    val actual: Dataset[DrugsIngredientsPerYear] =
      logic.getAvgDrugsIngredientsPerYear(TestingUtils.expectedDrugDetails.toDS())

    val expected: Dataset[DrugsIngredientsPerYear] = TestingUtils.averageIngredientsPerYear.toDS()
    //assert(actual.collect(), expected.collect) // equal
    assertDatasetEquals(actual, expected)
  }

  test("Testing getAvgDrugsIngredientsPerYearPerDelivery logic") {
    val spark = SparkSessionProvider._sparkSession
    import spark.implicits._
    logic = LogicUtils(spark)
    val actual: Dataset[DrugsIngredientsPerYearDelivery] =
      logic.getAvgDrugsIngredientsPerYearPerDelivery(TestingUtils.expectedDrugDetails.toDS())
    val expected: Dataset[DrugsIngredientsPerYearDelivery] = TestingUtils.averageIngredientsPerYearPerDeliver.toDS()

    assertDatasetEquals(actual, expected)
  }

}
