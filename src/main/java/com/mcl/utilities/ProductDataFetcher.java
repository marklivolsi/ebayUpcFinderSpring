//package com.mcl.utilities;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.concurrent.ExecutionException;
//
//public class ProductDataFetcher {
//
//    public void fetchAllData(UpcProductDataFetcher upcProductDataFetcher) throws ExecutionException, InterruptedException, IOException {
//        AsyncProcessor processor = new AsyncProcessor();
//        upcProductDataFetcher.fetchAllItemDetails(processor);
//        processor.shutdown();
//    }
//
//    public void fetchAllData(ArrayList<UpcProductDataFetcher> upcProductDataFetchers) throws ExecutionException, InterruptedException, IOException {
//        AsyncProcessor processor = new AsyncProcessor();
//        for (UpcProductDataFetcher upcProductDataFetcher : upcProductDataFetchers) {
//            upcProductDataFetcher.fetchAllItemDetails(processor);
//        }
//        processor.shutdown();
//    }
//
//}
