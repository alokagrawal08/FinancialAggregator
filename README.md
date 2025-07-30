# Real-Time Market Data Aggregator with Kafka and MongoDB

A comprehensive financial data pipeline that streams simulated live stock data through Apache Kafka, computes technical indicators like RSI in real-time using Kafka Streams, and persists data to MongoDB. The system also provides historical data analysis capabilities using MongoDB's aggregation framework to calculate Simple Moving Averages (SMA) and Exponential Moving Averages (EMA).

## 🏗️ Architecture Overview

```
Stock Data Generator → Kafka Topic → Kafka Streams (RSI Calculation) → MongoDB → REST API
                                          ↓
                               Historical Data Analysis (SMA/EMA)
```

## ✨ Features

- **Real-time Data Streaming**: Simulated live stock data generation and streaming
- **Technical Indicators**: Real-time RSI (Relative Strength Index) calculation using Kafka Streams
- **Historical Analysis**: SMA and EMA calculations using MongoDB aggregation pipelines
- **Time Series Storage**: Optimized MongoDB time series collections for financial data
- **RESTful API**: Clean endpoints for accessing live and historical data
- **Front-end Ready**: CORS-enabled endpoints for web application integration

## 🛠️ Technology Stack

- **Java 17+** (tested with Java 21)
- **Spring Boot 3.4.4**
- **Apache Kafka 3.9+** with KRaft mode
- **MongoDB Atlas** (free tier M0 compatible)
- **Spring Data MongoDB** for database operations
- **Spring Kafka** for Kafka integration
- **Kafka Streams** for real-time processing
- **Maven 3.9+** for dependency management

## 📋 Prerequisites

Before running the application, ensure you have:

1. **Java 17+** installed ([Download Java](https://adoptopenjdk.net/))
2. **Maven 3.9+** installed ([Download Maven](https://maven.apache.org/download.cgi))
3. **MongoDB Atlas cluster** set up ([Free Tier M0](https://www.mongodb.com/cloud/atlas/register/))
4. **Apache Kafka 3.9+** installed locally ([Download Kafka](https://kafka.apache.org/downloads))
5. **Historical dataset** (see Data Setup section)

## 🚀 Quick Start

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/financial-aggregator.git
cd financial-aggregator
```

### 2. Configure MongoDB

Update `src/main/resources/application.properties`:

```properties
spring.application.name=Financial Aggregator
spring.data.mongodb.uri=<Your-MongoDB-Atlas-Connection-String>
spring.data.mongodb.database=Stock_Market_Data
```

### 3. Set Up MongoDB Collections

Create the following time series collections in your `Stock_Market_Data` database:

**Live_Data Collection:**
- Time Field: `date`
- Granularity: Seconds

**Historical_Data Collection:**
- Time Field: `date`
- Meta Field: `company`
- Granularity: Seconds

### 4. Data Setup

Download the [Stock Market Historical Data](https://www.kaggle.com/datasets/khushipitroda/stock-market-historical-data-of-top-10-companies) dataset from Kaggle and place the `data.csv` file in `src/main/resources/`.

### 5. Start Kafka

Initialize Kafka storage (one-time setup):

```bash
# Generate cluster ID
CLUSTER_ID=$(bin/kafka-storage.sh random-uuid)
echo $CLUSTER_ID

# Format storage
bin/kafka-storage.sh format -t $CLUSTER_ID -c config/kraft/server.properties

# Start Kafka server
bin/kafka-server-start.sh config/kraft/server.properties
```

### 6. Run the Application

```bash
mvn spring-boot:run
```

## 📡 API Endpoints

### Live Data
- `GET /stocks/live` - Retrieve all live stock data with RSI

### Historical Data
- `GET /stocks/companies` - Get list of all available companies
- `GET /stocks/companyStocksBetween?company={SYMBOL}&startDate={YYYY-MM-DD}&endDate={YYYY-MM-DD}` - Get historical data for date range

### Technical Indicators
- `GET /stocks/sma?company={SYMBOL}&startDate={YYYY-MM-DD}&endDate={YYYY-MM-DD}` - Simple Moving Average
- `GET /stocks/ema?company={SYMBOL}&startDate={YYYY-MM-DD}&endDate={YYYY-MM-DD}` - Exponential Moving Average

### Example API Calls

```bash
# Get all companies
curl -v http://localhost:8080/stocks/companies

# Get historical AAPL data
curl -v "http://localhost:8080/stocks/companyStocksBetween?company=AAPL&startDate=2023-01-01&endDate=2023-02-01"

# Get SMA for AAPL
curl -v "http://localhost:8080/stocks/sma?company=AAPL&startDate=2023-01-01&endDate=2023-02-01"

# Get live data
curl -v http://localhost:8080/stocks/live
```

## 🏗️ Project Structure

```
src/main/java/com/mongodb/financialaggregator/
├── config/
│   ├── KafkaProducerConfig.java      # Kafka producer configuration
│   └── KafkaStreamsConfig.java       # Kafka Streams configuration
├── controller/
│   └── FinancialController.java      # REST API endpoints
├── model/
│   ├── LiveStockData.java           # Live data model
│   ├── StockMarketData.java         # Historical data model
│   └── DTO classes                  # Data transfer objects
├── repository/
│   ├── LiveDataRepository.java      # Live data repository
│   ├── FinancialRepository.java     # Historical data repository
│   └── CustomFinancialRepository... # Custom aggregation queries
├── service/
│   ├── SimulateLiveData.java        # Stock data generator
│   ├── RsiStreamProcessor.java      # Kafka Streams processing
│   ├── FinancialService.java        # Business logic
│   └── ReadInHistoricalData.java    # CSV data loader
└── utility/
    ├── LiveStockDataSerde.java      # Kafka serialization
    └── RsiCalculator.java           # RSI calculation logic
```

## 🔧 Configuration

### Kafka Configuration
- **Bootstrap Servers**: `localhost:9092`
- **Topic**: `stock-prices`
- **Application ID**: `rsi-streams-app`

### Technical Indicators Settings
- **RSI Period**: 14 days
- **SMA Period**: 4 days  
- **EMA Period**: 20 days

### Data Generation
- **Frequency**: Every 1 second
- **Simulated Company**: DOOF
- **Trend Flip**: Every 60 days

## 🔍 Monitoring and Debugging

The application includes comprehensive logging:

```
========== STOCK DEBUG INFO ==========
Company: DOOF
Date: Sun Jun 22 00:00:00 IST 2025
Open: 111.12
Close/Last: 115.26
High: 123.71
Low: 102.81
Volume: 1054.14
======================================
```

## 🎯 Key Features Explained

### Real-Time RSI Calculation
The system maintains a sliding window of the last 15 price points per company and calculates RSI using the standard formula when sufficient data is available.

### MongoDB Time Series Collections
Optimized for timestamped financial data with efficient querying, sorting, and trend analysis capabilities.

### Kafka Streams Processing
Processes stock data in real-time, enriching it with technical indicators before persisting to MongoDB.

### Aggregation Framework
Leverages MongoDB's powerful aggregation pipeline for calculating moving averages directly in the database, avoiding memory-intensive operations.

## 🚨 Troubleshooting

### Common Issues

1. **Kafka Connection Failed**
   - Ensure Kafka server is running on `localhost:9092`
   - Verify KRaft storage was properly formatted

2. **MongoDB Connection Issues**
   - Check your Atlas connection string
   - Ensure network access is configured in Atlas

3. **Missing Historical Data**
   - Verify `data.csv` is in `src/main/resources/`
   - Check CSV format matches expected structure

4. **RSI Not Calculating**
   - RSI requires at least 15 data points
   - Wait for sufficient data to accumulate

## 🔧 Development Setup

### Running with IDE
1. Import as Maven project
2. Set up run configuration with main class: `FinancialAggregatorApplication`
3. Ensure Kafka is running before starting the application

### Data Loading
The historical data loads automatically on first startup. To prevent reloading on subsequent runs, remove the `ApplicationRunner` implementation from `ReadInHistoricalData.java`.

## 📊 Front-End Integration

The API is CORS-enabled for `http://localhost:5173` (Vite default). A React front-end is available in the repository for visualizing the data with candlestick charts and technical indicators.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🙏 Acknowledgments

- [MongoDB Developer](https://github.com/mongodb-developer) for the original tutorial
- [Kaggle](https://www.kaggle.com/datasets/khushipitroda/stock-market-historical-data-of-top-10-companies) for the historical stock dataset
- Apache Kafka and MongoDB communities for excellent documentation

## 🔗 Related Resources

- [MongoDB Time Series Documentation](https://www.mongodb.com/docs/manual/core/timeseries-collections/)
- [Kafka Streams Documentation](https://kafka.apache.org/documentation/streams/)
- [Spring Data MongoDB Reference](https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/)

---

For questions or support, please open an issue in the repository.
