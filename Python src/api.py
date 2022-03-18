import requests

def get(id):
    # GET
    print(f"HTTP GET method testing for id {id}...")
    api_url = "https://jsonplaceholder.typicode.com/todos/" + str(id)

    response = requests.get(api_url)
    print(response.json())
    print(response.status_code)
    print(response.headers["Content-Type"])

def post():
    # POST.
    print("HTTP POST method testing...")
    data = {'userId': 1,'title': 'This was added by Ajay Singala','completed': True }
    api_url = "https://jsonplaceholder.typicode.com/todos"

    response = requests.post(api_url, json=data)
    print(response.json())
    print(response.status_code)

def put(id):
    # PUT.
    print(f"HTTP PUT method testing for id {id}...")
    get(id)

    data = {'userId': 1,'title': 'This was updated by Ajay Singala','completed': True }
    api_url = "https://jsonplaceholder.typicode.com/todos/" + str(id)

    print(f"Updating resource for id {id}...")
    response = requests.put(api_url, json=data)
    print(response.json())
    print(response.status_code)

def delete(id):
    # DELETE.
    print(f"HTTP DELETE method testing for id {id}...")
    get(id)

    api_url = "https://jsonplaceholder.typicode.com/todos/" + str(id)

    print(f"Deleting resource for id {id}...")
    response = requests.delete(api_url)
    print(response.json())
    print(response.status_code)


get(1)
get(5)

post()

put(5)

delete(5)
