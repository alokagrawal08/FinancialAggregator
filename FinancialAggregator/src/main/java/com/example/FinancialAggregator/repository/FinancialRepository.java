package com.example.FinancialAggregator.repository;

import com.example.FinancialAggregator.model.StockMarketData;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FinancialRepository extends MongoRepository<StockMarketData, ObjectId> {
    List<StockMarketData> findAllByCompanyAndDateBetween(String company, Date startDate, Date endDate);
}
