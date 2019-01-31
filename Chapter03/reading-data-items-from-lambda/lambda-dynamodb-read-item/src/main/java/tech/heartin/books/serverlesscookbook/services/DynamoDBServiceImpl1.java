package tech.heartin.books.serverlesscookbook.services;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;

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
    public final Response getItem(final Request request) {

        Table table = dynamoDB.getTable(request.getTableName());

        Item item = table.getItem(new PrimaryKey()
                .addComponent(request.getPartitionKey(), request.getPartitionKeyValue())
                .addComponent(request.getSortKey(), Integer.parseInt(request.getSortKeyValue())));

        return new Response("PK of item read using get-item (V1): " + prepareKeyStr(item, request), null);
    }

    @Override
    public final Response query(final Request request) {

        Table table = dynamoDB.getTable(request.getTableName());

        final String keyConditionExpression = request.getPartitionKey() + "=:" + request.getPartitionKey();
                //+ " and " + request.getSortKey() + "=:" + request.getSortKey();

        QuerySpec querySpec = new QuerySpec()
                .withKeyConditionExpression(keyConditionExpression);

        final Map<String, Object> valueMap = new HashMap<>();
        StringBuilder filterExpressionBuilder;
        if (request.getFilterData() != null) {
            filterExpressionBuilder = new StringBuilder();
            processFilterData(request, filterExpressionBuilder, valueMap);
            //Add to QuerySpec.
            querySpec.withFilterExpression(filterExpressionBuilder.toString());
        }

        valueMap.put(":" + request.getPartitionKey(), request.getPartitionKeyValue());
        //valueMap.put(":" + request.getSortKey(), request.getSortKeyValue());
        querySpec.withValueMap(valueMap);

        ItemCollection<QueryOutcome> items = table.query(querySpec);

        StringBuilder response = new StringBuilder();
        response.append("PK of items read with query (V1): ");
        for (Item item : items) {
            response.append(prepareKeyStr(item, request));
        }

        return new Response(response.toString(), null);
    }


    @Override
    public final Response scan(final Request request) {
        final Table table = dynamoDB.getTable(request.getTableName());

        final String projectionExpression = request.getPartitionKey() + " , " + request.getSortKey();
        final ScanSpec scanSpec = new ScanSpec()
                .withProjectionExpression(projectionExpression);

        StringBuilder filterExpressionBuilder;
        Map<String, Object> valueMap;
        if (request.getFilterData() != null) {
            filterExpressionBuilder = new StringBuilder();
            valueMap = new HashMap<>();
            processFilterData(request, filterExpressionBuilder, valueMap);
            // Add to ScanSpec.
            scanSpec.withFilterExpression(filterExpressionBuilder.toString());
            scanSpec.withValueMap(valueMap);
        }

        ItemCollection<ScanOutcome> scanItems = table.scan(scanSpec);

        final StringBuilder response = new StringBuilder();
        response.append("PK of items read with scan (V1): ");
        for (Item item : scanItems) {
            response.append(prepareKeyStr(item, request));
        }

        return new Response(response.toString(), null);
    }


    private String prepareKeyStr(final Item item, final Request request) {
        return "(" + item.get(request.getPartitionKey()) + ", " + item.get(request.getSortKey()) + ") ";
    }

    private void processFilterData(final Request request,
            final StringBuilder filterExpressionBuilder,
            final Map<String, Object> valueMap) {

        request.getFilterData().forEach((k, v) -> {
            final String var = ":" + k;
            if (!filterExpressionBuilder.toString().isEmpty()) {
                filterExpressionBuilder.append(" and ");
            }
            filterExpressionBuilder.append(k + "=" + var);
            valueMap.put(var, v);
        });
    }

}
