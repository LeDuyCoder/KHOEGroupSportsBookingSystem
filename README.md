# KHOE Group Sports Booking System

## 📋 Project Overview

**KHOE Group Sports Booking System** is a comprehensive management system for handling reservations of sports facilities within the KHOE Group sports complex. The system enables users to manage facility information, make bookings, cancel reservations, view revenue reports, and analyze service usage statistics.


## 🎯 Core Features

### 1. **Import Facility from CSV File** (50 LOC)
- Read data from `facility_schedule.csv` file
- Validate file format and data integrity
- Import facility information into the system

### 2. **Update Facility Information** (50 LOC)
- Update facility details (Location, Capacity, Availability Schedule)
- Validate input data
- Search by Facility ID or Name

### 3. **View Facilities & Services** (50 LOC)
- Display all available facilities and services
- Tabular format for easy reading
- Notification when no data is available

### 4. **Book a Facility / Service** (50 LOC)
- Reserve facilities based on available time slots
- Validate customer information, number of persons, and timing
- Generate unique booking codes
- Update facility status to "Booked"

### 5. **View Bookings** (50 LOC)
- Display booking list by date
- Default to today's bookings
- Sort by time order

### 6. **Cancel a Booking** (50 LOC)
- Cancel existing bookings
- Validate cancellation timing (cannot cancel past bookings)
- Confirmation before cancellation

### 7. **Monthly Revenue Report** (50 LOC)
- Generate revenue summary by month
- Aggregate revenue per facility
- Validate input month/year

### 8. **Service Usage Statistics** (50 LOC)
- Statistics on user count per service type
- Optional time range filtering
- Display number of players per facility

### 9. **Save All Data** (50 LOC)
- Save all system data to `BookingInfo.dat`
- Binary serialization of data
- Confirmation message upon successful save

### 10. **Quit** (50 LOC)
- Exit the application
- Prompt to save data if unsaved changes exist

## 🛠️ Technical Implementation

### **Design Patterns & Principles**
- **MVC Pattern**: Clear separation of Model-View-Controller
- **OOP Principles**: Full implementation of OOP concepts (Abstraction, Polymorphism, Encapsulation, Inheritance)
- **Separation of Concerns**: Distinct layers for business logic, data, and presentation

### **Key Components**
- **Models**: Define data structures and entities
- **Controllers**: Handle business logic and workflow
- **Services**: Manage complex business operations
- **Constants**: Centralized management of global constants

## 📁 Data Files

### **Input Files**
- `facility_schedule_Final.csv`: Initial facility data in CSV format

### **Output Files**
- `BookingInfo.dat`: Serialized booking data in binary format

## 🚀 How to Run

1. **Clone/Download the project**
2. **Import into NetBeans IDE**
3. **Build the project** using `build.xml`
4. **Run main class**: `KHOEGroupSportsBookingSystem`
5. **Follow menu prompts** to access various functionalities

## ⚙️ System Requirements

- **Java Development Kit** (JDK 8 or higher)
- **NetBeans IDE** (recommended) or any Java-supported IDE
- **CSV file** with proper format for facility data import

## 📝 Important Notes

- System requires properly formatted CSV file for successful import
- Booking data is automatically saved on exit (with user confirmation)
- Strict validation rules ensure data integrity
- Code is designed for easy maintenance and future extensions
- All functionalities follow the specified 50 LOC requirement per feature

## 👥 Development Team

This project is developed by DuyKR as part of the LAB211 course requirements, following object-oriented programming principles and software engineering best practices.

## 🔄 Workflow

The system follows a structured workflow:
1. **Data Import** → **Facility Management** → **Booking Operations** → **Reporting** → **Data Persistence**

Each functionality is designed to work independently while maintaining data consistency across the system.

## 📊 Sample Data Format

### Facility CSV Format:
Id, Facility Name, Facility Type, Location, Capacity, Availability Start, Availability End, Price, Status
BC-01, Badminton Court 1, Badminton, Zone A - 2nd Floor, 4, 2025-10-10 08:00, 2025-10-10 09:00, 300000, Not Booking

### Booking Information:
- Player name: 2-18 characters
- Facility ID: Must exist in system
- Date & Time: Must be within availability window
- Number of Persons: Must not exceed facility capacity
