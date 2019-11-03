package com.astrazeneca.utils

import java.io.{File, FileInputStream, FileOutputStream, InputStream}
import java.nio.file.{Files, Paths}
import java.util.zip.ZipInputStream

import org.apache.logging.log4j.scala.Logging

import scala.io.Source
import scala.util.Try

object FilesHandler extends Logging {

  /** Check if file exists
    *
    * @param filePath :String file name
    * @return Boolean `true` if file exists `false` if not exists
    */
  def checkFileExists(filePath: String): Boolean = {
    Files.exists(Paths.get(filePath))
  }

  /** read file content from path
    *
    * @param filePath : String
    * @return `Iterator[String]` Iterator of lines represent the file internal content lines
    */
  def readFileProjectResources(filePath: String): Iterator[String] = {
    val fileStream: InputStream = getClass.getResourceAsStream(filePath)
    Source.fromInputStream(fileStream).getLines
  }

  def getPath(path: String, format: String): String = {
    Try(getClass.getResource(path).getPath)
      .getOrElse(
        throw new IllegalArgumentException(
          "Couldn't find path=%s".format(path)
        )
      )
  }
  /** read file content from path
    *
    * @param filePath : String
    * @return `Try[Iterator[String]]` Iterator of lines if it empty it needs to be applied in matching
    */
  def readResourceFile(filePath: String): Try[Iterator[String]] =
    Try {
      val fileStream: InputStream = new FileInputStream(filePath)
      // getClass.getResourceAsStream(filePath)
      Source.fromInputStream(fileStream).getLines
    }

  /** getListOfFiles given input and extension pattern filter
    *
    * @param dir        : String Input path
    * @param extension  : String extension for filter
    * @param filesLimit :Int number of files to get from the list
    * @return List[File] list of input path dir filtered by the extension
    */
  def getListOfFiles(dir: String, extension: String, filesLimit: Int): List[File] = {
    val allFilesList = new File(dir).listFiles
      .filter(_.isFile)
      .toList
      .filter(_.getName.endsWith(extension))
    if (filesLimit != -1) {
      allFilesList.take(filesLimit)
    } else {
      allFilesList
    }
  }

  /** unzip the *.zip file and write output to destination path
    *
    * @param inputZipFilePath : String
    * @param destination      : String
    */
  def unzip(inputZipFilePath: String, destination: String): Unit = {
    //val outPutFolder: File = new File(inputZipFilePath)
    val inputFileStream = new FileInputStream(inputZipFilePath)
    val zippedInputStream = new ZipInputStream(inputFileStream)

    Stream
      .continually(zippedInputStream.getNextEntry)
      .takeWhile(_ != null)
      .foreach { file =>
        if (!file.isDirectory) {
          val outPath = Paths.get(destination).resolve(file.getName)
          val outPathParent = outPath.getParent
          if (!outPathParent.toFile.exists()) {
            outPathParent.toFile.mkdirs()
          }

          val outFile = outPath.toFile
          val out = new FileOutputStream(outFile)
          val buffer = new Array[Byte](4096)
          Stream
            .continually(zippedInputStream.read(buffer))
            .takeWhile(_ != -1)
            .foreach(out.write(buffer, 0, _))
        }
      }
  }

}
