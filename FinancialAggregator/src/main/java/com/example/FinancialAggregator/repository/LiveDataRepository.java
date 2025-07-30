package com.example.FinancialAggregator.repository;


import com.example.FinancialAggregator.model.LiveStockData;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LiveDataRepository extends MongoRepository<LiveStockData, ObjectId> {
}
