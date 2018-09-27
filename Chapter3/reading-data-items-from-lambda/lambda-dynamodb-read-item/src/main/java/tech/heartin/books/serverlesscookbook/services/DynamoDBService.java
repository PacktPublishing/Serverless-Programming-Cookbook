package tech.heartin.books.serverlesscookbook.services;

import tech.heartin.books.serverlesscookbook.domain.Request;
import tech.heartin.books.serverlesscookbook.domain.Response;

/**
 * DynamoDB operations.
 */
public interface DynamoDBService {

    /**
     * Put Item.
     * @param request table name.
     * @return Response object.
     */
    Response getItem(Request request);

    /**
     * Query Item.
     * @param request table name.
     * @return Response object.
     */
    Response query(Request request);

    /**
     * Scan Item.
     * @param request table name.
     * @return Response object.
     */
    Response scan(Request request);
}
