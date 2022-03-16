print((lambda x: x + 1)(10))

add_one = lambda x: x + 1
print(add_one(20))

answer = add_one(100)
print(answer)

fullname = lambda firstname, lastname: f'Full name is: {lastname}, {firstname}'
print(fullname("John", "Smith"))

add_two_numbers = lambda x, y: x + y
print(add_two_numbers(10,20))
print(add_two_numbers(110,120))

res = (lambda x, y: x + y)(10,20)
print(res)

# named params (keyword arguments)
print((lambda x,y,z: x + y + z)(1,2,3))
print((lambda x,y,z=5: x + y + z)(1,2))
print((lambda x,y,z=5: x + y + z)(1, y=10))

print(fullname(firstname="Mary", lastname="Jane"))

summ_of_nums = lambda *numbers: sum(numbers)
print(summ_of_nums(1,2,3))
print(summ_of_nums(1,2,3,4,5,6))
