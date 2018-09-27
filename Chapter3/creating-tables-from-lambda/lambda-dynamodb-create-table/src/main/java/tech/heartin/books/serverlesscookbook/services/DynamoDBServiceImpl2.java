package tech.heartin.books.serverlesscookbook.services;

import tech.heartin.books.serverlesscookbook.domain.Request;
import tech.heartin.books.serverlesscookbook.domain.Response;

import java.util.Arrays;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
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
    public final Response createTable(final Request request) {

        final CreateTableRequest createTableRequest = new CreateTableRequest(
                Arrays.asList(
                        new AttributeDefinition(request.getPartitionKey(), ScalarAttributeType.S),
                        new AttributeDefinition(request.getSortKey(), ScalarAttributeType.N)),
                request.getTableName(),
                Arrays.asList(
                        new KeySchemaElement(request.getPartitionKey(), KeyType.HASH),
                        new KeySchemaElement(request.getSortKey(), KeyType.RANGE)),
                new ProvisionedThroughput(request.getReadCapacityUnits(), request.getWriteCapacityUnits()));

        TableUtils.createTableIfNotExists(this.dynamoDBClient, createTableRequest);

        try {
            TableUtils.waitUntilActive(this.dynamoDBClient, request.getTableName());
        } catch (final AmazonClientException | InterruptedException e) {
            return new Response(null, "Failed in table active check in API version V2: " + e.getMessage());
        }

        return new Response(request.getTableName() + " created with API version V2.", null);
    }
}
