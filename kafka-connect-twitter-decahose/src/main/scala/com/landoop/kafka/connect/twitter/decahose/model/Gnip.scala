package com.landoop.kafka.connect.twitter.decahose.model

case class Gnip(urls: List[GnipUrl],
                profileLocations: List[GnipProfileLocation])

case class GnipUrl(url: String,
                   expanded_url: String,
                   expanded_status: Int,
                   expanded_url_title: String,
                   expanded_url_description: String)

case class GnipProfileLocation(address: GnipAddress,
                               displayName: String,
                               geo: GnipGeo,
                               objectType: String)

case class GnipAddress(country: String, countryCode: String)

case class GnipGeo(coordinates: List[Double],
                   `type`: String)
