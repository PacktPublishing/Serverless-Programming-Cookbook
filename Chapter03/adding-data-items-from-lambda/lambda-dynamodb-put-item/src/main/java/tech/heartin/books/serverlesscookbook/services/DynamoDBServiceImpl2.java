package tech.heartin.books.serverlesscookbook.services;

import tech.heartin.books.serverlesscookbook.domain.Request;
import tech.heartin.books.serverlesscookbook.domain.Response;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.util.TableUtils;

/**
 * Implementation of DynamoDBService that use AmazonDynamoDB client.
 */
public class DynamoDBServiceImpl2 implements DynamoDBService {

    private final AmazonDynamoDB dynamoDBClient;

    public DynamoDBServiceImpl2() {
        this.dynamoDBClient = AmazonDynamoDBClientBuilder.defaultClient();
    }

    @Override
    public final Response putItem(final Request request) {

        if (request.isWaitForActive()) {
            try {
                TableUtils.waitUntilActive(this.dynamoDBClient, request.getTableName());
            } catch (InterruptedException e) {
                return new Response(null,
                        "Error while waiting for table to become active with API version V2: " + e.getMessage());
            }
        }

        Map<String, AttributeValue> attributeValueMap = new HashMap<>();

        attributeValueMap.put(request.getPartitionKey(), new AttributeValue(request.getPartitionKeyValue()));
        attributeValueMap.put(request.getSortKey(), new AttributeValue().withN(request.getSortKeyValue().toString()));

        if (request.getStringData() != null) {
            request.getStringData().forEach((k, v)
                    -> attributeValueMap.put(k, new AttributeValue(v)));
        }

        if (request.getIntegerData() != null) {
            request.getIntegerData().forEach((k, v)
                    -> attributeValueMap.put(k, new AttributeValue().withN(v.toString())));
        }

        final PutItemRequest putItemRequest = new PutItemRequest()
                .withTableName(request.getTableName())
                .withItem(attributeValueMap)
                .withReturnValues(ReturnValue.NONE);

        this.dynamoDBClient.putItem(putItemRequest);

        return new Response("Item added into " + request.getTableName() + " with API version V2.", null);
    }

}
