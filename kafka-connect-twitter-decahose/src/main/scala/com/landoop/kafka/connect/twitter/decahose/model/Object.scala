package com.landoop.kafka.connect.twitter.decahose.model

case class Object(objectType: String, // i.e. "note"
                  id: String, // i.e. "object:search.twitter.com,2005:780843014214283265"
                  summary: String, // i.e. "@Cnvrswrld @KieferBobbie @Always_Trump No. Because they are blinded like everyone else by the globalist. Wake up."
                  link: String, // i.e. "http:\/\/twitter.com\/hhusted\/statuses\/780843014214283265"
                  postedTime: String // i.e. "2016-09-27T18:54:16.000Z"
                 )