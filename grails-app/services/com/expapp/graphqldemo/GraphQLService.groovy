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
class GraphQLService {
    def schema

    @PostConstruct
    def init() {
        schema = buildSchema()
    }

    private def buildSchema() {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("graphql/schema.graphql")
        def streamReader = new InputStreamReader(stream)
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(streamReader)

        RuntimeWiring wiring = RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query")
                .dataFetcher("games", gameFetcher)
                .dataFetcher("venues", venueFetcher)
                .dataFetcher("teams", teamFetcher)
        ).build()

        return new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring)
    }

    private def gameFetcher = { DataFetchingEnvironment env ->
        //return Game.list()
        env.context << [visited: "games"]
        if (! env.arguments) {
            return Game.list()
        } else if (env.arguments.name) {
            return [Game.findByName(env.arguments.name as String)]
        } else if (env.arguments.homeTeam) {
            Team team = Team.findByName(env.arguments.homeTeam as String)
            return Game.findAllByHomeTeam(team)
        }
    }

    private def venueFetcher = { DataFetchingEnvironment env ->
        println env.context
        return Venue.list()
    }

    private def teamFetcher = { DataFetchingEnvironment env ->
        return Team.list()
    }

    def query(GraphQLRequestCmd cmd) {
        ExecutionInput executionInput = ExecutionInput
                .newExecutionInput()
                .query(cmd.query)
                .variables(cmd.variables ?: [:])
                .operationName(cmd.operationName)
                .context([:])
                .build()

        return GraphQL.newGraphQL(schema).build().execute(executionInput)
    }

    private DataFetcher mutationDataFetcher() {
        return new DataFetcher() {
            @Override
            public Object get(DataFetchingEnvironment environment) {
                // mutate an object and return it
                return null
             
            }
        };
    }
}
