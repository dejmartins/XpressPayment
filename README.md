# XpressPayment

## Introduction

XpressPayment is a Spring Boot application designed to facilitate user authentication using JSON Web Tokens (JWT) and enable airtime payments through the Xpress Payment VTU API. Below, you'll find an overview of the project and its key features.

## Table of Contents

1. [Introduction](#introduction)
2. [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
    - [Configuration](#configuration)
3. [Authentication](#authentication)
    - [Obtaining a Token](#obtaining-a-token)
4. [Airtime Payments](#airtime-payments)
    - [Endpoint Access](#endpoint-access)
    - [Token Requirements](#token-requirements)
5. [Unit Tests](#unit-tests)
    - [Access Secured Endpoint Without Token](#access-secured-endpoint-without-token)
    - [Access Airtime Purchase Endpoint With Valid Token](#access-airtime-purchase-endpoint-with-valid-token)
    - [Calculate HMAC512](#calculate-hmac512)

## Getting Started

### Prerequisites

Ensure you have the following installed:

- [Java](https://www.java.com/) (version 17)
- [Your preferred IDE](#) (e.g., IntelliJ, Eclipse)

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/dejmartins/XpressPayment.git
   
2. Navigate to the project directory:

   ```bash
   cd XpressPayment

### Configuration

Adjust configuration settings in application.properties to match your environment.

## Authentication

### Obtaining a Token

To obtain an authentication token, make a POST request to the `/login` endpoint with valid credentials. The application comes with a default user:

- **username:** jerry@email.com
- **password:** password

## Airtime Payments

### Endpoint Access

Access the airtime purchase endpoint by making a POST request to `/api/airtime/purchase`. Ensure you include a valid JWT token in the request header.

### Token Requirements

Include the JWT token in the request header as follows:

   ```bash
   Authorization: Bearer <your-token-here>
   ```

## Unit Tests

### Access Secured Endpoint Without Token

Ensure that attempting to access secured endpoints without a token results in a 401 Unauthorized status.

### Access Airtime Purchase Endpoint With Valid Token

Verify that accessing the airtime purchase endpoint with a valid token returns an OK status.

### Calculate HMAC512

Test the calculation of HMAC512 with a sample data and key.
