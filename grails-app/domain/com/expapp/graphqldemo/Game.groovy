package com.expapp.graphqldemo

class Game {

    static belongsTo = [Team, Venue]
    static hasMany = [ticketPrograms: TicketProgram]

    String name
    Team homeTeam
    String awayTeam = ''
    Venue venue
    Date gameStartTime
    Boolean active = true
    Date     dateCreated
    Date     lastUpdated

    static constraints = {
        awayTeam(size: 0..30)
        name(size: 1..50, blank: false)
        gameStartTime()
        venue()
        homeTeam()
    }
}
