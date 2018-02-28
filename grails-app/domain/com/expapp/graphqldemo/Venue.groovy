package com.expapp.graphqldemo

class Venue {

    String name
    Team team
    Float  latitude
    Float  longitude
    Integer avgAttendance = 10000
    Integer maxAttendance = 10000
    Date   dateCreated
    Date   lastUpdated

    static constraints = {
        name(size: 1..30, blank: false)
        team(unique: true)
        latitude(min: -90F, max: 90F, scale: 4)
        longitude(min: -180F, max: 180F, scale: 4)
        avgAttendance(min: 0, max: 1000000)
        maxAttendance(min: 0, max: 1000000)
    }
}
