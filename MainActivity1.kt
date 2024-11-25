package com.example.refuel

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "start_page") {
        composable("start_page") { StartPageView(navController) }
        composable("signup_login") { SignupLogin(navController) }
        composable("location_view") { LocationView(navController) }
        composable("signup_details_view") { SignupDetailsView(navController) }
        composable("home_page") { Homepage(navController) }


  
        composable("product_selection/{productType}") { backStackEntry ->
        val productType = backStackEntry.arguments?.getString("productType") ?: ""
            ProductSelectionView(navController, productType)
        }

        // Checkout View
        composable("checkout/{vendor}/{quantity}/{pricePerUnit}") { backStackEntry ->
            val vendor = backStackEntry.arguments?.getString("vendor") ?: ""
            val quantity = backStackEntry.arguments?.getString("quantity")?.toInt() ?: 1
            val pricePerUnit = backStackEntry.arguments?.getString("pricePerUnit")?.toInt() ?: 0
            CheckoutView(navController, vendor, quantity, pricePerUnit)
        }

        // Payment View
        composable("payment/{vendor}/{quantity}/{pricePerUnit}") { backStackEntry ->
            val vendor = backStackEntry.arguments?.getString("vendor") ?: ""
            val quantity = backStackEntry.arguments?.getString("quantity")?.toInt() ?: 1
            val pricePerUnit = backStackEntry.arguments?.getString("pricePerUnit")?.toInt() ?:1
            PaymentView(navController, vendor, quantity, pricePerUnit )
        }

        // Corrected Confirmation View to accept pricePerUnit and quantity
        composable("confirmation/{totalAmount}/{pricePerUnit}/{quantity}/{paymentMethod}") { backStackEntry ->
            val totalAmount = backStackEntry.arguments?.getString("totalAmount")?.toInt() ?: 0
            val pricePerUnit = backStackEntry.arguments?.getString("pricePerUnit")?.toInt() ?: 0
            val quantity = backStackEntry.arguments?.getString("quantity")?.toInt() ?: 1
            val paymentMethod=backStackEntry.arguments?.getString("paymentMethod")?:"COD"
            ConfirmationView(navController, totalAmount, pricePerUnit, quantity, paymentMethod)
        }

    }
}
@Composable
fun StartPageView(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Image(
            painter = painterResource(id = R.drawable.refuell),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
        )

        Spacer(modifier = Modifier.height(70.dp))
        Text(
            text = "Skip the gas station, we bring fuel to you.⛽️",
            fontSize = 20.sp,
            color = Color(0xFFFFA500),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(50.dp))
        Image(
            painter = painterResource(id = R.drawable.re1),
            contentDescription = "Main Illustration",
            modifier = Modifier.size(300.dp)
        )

        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                navController.navigate("signup_login")
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            modifier = Modifier
                .padding(horizontal = 40.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Get Started", color = Color.White, fontSize = 18.sp)
        }
    }
}

@Composable
fun SignupLogin(navController: NavController) {
    var userID by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.re3), 
            contentDescription = "App Logo",
            modifier = Modifier.size(150.dp)
        )

        Text(
            text = "Login",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 20.dp)
        )

        OutlinedTextField(
            value = userID,
            onValueChange = { userID = it },
            label = { Text("Enter your ID") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Enter your Password") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Button(
            onClick = {
                if (userID.equals("dinesh", ignoreCase = true) && password == "123") {
                    loginMessage = "Login Successful!"
                    navController.navigate("location_view")
                } else {
                    loginMessage = "Invalid ID or Password"
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            modifier = Modifier
                .padding(horizontal = 40.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Login", color = Color.White, fontSize = 18.sp)
        }

        Text(
            text = loginMessage,
            color = if (loginMessage == "Login Successful!") Color.Green else Color.Red,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 10.dp)
        )

        Spacer(modifier = Modifier.weight(1f))
        TextButton(
            onClick = { navController.navigate("signup_details_view") },
            modifier = Modifier.padding(bottom = 20.dp)
        ) {
            Text(text = "New User? Sign Up", color = Color.Red, fontSize = 16.sp)
        }
    }
}

@Composable
fun LocationView(navController: NavController) {
    var location by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Enter Your Location",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.re5),
            contentDescription = "Location Image",
            modifier = Modifier
                .size(300.dp)
                .padding(bottom = 20.dp)
        )

        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Enter location") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (location.equals("vizag", ignoreCase = true)) {
                    navController.navigate("home_page")
                } else {
                    Toast.makeText(
                        navController.context,
                        "Location not recognized!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
        ) {
            Text(text = "Submit", color = Color.White)
        }
    }
}

@Composable
fun SignupDetailsView(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.re3),
            contentDescription = "Signup Image",
            modifier = Modifier.size(150.dp)
        )

        Text(
            text = "Signup Details",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Enter your name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Enter your email") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Create a password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                Toast.makeText(navController.context, "User signed up: $name, $email", Toast.LENGTH_SHORT).show()
                navController.navigate("signup_login")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = "Sign Up", color = Color.White, fontSize = 18.sp)
        }
    }
}
@Composable
fun Homepage(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Top Bar with Location and Profile
        TopBar()

        
        OffersSection()

   
        FuelSelectionSection(navController)

   
        FooterSection()
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val customH6Style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.15.sp
        )
        Text(
            text = "Visakhapatnam",
            style = customH6Style,
            fontWeight = FontWeight.Bold
        )

        Image(
            painter = painterResource(id = R.drawable.re3), 
            contentDescription = "Profile Icon",
            modifier = Modifier
                .size(30.dp)
                .clickable { /* Handle profile click */ }
        )
    }
}

@Composable
fun OffersSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.Red)
            .clip(RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Offers and Rewards",
            color = Color.White,
        )
        Text(
            text = "Save up to 10% on your first order",
            color = Color.White,
        )
    }
}

@Composable
fun FuelSelectionSection(navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        val customH6Style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.15.sp
        )

        Text(
            text = "Hello, Bhavana",
            style = customH6Style,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Select Product",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            FuelProductView(name = "Diesel", navController = navController)
            FuelProductView(name = "Petrol", navController = navController)
            FuelProductView(name = "Power Petrol", navController = navController)
        }
    }
}

@Composable
fun FuelProductView(name: String, navController: NavController) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = "$name Icon",
            modifier = Modifier.size(50.dp)
        )
        Text(text = name)
        Button(
            onClick = {
                navController.navigate("product_selection/$name") 
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        ) {
            Text(text = "+ Buy")
        }
    }
}

@Composable
fun FooterSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Refuel Toll-free: 1100010098"
        )
    }
}
@Composable
fun ProductSelectionView(navController: NavController, productType: String) {
    val prices = mapOf(
        "Diesel" to 98,
        "Petrol" to 110,
        "Power Petrol" to 115
    )

    val quantities = remember { mutableStateMapOf<String, Int>("IndianOil" to 1, "Bharat Petroleum" to 1, "HP" to 1) }

    val pricePerUnit = prices[productType] ?: 0

    // Column for displaying product selection
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Display Header
        Text(
            text = "Select Vendor - $productType",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        
        Column(modifier = Modifier.fillMaxWidth()) {
           
            for (vendor in arrayOf("IndianOil", "Bharat Petroleum", "HP")) {
                VendorCard(
                    vendor = vendor,
                    price = pricePerUnit,
                    quantity = quantities[vendor] ?: 1,
                    onQuantityChange = { newQuantity ->
                        quantities[vendor] = newQuantity
                    },
                    onCheckout = {
                        val quantity = quantities[vendor] ?: 1
                        navController.navigate("checkout/$vendor/$quantity/$pricePerUnit")
                    }
                )
            }
        }
    }
}
@Composable
fun CheckoutView(
    navController: NavController,
    vendor: String,
    quantity: Int,
    pricePerUnit: Int
) {
    val totalAmount = quantity * pricePerUnit
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val isFormValid = remember(name.value, email.value) {
        name.value.isNotEmpty() && email.value.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Header
        Text(
            text = "Checkout",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Vendor and Price Details
        Text(text = "Vendor: $vendor", fontWeight = FontWeight.Bold)
        Text(text = "Quantity: $quantity L")
        Text(text = "Price per L: ₹$pricePerUnit")
        Text(text = "Total Amount: ₹$totalAmount", fontWeight = FontWeight.Bold, color = Color.Green)

        // Name Input
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Name:", fontWeight = FontWeight.Bold)
        TextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text("Enter your name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Email Input
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Email:", fontWeight = FontWeight.Bold)
        TextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Enter your email address") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Proceed to Payment Button
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                if (isFormValid) {
                    // Navigate to the Payment View
                    navController.navigate("payment/$vendor/$quantity/$pricePerUnit")
                } else {
                   

                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isFormValid 
        ) {
            Text(text = "Proceed to Payment")
        }
    }
}
@Composable
fun PaymentView(
    navController: NavController,
    vendor: String,
    quantity: Int,
    pricePerUnit: Int
) {
    val totalAmount = quantity * pricePerUnit

    // For payment method selection
    var selectedPaymentMethod by remember { mutableStateOf("COD") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        // Payment Title
        Text("Payment Details", fontWeight = FontWeight.Bold, fontSize = 24.sp)

      
        Spacer(modifier = Modifier.height(16.dp))
        Text("Vendor: $vendor", fontSize = 18.sp)
        Text("Quantity: $quantity L", fontSize = 18.sp)
        Text("Price per L: ₹$pricePerUnit", fontSize = 18.sp)
        Text("Total Amount: ₹$totalAmount", fontSize = 18.sp)

        // Spacer
        Spacer(modifier = Modifier.height(24.dp))

        // Payment Method Selection
        Text("Select Payment Method", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))

        // COD Radio Button
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = selectedPaymentMethod == "COD",
                onClick = { selectedPaymentMethod = "COD" }
            )
            Text("Cash on Delivery", fontSize = 16.sp)
        }

      
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = selectedPaymentMethod == "Card",
                onClick = { selectedPaymentMethod = "Card" }
            )
            Text("Credit/Debit Card", fontSize = 16.sp)
        }

        // UPI Radio Button
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = selectedPaymentMethod == "UPI",
                onClick = { selectedPaymentMethod = "UPI" }
            )
            Text("UPI Payment", fontSize = 16.sp)
        }

        // Spacer
        Spacer(modifier = Modifier.height(20.dp))

        // Additional Information for UPI or Card
        if (selectedPaymentMethod == "Card" || selectedPaymentMethod == "UPI") {
            val paymentDetailsLabel = if (selectedPaymentMethod == "Card") {
                "Card Number"
            } else {
                "UPI ID"
            }

            var paymentDetail by remember { mutableStateOf("") }

            OutlinedTextField(
                value = paymentDetail,
                onValueChange = { paymentDetail = it },
                label = { Text(paymentDetailsLabel) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Confirm Payment Button
        Button(
            onClick = {
                // Pass the payment method to the confirmation screen along with other data
                navController.navigate("confirmation/$totalAmount/$pricePerUnit/$quantity/$selectedPaymentMethod")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Confirm Payment")
        }
    }
}
@Composable
fun ConfirmationView(
    navController: NavController,
    totalAmount: Int,
    pricePerUnit: Int,
    quantity: Int,
    paymentMethod: String
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        // Title
        Text("Order Confirmation", fontWeight = FontWeight.Bold, fontSize = 24.sp)

        // Order Summary
        Spacer(modifier = Modifier.height(16.dp))
        Text("Price: ₹$pricePerUnit", fontSize = 18.sp)
        Text("Quantity: $quantity L", fontSize = 18.sp)
        Text("Total Amount: ₹$totalAmount", fontSize = 18.sp)

        // Payment Method
        Spacer(modifier = Modifier.height(24.dp))
        Text("Payment Method: $paymentMethod", fontSize = 18.sp)

        // Confirmation Message
        Spacer(modifier = Modifier.height(16.dp))
        Text("Thank you for your order!", fontSize = 18.sp)

        // Navigate Back to Product Select (or other flow)

    }
}
@Composable
fun VendorCard(
    vendor: String,
    price: Int,
    quantity: Int,
    onQuantityChange: (Int) -> Unit,
    onCheckout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
            .shadow(4.dp, RoundedCornerShape(8.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = vendor,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

     
        Text(
            text = "Price: ₹$price/L",
            color = Color.Gray
        )

      
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            IconButton(onClick = { if (quantity > 1) onQuantityChange(quantity - 1) }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Decrease Quantity")
            }
            Text(text = "$quantity")
            IconButton(onClick = { onQuantityChange(quantity + 1) }) {
                Icon(imageVector = Icons.Default.Star, contentDescription = "Increase Quantity")
            }
        }

        Button(
            onClick = onCheckout,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Checkout")
        }
    }
}
@Composable
@Preview(showBackground = true)
fun PreviewApp() {
    AppNavigation()
}
