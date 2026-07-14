# 🚀 Crypto Dashboard

> A modern JavaFX desktop application that displays **real-time cryptocurrency prices** using the **Binance API**, featuring automatic live updates, price change indicators, and a clean, responsive user interface.

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=openjdk)
![JavaFX](https://img.shields.io/badge/JavaFX-21-blue?style=for-the-badge)
![Maven](https://img.shields.io/badge/Maven-3.9+-C71A36?style=for-the-badge&logo=apachemaven)
![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Completed-success?style=for-the-badge)

---

## 📖 Overview

**Crypto Dashboard** is a desktop application built with **JavaFX** that provides real-time cryptocurrency market data. It fetches live prices from the **Binance REST API**, updates the dashboard every second, and visually highlights price changes with color indicators.

The project demonstrates the integration of Java desktop development with RESTful APIs, asynchronous programming using `CompletableFuture`, and a responsive graphical user interface.

---

## ✨ Features

- 📈 Real-time cryptocurrency prices
- ⚡ Automatic updates every second
- 🟢 Green indicator for price increases
- 🔴 Red indicator for price decreases
- 🕒 Live clock and last update timestamp
- 💻 Modern JavaFX user interface
- 🔄 Asynchronous API requests
- 🌐 Binance REST API integration
- 🛡️ Graceful error handling
- 🧩 Modular Object-Oriented Design

---

# 🏗️ Project Structure

```text
crypto-dashboard/
│
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── crypto/
│                   ├── CryptoDashboard.java
│                   ├── DashboardController.java
│                   ├── PriceService.java
│                   └── CryptoPrice.java
│
├── pom.xml
├── LICENSE
└── README.md
```

---

# 🏛️ System Architecture

```text
                +----------------------+
                |        User          |
                +----------+-----------+
                           |
                           |
                           ▼
                 JavaFX Dashboard UI
                           |
                           ▼
               DashboardController
                           |
                           ▼
                  PriceService
                           |
                    HTTP Request
                           |
                           ▼
                    Binance REST API
                           |
                    JSON Response
                           |
                           ▼
                     Gson Parser
                           |
                           ▼
                    CryptoPrice Model
                           |
                           ▼
                 Dashboard Refresh
```

---

# 🔄 Application Workflow

```text
Application Starts
        │
        ▼
Initialize Dashboard
        │
        ▼
Load Cryptocurrency Cards
        │
        ▼
Fetch Live Prices
        │
        ▼
Receive JSON Data
        │
        ▼
Parse Response
        │
        ▼
Update Price Model
        │
        ▼
Refresh User Interface
        │
        ▼
Repeat Every Second
```

---

# 🛠️ Built With

| Technology | Purpose |
|------------|---------|
| Java 17 | Programming Language |
| JavaFX 21 | Desktop GUI |
| Maven | Dependency Management |
| Gson | JSON Parsing |
| HttpClient | REST API Communication |
| CompletableFuture | Asynchronous Requests |
| Binance API | Live Cryptocurrency Data |

---

# 💰 Supported Cryptocurrencies

The dashboard currently monitors:

- Bitcoin (BTC)
- Ethereum (ETH)
- Solana (SOL)
- Binance Coin (BNB)
- Ripple (XRP)
- Dogecoin (DOGE)
- Cardano (ADA)
- Polkadot (DOT)

Adding more cryptocurrencies only requires adding a new symbol inside the `DashboardController`.

---

# ⚙️ Installation

## Prerequisites

- Java JDK 17 or later
- Maven 3.9+
- Internet connection

---

## Clone the Repository

```bash
git clone https://github.com/HaithamAbuDraz/crypto-dashboard.git
```

---

## Navigate to the Project

```bash
cd crypto-dashboard
```

---

## Build the Project

```bash
mvn clean install
```

---

## Run the Application

```bash
mvn javafx:run
```

---

# 📡 Binance API

The application uses Binance's public REST API.

Example request:

```http
GET https://api.binance.com/api/v3/ticker/price?symbol=BTCUSDT
```

Example response:

```json
{
    "symbol":"BTCUSDT",
    "price":"64520.10"
}
```

No authentication is required.

---

# 📂 Class Overview

## CryptoDashboard

Application entry point.

Responsibilities:

- Launch JavaFX
- Create application window
- Initialize controller
- Handle application shutdown

---

## DashboardController

Responsible for:

- Building the interface
- Creating cryptocurrency cards
- Managing automatic updates
- Updating UI components
- Displaying status information

---

## PriceService

Responsible for:

- Connecting to Binance API
- Sending HTTP requests
- Parsing JSON responses
- Returning cryptocurrency prices
- Handling network failures

---

## CryptoPrice

Represents a cryptocurrency object containing:

- Symbol
- Name
- Current Price
- Previous Price
- Percentage Change
- Last Updated Time

---

# 🔥 Live Dashboard Features

✔ Live prices

✔ Price movement indicators

✔ Percentage changes

✔ Last update timestamp

✔ Automatic refresh every second

✔ Responsive JavaFX interface

✔ Real-time clock

✔ Smooth color animations

---

# 📊 Data Flow

```text
Binance API
      │
      ▼
PriceService
      │
      ▼
JSON Parser
      │
      ▼
CryptoPrice Object
      │
      ▼
DashboardController
      │
      ▼
JavaFX Dashboard
      │
      ▼
User
```

---

# 🛡️ Error Handling

The application gracefully handles:

- Network connection failures
- API timeouts
- Invalid API responses
- JSON parsing errors
- Temporary Binance server issues

The dashboard continues running and retries on the next refresh cycle.

---

# 🚀 Future Improvements

- 📈 Interactive price charts
- ⭐ Favorite cryptocurrencies
- 🔔 Price alerts and notifications
- 🔍 Search functionality
- 🌙 Dark / Light theme switch
- 📅 Historical price charts
- 💼 Portfolio tracker
- 📤 Export data to CSV or Excel
- 🌍 Multi-language support
- ⚙️ Custom refresh intervals

---

# 📚 Documentation

The project documentation can be found in the **docs/** directory.

It includes:

- Project Documentation
- User Guide
- System Architecture
- Workflow Diagram
- Class Diagram
- Activity Diagram
- Sequence Diagram

---

# 🤝 Contributing

Contributions are welcome!

1. Fork the repository.
2. Create a feature branch.

```bash
git checkout -b feature/new-feature
```

3. Commit your changes.

```bash
git commit -m "Add new feature"
```

4. Push your branch.

```bash
git push origin feature/new-feature
```

5. Open a Pull Request.

---

# 👨‍💻 Author

**Haitham Abu Draz**

Software Engineer

- GitHub: https://github.com/HaithamAbuDraz
- LinkedIn: https://www.linkedin.com/in/haithamabudraz/

---

# 📄 License

This project is licensed under the MIT License.

See the **LICENSE** file for more information.

---

# ⭐ Support

If you found this project useful, consider giving it a ⭐ on GitHub.

It helps others discover the project and motivates future improvements.

---
