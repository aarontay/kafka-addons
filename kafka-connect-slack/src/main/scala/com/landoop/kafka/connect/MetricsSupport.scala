package com.landoop.kafka.connect

import java.net.InetSocketAddress
import java.util.concurrent.TimeUnit

import com.codahale.metrics.graphite.{Graphite, GraphiteReporter}
import com.codahale.metrics.{MetricFilter, MetricRegistry}
import com.landoop.kafka.connect.slack.Config

import scala.concurrent.duration._

trait MetricsSupport extends Logging {
  def registry: MetricRegistry = MetricsSupport.registry

  def timed[T](id: String, msg: String)(thunk: => T): T = {
    val timer = registry.timer(id).time
    val result = thunk
    val duration = timer.stop.nanos
    log.debug(s"$msg in ${duration.toMillis}ms")
    result
  }

  val graphite = new Graphite(new InetSocketAddress(Config.graphiteHost, Config.graphitePort))
  val graphiteReporter = GraphiteReporter.forRegistry(registry)
    .prefixedWith("kafka.connect.slack.source")
    .convertRatesTo(TimeUnit.SECONDS)
    .convertDurationsTo(TimeUnit.MILLISECONDS)
    .filter(MetricFilter.ALL)
    .build(graphite)
  graphiteReporter.start(5, TimeUnit.SECONDS)

}

object MetricsSupport {
  val registry = new MetricRegistry
}