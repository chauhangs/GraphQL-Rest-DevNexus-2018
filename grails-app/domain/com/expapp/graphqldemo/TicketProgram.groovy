package com.expapp.graphqldemo

class TicketProgram {

    String name
    Integer ticketsAvailable
    Integer ticketsSold

    static constraints = {
        name(size: 1..30, blank: false)
        ticketsAvailable(min: 0, max: 10000)
        ticketsSold(min: 0, max: 10000)
    }
}
