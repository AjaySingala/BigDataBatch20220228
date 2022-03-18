class Dog:
    color = "brown"
    sound = "woof!"

    def __init__(self, name, age):
        print("ctor of Dog...")
        self.name = name
        self.age = age

    def description(self):
        return f"{self.name} is {self.color} and is {self.age} years old."

    def speak(self):
        return f"{self.name} says {self.sound}"

max = Dog("Max", 4)
buddy = Dog("Buddy", 5)

print(max)
print(max.name)
print(max.age)

# print(f"{max.name} is {max.color} and is {max.age} years old.")
# print(f"{buddy.name} is {buddy.color} and is {buddy.age} years old.")
max.color = "white"
# print(f"{max.name} is {max.color} and is {max.age} years old.")

# Polymorphism.
print(max.description())
print(buddy.description())

print(max.speak())
buddy.sound = "bow-wow!"
print(buddy.speak())

print(max)
print(buddy)
