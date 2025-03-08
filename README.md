# **Selenium & API Automation Project**

## **📌 Project Overview**
This project automates various test scenarios using **Selenium WebDriver**, **TestNG**, and **Rest-Assured**. The tests include **UI testing, API validation, performance testing, visual regression testing, and data-driven testing**.

### **✅ Features Implemented**
✔ **Data-Driven Testing** using **JSON & CSV**
✔ **UI Testing with Selenium WebDriver**
✔ **API Testing with Rest-Assured**
✔ **Visual Regression Testing using AShot**
✔ **Performance Testing (Page Load Time)**
✔ **Dynamic Data Generation (Faker.js for test data)**
✔ **Error Handling & Retry Mechanism**
✔ **Structured Logging & Test Reports**

---
## **📂 Project Structure**
```
Selenium-Automation-Project/
│── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── pages/               # Page Object Model Classes
│   │   │   │   ├── LoginPage.java
│   │   │   │   ├── ProductsPage.java
│   │   │   │   ├── CartPage.java
│   │   │   │   ├── CheckOutPage.java
│   │   │   ├── tests/               # Test Classes
│   │   │   │   ├── BaseTest.java
│   │   │   │   ├── LoginTest.java
│   │   │   │   ├── ProductFilterTest.java
│   │   │   │   ├── CheckoutTest.java
│   │   │   │   ├── ApiUiValidationTest.java
│   │   │   │   ├── VisualPerformanceTest.java
│   │   │   ├── utils/               # Utility Classes
│   │   │   │   ├── TestListener.java
│   │   ├── resources/               # Test Data
│   │   │   ├── users.json
│   │   │   ├── test-data.csv
│── screenshots/                     # Screenshots for Visual Testing
│── test-output/                      # Test Reports & Logs
│── pom.xml                           # Maven Dependencies
│── testng.xml                        # TestNG Suite Configuration
│── README.md                         # Project Documentation
```

---
## **🛠️ Setup Instructions**
### **1️⃣ Prerequisites**
Ensure you have the following installed:
- **Java 11+**
- **Maven**
- **ChromeDriver (latest version)**

### **2️⃣ Clone the Repository**
```sh
git clone https://github.com/your-repo/Selenium-Automation-Project.git
cd Selenium-Automation-Project
```

### **3️⃣ Install Dependencies**
```sh
mvn clean install
```

### **4️⃣ Run Test Cases**
Run all tests using TestNG:
```sh
mvn test
```
Run specific test suite:
```sh
mvn test -DsuiteXmlFile=testng.xml
```

---
## **📝 Test Scenarios & Implementation**

### **🔹 Task 1: Data-Driven Login Testing**
- **Login with multiple users (valid & invalid credentials)**
- **Use JSON/CSV as test data**
- **Capture screenshots on login failures**
- **Log response time for each login attempt**

**📌 Implementation:** [`LoginTest.java`](src/main/java/tests/LoginTest.java)

### **🔹 Task 2: Complex Product Filtering & Cart Verification**
- **Sort products by price (low to high)**
- **Dynamically select 3 random products and add to cart**
- **Verify product details in cart**
- **Remove the most expensive product and validate cart update**
- **Log test results in JSON format**

**📌 Implementation:** [`ProductFilterTest.java`](src/main/java/tests/ProductFilterTest.java)

### **🔹 Task 3: Multi-Step Checkout Workflow with Form Validation**
- **Perform a complete checkout**
- **Enter incorrect details and validate form errors**
- **Fix errors and validate price calculations (including tax)**
- **Capture screenshot before order confirmation**
- **Verify the order confirmation message**

**📌 Implementation:** [`CheckoutTest.java`](src/main/java/tests/CheckoutTest.java)

### **🔹 Task 4: API Testing & UI-API Validation**
- **Make a GET request to `https://reqres.in/api/users?page=2`**
- **Pick a random user from API and search in UI**
- **Compare API response with UI-rendered elements**
- **Handle API failures gracefully**

**📌 Implementation:** [`ApiUiValidationTest.java`](src/main/java/tests/ApiUiValidationTest.java)

### **🔹 Task 5: Visual & Performance Testing**
- **Capture a screenshot of the product listing page**
- **Compare it with a baseline image (fail if UI changes are detected)**
- **Measure page load time (fail if > 3 seconds)**
- **Implement retry mechanisms for network-related issues**

**📌 Implementation:** [`VisualPerformanceTest.java`](src/main/java/tests/VisualPerformanceTest.java)

---
## **📊 Test Reports & Logs**
- **Test Reports:** Located in `test-output/`
- **Screenshots:** Stored in `screenshots/`
- **JSON Logs:** Test execution logs stored in `test-output/`

---
## **📌 Enhancements & Future Improvements**
✅ Integrate **Allure Reports** for detailed reporting 📊  
✅ Extend **API Testing** for POST/PUT endpoints 🔄  
✅ Implement **Parallel Test Execution** for faster runs ⚡  
✅ Store logs in **Elasticsearch/Kibana** for better analysis 📂  



