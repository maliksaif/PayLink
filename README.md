# PayLink

## 📌 Overview
PayLink is a demo fintech application that simulates login authentication, fund transfers, and transaction history tracking. The app is designed to showcase basic banking functionality, including selecting source and destination accounts for transfers.

## 🚀 Features
✅ User authentication using Firebase Authentication  
✅ List of accounts to choose source and destination for transfers  
✅ Transaction history tracking  
✅ Secure and efficient architecture following MVVM principles

## 🏗 Project Architecture
This project follows Clean Architecture with the MVVM (Model-View-ViewModel) pattern for better code separation and maintainability.

- **ViewState:** Used to manage UI state updates. The ViewState reflects the current state of the UI and is updated by the ViewModel as data changes occur.
- **ViewEffect:** Handles navigation and one-time actions. It provides transient events to be presented to the user (for example, navigation actions or showing a toast message).
- **ViewEvent:** Captures single-click events or any one-time interactions that the UI needs to process. These events are consumed once and not retained in the ViewState.

### 🛠 Tech Stack
- **UI:** XML-based layouts
- **Architecture:** Clean Architecture with MVVM
- **Dependency Injection:** Dagger Hilt
- **Local Persistence:** Room Database
- **Authentication:** Firebase Authentication

## 🔧 How to Run the Project
1. **Clone the repository:**
   ```bash
   git clone -b main https://github.com/maliksaif/PayLink.git
   ```
2. **Open the project in Android Studio.**
3. **Sync Gradle dependencies.**
4. **Run the app** on an emulator or a physical device.

## 📌 Future Enhancements
- 🔹 Add unit and UI tests for better reliability.


