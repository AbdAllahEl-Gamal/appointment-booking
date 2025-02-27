# Appointment Booking System

This is a backend system designed for booking appointments with sales managers. The system provides available appointment slots based on specific customer criteria and handles overlapping slots, language, product, and rating filters.

## Features

- Fetch available appointment slots based on customer language, rating, and product interest.
- Slot availability is adjusted based on existing bookings, preventing overlapping appointments.
- Supports two languages: **German** and **English**.
- Handles two products: **SolarPanels** and **Heatpumps**.
- Customer ratings include **Gold**, **Silver**, and **Bronze**.

---

## Steps to Run the Application

- Clone the Repository  
🔹 git clone https://github.com/AbdAllahEl-Gamal/appointment-booking.git  
🔹 cd appointment-booking
- Build the Application  
🔹 mvn clean install
- Start the PostgreSQL Database and Application Using Docker  
🔹 docker-compose up --build
- Verify the Application  
🔹 The API will be available at: http://localhost:3000

---

## Project Structure

```plaintext
appointment-booking/
│── src/
│   ├── main/
│   │   ├── java/com/appointmentbooking/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   ├── util/
│   │   │   ├── AppointmentBookingApplication.java
│   │   ├── resources/
│   ├── test/
│   │   ├── java/com/appointmentbooking/
│   │   │   ├── controller/
│   │   │   ├── service/
│   │   │   ├── util/
│── Dockerfile
│── docker-compose.yml
│── init.sql
│── logback-spring.xml
│── pom.xml
│── README.md