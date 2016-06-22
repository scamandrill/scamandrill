package io.github.scamandrill.models

import org.joda.time.format.{DateTimeFormat, DateTimeFormatter, DateTimeFormatterBuilder}
import org.joda.time.{DateTime, DateTimeZone}
import play.api.libs.json._

object MandrillDateFormats {
  sealed trait MandrillDateFormatter extends Writes[DateTime] with Reads[DateTime] {
    def dateFormat: DateTimeFormatter
    override def writes(o: DateTime): JsValue = JsString(o.toString(dateFormat))
    override def reads(json: JsValue): JsResult[DateTime] =
      json.validate[String]
        .map(dateFormat.parseDateTime)
        .map(_.toDateTime(DateTimeZone.getDefault))
  }

  object DATETIME_FORMAT extends MandrillDateFormatter {
    override def dateFormat = {
      val baseFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")
      val secondsFractionParser = new DateTimeFormatterBuilder()
        .appendLiteral('.')
        .appendFractionOfSecond(0, 9)
        .toParser

      new DateTimeFormatterBuilder()
        .append(
          baseFormatter.getPrinter,
          new DateTimeFormatterBuilder()
            .append(baseFormatter.getParser)
            .appendOptional(secondsFractionParser)
            .toParser
        )
        .toFormatter.withZoneUTC()
    }
  }

  object DATE_FORMAT extends MandrillDateFormatter {
    override def dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd")
  }
}
