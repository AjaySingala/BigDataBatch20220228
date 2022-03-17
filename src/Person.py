from numpy import isin


class Person:
    def __init__(self, fname, lname):
        self.firstname = fname
        self.lastname = lname

    def printFullname(self):
        print(f"{self.lastname}, {self.firstname}")

class Student(Person):
    firstname = "some value"
    def __init__(self, fname, lname, school, gradYear):
        # self.firstname = fname
        # self.lastname = lname
        #Person.__init__(self, fname, lname)
        super().__init__(fname, lname)
        self.school = school
        self.graduationYear = gradYear

    def displayScore(self):
        print("Display marks scored by student...")

p1 = Person("John", "Doe")
p1.printFullname()

s1 = Student("Mary", "Jane", "Freemont High", 2011)
s1.printFullname()

print(s1.firstname)
print(s1.lastname)
print(s1.school)
print(s1.graduationYear)

s1.displayScore()

print(type(p1))
print(type(s1))

print(isinstance(p1, Person))
print(isinstance(s1, Person))
