package tech.heartin.books.serverlesscookbook.services;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;

import tech.heartin.books.serverlesscookbook.domain.Request;
import tech.heartin.books.serverlesscookbook.domain.Response;

import java.util.Arrays;

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
    public final Response createTable(final Request request) {

        if (tableExist(request.getTableName())) {
            return new Response(null, request.getTableName() + " already exist. Checked with version V1.");
        }

        Table table = dynamoDB.createTable(request.getTableName(),
                Arrays.asList(
                        new KeySchemaElement(request.getPartitionKey(), KeyType.HASH),  //Partition key
                        new KeySchemaElement(request.getSortKey(), KeyType.RANGE)), //Sort key
                Arrays.asList(
                        new AttributeDefinition(request.getPartitionKey(), ScalarAttributeType.S),
                        new AttributeDefinition(request.getSortKey(), ScalarAttributeType.N)),
                new ProvisionedThroughput(request.getReadCapacityUnits(), request.getWriteCapacityUnits()));

        if (request.isWaitForActive()) {
            try {
                table.waitForActive();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return new Response(request.getTableName() + " created with API version V1.", null);
    }

    private boolean tableExist(final String tableName) {
        if (this.dynamoDB.getTable(tableName).getDescription() != null) {
            return true;
        }
        return false;
    }

}
