package com.astrazeneca.utils

import java.net.URL

import com.astrazeneca.metadata.JsonRepoMetadata.{AppConfigRules, JsonConfig, PartitionsMetadata}
import com.astrazeneca.utils.Constants._
import com.astrazeneca.utils.FilesHandler._
import com.astrazeneca.utils.JsonExtractor._
import org.apache.logging.log4j.scala.Logging

import scala.language.postfixOps
import scala.sys.process._

/** Factory for DataDownloader instances. */
object DataDownloader extends Logging {

  /** Download online data from online repository, parse the json config, and unzip the downloaded files
    *
    * @param appConfig :AppConfigRules represents the application json config file
    */
  def getDataOnlineData(appConfig: AppConfigRules): Unit = {

    //Download Json file
    downloadOnlineData(
      appConfig.fdaConfigURL,
      projectCurrentPath + appConfig.fdaConfigFilePath
    )

    //parse the downloaded Json file
    val jsonConfig: JsonConfig = getJsonParsed[JsonConfig](
      projectCurrentPath + appConfig.fdaConfigFilePath
    )

    //download drugs data online
    val drugFilePartitionsDetails: List[PartitionsMetadata] =
      jsonConfig.results.drug.label.partitions
    drugFilePartitionsDetails.foreach(f => {
      if (!(f.file == null)) {
        val fileName = f.file.split("/").last
        val targetZippedFolder = projectCurrentPath + appConfig.fdaZippedPath + fileName
        downloadOnlineData(f.file, targetZippedFolder)
      }
    })

    //unzip
    getListOfFiles(
      projectCurrentPath + appConfig.fdaZippedPath,
      appConfig.zipFileExtension,
      appConfig.filesLimit
    ).foreach(
      file =>
        unzip(
          file.getCanonicalPath,
          projectCurrentPath + appConfig.fdaUnZippedPath
      )
    )
  }

  /** Download data from online link
    *
    * @param fileURL         :String represents the file url the  their name
    * @param destinationPath :String destination folder and file path
    */
  def downloadOnlineData(fileURL: String, destinationPath: String): Unit = {
    if (checkFileExists(destinationPath)) {
      logger.warn("file " + destinationPath + " already exists")
    } else {
      new URL(fileURL) #> new java.io.File(destinationPath) !!;
      logger.info("file " + destinationPath + " downloaded successfully")
    }
  }

}
