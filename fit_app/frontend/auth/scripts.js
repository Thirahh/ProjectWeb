document.addEventListener("DOMContentLoaded", function () {
  const signUpButton = document.getElementById("signUp");
  const signInButton = document.getElementById("signIn");
  const container = document.getElementById("container");

  signUpButton.addEventListener("click", () => {
    container.classList.add("right-panel-active");
  });

  signInButton.addEventListener("click", () => {
    container.classList.remove("right-panel-active");
  });

  // Base URL for API endpoints
  const API_BASE_URL = "http://localhost:8080/api/users";

  // Get the message element
  const messageElement = document.getElementById("message");

  // Function to show messages
  function showMessage(text, isSuccess) {
    messageElement.textContent = text;
    messageElement.classList.remove("hidden", "success", "error");
    messageElement.classList.add(isSuccess ? "success" : "error");

    // Hide the message after 5 seconds
    setTimeout(() => {
      messageElement.classList.add("hidden");
    }, 5000);
  }

  // Function to handle login
  async function handleLogin(event) {
    event.preventDefault();

    const email = document.querySelector(
      '#loginForm input[name="email"]'
    ).value;
    const password = document.querySelector(
      '#loginForm input[name="password"]'
    ).value;

    try {
      const response = await fetch(`${API_BASE_URL}/login`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ email, password }),
      });

      const data = await response.json();

      if (data.success) {
        // Save the token in localStorage
        localStorage.setItem("authToken", data.token);
        localStorage.setItem("userId", data.userId);
        localStorage.setItem("userEmail", data.email);
        localStorage.setItem("userName", data.name);

        showMessage("Login successful! Redirecting...", true);

        // Redirect to dashboard or home page after successful login
        setTimeout(() => {
          window.location.href = "../dashboard/index.html";
        }, 2000);
      } else {
        showMessage(
          data.message || "Login failed. Please check your credentials.",
          false
        );
      }
    } catch (error) {
      console.error("Error during login:", error);
      showMessage("An error occurred during login. Please try again.", false);
    }
  }

  // Function to handle registration
  async function handleRegister(event) {
    event.preventDefault();

    const form = document.getElementById("registerForm");
    const formData = new FormData(form);

    // Create registration data object
    const registerData = {
      name: formData.get("name"),
      email: formData.get("email"),
      password: formData.get("password"),
      gender: formData.get("gender"),
      dateOfBirth: formData.get("dateOfBirth"),
      heightCm: parseFloat(formData.get("heightCm")),
      weightKg: parseFloat(formData.get("weightKg")),
    };

    try {
      const response = await fetch(`${API_BASE_URL}/register`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(registerData),
      });

      const data = await response.json();

      if (data.success) {
        // Save the token in localStorage
        localStorage.setItem("authToken", data.token);
        localStorage.setItem("userId", data.userId);
        localStorage.setItem("userEmail", data.email);
        localStorage.setItem("userName", data.name);

        showMessage("Registration successful! Redirecting...", true);

        // Redirect to dashboard after successful registration
        setTimeout(() => {
          window.location.href = "../dashboard/index.html";
        }, 2000);
      } else {
        showMessage(
          data.message || "Registration failed. Please try again.",
          false
        );
      }
    } catch (error) {
      console.error("Error during registration:", error);
      showMessage(
        "An error occurred during registration. Please try again.",
        false
      );
    }
  }

  // Add event listeners to forms
  document.getElementById("loginForm").addEventListener("submit", handleLogin);
  document
    .getElementById("registerForm")
    .addEventListener("submit", handleRegister);

  // Check if user is already logged in
  const token = localStorage.getItem("authToken");
  if (token) {
    // Redirect to dashboard if already logged in
    window.location.href = "../dashboard/index.html";
  }
});
