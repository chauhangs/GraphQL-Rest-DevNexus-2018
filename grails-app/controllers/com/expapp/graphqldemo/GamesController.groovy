package com.expapp.graphqldemo

import grails.converters.JSON

class GamesController {

    def gameService

    static allowedMethods = [queryPost: "POST", queryGet: "GET"]

    def query(GraphQLRequestCmd cmd) {
        def result = gameService.query(cmd.query)
        if (result.errors) {
            response.status = 500
            render([errors: result.errors] as JSON)
        } else {
            render result.data as JSON
        }
    }
}
