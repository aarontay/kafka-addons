package com.streamreactor.sockets.flows

import akka.stream.ThrottleMode.Shaping
import akka.stream.scaladsl.Source
import com.typesafe.scalalogging.StrictLogging

import scala.concurrent.duration._

object SourceExtension extends StrictLogging {

  implicit class SourceRich[+Out, +Mat](val source: Source[Out, Mat]) extends AnyVal {
    def withSampling(count: Int, rate: Int): Source[Out, Mat] = {
      source
        .conflateWithSeed(Vector(_)) {
          case (buff, m) => if (buff.size < count) buff :+ m else buff
        }
        .throttle(1, rate.millis, 1, Shaping)
        .mapConcat(identity)
    }
  }

}
