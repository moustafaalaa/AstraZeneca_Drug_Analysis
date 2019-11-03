package com.astrazeneca.metadata

object DrugsLabelMetadata {

  case class DrugLabelConfig(meta: MetaDetails, results: List[ResultsDetails])

  case class MetaDetails(disclaimer: String, terms: String, license: String, last_updated: String, results: FileSummary)

  case class FileSummary(skip: Long, total: Long, limit: Long)

  case class ResultsDetails(
    package_label_principal_display_panel: List[String],
    active_ingredient: List[String],
    warnings: List[String],
    inactive_ingredient: List[String],
    effective_time: String,
    openfda: Option[OpenFDA],
    keep_out_of_reach_of_children: List[String],
    spl_product_data_elements: List[String],
    set_id: String,
    dosage_and_administration: List[String],
    version: String,
    purpose: List[String],
    questions: List[String],
    id: String,
    indications_and_usage: List[String])

  case class DrugDetails(year: String, splProductElements: List[(String, Int)], route: String)

  case class OpenFDA(
    manufacturer_name: List[String],
    unii: List[String],
    product_type: List[String],
    rxcui: List[String],
    spl_set_id: List[String],
    route: List[String],
    generic_name: List[String],
    upc: List[String],
    brand_name: List[String],
    product_ndc: List[String],
    substance_name: List[String],
    spl_id: List[String],
    application_number: List[String],
    is_original_packager: List[String],
    package_ndc: List[String])

  case class DrugsIngredientsPerYear(year: String, drugsName: String, avgNumberOfIngredients: Double)

  case class DrugsIngredientsPerYearDelivery(year: String, route: String, avgNumberOfIngredients: Double)

  case class InputData(
    effective_time: List[String],
    spl_product_data_elements: List[String],
    route: Option[List[String]])

}
