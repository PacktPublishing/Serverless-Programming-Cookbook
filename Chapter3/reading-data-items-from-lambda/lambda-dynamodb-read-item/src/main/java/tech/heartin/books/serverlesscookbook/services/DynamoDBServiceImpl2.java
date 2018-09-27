package tech.heartin.books.serverlesscookbook.services;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

import tech.heartin.books.serverlesscookbook.domain.Request;
import tech.heartin.books.serverlesscookbook.domain.Response;

/**
 * Implementation of DynamoDBService that use AmazonDynamoDB client.
 */
public class DynamoDBServiceImpl2 implements DynamoDBService {

    private final AmazonDynamoDB dynamoDBClient;

    public DynamoDBServiceImpl2() {
        this.dynamoDBClient = AmazonDynamoDBClientBuilder.defaultClient();
    }

    @Override
    public final Response getItem(final Request request) {

        final HashMap<String, AttributeValue> primaryKey = new HashMap<>();
        primaryKey.put(request.getPartitionKey(), new AttributeValue(request.getPartitionKeyValue()));
        primaryKey.put(request.getSortKey(), new AttributeValue().withN(request.getSortKeyValue()));

        final GetItemResult getItemResult = dynamoDBClient.getItem(new GetItemRequest()
                .withTableName(request.getTableName())
                .withKey(primaryKey));

        return new Response("PK of Item read using get-item (V2): "
                + prepareKeyStr(getItemResult.getItem(), request), null);
    }

    @Override
    public final Response query(final Request request) {

        final Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":" + request.getPartitionKey(),
                new AttributeValue(request.getPartitionKeyValue()));

        final String keyConditionExpression = request.getPartitionKey() + "=:" + request.getPartitionKey();

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(request.getTableName())
                .withKeyConditionExpression(keyConditionExpression)
                .withExpressionAttributeValues(expressionAttributeValues);

        StringBuilder filterExpressionBuilder;
        if (request.getFilterData() != null) {
            filterExpressionBuilder = new StringBuilder();
            processFilterData(request, filterExpressionBuilder, expressionAttributeValues);
            //Add to QueryRequest.
            queryRequest.withFilterExpression(filterExpressionBuilder.toString());
        }

        final QueryResult queryResult = dynamoDBClient.query(queryRequest);

        final StringBuilder response = new StringBuilder();
        response.append("PK of items read with query (V2): ");
        for (Map<String, AttributeValue> item : queryResult.getItems()) {
            response.append(prepareKeyStr(item, request));
        }


        return new Response(response.toString(), null);
    }

    @Override
    public final Response scan(final Request request) {

        final String projectionExpression = request.getPartitionKey() + ", " + request.getSortKey();
        ScanRequest scanRequest = new ScanRequest()
                .withTableName(request.getTableName())
                .withProjectionExpression(projectionExpression);

        StringBuilder filterExpressionBuilder;
        Map<String, AttributeValue> expressionAttributeValues;
        if (request.getFilterData() != null) {
            filterExpressionBuilder = new StringBuilder();
            expressionAttributeValues = new HashMap<>();
            processFilterData(request, filterExpressionBuilder, expressionAttributeValues);
            // Add to ScanRequest.
            scanRequest.withFilterExpression(filterExpressionBuilder.toString());
            scanRequest.withExpressionAttributeValues(expressionAttributeValues);
        }



        final ScanResult scanResult = dynamoDBClient.scan(scanRequest);

        final StringBuilder response = new StringBuilder();
        response.append("PK of items read with scan (V2): ");
        for (Map<String, AttributeValue> item : scanResult.getItems()) {
            response.append(prepareKeyStr(item, request));
        }

        return new Response(response.toString(), null);
    }

    private String prepareKeyStr(final Map<String, AttributeValue> item, final Request request) {
        return "(" + item.get(request.getPartitionKey()) + ", " + item.get(request.getSortKey()) + ") ";
    }

    private void processFilterData(final Request request,
            final StringBuilder filterExpressionBuilder,
            final Map<String, AttributeValue> expressionAttributeValues) {

        if (request.getFilterData() == null) {
            return;
        }

        request.getFilterData().forEach((k, v) -> {
            final String var = ":" + k;
            if (!filterExpressionBuilder.toString().isEmpty()) {
                filterExpressionBuilder.append(" and ");
            }
            filterExpressionBuilder.append(k + "=" + var);
            expressionAttributeValues.put(var, new AttributeValue(v));
        });
    }

}
