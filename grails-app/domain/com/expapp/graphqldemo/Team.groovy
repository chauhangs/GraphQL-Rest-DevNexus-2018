package com.expapp.graphqldemo

class Team {

    String name
    String shortName
    String city
    Date   dateCreated
    Date   lastUpdated

    static constraints = {
        name(size: 1..45, blank: false)
        shortName(size: 1..8, blank: false)
        city(size: 1..30)
    }
}
