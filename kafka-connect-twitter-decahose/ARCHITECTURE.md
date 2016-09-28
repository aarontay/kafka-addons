# Kafka Connect Framework

To understand how the Kafka Connect Framework can be utilized, as a developer of a Connect Source we need to
implement the following logic:

    while(true) {
      take each message from the http stream
      make it into Avro (optional)
      send it to an unbound linked-queue
    }

The framework will periodically call the method `poll()` to get the collected messages and push them to Kafka topic/s.

## At least once & exact-once schemantics

Imagine the scenario where our connector (app) gets from Decahose messages (1 .. 1000) and crashes
The connector task will be restarted but it would fetch messages i.e. (1001 .. 2000), resulting into messages 1..1000
for-ever being lost.

A potential solution to that would be to store the timestamp of the tweets in the `offsets` topic - and re-read discarding
anything before the stored offset.