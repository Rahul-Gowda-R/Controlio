package com.company.employeetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.company.employeetracker.data.database.entities.User
import com.company.employeetracker.data.firebase.FirebaseUserRepository
import com.company.employeetracker.ui.components.AdminBottomNavBar
import com.company.employeetracker.ui.components.EmployeeBottomNavBar
import com.company.employeetracker.ui.screens.admin.*
import com.company.employeetracker.ui.screens.auth.ForgotPasswordScreen
import com.company.employeetracker.ui.screens.auth.LoginScreen
import com.company.employeetracker.ui.screens.employee.*
import com.company.employeetracker.ui.theme.EmployeeTrackerTheme
import com.company.employeetracker.viewmodel.AuthState
import com.company.employeetracker.viewmodel.FirebaseAuthViewModel
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmployeeTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EmployeeTrackerApp()
                }
            }
        }
    }
}

@Composable
fun EmployeeTrackerApp() {

    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val userRepository = remember { FirebaseUserRepository() }

    var currentUser by remember { mutableStateOf<User?>(null) }
    val isAdmin = currentUser?.role == "admin"

    val showBottomBar =
        currentUser != null &&
                currentRoute != "login" &&
                currentRoute != "forgot_password" &&
                !currentRoute.orEmpty().startsWith("chat/") &&
                currentRoute != "notifications" &&
                currentRoute != "select_employee"

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                if (isAdmin) {
                    AdminBottomNavBar(
                        currentRoute = currentRoute ?: "admin_dashboard",
                        onNavigate = { navController.navigate(it) { launchSingleTop = true } }
                    )
                } else {
                    EmployeeBottomNavBar(
                        currentRoute = currentRoute ?: "home",
                        onNavigate = { navController.navigate(it) { launchSingleTop = true } }
                    )
                }
            }
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(paddingValues)
        ) {

            // 🔐 LOGIN
            composable("login") {

                val authViewModel: FirebaseAuthViewModel = viewModel()
                val authState by authViewModel.authState.collectAsState()

                // 🔁 React to Firebase login result
                LaunchedEffect(authState) {
                    if (authState is AuthState.Success) {

                        val firebaseUser = FirebaseAuth.getInstance().currentUser
                        if (firebaseUser == null) return@LaunchedEffect

                        userRepository.fetchUser(firebaseUser.uid) { user ->
                            if (user != null) {
                                currentUser = user

                                navController.navigate(
                                    if (user.role == "admin") "admin_dashboard" else "home"
                                ) {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        }
                    }
                }


                LoginScreen(
                    onLoginSuccess = { /* handled via authState */ },
                    onForgotPasswordClick = {
                        navController.navigate("forgot_password")
                    },
                    viewModel = authViewModel
                )
            }

            composable("forgot_password") {
                ForgotPasswordScreen(
                    onBack = { navController.popBackStack() }
                )
            }

            // 👨‍💼 EMPLOYEE
            composable("home") {
                currentUser?.let {
                    EmployeeHomeScreen(
                        currentUser = it,
                        onNavigateToSelectEmployee = {
                            navController.navigate("select_employee")
                        },
                        onNavigateToNotifications = {
                            navController.navigate("notifications")
                        }
                    )
                }
            }

            composable("tasks") {
                currentUser?.let {
                    EmployeeTasksScreen(
                        currentUser = it,
                        onBackClick = { navController.popBackStack() }
                    )
                }
            }

            composable("reviews") {
                currentUser?.let {
                    EmployeeReviewsScreen(
                        currentUser = it,
                        onBackClick = { navController.popBackStack() }
                    )
                }
            }

            composable("profile") {
                currentUser?.let {
                    EmployeeProfileScreen(
                        currentUser = it,
                        onLogout = {
                            FirebaseAuth.getInstance().signOut()
                            currentUser = null
                            navController.navigate("login") {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    )
                }
            }

            composable("notifications") {
                currentUser?.let {
                    NotificationsScreen(
                        currentUser = it,
                        onBackClick = { navController.popBackStack() },
                        onMessageClick = { userId ->
                            navController.navigate("chat/$userId")
                        }
                    )
                }
            }

            composable("chat/{userId}") {
                currentUser?.let {
                    ChatScreen(
                        currentUser = it,
                        otherUserId = 2,
                        onBackClick = { navController.popBackStack() }
                    )
                }
            }

            composable("select_employee") {
                currentUser?.let {
                    SelectEmployeeScreen(
                        currentUser = it,
                        onBackClick = { navController.popBackStack() },
                        onEmployeeSelected = { userId ->
                            navController.navigate("chat/$userId")
                        }
                    )
                }
            }

            // 🛠 ADMIN
            composable("admin_dashboard") { AdminDashboardScreen() }
            composable("employees") { AdminEmployeesScreen() }
            composable("admin_tasks") { AdminTasksScreen() }
            composable("analytics") { AdminAnalyticsScreen() }

            composable("admin_profile") {
                currentUser?.let {
                    AdminProfileScreen(
                        currentUser = it,
                        onBack = { navController.popBackStack() },
                        onLogout = {
                            FirebaseAuth.getInstance().signOut()
                            currentUser = null
                            navController.navigate("login") {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    )
                }
            }
        }
    }
}
