package com.astrazeneca.utils

import java.io.File

object Constants {
  val projectCurrentPath: File = new File(".").getCanonicalFile
  val appConfig: String = projectCurrentPath + "\\data\\config\\AppConfig.json"
}
