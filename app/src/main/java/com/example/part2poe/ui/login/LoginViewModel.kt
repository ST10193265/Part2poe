
import android.util.Log
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    private var _registeredUsername: String? = null
    private var _registeredPassword: String? = null

    fun setRegisteredUserCredentials(username: String, password: String) {
        _registeredUsername = username.trim()  // Trim whitespaces
        _registeredPassword = password.trim()  // Trim whitespaces

        Log.d("LoginViewModel", "Registered Username Set: $_registeredUsername")
        Log.d("LoginViewModel", "Registered Password Set: $_registeredPassword")
    }

    fun isValidUser(username: String, password: String): Boolean {
        val enteredUsername = username.trim()  // Trim whitespaces
        val enteredPassword = password.trim()  // Trim whitespaces

        Log.d("LoginViewModel", "Entered Username: $enteredUsername")
        Log.d("LoginViewModel", "Entered Password: $enteredPassword")

        val isValid = _registeredUsername == enteredUsername && _registeredPassword == enteredPassword
        Log.d("LoginViewModel", "Validation Result: $isValid")

        return isValid
    }
}