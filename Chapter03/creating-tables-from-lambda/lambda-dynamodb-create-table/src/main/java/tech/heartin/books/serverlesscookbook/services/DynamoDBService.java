package tech.heartin.books.serverlesscookbook.services;

import tech.heartin.books.serverlesscookbook.domain.Request;
import tech.heartin.books.serverlesscookbook.domain.Response;

/**
 * DynamoDB operations.
 */
public interface DynamoDBService {

    /**
     * Create table.
     * @param request table name.
     * @return Response object.
     */
    Response createTable(Request request);
}
