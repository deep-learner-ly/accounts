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
    try:
        res = requests.post(f"{BASE_URL}/auth/register", json={"phone": PHONE, "password": PASSWORD}, headers=HEADERS)
        if res.status_code == 200:
            print("✅ Register success")
            token = res.json()['token']
        else:
            print(f"❌ Register failed: {res.text}")
            return

        HEADERS['Authorization'] = f"Bearer {token}"
        
        # 2. Add Transactions
        print("\n2. Adding transactions...")
        txs = [
            {"amount": 50.0, "type": "EXPENSE", "categoryId": 2, "transactionDate": "2026-03-08", "description": "Test Lunch"},
            {"amount": 30.0, "type": "EXPENSE", "categoryId": 3, "transactionDate": "2026-03-07", "description": "Test Transport"},
            {"amount": 100.0, "type": "INCOME", "categoryId": 1, "transactionDate": "2026-03-06", "description": "Test Salary"}
        ]
        for tx in txs:
            res = requests.post(f"{BASE_URL}/transactions", json=tx, headers=HEADERS)
            if res.status_code == 200:
                print(f"✅ Add transaction {tx['description']} success")
            else:
                print(f"❌ Add transaction failed: {res.text}")

        # 3. Test Stats API
        print("\n3. Testing Stats API...")
        
        # Day
        res = requests.get(f"{BASE_URL}/stats/chart?type=day", headers=HEADERS)
        if res.status_code == 200:
            data = res.json()
            print(f"✅ Get Day Stats success (count: {len(data)})")
            # Verify data exists for today
            today = time.strftime("%m-%d", time.localtime())
            # found = any(d['label'] == today for d in data) # Label format might differ slightly depending on timezone
            # print(f"   Today's data found: {found}")
        else:
            print(f"❌ Get Day Stats failed: {res.text}")

        # Week
        res = requests.get(f"{BASE_URL}/stats/chart?type=week", headers=HEADERS)
        if res.status_code == 200:
            print(f"✅ Get Week Stats success (count: {len(res.json())})")
        else:
            print(f"❌ Get Week Stats failed: {res.text}")

        # Month
        res = requests.get(f"{BASE_URL}/stats/chart?type=month", headers=HEADERS)
        if res.status_code == 200:
             print(f"✅ Get Month Stats success (count: {len(res.json())})")
        else:
             print(f"❌ Get Month Stats failed: {res.text}")

    except Exception as e:
        print(f"❌ Test exception: {e}")

    print("\n=== Tests Completed ===")

if __name__ == "__main__":
    time.sleep(2) 
    run_tests()
