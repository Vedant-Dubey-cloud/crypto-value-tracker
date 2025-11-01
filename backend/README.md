Crypto Price Tracker

Crypto Price Tracker is a full-stack web application that displays the live market value of cryptocurrencies using the free CoinMarketCap API.
It provides real-time price data, allows users to search for individual coins, and visualizes market trends using interactive charts.

Overview

The application is divided into two parts:

Frontend: Built with HTML, CSS, and JavaScript. It provides an interactive user interface for searching coins and viewing top cryptocurrencies.

Backend: Built with Spring Boot (Java). It connects to the CoinMarketCap API, fetches cryptocurrency data, and serves it to the frontend.

Visualization: Implemented using Chart.js for simple and dynamic data charts.

Features

View live prices for any listed cryptocurrency

Display top 5 cryptocurrencies by market value

Search coins by name or symbol with auto-suggestions

Smooth chart visualization of price changes

Responsive and minimal design

Tech Stack

Frontend

HTML

CSS

JavaScript

Chart.js

Backend

Spring Boot

Java

RestTemplate for API calls

Setup and Installation
1. Clone the repository
git clone https://github.com/your-username/crypto-price-tracker.git
cd crypto-price-tracker

2. Backend Setup

Open the backend folder in your IDE (IntelliJ, VS Code, or Eclipse).

Create a file named application.yml inside src/main/resources with the following content:

spring:
  application:
    name: crypto

server:
  port: 9090
  servlet:
    context-path: /api/v1

CMC_API_KEY: your_api_key_here


Make sure your API key is not committed to GitHub.
Add the following to .gitignore if not already present:

src/main/resources/application.yml


3. Run the backend:

mvn spring-boot:run


Once started, the backend will be available at:

http://localhost:9090/api/v1

4. Frontend Setup

Navigate to the frontend folder.

Open index.html directly in your browser, or start a simple local server:

npx serve frontend


The frontend will communicate with the backend running on port 9090.
You can now search for any cryptocurrency or view the top five coins.

Notes

# The API key is required to fetch data from CoinMarketCap. 

If deploying online (Render, Railway, etc.), configure the CMC_API_KEY environment variable in your hosting platform.

Ensure CORS is properly configured if hosting frontend and backend separately.

License

This project is open-source and available for personal and educational use.
