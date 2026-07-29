# 📱 Controlio – Smart Employee Management System

Controlio is a modern Android application designed to simplify employee and workforce management. Built with **Kotlin**, **Jetpack Compose**, **Firebase**, and **Room Database**, it enables organizations to efficiently manage employees, assign tasks, monitor attendance, evaluate performance, and facilitate communication through a clean, responsive mobile interface.

---

# ✨ Features

### 👨‍💼 Admin Features

* Employee management
* Task creation and assignment
* Attendance monitoring
* Performance review management
* Workforce analytics dashboard
* Employee profile management
* Real-time data synchronization

### 👩‍💻 Employee Features

* Secure authentication
* View assigned tasks
* Task status tracking
* Attendance records
* Performance reviews
* In-app messaging
* Notifications
* Personal profile management

---

# 🛠️ Tech Stack

| Technology               | Purpose                    |
| ------------------------ | -------------------------- |
| Kotlin                   | Android Development        |
| Jetpack Compose          | Modern UI Toolkit          |
| Firebase Authentication  | User Authentication        |
| Firebase Firestore       | Cloud Database             |
| Firebase Cloud Messaging | Notifications              |
| Room Database            | Local Data Storage         |
| MVVM Architecture        | Application Design Pattern |
| Android Studio           | Development Environment    |

---

# 📂 Project Structure

```text
Controlio/
│
├── app/
│   ├── data/
│   │   ├── database/
│   │   ├── repository/
│   │   └── entities/
│   │
│   ├── ui/
│   │   ├── components/
│   │   ├── screens/
│   │   │   ├── admin/
│   │   │   ├── employee/
│   │   │   └── auth/
│   │   ├── theme/
│   │   └── utils/
│   │
│   ├── viewmodel/
│   ├── MainActivity.kt
│   └── AndroidManifest.xml
│
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

---

# 🚀 Key Modules

* 🔐 Authentication
* 👥 Employee Management
* 📋 Task Management
* 🗓 Attendance Tracking
* ⭐ Performance Reviews
* 💬 Employee Messaging
* 🔔 Notifications
* 📊 Analytics Dashboard
* 👤 User Profiles

---

# ⚙️ Installation

### Clone the repository

```bash
git clone https://github.com/Rahul-Gowda-R/Controlio.git
```

### Open in Android Studio

Open the project and allow Gradle to sync.

### Configure Firebase

1. Create a Firebase project.
2. Enable **Authentication** and **Cloud Firestore**.
3. Download the `google-services.json` file.
4. Place it inside:

```text
app/google-services.json
```

### Build & Run

Connect an Android device or start an emulator, then run the application from Android Studio.

---

# 🏛️ Architecture

Controlio follows the **MVVM (Model–View–ViewModel)** architecture to promote clean code, maintainability, and scalability.

```text
UI (Jetpack Compose)
        │
        ▼
ViewModel
        │
        ▼
Repository
        │
 ┌──────┴──────┐
 ▼             ▼
Firebase     Room Database
```

---

# 🚀 Future Enhancements

* GPS-based attendance
* Face recognition for employee verification
* Payroll management
* Leave management system
* Team collaboration workspace
* Calendar integration
* PDF report generation
* Dark mode
* Offline synchronization
* Admin approval workflows

---

# 📚 Learning Outcomes

* Android development with Kotlin
* Declarative UI using Jetpack Compose
* Firebase Authentication & Firestore integration
* Local persistence with Room Database
* MVVM architecture
* State management
* Real-time data synchronization
* Modern Android application design

---

# 🤝 Contributing

Contributions are welcome!

1. Fork the repository.
2. Create a feature branch.

```bash
git checkout -b feature-name
```

3. Commit your changes.

```bash
git commit -m "Add new feature"
```

4. Push the branch.

```bash
git push origin feature-name
```

5. Open a Pull Request.

---

# 👨‍💻 Author

**Rahul Gowda R**

* GitHub: https://github.com/Rahul-Gowda-R
* LinkedIn: https://www.linkedin.com/in/rahulgowdar/

---

# 📄 License

This project is licensed under the MIT License. See the `LICENSE` file for details.

---

## ⭐ Support

If you found this project useful, consider giving it a **⭐ Star** on GitHub and sharing your feedback!
