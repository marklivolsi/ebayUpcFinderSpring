package com.mcl.utilities;

import com.mcl.config.PropertyKeys;
import com.mcl.models.FindingApiRoot;
import com.mcl.models.Item;
import com.mcl.models.ShoppingApiRoot;
import com.mcl.requests.Request;
import com.mcl.requests.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class UpcProductDataFetcher {

    private final String upc;
    private FindingApiRoot findingApiRoot;
    private Map<String, ShoppingApiRoot> itemIdResponseMap;

    public UpcProductDataFetcher(String upc) {
        this.upc = upc;
        itemIdResponseMap = new HashMap<>();
    }

    public String getUpc() {
        return upc;
    }

    public List<Item> getItemListings() {
        List<Item> items = new ArrayList<>();
        for (Item item : this.getFindingApiRoot().getFindCompletedItemsResponse().get(0).getSearchResult().get(0).getItem()) {
            items.add(item);
        }
        return items;
    }

    public void fetchAllItemDetails() throws ExecutionException, InterruptedException, IOException {
        AsyncProcessor processor = new AsyncProcessor(30);
        fetchCompletedListings(processor);
        executeAllItemIdRequests(processor);
        processor.shutdown();
        mergeAllItemDetails();
    }

    private void mergeAllItemDetails() {
        List<Item> itemList = findingApiRoot.getFindCompletedItemsResponse().get(0).getSearchResult().get(0).getItem();
        for (Item item : itemList) {
            item.mergeItemDetails(itemIdResponseMap);
        }
    }

    private void fetchCompletedListings(AsyncProcessor processor) throws ExecutionException, InterruptedException, IOException {
        Map<String, String> params = new ApiParameterMapBuilder().getDefaultFindingApiParameterMap();
        params.put("keywords", upc);
        String baseUrl = PropertyKeys.FIND_API_BASE_URL.getValue();
        Request request = new RequestBuilder(baseUrl, params).buildRequest();
        Response response = processor.extract(processor.execute(request));
        findingApiRoot = new ResponseDeserializer().deserialize(response, FindingApiRoot.class);
    }

    private void executeAllItemIdRequests(AsyncProcessor processor) throws ExecutionException, InterruptedException, IOException {
        ArrayList<Response> responses = processor.extract(processor.execute(buildItemIdRequestList()));
        deserializeItemIdResponses(responses);
    }

    private void deserializeItemIdResponses(ArrayList<Response> responses) throws IOException {
        Map<String, ShoppingApiRoot> map = new HashMap<>();
        for (Response response : responses) {
            ShoppingApiRoot root = new ResponseDeserializer().deserialize(response, ShoppingApiRoot.class);
            String id = root.getItem().getItemID();
            map.put(id, root);
        }
        itemIdResponseMap = map;
    }

    private ArrayList<Request> buildItemIdRequestList() throws IOException {
        ArrayList<Request> requests = new ArrayList<>();
        String baseUrl = PropertyKeys.SHOP_API_BASE_URL.getValue();
        List<String> ids = getItemIdList();
        for (String id : ids) {
            Map<String, String> params = new ApiParameterMapBuilder().getDefaultShoppingApiParameterMap();
            params.put("ItemID", id);
            Request request = new RequestBuilder(baseUrl, params).buildRequest();
            requests.add(request);
        }
        return requests;
    }

    private ArrayList<String> getItemIdList() {
        ArrayList<String> ids = new ArrayList<>();
        List<Item> items = findingApiRoot.getFindCompletedItemsResponse().get(0)
                .getSearchResult().get(0)
                .getItem();
        for (Item item : items) {
            ids.add(item.getItemId().get(0));
        }
        return ids;
    }

    public FindingApiRoot getFindingApiRoot() {
        return findingApiRoot;
    }

    public Map<String, ShoppingApiRoot> getItemIdResponseMap() {
        return itemIdResponseMap;
    }

}
