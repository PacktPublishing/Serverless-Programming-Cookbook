package tech.heartin.books.serverlesscookbook.services;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

import tech.heartin.books.serverlesscookbook.domain.Request;
import tech.heartin.books.serverlesscookbook.domain.Response;

/**
 * Implementation of DynamoDBService that use DynamoDB wrapper client.
 */
public class DynamoDBServiceImpl1 implements DynamoDBService {

    private final DynamoDB dynamoDB;

    public DynamoDBServiceImpl1() {
        final AmazonDynamoDB dynamoDBClient = AmazonDynamoDBClientBuilder.defaultClient();
        this.dynamoDB = new DynamoDB(dynamoDBClient);

    }

    @Override
    public final Response putItem(final Request request) {

        Table table = dynamoDB.getTable(request.getTableName());

        if (request.isWaitForActive()) {
            try {
                table.waitForActive();
            } catch (InterruptedException e) {
                return new Response(null,
                        "Error while waiting for table to become active with API version V2: " + e.getMessage());
            }
        }

        Item item = new Item()
                .withPrimaryKey(request.getPartitionKey(), request.getPartitionKeyValue(),
                        request.getSortKey(), request.getSortKeyValue());

        if (request.getStringData() != null) {
            request.getStringData().forEach((k, v) -> item.withString(k, v));
        }

        if (request.getIntegerData() != null) {
            request.getIntegerData().forEach((k, v) -> item.withInt(k, v));
        }

        table.putItem(item);

        return new Response("Item added into " + request.getTableName() + " with API version V1.", null);
    }

}
