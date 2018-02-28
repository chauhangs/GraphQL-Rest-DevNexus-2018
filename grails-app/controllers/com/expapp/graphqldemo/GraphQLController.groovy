package com.expapp.graphqldemo

import grails.converters.JSON
import grails.validation.Validateable

class GraphQLController {
    def graphQLService

    def query(GraphQLRequestCmd cmd) {
        def result = graphQLService.query(cmd)
        if (result.errors) {
            response.status = 500
            render([errors: result.errors] as JSON)
        } else {
            render result.data as JSON
        }
    }
}

@Validateable
class GraphQLRequestCmd
{
    String query
    String operationName
    Map variables

    static constraints = {
        query nullable: false
        operationName nullable: true
        variables nullable: true
    }
}

