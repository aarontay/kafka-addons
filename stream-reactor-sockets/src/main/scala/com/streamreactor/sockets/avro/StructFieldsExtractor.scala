package com.streamreactor.sockets.avro

import org.apache.avro.generic.GenericRecord
import org.apache.avro.util.Utf8
import scala.collection.JavaConversions._

case class RecordData(timestamp: Long, fields: Seq[(String, Any)])

trait FieldsValuesExtractor {
  def get(record: GenericRecord): Map[String, Any]
}

case class GenericRecordFieldsValuesExtractor(includeAllFields: Boolean,
                                              fieldsAliasMap: Map[String, String]) extends FieldsValuesExtractor {

  def get(record: GenericRecord): Map[String, Any] = {
    val fields = record.getSchema.getFields
      .filter(f => includeAllFields || fieldsAliasMap.contains(f.name.toUpperCase()))

    fields.map { field =>
      val fieldName = field.name
      val value = record.get(fieldName) match {
        case utf: Utf8 => new String(utf.getBytes)
        case other => other
      }

      fieldsAliasMap.getOrElse(fieldName.toUpperCase, fieldName) -> value
    }.toMap
  }

}
