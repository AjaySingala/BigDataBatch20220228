# Positional Arguments.
def foo(item, qty = 15, price = 2.99):
    print(f"{qty} numbers orderd for {item} at {price} per unit")
    # amount = qty * price
    # print(amount)

foo(10, "Pens", 3.99)
foo(10, "Pens")
#foo("apples", 12, 3.59)

# Keyword Arguments.
foo(qty = 24, item = "apples", price = 2.59)
foo(item = "apples", price = 2.59, qty = 24)
foo(item = "apples", qty = 24)
