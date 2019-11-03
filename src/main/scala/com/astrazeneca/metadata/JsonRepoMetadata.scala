package com.astrazeneca.metadata

object JsonRepoMetadata {

  /** A AppConfigRules case class represent the options for the application running
    *
    * @param fdaConfigURL: String Url for the fda meta data
    * @param fdaConfigFilePath: String destination of file path for the fda meta data
    * @param fdaZippedPath: String destination of zipped folder path
    * @param zipFileExtension: String zip file extension
    * @param fdaUnZippedPath: String destination of unzipped folder path
    * @param fdaDrugLabelDataExtension: String extension of the source file
    * @param readFromLocal: Boolean option to read from local if the data is available
    * @param filesLimit: Int option to run based on limited number of files
    * @param startingYear: String starting year for analysis.
    * @param unKnownRouteName: String in string to replace the route name in case the route is null
    */

  case class AppConfigRules(
    fdaConfigURL: String,
    fdaConfigFilePath: String,
    fdaZippedPath: String,
    zipFileExtension: String,
    fdaUnZippedPath: String,
    fdaDrugLabelDataExtension: String,
    readFromLocal: Boolean,
    filesLimit: Int,
    startingYear: String,
    unKnownRouteName: String)

  case class JsonConfig(meta: MetaDetails, results: ResultsDetails)

  case class MetaDetails(disclaimer: String, terms: String, license: String, last_updated: String)

  case class ResultsDetails(
    device: DeviceMetadata,
    food: FoodMetadata,
    other: OtherDetails,
    drug: DrugMetadata,
    animalandveterinary: AnimalAndVeterinaryDetails)

  case class DeviceMetadata(
    `510k`: FileExtractionDetails, //ugly name but to match the meta data
    classification: FileExtractionDetails,
    enforcement: FileExtractionDetails,
    recall: FileExtractionDetails,
    registrationlisting: FileExtractionDetails,
    pma: FileExtractionDetails,
    udi: FileExtractionDetails,
    event: FileExtractionDetails
  )

  case class FoodMetadata(enforcement: FileExtractionDetails, event: FileExtractionDetails)

  case class OtherDetails(nsde: FileExtractionDetails)

  case class DrugMetadata(
    enforcement: FileExtractionDetails,
    ndc: FileExtractionDetails,
    event: FileExtractionDetails,
    label: FileExtractionDetails)

  case class AnimalAndVeterinaryDetails(event: FileExtractionDetails)

  case class FileExtractionDetails(total_records: Int, export_date: String, partitions: List[PartitionsMetadata])

  case class PartitionsMetadata(size_mb: String, records: Int, display_name: String, file: String)

}
