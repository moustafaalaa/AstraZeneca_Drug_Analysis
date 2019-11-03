package com.astrazeneca.utils

import com.astrazeneca.utils.FilesHandler.readResourceFile
import org.json4s.DefaultFormats
import org.json4s.jackson.JsonMethods.parse

import scala.util.{Failure, Success, Try}

/** JsonExtractor singleton object includes all utilities
  *  for json parsing from json string or file path
  */
object JsonExtractor {

  /** Parse Json file into any generic case class T. This function try to read the file from the resources.
    * It will get IllegalArgumentException if file not parsed correctly
    *
    *
    *  @param jsonPath:String represent the
    *  @param m: Manifest is an implicit param for parsing json to T
    *  @return T object represent json case class
    */
  def getJsonParsed[T](jsonPath: String)(implicit m: Manifest[T]): T = {
    readResourceFile(jsonPath) match {
      case Success(jsonStr) => getJsonObj[T](jsonStr.mkString)
      case Failure(exc) => throw new IllegalArgumentException(exc)
    }
  }

  /** getJsonObj parse json multi-line or one line string.
    *
    *  @param jsonString : String represent json string
    *  @return T object represent json case class
    */
  def getJsonObj[T](jsonString: String)(implicit m: Manifest[T]): T = {
    extractJsonFromStr(jsonString) match {
      case Success(jsonParsed) ⇒
        jsonParsed
      case Failure(exc) ⇒
        throw new IllegalArgumentException(exc)
    }
  }

  /** extractJsonFromStr try to parse json object from string file.
    *
    *  @param jsonString : String represent json string
    *  @return Try[T] try [object T] represent json case class.
    */
  private def extractJsonFromStr[T](
    jsonString: String
  )(implicit m: Manifest[T]): Try[T] = {
    implicit val formats: DefaultFormats.type = DefaultFormats
    Try {
      parse(jsonString).extract[T]
    }
  }
}
