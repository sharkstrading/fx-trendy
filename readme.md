# Trendiness analyzer for the Forex market

Using data from the Dukascopy group, obtain a list of highly trendy forex pairs.

Example output:

    2022-07-21 Today's Trendy Forex instruments:
    Sides: [SELL]
    Detected 28 instruments
    -------------------------------------------------------
    6/6 NZD/CAD SELL
    6/6 GBP/NZD SELL
    6/6 GBP/CHF SELL
    6/6 GBP/CAD SELL
    6/6 GBP/AUD SELL
    6/6 EUR/CAD SELL
    6/6 EUR/AUD SELL
    5/6 NZD/USD SELL
    5/6 NZD/CHF SELL
    5/6 GBP/USD SELL
    5/6 EUR/USD SELL
    5/6 EUR/CHF SELL
    5/6 AUD/USD SELL
    5/6 AUD/CAD SELL

## How to run the application

Request a demo account at the Dukascopy website.
Protip: email `support@dukascopy.com` and ask for the account to be permanent and non-expiring
Copy the config file in `config.example` to `config` and add your username/password.

Then:

```
 $ gradle run
```

## How to pack the application up in a distributable zip file

```
 $ gradle distZip
```

## Recommended software

Java 8 since the JForex SDK assumes you use it, otherwise you may run into Reflection errors
Gradle 7.3.3
