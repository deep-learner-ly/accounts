import requests
import json
import time

BASE_URL = "http://localhost:8080/api"
HEADERS = {'Content-Type': 'application/json'}
PHONE = f"138{int(time.time())}" # Unique phone
PASSWORD = "password123"

def run_tests():
    print("=== Starting API Tests ===")
    
    # 1. Register/Login
    print(f"\n1. Registering user {PHONE}...")
    # Since we use auto-registration on login-code, let's use that flow or mock login
    # For simplicity, we'll try to login/register with password
    
    try:
        # Register
        res = requests.post(f"{BASE_URL}/auth/register", json={"phone": PHONE, "password": PASSWORD}, headers=HEADERS)
        if res.status_code == 200:
            print("✅ Register success")
            token = res.json()['token']
        else:
            print(f"❌ Register failed: {res.text}")
            return

        HEADERS['Authorization'] = f"Bearer {token}"
        
        # 2. Add Transaction
        print("\n2. Adding transaction...")
        tx = {
            "amount": 50.0,
            "type": "EXPENSE",
            "categoryId": 2,
            "transactionDate": "2026-03-08",
            "description": "Test Lunch"
        }
        res = requests.post(f"{BASE_URL}/transactions", json=tx, headers=HEADERS)
        if res.status_code == 200:
            print("✅ Add transaction success")
        else:
            print(f"❌ Add transaction failed: {res.text}")

        # 3. Export
        print("\n3. Testing Export CSV...")
        res = requests.get(f"{BASE_URL}/export/csv", headers=HEADERS)
        if res.status_code == 200 and "text/csv" in res.headers.get('Content-Type', ''):
            print("✅ Export CSV success")
        else:
            print(f"❌ Export CSV failed: {res.status_code}")

        # 4. Reminder
        print("\n4. Testing Reminder Settings...")
        reminder = {
            "enabled": True,
            "time": "09:00",
            "repeatDays": "1,2,3,4,5"
        }
        res = requests.post(f"{BASE_URL}/reminders", json=reminder, headers=HEADERS)
        if res.status_code == 200:
            print("✅ Save reminder success")
        else:
            print(f"❌ Save reminder failed: {res.text}")
            
        res = requests.get(f"{BASE_URL}/reminders", headers=HEADERS)
        if res.status_code == 200 and res.json().get('enabled') == True:
             print("✅ Get reminder success")
        else:
             print(f"❌ Get reminder failed: {res.text}")

        # 5. Feedback
        print("\n5. Testing Feedback...")
        feedback = {"content": "Great app!", "contact": "test@example.com"}
        res = requests.post(f"{BASE_URL}/feedback", json=feedback, headers=HEADERS)
        if res.status_code == 200:
            print("✅ Submit feedback success")
        else:
            print(f"❌ Submit feedback failed: {res.text}")

        # 6. Update Password
        print("\n6. Testing Update Password...")
        res = requests.post(f"{BASE_URL}/auth/update-password", json={"password": "newpassword123"}, headers=HEADERS)
        if res.status_code == 200:
            print("✅ Update password success")
        else:
            print(f"❌ Update password failed: {res.text}")

    except Exception as e:
        print(f"❌ Test exception: {e}")

    print("\n=== Tests Completed ===")

if __name__ == "__main__":
    # Wait for server to start if running in CI/CD
    time.sleep(2) 
    run_tests()
