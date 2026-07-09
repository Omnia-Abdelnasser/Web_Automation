# Web Automation Graduation Project

This is a comprehensive Web Automation Testing project built as a Graduation Project. The project automates **32 end-to-end test cases** on the OpenCart demo application [awesomeqa.com/ui](https://awesomeqa.com/ui/index.php).

---

## 👥 Team Members

*   **Amr Mohamed barghash** (Team Leader) 👑
*   **Omnia Abdenasser Mohamed**
*   **Alaa Mahmoud omran**
*   **Ahemd Tamer Elromy**
*   **Assem mostafa fouad**
*   **Mohamed Fawzy**

---

## 🛠️ Technology Stack

*   **Programming Language:** Java (JDK 17)
*   **Automation Framework:** Selenium WebDriver
*   **Test Runner:** TestNG
*   **Build Tool:** Maven
*   **Reporting Tool:** Extent Reports (Spark Reporter)
*   **Design Pattern:** Page Object Model (POM)

---

## 📁 Project Structure

```text
Graduation_pro/
│
├── presentation/               # Interactive HTML defense presentation & assets
│   ├── presentation.html       # The main presentation file
│   └── ...                     # Presentation assets (images, screenshots)
│
├── src/
│   └── test/
│       └── java/
│           ├── base/           # Browser setup and tear-down logic
│           ├── listeners/      # TestNG Extent Report listener
│           ├── pages/          # Page Object classes (POM)
│           ├── reports/        # Extent Report manager (Singleton configuration)
│           ├── tests/          # All 32 automated test cases (auto_1-12 & TC_01-20)
│           └── utils/          # Configurations and wait utilities
│
├── testng.xml                  # Test suite runner configuration file
├── pom.xml                     # Maven dependencies configuration
└── report.html                 # Final generated interactive HTML Extent Report
```

---

## 📊 Project Presentation

We have designed a custom interactive HTML presentation for the graduation defense, complete with project details, a test execution simulator, and the framework architecture walkthrough.

*   **File Path:** `presentation/presentation.html`
*   **To Run:** Open `presentation.html` in Chrome or any modern web browser to view the slides and interact with the execution simulator.

---

## 🧪 Automated Test Cases (32 Total)

The test suite consists of two main categories:

### 1. Document Test Cases (`auto_1` to `auto_12`)
These represent the 12 core test scenarios requested for the project:
*   `auto_1_RegisterTest` - Registration Functionality (Positive and Negative Scenarios)
*   `auto_2_LoginTest` - Login Functionality (Positive and Negative Scenarios)
*   `auto_3_ForgotPasswordTest` - Forgotten Password Functionality
*   `auto_4_ProductSearchTest` - Product Search Functionality
*   `auto_5_CurrencySwitchTest` - Currency Switching (US Dollar to Euro)
*   `auto_6_RandomCategoryTest` - Random Category Selection & Navigation
*   `auto_7_HoverCategoryTest` - Hover Navigation Menu & Sub-Category Selection
*   `auto_8_LoggedUserCategoryTest` - Browsing Categories as a Logged-in User
*   `auto_9_WishlistTest` - Adding Products to Wishlist
*   `auto_10_CompareListTest` - Adding Products to Compare List
*   `auto_11_AddToCartTest` - Adding Products to the Shopping Cart
*   `auto_12_CheckoutTest` - Completing an Order with Successful Payment

### 2. Excel-Mapped Test Cases (`TC_01` to `TC_20`)
These represent the additional 20 test cases designed and mapped sequentially:
*   `TC_01` to `TC_20` covering advanced user account updates, address books, reviews, newsletters, coupons, shopping cart modifications, and shipping estimations.

---

## 🚀 How to Run the Tests

### Option A: Run via IntelliJ IDEA
1. Open the project in **IntelliJ IDEA**.
2. Right-click on the `testng.xml` file in the root directory.
3. Select **Run '...testng.xml'**.

### Option B: Run via Maven CLI
Run the following command in your terminal:
```bash
mvn clean test
```

---

## 📊 Test Report

After the execution completes, a beautiful interactive HTML report named `report.html` is automatically generated in the project root directory.

### How to view the report:
1. Locate `report.html` in the project files.
2. Right-click the file and select **Open in Browser** -> **Chrome** (or any other browser).
3. The report displays charts, execution timelines, detailed logs, and pass/fail statuses for all 32 tests.
