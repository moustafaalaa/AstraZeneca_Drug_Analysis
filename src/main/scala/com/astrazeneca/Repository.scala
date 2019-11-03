package com.astrazeneca

import org.apache.spark.sql.Row

object Repository {

  val mamberData: Seq[Row] = Seq(
    Row("1234", "CX", java.sql.Timestamp.valueOf("2018-09-09 00:00:00")),
    Row("1234", "MA", java.sql.Timestamp.valueOf("2018-03-02 00:00:00")),
    Row("5678", "NY", java.sql.Timestamp.valueOf("2019-01-01 00:00:00")),
    Row("5678", "QA", java.sql.Timestamp.valueOf("2018-01-01 00:00:00")),
    Row("7088", "SF", java.sql.Timestamp.valueOf("2018-09-01 00:00:00"))
  )

}
