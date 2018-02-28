package com.expapp.graphqldemo

import grails.transaction.Transactional
import graphql.ExecutionInput
import graphql.GraphQL
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.SchemaGenerator
import graphql.schema.idl.SchemaParser
import graphql.schema.idl.TypeDefinitionRegistry

import javax.annotation.PostConstruct

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring

@Transactional
class GameService {
    def schema

    @PostConstruct
    def init() {
        schema = buildSchema()
    }

    private def buildSchema() {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("graphql/game-schema.graphql")
        def streamReader = new InputStreamReader(stream)
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(streamReader)

        RuntimeWiring wiring = RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query")
                .dataFetcher("games", gameFetcher)
        ).build()

        return new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring)
    }

    private def gameFetcher = { DataFetchingEnvironment env ->
        if (! env.arguments) {
            return Game.list()
        } else if (env.arguments.name) {
            return [Game.findByName(env.arguments.name as String)]
        } else if (env.arguments.homeTeam) {
            Team team = Team.findByName(env.arguments.homeTeam as String)
            return Game.findAllByHomeTeam(team)
        }
    }

    def query(String query) {
        ExecutionInput executionInput = ExecutionInput
                .newExecutionInput()
                .query(query)
                .variables([:])
                .operationName(null)
                .context()
                .build()

        return GraphQL.newGraphQL(schema).build().execute(executionInput)
    }
}
