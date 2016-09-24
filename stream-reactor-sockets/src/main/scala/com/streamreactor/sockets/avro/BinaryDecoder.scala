package com.streamreactor.sockets.avro

import kafka.serializer.Decoder

case object BinaryDecoder extends Decoder[AnyRef] {
  override def fromBytes(bytes: Array[Byte]): AnyRef = bytes
}
