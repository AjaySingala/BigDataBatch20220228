dict1 = dict()
print(dict1)
print(type(dict1))

dict1["one"] = 1
dict1["two"] = 2

print(dict1)

dict1["Three"] = "Tri"
dict1["Four"] = "For"
print(dict1)

print(dict1["Three"])
four = dict1.get('Four')
print(four)

# Edit value in a dictionary.
dict1["one"] = "One"
print(dict1)
print(dict1["one"])

dict1["one"] = "first"
print(dict1)

print("List all values:")
print(dict1.values())
print(dict1.keys())

# Check if a value exists in a dictionary.
vals = dict1.values()
keys = dict1.keys()

print("Tri" in vals)
print("Three" in keys)

# # Delete a specific key-value item.
# print(dict1.pop("Four"))
# print(dict1)

del dict1["Three"]
print(dict1)

# # Delete an arbitrary key-value pair.
# print(dict1.popitem())

print(dict1.items())

for item in dict1.items():
    print(item)

print(list(sorted(dict1.keys())))

# # dictionary with List as value.
# customers = dict()
# customers[101] = ["John", "Smith", 32, "Dallas", "TX", "jsmith@gmail.com"]
# customers[102] = ["Mary", "Jane", 23, "Reston", "VA", "mjane@gmail.com"]

# print(customers)
# print(customers[101])
# print(customers[101][3])

