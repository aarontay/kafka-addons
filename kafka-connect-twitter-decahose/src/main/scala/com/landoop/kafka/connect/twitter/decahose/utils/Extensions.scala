package com.landoop.kafka.connect.twitter.decahose.utils

import java.util.concurrent.{LinkedBlockingQueue, TimeUnit}
import java.util
import com.google.common.collect.Queues

object Extensions {

  implicit class LinkedBlockingQueueExtension[T](val lbq: LinkedBlockingQueue[T]) extends AnyVal {
    def drainWithTimeoutTo(collection: util.Collection[_ >: T], maxElements: Int, timeout: Long, unit: TimeUnit): Int = {
      Queues.drain[T](lbq, collection, maxElements, timeout, unit)
    }
  }

}
