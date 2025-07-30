package com.example.FinancialAggregator.repository;



import com.example.FinancialAggregator.model.CompanyDTO;
import com.example.FinancialAggregator.model.ExponentialMovingAverageDTO;
import com.example.FinancialAggregator.model.SimpleMovingAverageDTO;

import java.util.Date;
import java.util.List;

public interface CustomFinancialRepository {
    List<CompanyDTO> getAllCompanies();
    List<SimpleMovingAverageDTO> getSimpleMovingAverage(String company, Date startDate, Date endDate);
    List<ExponentialMovingAverageDTO> getExponentialMovingAverage(String company, Date startDate, Date endDate);
}