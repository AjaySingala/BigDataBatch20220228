class Vehicle:
    def __init__(self, make, year, color, tyres):
        self.make = make
        self.year = year
        self.color = color
        self.tyres = tyres

    def start(self):
        print(f"Starting {self.make}")

    def stop(self):
        print(f"Stoping {self.make}")

class Car(Vehicle):
    # def start(self):
    #     print(f"Starting {self.make} with a key")

    def stop(self):
        print(f"Stoping {self.make} with a key")
        
class BMW(Car):
    def start(self):
        print(f"Starting {self.make} with button-start")

    def stop(self):
        print(f"Stoping {self.make} with button-start")

class Audi(Car):
    def start(self):
        print(f"Starting {self.make} using a mobile app")

    def stop(self):
        print(f"Stoping {self.make} using a mobile app")

class Mazda(Car):
    pass

audi = Audi("Audi", 2021, "Black", 4)
bmw = BMW("BMW", 2022, "Green", 4)

audi.start()
bmw.start()

mazda = Mazda("Mazda", 2020, "Gray", 5)
mazda.start()
