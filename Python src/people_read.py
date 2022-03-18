import json

with open("people.json") as file:
    data = json.load(file)

    print("Type: ", type(data))

    print(data["people"])
    print(data["people"][0])
    print(data["people"][0]["name"])
    