package net.sigusr.mqtt.impl.protocol

import cats.effect.IO
import cats.effect.testing.specs2.CatsEffect
import org.specs2.mutable._

// http://stanford-clark.com/MQTT/#msg-id
class IdGeneratorSpec extends Specification with CatsEffect {

  "An id generator" should {

    "Provide ids > 0" in {
      IdGenerator[IO]().flatMap(gen =>
        gen.next.replicateA(100*1000)
          .map(ids => ids.forall(_ > 0) must beTrue)
      )
    }

    "Provide ids <= 65535" in {
      IdGenerator[IO]().flatMap(gen =>
        gen.next.replicateA(100*1000)
          .map(ids => ids.forall(_ <= 65535) must beTrue)
      )
    }

    "Provide 65535 different ids" in {
      IdGenerator[IO]().flatMap(gen =>
        gen.next.replicateA(65535)
          .map(ids => ids.distinct.size must_== ids.size)
      )
    }
  }
}
